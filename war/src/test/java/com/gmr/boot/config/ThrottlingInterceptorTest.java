package com.gmr.boot.config;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.gmr.boot.rest.RestConstants;
import com.gmr.boot.test.AbstractIntegrationTest;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;


@DatabaseSetup("classpath:testdata/auth.xml")
public class ThrottlingInterceptorTest extends AbstractIntegrationTest {

    @Before
    public void setUp() {
        RestAssured.port = getPort();
    }


    @Test
    public void profileRequestsThrottlingExceeded() {
        String accessToken = authenticate();

        for (int i = 0; i < 5; i++) {
            given()
                    .header("Authorization", "Bearer " + accessToken)
                    .get(RestConstants.API_PREFIX + "/profile")
                    .then()
                    .log()
                    .all();
        }
    }

}
