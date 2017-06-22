/**
 * Created by maraujo on 2/28/17.
 */
/// <reference path="../_all.d.ts" />
"use strict";
import {ResourceController} from "./resource.controller";
import {RouterConfig, RouterType} from "../routes";
import {Container} from "typedi";


let resource = Container.get(ResourceController);

export const ResourcesRoutes: RouterConfig[] = [
    {
        path: 'resource',
        callback: resource.index.bind(resource),
        restrict: false
    },
    {
        path: 'resource',
        callback: resource.upload.bind(resource),
        type: RouterType.POST,
        restrict: false,
        isMultipart: true
    }
];