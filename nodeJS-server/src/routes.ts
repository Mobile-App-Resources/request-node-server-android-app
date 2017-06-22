/**
 * Created by mateus on 12/1/16.
 */
import {Request, Response, NextFunction, Router, RequestHandler, Application} from 'express';
import * as multiparty from 'multiparty';

import {CountryRoutes} from "./country/country.route";
import {ResourcesRoutes} from "./resources/resource.route";

export interface RouterConfig {
    path: string;
    type?: RouterType;
    callback?: RequestHandler;
    children?: Array<RouterConfig>;
    restrict?: boolean;
    isMultipart?: boolean;
    checkRestriction?: Array<RequestHandler>;

}
export const enum RouterType {
    GET,
    POST,
    PUT,
    DELETE
}

export const routes: Array<RouterConfig> = [


    // country route
    /////////////////
    ...CountryRoutes,

    // resource route
    /////////////////
    ...ResourcesRoutes,
];

function buildRouters(router: Router, routersConfig: Array<RouterConfig>, parentPath?: string): Router {

    for (let routerConf of routersConfig) {

        let path = parentPath ? parentPath + routerConf.path : `/${routerConf.path}`;
        let handlers = [];
        if(routerConf.restrict != false) {
            handlers = routerConf.checkRestriction && routerConf.checkRestriction.length > 0 ? routerConf.checkRestriction : [checkExistAndValidToken]
        }

        handlers.push(routerConf.callback);

        switch (routerConf.type) {
            
            case RouterType.POST:
                if(routerConf.isMultipart) {
                    handlers.splice((routerConf.restrict ? 1 : 0), 0, makeMultipartFormData);
                }

                router.post(path, handlers);
                break;
            
            case RouterType.PUT:
                if(routerConf.isMultipart) {
                    handlers.splice((routerConf.restrict ? 1 : 0), 0, makeMultipartFormData);
                }
                router.put(path, handlers);
                break;
            
            case RouterType.DELETE:
                router.delete(path, handlers);
                break;
            
            default:
                router.get(path, handlers);
                break;
        }

        if(routerConf.children && routerConf.children.length > 0) {
            buildRouters(router, routerConf.children, `${path}/`)
        }
    }

    return router;
}


export function checkExistAndValidToken(req: Request, res: Response, next: NextFunction) {
    
    /**
     * Take the token from:
     *
     *  - the POST value access_token
     *  - the GET parameter access_token
     *  - the tr-access-token header
     *    ...in that order.
     */

}

export function makeMultipartFormData(req: Request, res: Response, next: NextFunction) {
    let form = new multiparty.Form();
    // Errors may be emitted
    // Note that if you are listening to 'part' events, the same error may be
    // emitted from the `form` and the `part`.
    form.on('error', function(err) {
        console.log('Error parsing form: ' + err.stack);
    });

    form.parse(req, (err, fields, files) => {
        req['fields'] = fields;
        if(files) {
            let arrayFiles = [];
            for (let keys in files) {
                if(files[keys]) {
                    arrayFiles.push(...files[keys]);
                }
            }
            req['files'] = arrayFiles;//files.constructor === Array ? files : [files];
        } else {
            req['files'] = [];
        }
        return next();
    });

}

export function bootstrap(app: Application): void {
    let router = buildRouters(Router(), routes);
    let application: any = app;
    application.use('/api/', router);
}