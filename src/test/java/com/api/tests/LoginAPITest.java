package com.api.tests;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

import static io.restassured.module.jsv.JsonSchemaValidator.*;


public class LoginAPITest {
	
	private UserCredentials usercredBody;
	
	@BeforeMethod(description = "create the payload for login API")
	public void setup() {
		usercredBody = new UserCredentials("iamfd", "password");
	}

	@Test(description = "Verify if login api is working for users iamfd", groups = {"api","smoke","regression"})
	public void loginAPITest() throws IOException {
		
		given()
		 .spec(requestSpec(usercredBody))
		.when()
		 .post("login")
		.then()
		 .spec(responseSpec_OK())
		 .body("message", equalTo("Success"))
		 .and()
		 .body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
		 
		
	
	}
	
}
