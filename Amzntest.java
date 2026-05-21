package testng_Prax;

import java.time.Duration;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class Amzntest {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeTest
    public void beforeTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));        
        driver.get("https://www.amazon.in/");
        js = (JavascriptExecutor) driver;
    }
  @Test(priority=1)
  public void registration() throws InterruptedException {
      driver.get("https://www.amazon.in/ap/signin?openid.return_to=https%3A%2F%2Fwww.amazon.in%2F%3Fref_%3Dnav_ya_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=inflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0");

      WebElement email = driver.findElement(By.id("ap_email_login"));
      email.sendKeys("gautam95.pattnaik@gmail.com");
      email.sendKeys(Keys.ENTER);
      
      driver.findElement(By.xpath("//*[@id=\"intention-submit-button\"]/span/input")).click();      
      // 3. Name Field
      WebElement yourName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_customer_name")));
      yourName.sendKeys("Gautam");
      
      // 4. Mobile Number
      WebElement mobileNumber = driver.findElement(By.id("ap_phone_number"));
      mobileNumber.sendKeys("8448566330");
      try {
          WebElement password = driver.findElement(By.id("ap_password"));
          if (password.isDisplayed()) {
              password.sendKeys("emiliatanmajitenshii");
          }
      } catch (Exception e) {
             System.out.println("PASSWORD FIELD NOT DISPLAYED.");
      }
      
      WebElement verifyMobileBtn = driver.findElement(By.id("continue"));
      verifyMobileBtn.click();
      
      try {
          WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
          longWait.until(ExpectedConditions.or(
              ExpectedConditions.visibilityOfElementLocated(By.name("cvf_captcha_input")), 
              ExpectedConditions.visibilityOfElementLocated(By.id("cvf-page-title"))
          ));
          Thread.sleep(30000); 
          
      } catch (Exception e) {
          System.out.println("Error: OTP/Captcha screen not displaying or timed out");
      }
  }
  
  @Test(priority = 2)
  public void login() throws InterruptedException {
      System.out.println("Running Test 2: Login");
      driver.get("https://www.amazon.in/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.in%2F%3Fref_%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=inflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0");
      
      WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_email_login")));
      email.sendKeys("8448566330");
      email.sendKeys(Keys.ENTER);
      
      WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
      pass.sendKeys("emiliatanmajitenshii");
      pass.sendKeys(Keys.ENTER);
      System.out.println(" LOGIN CREDENTIALS ENTERED.");
      Thread.sleep(10000);
      
  }
  
  @Test(priority = 3)
  public void multipleSearch() throws InterruptedException {
  	Thread.sleep(10000);
      System.out.println(" Test 3: Multiple Search");
      String[] itemsToSearch = {"Dune", "iPad", "Headphones"};
      
      for (String item : itemsToSearch) {
          WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
          searchBox.clear();
          searchBox.sendKeys(item);
          searchBox.sendKeys(Keys.ENTER);
          System.out.println("Searched for: " + item);
          Thread.sleep(1000);
      }
  }
  
  @Test(priority = 4)
  public void productDetail() throws InterruptedException {
      System.out.println("Test 4: Product Detail");
      Thread.sleep(2000); 
      WebElement firstProductImage = wait.until(ExpectedConditions.elementToBeClickable(
          By.xpath("(//div[@data-component-type='s-search-result']//img)[1]/ancestor::a")
      ));
      
      js.executeScript("window.scrollBy(0,400)");
      Thread.sleep(1000);
      
      firstProductImage.click();
      String mainWindow = driver.getWindowHandle();
      for (String handle : driver.getWindowHandles()) {
          if (!handle.equals(mainWindow)) {
              driver.switchTo().window(handle);
              Thread.sleep(3000);
              System.out.println("Product Detail Page opened. Title: " + driver.getTitle());
              break;
          }
      }
  }

  @Test(priority = 5)
  public void addToCart() throws InterruptedException {
  	Thread.sleep(2000);
  	js.executeScript("window.scrollBy(0,500)");
      Thread.sleep(1000);
  	
      System.out.println("Test 5: Add To Cart");
      WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
      addToCartBtn.click();
      Thread.sleep(3000);
      System.out.println("Item added to cart.");
  }

  @Test(priority = 6, dependsOnMethods = {"addToCart"})
  public void deleteCartItem() throws InterruptedException {
      System.out.println("Test 6: Delete Cart Item");
      driver.navigate().to("https://www.amazon.in/gp/cart/view.html");        
      WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Delete']")));
      deleteBtn.click();
      Thread.sleep(2000);
      System.out.println("Item deleted from cart.");
  }

  @Test(priority = 7)
  public void changeAddress() throws InterruptedException {
      System.out.println("-> Running Test 7: Checkout & Change Address");
      Thread.sleep(2000);

      try {
          WebElement proceedToBuyBtn = wait.until(ExpectedConditions.elementToBeClickable(By.name("proceedToRetailCheckout")));
          proceedToBuyBtn.click();
          System.out.println("Proceed to Buy clicked.");
          Thread.sleep(3000);
          
          try {
              WebElement pass = driver.findElement(By.id("ap_password"));
              if (pass.isDisplayed()) {
                  pass.sendKeys("Harsh@210192");
                  pass.sendKeys(Keys.ENTER);
                  System.out.println("   --- Re-entered password for security check.");
                  Thread.sleep(4000);
              }
          } catch (Exception e) {
              System.out.println("  No security check screen. Moving to Checkout.");
          }
          
          WebElement changeAddressLink = wait.until(ExpectedConditions.elementToBeClickable(
              By.xpath("//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'change')] | //a[@id='addressChangeLinkId']")
          ));
          changeAddressLink.click();
          System.out.println("   - Clicked on 'Change' address link.");
          Thread.sleep(3000);
          
          java.util.List<WebElement> allAddressRadios = driver.findElements(By.xpath("//div[contains(@id, 'select-destination')]//input[@type='radio']"));
          
          if(allAddressRadios.size() >= 2) {
              WebElement secondAddressBtn = allAddressRadios.get(1);
              Thread.sleep(1000);
              
              WebElement clickableLabel = secondAddressBtn.findElement(By.xpath("./ancestor::label"));
              clickableLabel.click();
              System.out.println("   - Selected the 2nd address from the list.");
              Thread.sleep(2000); 
          } else {
              System.out.println("   - Warning: 2nd address nahi mila. Account mein shayad 1 hi address hai.");
          }

          WebElement useThisAddressBtn = wait.until(ExpectedConditions.elementToBeClickable(
              By.xpath("//input[contains(@id, 'checkout-primary-continue-button')] | //*[@id='checkout-primary-continue-button-id']//input")
          ));
          
          js.executeScript("arguments[0].scrollIntoView({block: 'center'});", useThisAddressBtn);
          Thread.sleep(1000);
              
          useThisAddressBtn.click();
          System.out.println("Successfully changed the delivery address for checkout!");
          Thread.sleep(3000);

      } catch (Exception e) {
          System.out.println(" Error found in either Checkout or Address change: " + e.getMessage());
      }
  }
  
  @AfterTest
  public void aftertest() {
	  driver.quit();
      System.out.println(" Browser close");
  }
}
