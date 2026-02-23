package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderUtil2 {

	private ExcelReaderUtil2() {

	}

	public static <T> Iterator<T> loadTestData(String sheetName, Class<T> clazz) {

		InputStream iStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(iStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XSSFSheet sheet = workbook.getSheet(sheetName);

		  List<T> dataList = Poiji.fromExcel(sheet, clazz);
		  
		  return dataList.iterator();

			
		

	}

}
