/// <reference path="_all.d.ts" />
"use strict";

require("es6-shim");
require("reflect-metadata");
import * as bodyParser from "body-parser";
import * as express from "express";
import * as expressUserAgent from "express-useragent";
import * as favicon from "serve-favicon";
import * as logger from "morgan";
import * as cookieParser from "cookie-parser";
import { Application, Router, Request, Response, NextFunction} from "express";
import * as path from "path";
import {Container} from "typedi";

import * as routes from './routes';
import {Configuration} from "./config";

/**
 * The server.
 *
 * @class Server
 */
class Server {

    public app: Application;

    /**
     * Bootstrap the application.
     *
     * @class Server
     * @method bootstrap
     * @static
     * @return {ng.auto.IInjectorService} Returns the newly created injector for this app.
     */
    public static bootstrap(): Server {
        return new Server();
    }

    /**
     * Constructor.
     *
     * @class Server
     * @constructor
     */
    constructor() {
        //create expressjs application
        //////////////////////////////
        this.app = express();

        //configure application
        ///////////////////////
        this.config();

        //configure routes
        //////////////////
        this.routes();
    }

    /**
     * Configure application
     *
     * @class Server
     * @method config
     * @return void
     */
    private config() {
        //configure jade
        let application: any = this.app; 
        application.set("views", path.join(__dirname, "views"));
        application.set("view engine", "jade");

        //mount logger
        //////////////
        application.use(logger('dev'));
        application.use(cookieParser());
        application.use(expressUserAgent.express());

        //mount json form parser
        ////////////////////////
        application.use(bodyParser.json());

        //mount query string parser
        ///////////////////////////
        /*application.use(bodyParser.urlencoded({ extended: true }));*/
        application.use(bodyParser.urlencoded({ extended: false }));

        //add static paths
        //////////////////
        application.use(express.static(__dirname + '/public'));
        application.use(express.static(__dirname + '/bower_components'));
        //app.use(favicon(path.join(__dirname, "public", "favicon.ico")));

        //uploadFiles file
        ////////////////////
        process.env.TMPDIR = Configuration.TEMPDIR;

        // catch 404 and forward to error handler
        ///////////////////////////////////////////
        application.use(function(err: any, req: Request, res: Response, next: NextFunction) {
            let error: any;
            error = new Error("Not Found");
            error.status = 404;
            next(error);
        });

        // error handler
        //////////////////
        application.use(function(err: any, req: Request, res: Response, next: NextFunction) {
            // set locals, only providing error in development
            res.locals.message = err.message;
            let reqApp: any = req.app;
            res.locals.error = reqApp.get("env") === "development" ? err : {};

            // render the error page
            res.status(err.status || 500);
            res.render("error");
        });
        
        
        
        // Allow Cross Domain
        ///////////////////
        application.use(this.allowCrossDomain.bind(this));

    }


    //CORS middleware
    private allowCrossDomain(req: Request, res: Response, next: NextFunction) {

        // Website you wish to allow to connect
        res.setHeader('Access-Control-Allow-Origin', 'http://localhost:3010');

        // Request methods you wish to allow
        res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

        // Request headers you wish to allow
        res.setHeader('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, UTCOffset, tr-access-token, Cookies');

        // Set to true if you need the website to include cookies in the requests sent
        // to the API (e.g. in case you use sessions)
        res.setHeader('Access-Control-Allow-Credentials', `${true}`);

        // Pass to next layer of middleware
        next();

    }

    /**
     * Configure routes
     *
     * @class Server
     * @method routes
     * @return void
     */
    private routes() {

        //build routes
        ////////////
        routes.bootstrap(this.app);
        
    }
}

export = Server.bootstrap().app;