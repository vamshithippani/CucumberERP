package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static Properties conpro;
	public static WebDriver driver;
	//method for launching browser
	public static WebDriver startBrowser()throws Throwable
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();	
		
		return driver;
	}
	//method for launching url
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for webelement to wait
	public static void waitForElement(String Locatorname,String LocatorValue,String TestData)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(Locatorname.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(Locatorname.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		if(Locatorname.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
	}
	//method for type action
	public static void typeAction(String LocatorName,String LocatorValue,String TestData)
	{
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
	}
	//method for click action
	public static void clickAction(String LocatorName,String LocatorValue)
	{
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
	}
	//method for page title validation
	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title, "Title is Not Matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	//method for closing browser
	public static void closeBrowser()
	{
		driver.quit();
	}
//method for date generation
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat  df = new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
		return df.format(date);
	}
	// method for mouse click
	public static void mouseClick() throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'StockItems')]"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'StockCategories')])[2]"))).perform();
		Thread.sleep(3000);
	}
	 // method for categoryTable
	public static void categoryTable(String Exp_Data)throws Throwable
	{
		//if search textBox is not displayed click search panel
		if(!driver.findElement(By.xpath(conpro.getProperty("seach-textbox"))).isDisplayed())
			// click search panel button
		driver.findElement(By.xpath(conpro.getProperty("seach-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("seach-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("seach-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("seach-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='tableewTable']/body/tr[1]/td[4]/div/span/span")).getText();
		Reporter.log(Exp_Data+"  "+Act_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data,"Category Name is Not Matching");
		}catch(AssertionError a)
	{
		Reporter.log(a.getMessage(),true);
	}
}
	// method for list boxes
	public static void dropDownAction(String LocatorName,String LocatorValue,String TestData)
	{
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			int value =Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			int value =Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			int value =Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.id(LocatorValue)));
			element.selectByIndex(value);
		}
	}
	//method for capture stock number into notepad
	public static void captureStock(String LocatorName,String LocatorValue)throws Throwable
	{
		String stockNumber="";
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			stockNumber =driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			stockNumber =driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			stockNumber =driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}
		//create note pad and write stock number
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNumber);
		bw.flush();
		bw.close();
	}
	//method for stock table
	public static void stockTable()throws Throwable
	{
		//read stock number from note pad
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data =br.readLine();
		//if search textBox not displayed click search panel
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//click search panel button
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Act_Data+"       "+Exp_Data,true);
		try {
			Assert.assertEquals(Act_Data, Exp_Data,"Stock number is Not Matching");
		}catch(AssertionError a)
		{
			Reporter.log(a.getMessage(),true);

		}
}
	// method for supplier number to capture into notepad
	public static void capturesupplier(String LocatorName,String LocalValue) throws Throwable
	{
		String suppliernumber ="";
		if(LocatorName.equalsIgnoreCase("xpath"))
		{
			suppliernumber = driver.findElement(By.xpath(LocalValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("name"))
		{
			suppliernumber = driver.findElement(By.xpath(LocalValue)).getAttribute("value");
		}
		if(LocatorName.equalsIgnoreCase("id"))
		{
			suppliernumber = driver.findElement(By.xpath(LocalValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(suppliernumber);
		bw.flush();
		bw.close();
	}
	// method for supplier table
	public static void supplierTable()throws Throwable
	{
	FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		//click search panel button
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_Data = driver.findElement(By.xpath("//table[@class='tablewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Act_Data+"   "+Exp_Data,true);
	try {
	Assert.assertEquals(Act_Data, Exp_Data,"supplier number is not matching");
	}catch(AssertionError a)
	{
		Reporter.log(a.getMessage(),true);
	}
	}
	// method for supplier number to capture into notepad
		public static void capturecustomer(String LocatorName,String LocalValue) throws Throwable
		{
			String suppliernumber ="";
			if(LocatorName.equalsIgnoreCase("xpath"))
			{
				suppliernumber = driver.findElement(By.xpath(LocalValue)).getAttribute("value");
			}
			if(LocatorName.equalsIgnoreCase("name"))
			{
				suppliernumber = driver.findElement(By.xpath(LocalValue)).getAttribute("value");
			}
			if(LocatorName.equalsIgnoreCase("id"))
			{
				suppliernumber = driver.findElement(By.xpath(LocalValue)).getAttribute("value");
			}
			FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(suppliernumber);
			bw.flush();
			bw.close();
		}
		// method for customer table
		public static void customerTable()throws Throwable
		{
		FileReader fr = new FileReader("./CaptureData/customernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			//click search panel button
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver.findElement(By.xpath("//table[@class='tablewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Act_Data+"   "+Exp_Data,true);
		try {
		Assert.assertEquals(Act_Data, Exp_Data,"customer number is not matching");
		}catch(AssertionError a)
		{
			Reporter.log(a.getMessage(),true);
		}
		}
}
