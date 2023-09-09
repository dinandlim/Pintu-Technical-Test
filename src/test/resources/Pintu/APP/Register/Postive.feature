Feature: APP - Register

  Scenario: New user register to apps
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User verify all label in register page
    And User input 'TesterOne' in name field
    And User input random email in email textfield
    And User input 'Testing123' in password field
    And User input 'Testing123' in confirm password field
    And User click 'REGISTER' button
    Then User see notification registration success
    And User close the app