package com.api.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.*;
import static com.api.utils.DayTimeUtil.*;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	
	@Test
	public void createJobAPITest() {
		
		Customer customer = new Customer("Raja", "sekar", "9025398090", "", "raja@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("010", "Vrindavan", "quarter", "Chennai", "AAA", "560102", "India", "TamilNadu");
		CustomerProduct customerProduct  = new CustomerProduct(getTimeWithDayAgo(10), "66522779607935", "66522779607935", "66522779607935", getTimeWithDayAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.POOR_BATTERY_LIFE.getCode(), "Phone is getting switch off");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
	
		CreateJobPayload createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.APPLE.getcode(), customer, customerAddress, customerProduct, problemsList);
		
		
		given()
		.spec(SpecUtil.requestSpecWithAuthBody(Role.FD, createJobPayload))
		.log().all()
		.when()
		  .post("/job/create")
		.then()
		  .spec(SpecUtil.responseSpec_OK())
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
		  .body("message", Matchers.equalTo("Job created successfully. "))
		  .body("data.id", Matchers.notNullValue())
		  .body("data.mst_service_location_id", Matchers.equalTo(1))
		  .body("data.job_number", Matchers.startsWith("JOB_"));

		
	}

}
