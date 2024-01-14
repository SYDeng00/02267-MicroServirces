Feature: Payment
#  Scenario: Successful Payment
#    Given a customer with a bank account with balance 1000
#    And that the customer is registered with DTU Pay
#    Given a merchant with a bank account with balance 2000
#    And that the merchant is registered with DTU Pay
#    When the merchant initiates a payment for 100 kr by the customer
#    Then the payment is successful
#    And the balance of the customer at the bank is 900 kr
#    And the balance of the merchant at the bank is 2100 kr

  Scenario: Successful Payment Transaction
    Given After getting the transaction information from the merchant with message ID "UUID", merchant ID "UUID", token "string", and amount "BigDecimal", publish the message event that validates the customer's token
    Then Validate customer's token message event is published
    When After getting the validation result of the customer's token, post a message event to get the bank account numbers of both parties
    Then The message event to get the bank account numbers of both parties has been published
    When After obtaining the bank account numbers of both parties, the payment operation is performed and the message event updating the payment report is published
    Then The message event of updating the payment report is released, and the payment is successful

  Scenario: Failed Payment Transaction Due to Invalid Token
    Given After getting the transaction information from the merchant, publish a message event validating the customer's token
    Then A message event validating the customer's token is posted
    When After getting the validation result of the customer's token is invalid, publish the payment result message event
    Then The payment result message event has been posted, and the payment failed