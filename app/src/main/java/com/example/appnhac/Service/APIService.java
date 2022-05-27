package com.example.appnhac.Service;

public class APIService {
    private static String base_url = "https://mp3androidvn.000webhostapp.com/Server/";

    public static DataService getService() {
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
