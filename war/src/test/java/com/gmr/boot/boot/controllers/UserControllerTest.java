package com.gmr.boot.boot.controllers;

import com.gmr.boot.Application;
import com.gmr.boot.rest.Constants;
import com.gmr.boot.test.TokenInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

import java.io.IOException;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@TestPropertySource(locations="classpath:test.properties")
public class UserControllerTest {

    // Subject under test
    private UserControllerApi api;


    // Will contain the random free port number
    @Value("${local.server.port}")
    private int port;


    @Before
    public void setUp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:" + port)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(UserControllerApi.class);
    }


    /**
     * Accessing a protected resource without having been authenticated produces a 401 Authentication Error
     */
    @Test
    public void testAuthenticationError() throws IOException {
        // execute
        Response<User> response = api.getCurrentUser().execute();

        // verify
        assertFalse(response.isSuccess());
        assertEquals(401, response.code());
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:beforeTestRun.sql"})
    public void testSuccessfulAuthentication() throws IOException {
        // execute
        Response<TokenInfo> response = api.getAccessToken(
                "Basic Ym9vdF93ZWJhcHA6NTlkMTRmMDEtMzhkYS00MDFjLTgwMTQtYjZjMDM1NjI3MWM4",
                "admin",
                "admin",
                "boot_webapp",
                "59d14f01-38da-401c-8014-b6c0356271c8",
                "password").execute();

        // verify
        assertTrue(response.isSuccess());
    }


    private interface UserControllerApi {

        @GET(Constants.API_PREFIX + "/user?currentUser=true")
        Call<User> getCurrentUser();

        @POST("/oauth/token")
        @FormUrlEncoded
        Call<TokenInfo> getAccessToken(@Header("Authorization") String authHeader,
                                        @Field("username") String userName,
                                        @Field("password") String password,
                                        @Field("client_id") String clientId,
                                        @Field("client_secret") String clientSecret,
                                        @Field("grant_type") String grantType);

    }

}
