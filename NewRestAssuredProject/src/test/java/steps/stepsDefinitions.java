package steps;

import java.util.List;
import java.util.Map;
import org.testng.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojoClass.User;

public class stepsDefinitions {
	private static RequestSpecification request = RestAssured.given();	
	private static User parm;
	private static Response response;
	private static int actualStatusCode;
	private static String base_URI = "https://reqres.in/";
	
	/*
	 * Author : Subhasree Swain
	 * Description: Step definition to send GET request. It takes input as path parameter. Path parameter contains path and query parameter
	*/
    @And("User sends GET request to {string} and saves response in variable")
	public void getRequest(String pathParm) {
		RestAssured.baseURI = base_URI;	
		response = request.get(base_URI+pathParm);		
	}
	
	/*
	 * Author : Subhasree Swain
	 * Description: This step definition adds header to request.
	*/
	@Given ("User provides the header") 
	public void setHeader() {	
		request.header("Content-Type", "application/json");
	}

	/*
	 * Author : Subhasree Swain
	 * Description: Step definition to create request body. POJO class and jackson dependency are used for this
	*/
	@And ("User sets the {string} and {string} as body")
	public void setValue(String nameValue, String jobValue) {	
		parm = new User(nameValue, jobValue);	
	}
	
	/*
	 * Author : Subhasree Swain
	 * Description: Step definition to POST the request. 
	*/
	@When ("User sends POST request to {string}")
	public void postRequest(String URI) {
		response = request.body(parm).post(base_URI+URI); 
	}
	
	/*
	 * Author : Subhasree Swain
	 * Description: Step definition to validate REST responses.
	*/
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
				Assert.fail(columns.get(table.cell(0,0))+ " - Element doesn't exist in response.");
			}
		}    
	}
	
	/*
	 * Author : Subhasree Swain
	 * Description: Step definition to validate status code
	*/
	@And ("User validates status code as {int}")
	public void validateStatusCode(int expectedStatusCode) {		
		actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, expectedStatusCode);

	}
}
