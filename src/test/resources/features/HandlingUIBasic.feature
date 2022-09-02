@Framework
Feature: Handling UI Basic Functionality

  @UITest @UIRegistration
  Scenario Outline: User should be able to submit visa form
    Given User on UI registration form
    When User select from country "<fromCountry>" and to country "<toCountry>"
    And User enter dob as "<dateOfBirth>"
    And User enter name as "<firstName>" and "<lastName>"
    And User enter contact details as "<email>" and "<phone>"
    And User enter the comment "<comments>"
    And User submit the registration form
    Then User should see the confirmation number displayed

    Examples:
      | fromCountry      | toCountry                   | dateOfBirth | firstName | lastName  | email              | phone          | comments |
      | Isle of Man      | Mali                        | 2011-05-31  | Kraig     | Wiza      | Kraig@nobody.com   | 1-000-884-1373 | comment1 |
      | Lithuania        | Mexico                      | 2001-01-01  | Houston   | Kertzmann | Houston@nobody.com | 284.864.6580   |          |
      | Somalia          | Greece                      | 2004-07-02  | Ruthie    | Stamm     | Ruthie@nobody.com  | 1-209-813-9712 | comment2 |
      | Christmas Island | French Southern Territories | 2019-04-05  | Shonna    | Nolan     | Shonna@nobody.com  | (162) 387-0305 |          |