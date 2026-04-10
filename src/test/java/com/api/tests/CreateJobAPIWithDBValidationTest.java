package com.api.tests;

import static com.api.utils.DayTimeUtil.getTimeWithDayAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuthBody;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class CreateJobAPIWithDBValidationTest {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	CustomerAddress customerAddress;
	CustomerProduct customerProduct;

	@BeforeMethod(description = "Creating createjob api request payload")
	public void setup() {

		customer = new Customer("Raja", "sekar", "9025398090", "", "raja@gmail.com", "");
		customerAddress = new CustomerAddress("010", "Vrindavan", "quarter", "Chennai", "AAA", "560102", "India",
				"TamilNadu");
		customerProduct = new CustomerProduct(getTimeWithDayAgo(10), "66533799807095", "66533799807095",
				"66533799807095", getTimeWithDayAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.POOR_BATTERY_LIFE.getCode(), "Phone is getting switch off");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);

		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.APPLE.getcode(), customer,
				customerAddress, customerProduct, problemsList);

	}

	@Test(description = "Verify if create job api is able to create Warranty job", groups = { "api", "regression",
			"smoke" })
	public void createJobAPITest() {

		Response response = given().spec(requestSpecWithAuthBody(Role.FD, createJobPayload)).log().all().when()
				.post("/job/create").then().spec(responseSpec_OK())
				.body(JsonSchemaValidator
						.matchesJsonSchemaInClasspath("response-schema\\CreateJobAPIResponseSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.id", Matchers.notNullValue()).body("data.mst_service_location_id", Matchers.equalTo(1))
				.body("data.job_number", Matchers.startsWith("JOB_")).extract().response();

		int customerId = response.then().extract().body().jsonPath().getInt("data.tr_customer_id");

		CustomerDBModel customerDBModel = CustomerDao.getCustomerInfo(customerId);

		Assert.assertEquals(customer.first_name(), customerDBModel.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDBModel.getLast_name());
		Assert.assertEquals(customer.mobile_number(), customerDBModel.getMobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), customerDBModel.getMobile_number_alt());
		Assert.assertEquals(customer.email_id(), customerDBModel.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), customerDBModel.getEmail_id_alt());

		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao
				.getCustomerAddressData(customerDBModel.getTr_customer_address_id());

		Assert.assertEquals(customerAddress.flat_number(), customerAddressDBModel.getFlat_number());
		Assert.assertEquals(customerAddress.apartment_name(), customerAddressDBModel.getApartment_name());
		Assert.assertEquals(customerAddress.street_name(), customerAddressDBModel.getStreet_name());
		Assert.assertEquals(customerAddress.landmark(), customerAddressDBModel.getLandmark());
		Assert.assertEquals(customerAddress.area(), customerAddressDBModel.getArea());
		Assert.assertEquals(customerAddress.pincode(), customerAddressDBModel.getPincode());
		Assert.assertEquals(customerAddress.country(), customerAddressDBModel.getCountry());
		Assert.assertEquals(customerAddress.state(), customerAddressDBModel.getState());

		int customerProductId = response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
		
		CustomerProductDBModel customerProductDBData = CustomerProductDao.getProductInfoFromDB(customerProductId);
		
		Assert.assertEquals(customerProductDBData.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDBData.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDBData.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductDBData.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductDBData.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductDBData.getMst_model_id(), customerProduct.mst_model_id());

	}

}
