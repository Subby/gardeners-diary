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
}
