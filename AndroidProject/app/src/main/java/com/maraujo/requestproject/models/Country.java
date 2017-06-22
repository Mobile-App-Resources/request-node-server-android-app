package com.maraujo.requestproject.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by maraujo on 6/19/17.
 */

public class Country {

    @SerializedName("id")
    private Integer id;

    @SerializedName("geonameId")
    private Long geonameId;

    @SerializedName("name")
    private String name;

    @SerializedName("asciiName")
    private String asciiName;

    @SerializedName("alternateNames")
    private String alternateNames;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("featureClass")
    private String featureClass;

    @SerializedName("featureCode")
    private String featureCode;

    @SerializedName("countryCode")
    private String countryCode;

    @SerializedName("cc2")
    private String cc2;

    @SerializedName("admin1Code")
    private String admin1Code;

    @SerializedName("admin2Code")
    private String admin2Code;

    @SerializedName("admin3Code")
    private String admin3Code;

    @SerializedName("admin4Code")
    private String admin4Code;

    @SerializedName("population")
    private Long population;

    @SerializedName("elevation")
    private Integer elevation;

    @SerializedName("gtopo30")
    private Integer gtopo30;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("modification_date")
    private Date modificationDate;
}
