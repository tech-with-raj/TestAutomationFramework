package com.demo.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFileMapToPOJO {

	public static void main(String[] args) throws IOException, CsvException {


		InputStream resPath = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/LoginCreds.csv");
		
		InputStreamReader iReader = new InputStreamReader(resPath);

		CSVReader csvReader = new CSVReader(iReader);

		CsvToBean<UserPOJO> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(UserPOJO.class)
				.withIgnoreEmptyLine(true)
				.build();
		
	     List<UserPOJO> listOfUser = csvToBean.parse();
	     
	     System.out.println(listOfUser);
			
		}

	}

