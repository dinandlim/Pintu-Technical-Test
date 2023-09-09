Feature: APP - Login

  Scenario: User Login without input email and password
    Given User open Login Register App
    And User redirect to login page
    And User see all text in login page
    When User click 'LOGIN' button
    Then User see 'Enter Valid Email' activity label
    And User close the app

  Scenario: User Login without input email
    Given User open Login Register App
    And User redirect to login page
    And User see all text in login page
    And User input 'Testing123' in password field
    When User click 'LOGIN' button
    Then User see 'Enter Valid Email' activity label
    And User close the app

  Scenario: User Login with invalid email format
    Given User open Login Register App
    And User redirect to login page
    And User see all text in login page
    And User input 'Testing.com' in email field
    And User input 'Testing123' in password field
    When User click 'LOGIN' button
    Then User see 'Enter Valid Email' activity label
    And User close the app

  Scenario: User Login without input password
    Given User open Login Register App
    And User redirect to login page
    And User see all text in login page
    And User input 'Testing@gmail.com' in email field
    When User click 'LOGIN' button
    Then User see error notification in email textfield
    And User close the app

  Scenario: User Login with invalid email and password
    Given User open Login Register App
    And User redirect to login page
    And User see all text in login page
    And User input random email in email textfield
    And User input 'Testing123' in password field
    When User click 'LOGIN' button
    Then User see 'Wrong Email or Password' activity label
    And User close the app












