Feature: Payment
Scenario: Successful Payment
Given a customer with a bank account with balance 980
And that the customer is registered with DTU Pay
Given a merchant with a bank account with balance 1020
And that the merchant is registered with DTU Pay
When the merchant initiates a payment for 10 kr by the customer
Then the payment is successful
And the balance of the customer at the bank is 970 kr
And the balance of the merchant at the bank is 1030 kr