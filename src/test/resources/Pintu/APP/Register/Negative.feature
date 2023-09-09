Feature: APP - Register

  Scenario: New user see error message after click register button without input anything
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User click 'REGISTER' button
    Then User see error notification in name textfield
    And User close the app

  Scenario: New user see error message after click register button with invalid email format
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User input 'TesterOne' in name field
    And User input 'TestingEmail.com' in email field
    And User input 'Testing123' in password field
    And User input 'Testing123' in confirm password field
    And User click 'REGISTER' button
    Then User see error notification in email textfield
    And User close the app

  Scenario: New user see error message after click register button without input email
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User input 'TesterOne' in name field
    And User input 'Testing123' in password field
    And User input 'Testing123' in confirm password field
    And User click 'REGISTER' button
    Then User see error notification in email textfield
    And User close the app

  Scenario: New user see error message after click register button with input registered email
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User input 'TesterOne' in name field
    And User input 'Testing@gmail.com' in email field
    And User input 'Testing123' in password field
    And User input 'Testing123' in confirm password field
    And User click 'REGISTER' button
    Then User see notification registration success
    And User navigate back
    When User click 'No account yet? Create one' button
    And User input 'TesterOne' in name field
    And User input 'Testing@gmail.com' in email field
    And User input 'Testing123' in password field
    And User input 'Testing123' in confirm password field
    And User click 'REGISTER' button
    Then User see notification email already exists
    And User close the app

  Scenario: New user see error message after click register button without input any password
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User input 'TesterOne' in name field
    And User input random email in email textfield
    And User input 'Testing123' in confirm password field
    And User click 'REGISTER' button
    Then User see error notification in password textfield
    And User close the app

  Scenario: New user see error message after click register button without input confirm password textfield
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User input 'TesterOne' in name field
    And User input random email in email textfield
    And User input 'Testing123' in password field
    And User click 'REGISTER' button
    Then User see error notification in confirm password textfield
    And User close the app

  Scenario: New user see error message after click register button if input password not matches with confirm password
    Given User open Login Register App
    And User redirect to login page
    When User click 'No account yet? Create one' button
    And User input 'TesterOne' in name field
    And User input random email in email textfield
    And User input 'Testing123' in password field
    And User input 'Testing122' in confirm password field
    And User click 'REGISTER' button
    Then User see error notification in confirm password textfield
    And User close the app











