package com.gmr.boot.controllers;


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.gmr.boot.domain.CredentialsUserProfile;
import com.gmr.boot.rest.Constants;
import com.gmr.boot.test.AbstractIntegrationTest;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;


import static com.jayway.restassured.RestAssured.*;


@DatabaseSetup("classpath:testdata/auth.xml")
public class ProfileControllerTest extends AbstractIntegrationTest {

    @Before
    public void setUp() {
        RestAssured.port = getPort();
    }


    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "classpath:testdata/create_user_result.xml")
    public void register() {

        CredentialsUserProfile profile = new CredentialsUserProfile("testUser", "testUser@test.com", "testPassword".toCharArray());

        // Register
        given()
                .header("Content-Type", "application/json")
                .body(profile)
                .post(Constants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(200);

        // Authenticate with the new user
        given()
                .header("Authorization", "Basic Ym9vdF93ZWJhcHA6NTlkMTRmMDEtMzhkYS00MDFjLTgwMTQtYjZjMDM1NjI3MWM4")
                .formParam("grant_type", "password")
                .formParam("username", "testUser")
                .formParam("password", "testPassword")
                .post("/oauth/token")
                .then()
                .statusCode(200);
    }


    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "classpath:testdata/create_user_failed.xml")
    public void registerExistentUserAndFail() {
        CredentialsUserProfile profile = new CredentialsUserProfile("admin", "testUser@test.com", "otherPassword".toCharArray());

        given()
                .header("Content-Type", "application/json")
                .body(profile)
                .post(Constants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(400);
    }


    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "classpath:testdata/create_user_failed.xml")
    public void registerWithoutUsernameAndFail() {
        CredentialsUserProfile profile = new CredentialsUserProfile(null, "test@test.com", "otherPassword".toCharArray());

        // Create user
        given()
                .header("Content-Type", "application/json")
                .body(profile)
                .post(Constants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(400);
    }


    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "classpath:testdata/create_user_failed.xml")
    public void registerWithoutEmailAndFail() {
        CredentialsUserProfile profile = new CredentialsUserProfile("testUser", null, "otherPassword".toCharArray());

        // Create user
        given()
                .header("Content-Type", "application/json")
                .body(profile)
                .post(Constants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(400);
    }


    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "classpath:testdata/create_user_failed.xml")
    public void registerWrongEmailFormatAndFail() {
        CredentialsUserProfile profile = new CredentialsUserProfile("testUser", "email", "otherPassword".toCharArray());

        // Create user
        given()
                .header("Content-Type", "application/json")
                .body(profile)
                .post(Constants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(400);
    }


    @Test
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED, value = "classpath:testdata/create_user_failed.xml")
    public void registerExistentEmailAndFail() {
        CredentialsUserProfile profile = new CredentialsUserProfile("testUser", "admin@admin.com", "otherPassword".toCharArray());

        // Create user
        given()
                .header("Content-Type", "application/json")
                .body(profile)
                .post(Constants.API_PREFIX + "/profile")
                .then()
                .log()
                .all()
                .statusCode(400);
    }

}
