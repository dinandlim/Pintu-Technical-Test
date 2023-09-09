Feature: APP - Postive

  Scenario: User login with registered email and password
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User verify all label in register page
    And User input 'TesterOne' in name field
    And User input random email in email textfield
    And User verify inputed email is correct
    And User input 'Testing123' in password field
    And User input 'Testing123' in confirm password field
    And User click 'REGISTER' button
    Then User see notification registration success
    And User navigate back
    And User redirect to login page
    And User see all text in login page
    When User input registered email
    And User input 'Testing123' in password field
    And User click 'LOGIN' button
    Then User verify registered user data after login
    And User close the app


