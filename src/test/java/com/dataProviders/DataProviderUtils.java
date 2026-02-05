package com.dataProviders;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataProviders.api.bean.CreateJobBean;
import com.dataProviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {

		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);

	}

	@DataProvider(name = "createJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {

		Iterator<CreateJobBean> CreateJobBeanIterator = CSVReaderUtil.loadCSV("testData/CreateJobAPIData.csv",
				CreateJobBean.class);

		ArrayList<CreateJobPayload> payloadsList = new ArrayList<CreateJobPayload>();

		while (CreateJobBeanIterator.hasNext()) {

			CreateJobBean tempBean = CreateJobBeanIterator.next();

			CreateJobPayload tempPayload = CreateJobBeanMapper.mapper(tempBean);

			payloadsList.add(tempPayload);

		}

		return payloadsList.iterator();

	}

	@DataProvider(name = "createJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobFakerDataProvider() {
		String fakerCount = System.getProperty("fakercount", "5");
		int count = Integer.parseInt(fakerCount);
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(count);
		return payloadIterator;

	}

	@DataProvider(name = "LoginAPIJsonDataProvider", parallel = true)
	public static Iterator<UserCredentials> loginAPIJsonDataProvider() {

		return JsonReaderUtil.loadJson("testData/loginAPITestData.json", UserCredentials[].class);

	}

}
