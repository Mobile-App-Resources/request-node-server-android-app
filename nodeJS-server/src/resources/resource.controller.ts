/**
 * Created by maraujo on 2/28/17.
 */
/// <reference path="../_all.d.ts" />
"use strict";
import {Request, Response, NextFunction} from "express";
import {Service, Inject, Require} from "typedi";
import {Controller, ResultModel, ResultMessageModel} from "../commons/index";
import {ResourceService} from "./resource.service";

@Service()
export class ResourceController extends Controller<any> {

    @Inject()
    private _resourceService: ResourceService;

    public index(req: Request, res: Response, next: NextFunction) {
        let resourcePath = req.query.resourcePath;
        this.processFile(res, `./public/images/${resourcePath}.png`);
    }

    public upload(req: Request, res: Response, next: NextFunction) {
           this.processOutcomeTyped(this._resourceService.uploadFiles(req), res);
    }

}