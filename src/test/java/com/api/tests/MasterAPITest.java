package com.api.tests;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() {
		
		given()
		   .spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
		   .post("master")
		.then()
		   .spec(SpecUtil.responseSpec_OK())
		   .body("message", equalTo("Success"))
		   .body("data", notNullValue())
		   .body("data", hasKey("mst_oem"))
		   .body("$", hasKey("message"))
		   .body("$", hasKey("data"))
		   .body("data.mst_oem.size()", greaterThan(0))
		   .body("data.mst_model.size()", equalTo(3))
		   .body("data.mst_oem.id", everyItem(notNullValue()))
		   .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponse_FD.json"));
		   
		   
		
	}
	
	
	@Test
	public void invalidTokenMasterAPI() {
		
		given()
		   .spec(SpecUtil.requestSpec())
		.when()
		   .post("master")
		.then()
		   .spec(SpecUtil.responseSpec_TEXT(401));
		
	}
	
	
	
	
	
	
	
	

}
