package com.raghib.link;

/**
 * Reference:-
 * https://stackoverflow.com/questions/34056374/maven-compile-error-package-org-testng-asserts-does-not-exist
 * 
 * I found out that removing scope inside testng dependency worked. 
 * I tried running with scope added to the same dependency but failed. 
 * Strange but it just worked by removing testng scope dependency.
 * 
<dependency>
	<groupId>org.testng</groupId>
	<artifactId>testng</artifactId>
	<version>${testng-version}</version>
	<!--<scope>test</scope>-->
</dependency>
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import com.raghib.selenium.BaseClass;

public class BrokenLink extends BaseClass {
	public static WebDriver driver;
	public static String browserName = "chrome";
	public static String browserVersion = "116";

	public static String url = "https://rahulshettyacademy.com/AutomationPractice/";

	public static String soapUICssSelector = "a[href*='soapui']";
	public static String brokenLinkCssSelector = "a[href*='brokenlink']";
	public static String allFooterLinks = "li[class='gf-li'] a";
	public static HttpURLConnection connection = null;
	public static int statusCode = 0;
	public static int count = 0;
	public static SoftAssert softAssertObject = null;

	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
		// Chrome Browser
		driver = BaseClass.getDriver(browserName, browserVersion);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		driver.get(url);

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
		 * System.out.println("Request Status Code : "+statusCode);
		 * BaseClass.quitDriver();
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
		 * BaseClass.quitDriver();
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
			System.out.println("URL : " + allURL);
			System.out.println("Status Code : " + statusCode);

			softAssertObject.assertTrue(statusCode < 400,
					"The link " + link.getText() + " is broken with the status code " + statusCode);
		}
		System.out.println("Total Footer Link Count : " + count);

		// softAssertObject.assertAll();
		/*
		 * NOTE - If we uncomment this code "softAssertObject.assertAll()" then below
		 * error is occur. Exception in thread "main" java.lang.AssertionError: The
		 * following asserts failed: The link Appium is broken with the status code 403
		 * expected [true] but found [false], The link Broken Link is broken with the
		 * status code 404 expected [true] but found [false] at
		 * org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:47) at
		 * org.testng.asserts.SoftAssert.assertAll(SoftAssert.java:31) at
		 * com.raghib.selenium.BrokenLink.main(BrokenLink.java:83)
		 */
	}
}
