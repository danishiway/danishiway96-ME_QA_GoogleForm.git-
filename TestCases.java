package demo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    public void endTest()
    {
        System.out.println("End Test: TestCases");
        ((WebDriver) driver).close();
        ((WebDriver) driver).quit();

    }

    
    public  void testCase01() throws InterruptedException{
        System.out.println("Start Test case: testCase01");
     
            // Navigate to Google Form
            ((WebDriver) driver).get("https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");

            // Wait for form to load
            ((WebDriver) driver).manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Fill in name
            
            enterNametext(driver, "Danish Faraz");
          
            
            // Fill in the sentence with epoch time
            enterSentance( driver, "QA is Fun");

            // Select Automation Testing experience
            
            selectAutomationExp(driver);
  
            // Select Java, Selenium, and TestNG
            WebElement javaCheckbox = ((WebDriver) driver).findElement(By.xpath("//span[text()='Java']"));
            javaCheckbox.click();

            WebElement seleniumCheckbox = ((WebDriver) driver).findElement(By.xpath("//span[text()='Selenium']"));
            seleniumCheckbox.click();

            WebElement testngCheckbox = ((WebDriver) driver).findElement(By.xpath("//span[text()='TestNG']"));
            testngCheckbox.click();

            // Select how you would like to be addressed
            selectFromDropdown(driver, "Mr");

            // Provide current date minus 7 days
            LocalDate currentDateMinus7Days = LocalDate.now().minusDays(7);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String dateFormatted = currentDateMinus7Days.format(formatter);
            WebElement dateInput = ((WebDriver) driver).findElement(By.xpath("//input[@type='date']"));
            dateInput.sendKeys(dateFormatted);
            
            CurrentTime((WebDriver) driver, 0);
            CurrentTime((WebDriver) driver, 1);

          
            // Change URL to trigger pop-up
            ((WebDriver) driver).navigate().to("https://www.amazon.in");
            Thread.sleep(2000);
            // Handle pop-up by clicking cancel
            handlingAlert(driver, false);
            // Submit the form
            WebElement submitButton = ((WebDriver) driver).findElement(By.xpath("//*[normalize-space(text())='Submit']/ancestor::div[1]"));
            submitButton.click();

            // Wait for success message
            ((WebDriver) driver).manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Print success message
            WebElement successMessage = ((WebDriver) driver).findElement(By.xpath("//div[@role='heading']/../div[3]"));
            System.out.println(successMessage.getText());
        
    }
    
    public static void enterNametext(ChromeDriver driver, String name) {
    	WebElement nameInput = ((WebDriver) driver).findElement(By.xpath("//div['Xb9hP']/input[@type='text']"));
        nameInput.sendKeys(name);
    }
  public static void enterSentance(ChromeDriver driver, String text)  {
	  long epochTime = System.currentTimeMillis();

    	WebElement sentenceInput = ((WebDriver) driver).findElement(By.xpath("//textarea[@aria-label='Your answer']"));
        sentenceInput.sendKeys(text + epochTime);
    }
  
  public static void selectAutomationExp(ChromeDriver driver) {
	  WebElement selectexp = ((WebDriver) driver).findElement(By.xpath(
              "(//span[normalize-space(text()) = 'How much experience do you have in Automation Testing?']/ancestor::div[4]//div[@class='AB7Lab Id5V1'])[2]"));
	  selectexp.click();
  }
 
  public static void selectFromDropdown(ChromeDriver driver, String textToSelect)throws InterruptedException{
   
       
      WebElement dropdownElement = ((WebDriver) driver).findElement(By.xpath("//*[normalize-space(text()) = 'How should you be addressed?']/ancestor::div[4]//div[@class='MocG8c HZ3kWc mhLiyf LMgvRb KKjvXb DEh1R']"));

      // Create a Select instance
      dropdownElement.click();
      Thread.sleep(2000);

      // Select the option by visible text
      ((WebDriver) driver).findElement(By.xpath("(//div[@data-value='" + textToSelect + "'])[2]")).click();
      Thread.sleep(2000);
      System.out.println("Success!");
  }
  public static void handlingAlert(ChromeDriver driver, Boolean accept) throws InterruptedException {
     
      Alert alert = ((WebDriver) driver).switchTo().alert();
      Thread.sleep(2000);

      if (accept) {
          
          alert.accept();
      } else {
          alert.dismiss();
      }
      System.out.println("Success!");
  }
  public static void CurrentTime(WebDriver driver, int inputIndex) {
	    // Find the input field based on the question text and input index
	    WebElement timeField = driver.findElement(By.xpath(String.format(
	            "(//*[normalize-space(text()) = 'What is the time right now?']/ancestor::div[4]//input[@class='whsOnd zHQkBf'])[%d]",
	          inputIndex + 1)));

	    // Get the current time and format it with AM/PM
	    LocalTime currentTime = LocalTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
	    String formattedTime = currentTime.format(formatter);

	    // Input the formatted time into the field
	    timeField.sendKeys(formattedTime);
	}
}
