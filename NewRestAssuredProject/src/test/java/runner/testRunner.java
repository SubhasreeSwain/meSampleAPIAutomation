package runner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		plugin = {"pretty", "html:target/cucumberHtmlReport", "json:target/cucumber.json"},
		tags = {"@all"},
		features={"src/test/resources"},
		glue={"src/test/java"}
		)

public class testRunner {
	
	//@AfterSuite
	//Close DB Connection, etc
	
	//@BeforeSuite
	//Create DB connection, etc

}


