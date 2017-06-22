/**
 * Created by maraujo on 3/27/17.
 */
"use strict";
import {deserialize, autoserialize, autoserializeAs} from "cerialize/dist/serialize";
import {DateUtils} from "../date/date-utils";

export class LocationModel {

    @autoserializeAs("geonameId")
    public geoname_id: number;

    @autoserialize
    public name: string;

    @autoserializeAs("asciiName")
    public ascii_name: string;

    @autoserializeAs("alternateNames")
    public alternate_names: string;

    @autoserialize
    public latitude: number;

    @autoserialize
    public longitude: number;

    @autoserializeAs("featureClass")
    public feature_class: string;

    @autoserializeAs("featureCode")
    public feature_code: string;

    @autoserializeAs("countryCode")
    public country_code: string;

    @autoserialize
    public cc2: string;

    @autoserializeAs("admin1Code")
    public admin1_code: string;

    @autoserializeAs("admin2Code")
    public admin2_code: string;

    @autoserializeAs("admin3Code")
    public admin3_code: string;

    @autoserializeAs("admin4Code")
    public admin4_code: string;

    @autoserialize
    public population: number;

    @autoserialize
    public elevation: number;

    @autoserialize
    public gtopo30: number;

    @autoserialize
    public timezone: string;


    public modification_date: string;

    /*public static OnSerialized(instance : LocationModel, json : any) : void {
        json.modification_date = DateUtils.dateOnlyTextFromDate(instance.modification_date, DateUtils.dateAndTimeFormat);
    }

    public static OnDeserialized(instance : LocationModel, json : any) : void {
        instance.modification_date = DateUtils.dateOnlyFromDateText(json.modification_date, DateUtils.dateAndTimeFormat);
    }*/
}