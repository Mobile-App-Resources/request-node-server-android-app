/**
 * Created by maraujo on 3/16/17.
 */
/// <reference path="../_all.d.ts" />
"use strict";
import {CountryController} from "./country.controller";
import {RouterConfig, RouterType} from "../routes";
import {Container} from "typedi";


let country = Container.get(CountryController);

export const CountryRoutes: RouterConfig[] = [
    {
        path: 'country/all',
        callback: country.getAll.bind(country),
        type: RouterType.GET,
        restrict: false
    }, {
        path: 'country/:id',
        callback: country.index.bind(country),
        type: RouterType.GET,
        restrict: false
    },
];