/**
 * Created by maraujo on 3/16/17.
 */
/// <reference path="../_all.d.ts" />
"use strict";
import {Request, Response, NextFunction} from "express";
import {Service, Inject, Require} from "typedi";
import {Controller, ResultModel, ResultMessageModel} from "../commons/index";
import {CountryService} from "./country.service";
import {CountryModel} from "./country.model";

@Service()
export class CountryController extends Controller<CountryModel> {

    @Inject()
    private _countryService: CountryService;

    public index(req: Request, res: Response, next: NextFunction) {
        this.processOutcomeTyped(this._countryService.findById(req.params.id), res);
    }

    public getAll(req: Request, res: Response, next: NextFunction) {
        this.processOutcomeTyped(this._countryService.getAll(), res);
    }

}