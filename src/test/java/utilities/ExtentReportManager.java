package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter; // UI of the report
    public ExtentReports extent; // populates common info on the report
    public ExtentTest test; // creates test case entries in the report and updates the status of the test methods

    String reportName;

    public void onStart(ITestContext testContext) {

//        System.out.println("---------------- EXTENT REPORT START ----------------");

//        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//        Date dt = new Date();
//        String currentDateTimeStamp = df.format(dt);

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // does the above 3 lines in only 1

        reportName = "Test-Report-" + timeStamp + ".html"; // names the report using full timestamp

        sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName); // filepath for report output

        sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Title of the report
        sparkReporter.config().setReportName("Opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Application", "opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub-Module", "Customers");
        extent.setSystemInfo("Username", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("OS");
        extent.setSystemInfo("Operating System", os);

        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser Name", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    public void onTestSuccess(ITestResult testResult) {
        test = extent.createTest(testResult.getTestClass().getName()); // creates a new entry in the report
        test.assignCategory(testResult.getMethod().getGroups()); // to display groups in the report
        test.log(Status.PASS, "TEST PASSED: " + testResult.getName()); // updates the test status: passed/failed/skipped
    }

    public void onTestFailure(ITestResult testResult) {
        test = extent.createTest(testResult.getName());
        test.assignCategory(testResult.getMethod().getGroups());

        test.log(Status.FAIL, "TEST FAILED: " + testResult.getName());
        test.log(Status.FAIL, "CAUSE OF FAILURE: " + testResult.getThrowable()); // prints stack trace?

        try {
            String imgPath = new BaseClass().captureScreen(testResult.getName());
            test.addScreenCaptureFromPath(imgPath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult testResult) {
        test = extent.createTest(testResult.getTestClass().getName());
        test.assignCategory(testResult.getMethod().getGroups());

        test.log(Status.SKIP, "TEST SKIPPED: " + testResult.getName());
        test.log(Status.INFO, testResult.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context) {
        extent.flush(); // ends reporting procedure? by sending all the collected report data to the ExtentSparkReporter to display via html
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + reportName;
        File extentReport = new File(pathOfExtentReport);

        try{
            Desktop.getDesktop().browse(extentReport.toURI());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

//        // create and send an email of the report
//        try{
//            String urlPath = "file:///" + System.getProperty("user.dir") + "\\reports\\" + reportName;
//            URL url = new URL(urlPath);
//
//            ImageHtmlEmail email = new ImageHtmlEmail();
//            email.setDataSourceResolver(new DataSourceUrlResolver(url));
//            email.setHostName("smtp.googlemail.com");
//            email.setSmtpPort(465);
//            email.setAuthenticator(new DefaultAuthenticator("evanreca@aim.com", "password")); // sender must provide their actual password (in plaintext??)
//            email.setSSLOnConnect(true);
//            email.setFrom("evanreca@gmail.com"); // sender
//            email.setSubject("Test Results");
//            email.setMsg("Please find attached report...");
//            email.addTo("evanreca@aim.com"); // receiver
//            email.attach(url, "extent report", "please check report...");
//            email.send();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (EmailException e) {
//            e.printStackTrace();
//        }

//        System.out.println("---------------- EXTENT REPORT END ----------------");
    }
}
