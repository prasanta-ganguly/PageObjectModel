package com.crm.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.crm.qa.base.TestBase;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT = 30;
	public static long IMPLICIT_WAIT = 20;
	
	public static Xls_Reader read;
	
	static Workbook book;
	static Sheet sheet;
	
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")+"\\src\\main\\java\\com\\crm\\qa\\testdata\\FreeCrmTestData.xlsx";

	public void switchToFrame(String frameName) {
		driver.switchTo().frame(frameName);
	}

public static void takeScreenshotForWebEvenListener() throws IOException {
	
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		
		FileUtils.copyFile(scrFile, new File(currentDir + "/WebEventListener-screenshots/" + System.currentTimeMillis() + ".png"));
		
		}

public static Object[][] getTestData(String sheetName) {
	FileInputStream file = null;
	try {
		file = new FileInputStream(TESTDATA_SHEET_PATH);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	try {
		book = WorkbookFactory.create(file);
	} catch (InvalidFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	sheet = book.getSheet(sheetName);
	Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
	// System.out.println(sheet.getLastRowNum() + "--------" +
	// sheet.getRow(0).getLastCellNum());
	for (int i = 0; i < sheet.getLastRowNum(); i++) {
		for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
			data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			// System.out.println(data[i][k]);
		}
	}
	return data;
}


/*public static ArrayList<Object[]> getTestData(String sheetName) {
	ArrayList<Object[]> ar = new ArrayList<Object[]>();
	FileInputStream file = null;
	try {
		file = new FileInputStream(TESTDATA_SHEET_PATH);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	try {
		book = WorkbookFactory.create(file);
	} catch (InvalidFormatException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	sheet = book.getSheet(sheetName);

for(int i=0; i<sheet.getLastRowNum(); i++){
   ///for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++){
     String title = sheet.getRow(i+1).getCell(0).toString();
     String ftName = sheet.getRow(i+1).getCell(1).toString();
     String ltName = sheet.getRow(i+1).getCell(2).toString();
     String compName = sheet.getRow(i+1).getCell(3).toString();

	Object ob[] = {title, ftName, ltName, compName};
	ar.add(ob);
}

//}
return ar;
}*/

/*public static ArrayList<Object[]> getTestData(String sheetName){
	
	ArrayList<Object[]> ar = new ArrayList<Object[]>();
	
	try {
		read = new Xls_Reader(TESTDATA_SHEET_PATH);
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	int rowsCount = read.getRowCount(sheetName);
	//int colsCount = read.getColumnCount(sheetName);
	
	for(int rowNum=2; rowNum<=rowsCount; rowNum++) {
			
			String title = read.getCellData(sheetName, "title", rowNum);
			String ftName = read.getCellData(sheetName, "firstname", rowNum);
			String ltName = read.getCellData(sheetName, "lastname", rowNum);
			String ComName = read.getCellData(sheetName, "company", rowNum);
			
			Object ob[] = {title, ftName, ltName, ComName};
			ar.add(ob);
		}
	
	return ar;
}*/
	
}