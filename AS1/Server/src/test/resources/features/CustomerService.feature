Feature: Customer Service

  @queriesAccountBalance
  Scenario: User queries their account balance
    Given a user with a bank account
    When the user queries for their account balance
    Then the account balance should be displayed
