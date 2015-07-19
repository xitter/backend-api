package service;

import service.RestService;

/**
 * Created by vijay on 18/7/15.
 */
public class ZomatoService {

    public static final String ZOMATO_API_KEY = "7749b19667964b87a3efc739e254ada2";
    public static final String ZOMATO_API_PREFIX = "https://api.zomato.com/v1/";
    public static final String ZOMATO_API_AUTH_HEADER = "X-Zomato-API-Key";

    public static Object getLocality(String lat, String lon) throws Exception {
        return RestService.sendGet(ZOMATO_API_PREFIX + "geocode.json?lat=" + lat + "&lon=" + lon, ZOMATO_API_AUTH_HEADER, ZOMATO_API_KEY);
    }

    public static void getSuggestions(String lat, String lon) throws Exception {
    //    return RestService.sendGet(ZOMATO_API_PREFIX + "geocode.json?lat=" + lat + "&lon=" + lon, ZOMATO_API_AUTH_HEADER, ZOMATO_API_KEY);
    }
}

