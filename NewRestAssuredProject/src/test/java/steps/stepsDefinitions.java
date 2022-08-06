package steps;

import java.util.List;
import java.util.Map;
import org.junit.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojoClass.*;

public class stepsDefinitions {
	private static RequestSpecification request = RestAssured.given();	
	private static User parm;
	private static Response response;
	private static int actualStatusCode;
	private static String base_URI = "https://reqres.in/api/users/";
	
	//GET method query parameter is beng passed from feature file
    @And("User sends {string} and saves response in variable")
	public void getRequest(String queryParm) {
		RestAssured.baseURI = base_URI;	
		response = request.get(base_URI+queryParm);		
	}
	
    //Adds header to request
	@Given ("User provides the header") 
	public void setHeader() {	
		request.header("Content-Type", "application/json");
	}

	//Ceates body for POST request
	@And ("User sets the {string} and {string} as body")
	public void setValue(String nameValue, String jobValue) {	
		parm = new User(nameValue, jobValue);	
	}
	
	//Submit POST request
	@When ("User sends POST request to {string}")
	public void postRequest(String URI) {
		request =request.body(parm);
		request.given().log().body();
		response = request.body(parm).contentType(ContentType.JSON).accept(ContentType.JSON).post(URI); 
	}
	
	//Response validation. THis methid can handle both GET and POST response validation
	@Then ("User validates response")
	public void validateResponses(DataTable table) {

		List<Map<String, String>> rows = table.asMaps(String.class, String.class);
		for (Map<String, String> columns : rows) {
			String responseString = response.asString();
			try {
				String actualValue = JsonPath.from(responseString).get(columns.get(table.cell(0,0))).toString();
				Assert.assertEquals(actualValue.toString(), columns.get(table.cell(0,1)));
			}
			catch(Exception e) {
				System.out.println(columns.get(table.cell(0,0))+ " - Element doesn't exist in response.");
			}
		}    
	}
	
	//Validates status code
	@And ("User validates the success status code as {int}")
	public void validateStatusCode(int expectedStatusCode) {		
		actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode);

	}
}
