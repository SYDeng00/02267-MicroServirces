Feature: Payment

  Scenario: Create a payment with valid account
    Given merchantID, token, amount
    When the service create a payment
    Then Ask bank for transaction
    Then the transaction succeed

  Scenario: Create a payment without valid customer account
    Given merchantID, token, amount
    When the service ask for authentication
    Then The token is invalid