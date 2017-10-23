package uk.ac.aston.gardnersdiary.services.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Denver on 22/10/2017.
 */
public class UniRestClientTest {

    private Fixture fixture;

    @Before
    public void setup() {
        fixture = new Fixture();
    }

    @Test
    public void get() {
        fixture.givenUniRestClientIsSetup();
        HttpResponse getResponse = fixture.whenGetIsCalled();
        fixture.thenValidGetResponseIsReturned(getResponse);
    }

    private class Fixture {
        private static final String HTTP_HTTPBIN_URL = "http://httpbin.org";
        private UniRestClient uniRestClient;

        private void givenUniRestClientIsSetup() {
            uniRestClient = new UniRestClient();
        }

        private HttpResponse whenGetIsCalled() {
            return ((HttpResponse<JsonNode>) uniRestClient.get(HTTP_HTTPBIN_URL + "/get"));
        }

        private void thenValidGetResponseIsReturned(HttpResponse<JsonNode> getResponse) {
            assertEquals(200, getResponse.getStatus());
            assertEquals("OK", getResponse.getStatusText());
            assertGetResponseJsonElements(getResponse);
        }

        private void assertGetResponseJsonElements(HttpResponse<JsonNode> getResponse) {
            JSONObject responseBody = getResponse.getBody().getObject();
            String argsElement = ((JSONObject) responseBody.get("args")).toString();
            String urlElement = responseBody.get("url").toString();
            assertEquals("{}", argsElement);
            assertEquals("http://httpbin.org/get", urlElement);
        }
    }

}