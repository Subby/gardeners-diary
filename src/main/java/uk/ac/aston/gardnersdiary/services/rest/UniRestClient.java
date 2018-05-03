package uk.ac.aston.gardnersdiary.services.rest;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denver on 16/10/2017.
 */
public class UniRestClient implements RestClient {

    public UniRestClient() {

    }

    @Override
    public Object get(String url) {
        try {
            return Unirest.get(url).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object postWithAPIKey(String url, String APIKey, Map<String, Object> fields) {
        try {
            return Unirest.post(url)
                    .basicAuth("api", APIKey)
                    .queryString(fields)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }


}
