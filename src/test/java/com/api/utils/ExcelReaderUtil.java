package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {

	public static void main(String[] args) throws IOException {

		InputStream iStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(iStream);

		XSSFSheet sheet = workbook.getSheet("LoginTestData");

		XSSFRow row;

		XSSFCell cell;

		int totalRow = sheet.getLastRowNum();

		int totalcol = sheet.getRow(0).getLastCellNum() - 1;

		for (int rowIndex = 0; rowIndex <= totalRow; rowIndex++) {

			for (int colIndex = 0; colIndex <= totalcol; colIndex++) {

				 row = sheet.getRow(rowIndex);
				 cell = row.getCell(colIndex);
				 				 
				 System.out.print(cell+" ");

			}
			
			System.out.println();

		}

	}

}
