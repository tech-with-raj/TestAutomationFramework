package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

public class CustomerAddressDao {

	private static final String CUSTOMER_ADDRESS_QUERY = """

			SELECT * from tr_customer_address where id = ?

			""";

	public static CustomerAddressDBModel getCustomerAddressData(int CustomerAddressId) {
		Connection connection;
		CustomerAddressDBModel customerAddressDBModel = null;

		try {

		    connection = DatabaseManager.getConnection();

			PreparedStatement ps = connection.prepareStatement(CUSTOMER_ADDRESS_QUERY);

			ps.setInt(1, CustomerAddressId);

			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {			
				
				customerAddressDBModel = new CustomerAddressDBModel(resultSet.getInt("id"), resultSet.getString("flat_number"), 
						resultSet.getString("apartment_name"), resultSet.getString("street_name"), resultSet.getString("landmark"), resultSet.getString("area"),
						resultSet.getString("pincode"), resultSet.getString("country"), resultSet.getString("state"));
				
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return customerAddressDBModel;

	}

}
