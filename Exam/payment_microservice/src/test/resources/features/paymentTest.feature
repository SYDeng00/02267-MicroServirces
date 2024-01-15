Feature: Payment

  Scenario: Successful Payment Transaction
    Given After getting the transaction information from the merchant with message ID "string", merchant ID "string", token "string", and amount "int", publish the message event that validates the customer's token
    Then Validate customer token message event is published
    When After getting the validation result of the customer token, post a message event to get the bank account numbers of both parties
    Then The message event to get the bank account numbers of both parties has been published
    When After obtaining the bank account numbers of both parties, the payment operation is performed and the message event updating the payment report is published
    Then The message event of updating the payment report is released, and the payment is successful

  Scenario: Failed Payment Transaction Due to Invalid Token
    Given After getting the transaction information from the merchant, publish a message event validating the customer token
    Then A message event validating the customer token is posted
    When After getting the validation result of the customer token is invalid, publish the payment result message event
    Then The payment result message event has been posted, and the payment failed