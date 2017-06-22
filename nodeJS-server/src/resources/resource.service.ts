/**
 * Created by maraujo on 2/28/17.
 */

import * as fs from "fs";
import {Request} from "express";
import {Service, Inject} from "typedi";
import {Outcome, IService} from "../commons/index";
import {Configuration} from "../config";
import {CountryModel} from "../country/country.model";

@Service()
export class ResourceService implements IService<any> {

    create(resource: any) {
    }

    update(resource: any) {
    }

    deleteById(id: number) {
    }

    findById(id: number) {
    }

    async uploadFiles(req: Request): Promise<Outcome<CountryModel>> {

        let country = new CountryModel();

        let texts: any = req["fields"];

        for (let textKey in texts) {
            let message = `field => ${textKey}, value => `;
            if(texts[textKey].length > 0) {
                for (let value of texts[textKey]) {
                    message += value;
                }
            }
            console.log(message);
        }

        let files: any = req["files"];

        for (let file of files) {
            let tempPath = file.path;
            let targetPath = Configuration.UPLOAD_DIR + file.originalFilename;

            fs.rename(tempPath, targetPath, (error) => {
                if(error) {
                    console.error(error);
                }
            });
        }

        return Outcome.ResultSuccess(country);

    }

}