package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.sql.RowSetMetaData;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;

public class ExcelReaderUtil2 {

	private ExcelReaderUtil2() {

	}

	public static Iterator<UserCredentials> loadTestData() {

		InputStream iStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(iStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XSSFSheet sheet = workbook.getSheet("LoginTestData");

		XSSFRow myRow;

		XSSFCell myCell;

		XSSFRow hearderRows = sheet.getRow(0);

		int usernameIndex = -1;
		int passwordIndex = -1;

		for (Cell cell : hearderRows) {

			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {

				usernameIndex = cell.getColumnIndex();

			}
			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {

				passwordIndex = cell.getColumnIndex();

			}

		}

		int lastRowIndex = sheet.getLastRowNum();

		XSSFRow rowData;
		UserCredentials userCredentials = null;

		ArrayList<UserCredentials> userList = new ArrayList<UserCredentials>();

		for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {

			rowData = sheet.getRow(rowIndex);

			userCredentials = new UserCredentials(rowData.getCell(usernameIndex).toString(),
					rowData.getCell(passwordIndex).toString());

			userList.add(userCredentials);

		}

		return userList.iterator();

	}

}
