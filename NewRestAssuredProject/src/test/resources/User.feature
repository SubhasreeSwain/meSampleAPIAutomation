#Author: Subhasree Swain

Feature: User API Automation

  @all
  Scenario Outline: Validate GET request
    Given User provides the header 
    And User sends GET request to "api/users/<ID>" and saves response in variable 
    Then User validates response
     |	FieldName		| FieldValue	|
     |	data.id  		| <ID>    		| 
     |	data.email 	| <Email> 		| 
    And User validates status code as 200
    Examples: 
     | ID | Email                  | 
     | 3  | emma.wong@reqres.in    | 
     | 2  | janet.weaver@reqres.in |
    
  @all
  Scenario Outline: Validate POST request
    Given User provides the header 
    And User sets the "<Name>" and "<Job>" as body
    When User sends POST request to "api/users/"
    Then User validates response
    | FieldName | FieldValue |
    | name      | <Name>     |
    | job       | <Job>      |
    And User validates status code as 201
    Examples: 
    | Name 		| Job 		|
    | Michael   | Developer |
    | Monica 	| QA 		|

  
      
    
    