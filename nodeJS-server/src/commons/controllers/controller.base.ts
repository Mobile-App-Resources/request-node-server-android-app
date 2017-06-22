/**
 * Created by maraujo on 4/7/17.
 */
"use strict";
import * as fs from "fs";
import {Request, Response, NextFunction} from "express";
import {Outcome} from "../db/outcome";
import {ResultModel} from "../response/Result.model";
import {ResultMessageModel} from "../response/result-message.model";


export class Controller<T> {

    processTyped(result: Promise<T>, res: Response) {
        this.process(result, res);
    }

    process(process: Promise<any>, res: Response) {
        process.then(result => {

            res.json(ResultModel.success(result));

        }).catch(function (error) {

            let errorMessage: string = error.message;
            res.json(ResultMessageModel.errorMessage(errorMessage));

        });
    }

    processMessage(process: Promise<{message: string, status: boolean}>, res: Response) {
        process.then(result => {

            res.json(ResultMessageModel.message(result.message, result.status));

        }).catch(function (error) {

            let errorMessage: string = error.message;
            res.json(ResultMessageModel.errorMessage(errorMessage));

        });
    }

    processErrorMessage(process: {message: string, status: boolean}, res: Response) {
        res.json(ResultMessageModel.message(process.message, process.status));
    }

    processOutcomeTyped(outcome: Promise<Outcome<T>>, res: Response) {
           this.processOutcome(outcome, res);
    }

    processOutcome<E>(outcome: Promise<Outcome<E>>, res: Response) {
           outcome.then((outcome:Outcome<E>) => {

               res.json(ResultModel.successOutcome(outcome));

           }).catch(function (error) {

               let errorMessage: string = error.message;
               res.json(ResultMessageModel.errorMessage(errorMessage));

           });
    }

    processFile(res: Response, filePath: string, contentType?: string, type?: string) {
        let file = fs.readFileSync(filePath);
        res.writeHead(200, {'Content-Type': contentType || 'image/png' });
        res.end(file, type || 'binary');
    }

}