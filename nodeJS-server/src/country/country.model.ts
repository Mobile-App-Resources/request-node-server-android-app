/**
 * Created by maraujo on 3/16/17.
 */
/// <reference path="../_all.d.ts" />
"use strict";
import {deserialize, autoserialize, autoserializeAs, inheritSerialization} from "cerialize/dist/serialize";
import {LocationModel} from "../commons/index";

@inheritSerialization(LocationModel)
export class CountryModel extends LocationModel {

    @autoserialize
    id: number;
}