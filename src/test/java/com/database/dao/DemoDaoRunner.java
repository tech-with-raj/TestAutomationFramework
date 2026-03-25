package com.database.dao;

import java.util.ArrayList;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataProviders.api.bean.CreateJobBean;

public class DemoDaoRunner {

	public static void main(String[] args) {

		List<CreateJobBean> beanlist = CreateJobPayloadDataDao.getCreateJobPayloadData();
		
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();

		for (CreateJobBean createJobBean : beanlist) {

			CreateJobPayload payload = CreateJobBeanMapper.mapper(createJobBean);
			
			payloadList.add(payload);

		}
		
		

	}

}
