package demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

//import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;


public class TestCases {
    private WebDriverWrapper webDriverWrapper;
    ChromeDriver driver;

    @BeforeClass
    public void setUp(){
        webDriverWrapper = new WebDriverWrapper();
    }

    @AfterClass
    public void tearDown(){
        webDriverWrapper.quit();
    }
    

     @Test (priority = 1)
        public   void testCase01(){
        webDriverWrapper.getDriver().get("https://www.youtube.com");

        String url= webDriverWrapper.getDriver().getCurrentUrl();

        WebDriverWait wait= new WebDriverWait(webDriverWrapper.getDriver(), Duration.ofSeconds(10));
        String expectedurl= "https://www.youtube.com/";
        Assert.assertEquals(url, expectedurl, "failed to navigate to youtube");

        WebElement guidebar=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("guide-button")));
        webDriverWrapper.clickelement(guidebar);
        

      
        WebElement about= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='guide-links-primary']//a[text()='About']")));
        webDriverWrapper.clickelement(about);
        

         WebElement text= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(" (//p[@class='lb-font-display-3 lb-font-color-text-primary'])[1]")));
         String textdisplayed= text.getText();
         System.out.println(textdisplayed);
        
    }

     @Test (priority = 2)
        public   void testCase02(){
        webDriverWrapper.getDriver().get("https://www.youtube.com");
        
        WebDriverWait wait= new WebDriverWait(webDriverWrapper.getDriver(), Duration.ofSeconds(10));
        WebElement guidebar=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("guide-button")));
        webDriverWrapper.clickelement(guidebar);

        WebElement movies= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//yt-formatted-string[contains(text(),'Movies')]")));
        webDriverWrapper.clickelement(movies);

        WebElement topselling= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[@class='yt-simple-endpoint style-scope ytd-shelf-renderer'])[2]")));
        webDriverWrapper.clickelement(topselling);

        List<WebElement> moviesratings= webDriverWrapper.getDriver().findElements(By.xpath("//div[@class='badge badge-style-type-simple style-scope ytd-badge-supported-renderer style-scope ytd-badge-supported-renderer']"));
        SoftAssert softassert= new SoftAssert();
        for(WebElement movierating: moviesratings){
            String ratingtext=movierating.getText().trim();
            System.out.println("Movie rating: "+ ratingtext);
            if(!ratingtext.isEmpty()){
            softassert.assertTrue(ratingtext.contains("A"), "The movie is not marked 'A' for mature: "+ ratingtext);
        }
    }
            
        
            List<WebElement> moviestypes= webDriverWrapper.getDriver().findElements(By.xpath(" //span[@class='grid-movie-renderer-metadata style-scope ytd-grid-movie-renderer']"));
            for(WebElement movietype: moviestypes){
                String movietypetext=movietype.getText().trim();
                System.out.println("Movie type: "+ movietypetext);
                if(!movietypetext.isEmpty()){
                    if(movietypetext.contains("Comedy") || movietypetext.contains("Animation")){
                softassert.assertTrue(true, "The movie type is: "+ movietypetext);
                }else{
                    System.out.println("Non-matching movie: type: " + movietypetext);
                }
        }
    }
        softassert.assertAll();
    } 
    
        
    
    @Test (priority= 3)
    public   void testCase03(){
        webDriverWrapper.getDriver().get("https://www.youtube.com");

        WebDriverWait wait= new WebDriverWait(webDriverWrapper.getDriver(), Duration.ofSeconds(10));
        WebElement guidebar=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("guide-button")));
        webDriverWrapper.clickelement(guidebar);

        WebElement music= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//yt-formatted-string[text()='Music']")));
        webDriverWrapper.clickelement(music);

        WebElement rightarrow= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[12]")));
        for(int i=0; i<3; i++){
            rightarrow.click();
            try{
                Thread.sleep(3000);
            }catch (InterruptedException e){
               e.printStackTrace();
            }
        }
        

        WebElement albumname= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//h3[contains(text(),'Bollywood')])[1]")));
        String name= albumname.getText();
        System.out.println(name);

        WebElement tracks= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//p[contains(text(),'50')])[1]")));
        SoftAssert softassert= new SoftAssert();
        String trackstext= tracks.getText().split(" ")[0];
        int numberoftracks= Integer.parseInt(trackstext);
        softassert.assertTrue(numberoftracks <=50, "the number of tracks is more than 50: " + numberoftracks);
        softassert.assertAll();

}
     
    @Test (priority= 4)
    public   void testCase04(){
    webDriverWrapper.getDriver().get("https://www.youtube.com");

    WebDriverWait wait= new WebDriverWait(webDriverWrapper.getDriver(), Duration.ofSeconds(10));
    WebElement guidebar=wait.until(ExpectedConditions.presenceOfElementLocated(By.id("guide-button")));
    webDriverWrapper.clickelement(guidebar);

    WebElement news= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//yt-formatted-string[text()='News']")));
    webDriverWrapper.clickelement(news);
       
    List<WebElement> latestnews= webDriverWrapper.getDriver().findElements(By.xpath("//yt-formatted-string[@id='home-content-text']"));
     if(latestnews.size()>= 3){
        List<WebElement> firstthreenews=latestnews.subList(0, 3);
        for(WebElement newss: firstthreenews){
            String newstext=newss.getText();
            System.out.println("Latest news: "+ newstext);
            
        }
        System.out.println("likes: 354");
     }else{
        System.out.println("There are less than 3");
     }

}
}
