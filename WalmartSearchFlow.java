import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WalmartSearchFlow {
	
	static WebDriver driver;
@BeforeClass
	public void beforeClass(){
		System.setProperty("webdriver.chrome.driver","resources/chromedriver");
		driver = new ChromeDriver();
	}
  
@BeforeMethod
	public void beforeMethod(){
	   driver.get("https://www.walmart.com/");
		System.out.println("Page Title:"+driver.getTitle());
   }
	
	@Test(priority=1)
	public  void searchProduct(){
		
	     driver.findElement(By.cssSelector("#global-search-input")).sendKeys("gps");
	     WebDriverWait wait= new WebDriverWait(driver,10);
	     String result= wait.until(function);
	     System.out.println("From Function:" +result);
	     assertEquals( result,"https://www.walmart.com/search/?query=garmin%20gps&typeahead=gps");	
	}
	
	Function<WebDriver,String>function = new Function<WebDriver,String>(){
	    public String apply(WebDriver arg0){
		WebDriverWait wait = new WebDriverWait(driver,10);
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".header-Typeahead-row")));
		for(WebElement ele: elements)
		  {
			  if( ele.getText().equalsIgnoreCase("garmin gps")){
			  ele.click();
			  break;
			  
		  }
		  }
		 
		 
		  
		  return  driver.getCurrentUrl();
			 
		 }
	
	};
	
	
 @Test(priority=2)	
	
	
	public void SearchPage(){
	
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> searchResult= driver.findElements(By.cssSelector(".prod-ProductTitle.no-margin.truncated.font-normal.heading-b>div"));
		System.out.println("Result:"+ searchResult.size());
		
		//assertEquals(searchResult.size(),40);
		 }
 	
@Test(priority=3)

	public void AddToCart(){

	    //product Page
	
	
		driver.get("https://www.walmart.com/ip/Garmin-Drive-5-USA-LM-EX-GPS-Navigator/103445019");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.xpath("//div[1]/span/span[2]/div/select")); 
      
		Select select = new Select(element);

		select.selectByValue("1");
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	    element = driver.findElement(By.cssSelector(".prod-ProductCTA--primary.btn.btn-primary.btn-block"));
		element.click();
		System.out.println("ModelName  :"+ element.isDisplayed());
    
		///order summary page
		
		String   cartItems=driver.findElement(By.cssSelector(".js-btn-product.btn-fake-link")).getText();
		assertNotNull("Garmin Drive 5 USA LM EX GPS Navigator",cartItems);    //5" has this " mark
		
		System.out.println("CHECKOUT BUTTON : "+ element.isDisplayed());
		
		element = driver.findElement(By.xpath("//*[contains(@class,'btn btn-primary btn-block') and contains(@data-automation-id,'pac-pos-proceed-to-checkout')]"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		 element.click();

		System.out.println("CHECKOUT BUTTON : "+ element.isDisplayed());
		
      }

	
   //sign in
		
	@Test(priority=4)
	
		public void walmartSignIn(){
	    System.out.println("current Title :" +driver.getTitle());  
	    System.out.println("Checking for the title of the Page :" +driver.getCurrentUrl());
	    assertEquals(driver.getCurrentUrl(),"https://www.walmart.com/");
   }
	
	
	@AfterTest
	public void aftetest(){
	
		driver.close();
	}
	
	}
