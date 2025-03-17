package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	@DataProvider(name="LoginData")
	public String[] [] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx"; // taking xl file from test Data
		ExcelUtility xlutil = new ExcelUtility(path); // creating on object for xl utility
		
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1",1); // assigning the value 1 for the row number
		
		String logindata[][]= new String [totalrows][totalcols]; // created for two dimension array whish can store data
		for(int i = 1; i<totalrows; i++)//read the data from xl storing in two dimension array
		{
			for(int j=0; j<totalcols; j++)
			{
				logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j); // 1,0
				
			}
		}
		return logindata; // returning two dimension array. return type string
	}
	
	

}
