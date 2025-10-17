package com.demo.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) throws IOException, CsvException {

//		File csvFile = new File("src/main/resources/testData/LoginCreds.csv");
//		FileReader fileReader = new FileReader(csvFile);

		InputStream resPath = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/LoginCreds.csv");
		
		InputStreamReader iReader = new InputStreamReader(resPath);

		CSVReader csvReader = new CSVReader(iReader);

		List<String[]> dataList = csvReader.readAll();

		for (String[] dataArray : dataList) {

		
				System.out.println(dataArray[0]);
				System.out.println(dataArray[1]);

			
		}

	}

}
