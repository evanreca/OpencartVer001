package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties properties;

    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"OS", "browser"})
    public void setup(String os, String browser) throws IOException {

        // loading config.properties file
        FileReader file = new FileReader("./src//test//resources//config.properties");
        properties = new Properties();
        properties.load(file);

        // creating logger
        logger = LogManager.getLogger(this.getClass());


        if (properties.getProperty("execution_env").equalsIgnoreCase("remote")) {

            String huburl = "http://localhost:4444/wd/hub";

            DesiredCapabilities capabilities = new DesiredCapabilities();

            //os
            if (os.equalsIgnoreCase("windows 11")) {
                capabilities.setPlatform(Platform.WIN11);
            }
            else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
            }
            else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            }
            else {
                System.out.println("NO MATCHING OS");
            }

            //browser
            switch (browser.toLowerCase()) {
                case ("microsoft edge"):
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                case ("google chrome"):
                    capabilities.setBrowserName("chrome");
                    break;
                case ("mozilla firefox"):
                    capabilities.setBrowserName("firefox");
                    break;
                default:
                    System.out.println("NO MATCHING BROWSER");
                    return;
            }

            driver = new RemoteWebDriver(new URL(huburl), capabilities);
        }

        if (properties.getProperty("execution_env").equalsIgnoreCase("local")) {
            // reading browser from xml, different browser = different driver
            switch (browser.toLowerCase()) {
                case ("microsoft edge"):
                    driver = new EdgeDriver();
                    break;
                case ("google chrome"):
                    driver = new ChromeDriver();
                    break;
                case ("mozilla firefox"):
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("INVALID BROWSER NAME");
                    return;
            }
        }

        // driver settings initialization
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(properties.getProperty("appURL")); // reading URL from config.properties
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        driver.quit();
    }

    public String randomString() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }

    public String randomNumber() {
        String generatedNumber = RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }

    public String randomAlphanumeric() {
        String generatedAlphanumeric = RandomStringUtils.randomAlphanumeric(15);
        return generatedAlphanumeric;
    }

    public String captureScreen(String testName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }
}
