package selenium;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

public class Testing {
    WebDriver webDriver;
    public static Properties prop;

    public static ArrayList<NamesDto> names;
    public static Properties readFile() {
        prop = new Properties();
        try {
            prop.load(Testing.class.getClassLoader().getResourceAsStream("prop.properties"));
        } catch (
                IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }
    @BeforeClass
    public static void beforeTest() {
        prop = readFile();
        System.out.println(prop);
        names = ReadCSV.readNamesFromCSV("src/main/resources/names.csv");
        System.out.println(names);
    }
    @Test
    public void getResults(){
        readFile();
        System.out.println(prop.getProperty("name")+" "+prop.getProperty("surname"));
    }
    @Test
    public void getIntoGoogle() throws InterruptedException {
        readFile();
        System.setProperty("webdriver.chrome.driver", "C:/Users/CMDQ123/Downloads/chromedriver_win32 (1)/chromedriver.exe");
        webDriver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        webDriver.manage().window().maximize();
        webDriver.get(prop.getProperty("url"));
        WebElement search = webDriver.findElement(By.name("q"));
        for (int i = 1; i < names.size(); i++) {
            Thread.sleep(2000);
            search.sendKeys(names.get(i).getFirstName() + " " + names.get(i).getLastName());
            webDriver.findElement(By.xpath(prop.getProperty("htmlBody"))).click();
            Thread.sleep(5000);
            //String text = webDriver.findElement(By.name("btnK")).getAttribute("value");
            //System.out.println("text: " + text);
            webDriver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[3]/center/input[1]")).click();
            //new WebDriverWait(webDriver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.name("btnK"))).click();
            Thread.sleep(2000);
            webDriver.navigate().back();
        }
        Thread.sleep(4000);
        webDriver.quit();
    }
}


