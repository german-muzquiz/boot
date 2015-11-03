package com.gmr.boot.rest;

public abstract class Constants {

    private Constants() { /* no instances allowed */ }

    public static final String API_VERSION = "1.0";

    public static final String API_RESOURCE_ID = "api";

    public static final String API_PREFIX = "/" + API_RESOURCE_ID + "/" + API_VERSION;

}
