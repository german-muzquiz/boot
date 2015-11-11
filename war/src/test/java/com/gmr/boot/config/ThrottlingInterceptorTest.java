package com.gmr.boot.config;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.gmr.boot.domain.CredentialsUserProfile;
import com.gmr.boot.rest.RestConstants;
import com.gmr.boot.test.AbstractIntegrationTest;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.TestPropertySource;

import static com.jayway.restassured.RestAssured.given;


@TestPropertySource(locations="classpath:throttling.properties")
@DatabaseSetup("classpath:testdata/auth.xml")
public class ThrottlingInterceptorTest extends AbstractIntegrationTest {

    @Before
    public void setUp() {
        RestAssured.port = getPort();
    }


    @Test
    public void throttlingProtectionAuthenticatedUser() {
        String accessToken = authenticate();

        given()
                .header("Authorization", "Bearer " + accessToken)
                .get(RestConstants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .get(RestConstants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(403);
    }


    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "classpath:testdata/create_user_result.xml")
    public void throttlingProtectionRegister() {
        CredentialsUserProfile profile = new CredentialsUserProfile("testUser", "testUser@test.com", "testPassword".toCharArray());

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic Ym9vdF93ZWJhcHA6NTlkMTRmMDEtMzhkYS00MDFjLTgwMTQtYjZjMDM1NjI3MWM4")
                .body(profile)
                .post(RestConstants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(200);

        profile = new CredentialsUserProfile("testUser2", "testUser2@test.com", "testPassword".toCharArray());

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic Ym9vdF93ZWJhcHA6NTlkMTRmMDEtMzhkYS00MDFjLTgwMTQtYjZjMDM1NjI3MWM4")
                .body(profile)
                .post(RestConstants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(403);
    }

}
