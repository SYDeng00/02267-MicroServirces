Feature: Payment
###################################Test call bank function###################################
  Scenario: Create a payment with valid account
    Given merchantID, token
    Given affordedAmount
    When the service create a payment
    Then Ask bank for transaction
    Then the transaction succeed

#  Scenario: Create a payment without valid customer account
#    Given merchantID, token, amount
#    When the service ask for authentication
#    Then The token is invalid

   Scenario: Create a payment customer cannot affort
     Given merchantID, token
    Given unAffordedAmount
     When the service create a payment customer cannot affort
     Then Ask bank for transaction a lot of money
     Then The transaction failed for customer cannot afford


###################################Test refund function###################################



###################################Test ask back account function###################################
