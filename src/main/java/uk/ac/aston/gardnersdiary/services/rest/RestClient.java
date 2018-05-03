package uk.ac.aston.gardnersdiary.services.rest;

import java.util.Map;

/**
 * Created by Denver on 16/10/2017.
 */
public interface RestClient {

    /**
     * Issues a GET request to the given URL.
     * @param url the url to send the request to
     * @return the data returned from the GET request
     */
    Object get(String url);

    /**
     * Sends a POST request to the given URL.
     * @param url the url to send the request to
     * @param APIKey API key to use with the request
     * @param fields the fiels to send for the request
     * @return the data returned from the POST request
     */
    Object postWithAPIKey(String url, String APIKey, Map<String, Object> fields);
}
