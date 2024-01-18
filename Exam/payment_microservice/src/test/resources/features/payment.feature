Feature: Payment
###################################Test call bank function###################################
  # @author Yingli
  Scenario: Create a payment with valid account
    Given merchantID, token
    Given affordedAmount
    When the service create a payment
    Then Ask bank for transaction
    Then the transaction succeed

    # @author Yingli
   Scenario: Create a payment customer cannot affort
     Given merchantID, token
    Given unAffordedAmount
     When the service create a payment customer cannot affort
     Then Ask bank for transaction a lot of money
     Then The transaction failed for customer cannot afford


###################################Test refund function###################################
  Scenario: Create a refund with valid account
    Given merchantID, token
    Given affordedAmount
    Given refundId
    When the service create a refund
    Then Ask bank for refund transaction
    Then the refund transaction succeed

  Scenario: Refund failed due to unAffordedAmount
    Given merchantID, token
    Given refundId
    When The service creates a refund that the merchant cannot afford
    Then Ask bank for refund a lot of money
    Then the refund transaction failed


###################################Test ask back account function###################################
  # @author Yingli
  Scenario Outline: Received token validation result with true
    Given merchantID, token
    Given customerID
    When Received token validation result "<result>"
    When the service create a payment
    Then Send message to corresponding services
    Examples:
      | result |
      | true   |
      | false  |

