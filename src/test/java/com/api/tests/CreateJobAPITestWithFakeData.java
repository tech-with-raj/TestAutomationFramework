package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuthBody;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITestWithFakeData {

	private CreateJobPayload createJobPayload;
	private final static String COUNTRY = "India";

	@BeforeMethod(description = "Creating createjob api request payload")
	public void setup() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();

	}

	@Test(description = "Verify if create job api is able to create Warranty job", groups = { "api", "regression",
			"smoke" })
	public void createJobAPITest() {

		int customerId = given().spec(requestSpecWithAuthBody(Role.FD, createJobPayload)).log().all().when()
				.post("/job/create").then().spec(responseSpec_OK())
				.body(JsonSchemaValidator
						.matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.id", Matchers.notNullValue()).body("data.mst_service_location_id", Matchers.equalTo(1))
				.body("data.job_number", Matchers.startsWith("JOB_")).extract().body().jsonPath()
				.getInt("data.tr_customer_id");

		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);

		Assert.assertEquals(expectedCustomerData.first_name(), actualCustomerDataInDB.getFirst_name());
		Assert.assertEquals(expectedCustomerData.last_name(), actualCustomerDataInDB.getLast_name());
		Assert.assertEquals(expectedCustomerData.mobile_number(), actualCustomerDataInDB.getMobile_number());
		Assert.assertEquals(expectedCustomerData.mobile_number_alt(), actualCustomerDataInDB.getMobile_number_alt());
		Assert.assertEquals(expectedCustomerData.email_id(), actualCustomerDataInDB.getEmail_id());
		Assert.assertEquals(expectedCustomerData.email_id_alt(), actualCustomerDataInDB.getEmail_id_alt());

		CustomerAddress expectedCustomerAddress = createJobPayload.customer_address();
		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao
				.getCustomerAddressData(actualCustomerDataInDB.getTr_customer_address_id());

		Assert.assertEquals(expectedCustomerAddress.flat_number(), customerAddressDBModel.getFlat_number());
		Assert.assertEquals(expectedCustomerAddress.apartment_name(), customerAddressDBModel.getApartment_name());
		Assert.assertEquals(expectedCustomerAddress.street_name(), customerAddressDBModel.getStreet_name());
		Assert.assertEquals(expectedCustomerAddress.landmark(), customerAddressDBModel.getLandmark());
		Assert.assertEquals(expectedCustomerAddress.area(), customerAddressDBModel.getArea());
		Assert.assertEquals(expectedCustomerAddress.pincode(), customerAddressDBModel.getPincode());
		Assert.assertEquals(expectedCustomerAddress.country(), customerAddressDBModel.getCountry());
		Assert.assertEquals(expectedCustomerAddress.state(), customerAddressDBModel.getState());

	}

}
