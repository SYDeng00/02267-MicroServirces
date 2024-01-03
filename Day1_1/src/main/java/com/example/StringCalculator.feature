Feature: String calculator
  Scenario: Add empty string
    Given a calculator
    When I add ""
    Then the result should be 0

  Scenario: Add one number
    Given a calculator
    When I add "1"
    Then the result should be 1

  Scenario: Add two numbers
    Given a calculator
    When I add "1,2"
    Then the result should be 3

  Scenario: Add three numbers
    Given a calculator
    When I add "1\n2,3"
    Then the result should be 6


  # Additional scenarios here...



