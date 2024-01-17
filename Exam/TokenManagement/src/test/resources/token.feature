Feature: Token

  Scenario: Generating tokens for a customer
    Given a customer with ID
    When I request tokens for the customer
    Then tokens should be successfully generated for the customer

  Scenario: Validating an expired token
    Given an expired token is available for a customer
    When I validate the token
    Then the token should be marked as invalid
