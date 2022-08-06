#===================================================================
#This automation is done using sample REST API  https://reqres.in/
#Create suers function is automated
#===================================================================

Feature: User API Automation

  @all
  Scenario Outline: Validate GET request
    Given User provides the header 
    And User sends "<ID>" and saves response in variable 
    Then User validates response
     |	FieldName		| FieldValue	|
     |	data.id  		| <ID>    		| 
     |	data.email1 	| <Email> 		| 
    And User validates the success status code as 200
    Examples: 
     | ID | Email                  | 
     | 3  | emma.wong@reqres.in    | 
     | 2  | janet.weaver@reqres.in |
    
 @all
 Scenario Outline: Validate POST request
    Given User provides the header 
    And User sets the "<Name>" and "<Job>" as body
    When User sends POST request to "https://reqres.in/api/users"
    Then User validates response
    | FieldName | FieldValue |
    | name      | <Name>     |
    | job       | <Job>      |
    And User validates the success status code as 201
    Examples: 
    | Name 		| Job 			| 
    | Michael | Developer |
    | Monica 	| QA 				|

  
      
    
    