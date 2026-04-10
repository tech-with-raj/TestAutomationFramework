package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {

		CustomerAddressDBModel customerDBdata = CustomerAddressDao.getCustomerAddressData(243002);
		
		System.out.println(customerDBdata);
		
	}

}
