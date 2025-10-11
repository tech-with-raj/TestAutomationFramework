package com.api.tests;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;
import com.pojo.CreateJobPayload;
import com.pojo.Customer;
import com.pojo.CustomerAddress;
import com.pojo.CustomerProduct;
import com.pojo.Problems;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() {
		
		Customer customer = new Customer("Raja", "sekar", "9025398090", "", "raja@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("010", "Vrindavan", "quarter", "Chennai", "AAA", "560102", "India", "TamilNadu");
		CustomerProduct customerProduct  = new CustomerProduct("2025-07-01T18:30:00.000Z", "66512779617843", "66512779617843", "66512779617843", "2025-07-01T18:30:00.000Z", 1, 1);
		Problems problems = new Problems("2", "Phone is getting switch off");
		Problems[] problemArray  = new Problems[1];
		problemArray[0]=problems;
		
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemArray);
		
		
		given()
		.spec(SpecUtil.requestSpecWithAuthBody(Role.FD, createJobPayload))
		.log().all()
		.when()
		  .post("/job/create")
		.then()
		  .spec(SpecUtil.responseSpec_OK());

		
	}

}
