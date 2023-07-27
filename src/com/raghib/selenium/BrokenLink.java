package com.raghib.selenium;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLink {

	public static String soapUICssSelector = "a[href*='soapui']";
	public static String brokenLinkCssSelector = "a[href*='brokenlink']";
	public static String allFooterLinks = "li[class='gf-li'] a";
	public static HttpURLConnection connection = null;
	public static int statusCode = 0;
	public static int count = 0;
	public static SoftAssert softAssertObject = null;

	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
		// Set the driver path
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Driver\\chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/AutomationPractice/");

		// FOR SINGLE LINKS IN FOOTER

		// String url =
		// driver.findElement(By.cssSelector(soapUICssSelector)).getAttribute("href");
		/*
		 * String url =
		 * driver.findElement(By.cssSelector(brokenLinkCssSelector)).getAttribute("href"
		 * ); System.out.println(url);
		 * 
		 * //HTTP METHODS -> GET / POST /PUT / DELETE / HEAD / TRACE / OPTION
		 * 
		 * connection = (HttpURLConnection) new URL(url).openConnection();
		 * connection.setRequestMethod("HEAD"); connection.connect(); int statusCode =
		 * connection.getResponseCode();
		 * System.out.println("Request Status Code : "+statusCode); driver.quit();
		 */

		// FOR ALL THE FOOTER LINKS		
		/*
		 * List<WebElement> links = driver.findElements(By.cssSelector(allFooterLinks));
		 * for (WebElement link : links) { String allURL = link.getAttribute("href");
		 * connection = (HttpURLConnection) new URL(allURL).openConnection();
		 * connection.setRequestMethod("HEAD"); connection.connect(); statusCode =
		 * connection.getResponseCode(); count++; if (statusCode >= 400) {
		 * System.out.println("The link " + link.getText() +
		 * " is broken with the status code " + statusCode); } }
		 * System.out.println("Total Footer Link Count : "+count); Thread.sleep(5000);
		 * driver.quit();
		 */

		// FOR ALL THE FOOTER LINKS

		softAssertObject = new SoftAssert();
		List<WebElement> links = driver.findElements(By.cssSelector(allFooterLinks));
		for (WebElement link : links) {
			String allURL = link.getAttribute("href");
			connection = (HttpURLConnection) new URL(allURL).openConnection();
			connection.setRequestMethod("HEAD");
			connection.connect();
			statusCode = connection.getResponseCode();
			count++;
			System.out.println("URL : "+allURL);
			System.out.println("Status Code : " + statusCode);

			softAssertObject.assertTrue(statusCode < 400,
					"The link " + link.getText() + " is broken with the status code " + statusCode);
		}
		System.out.println("Total Footer Link Count : " + count);
		softAssertObject.assertAll(); 
	}
}
