#Author: Siyuan Deng

Feature: Token test

  Scenario: Get tokens as a customer
    Given the customer "Token11" "Token21" with CPR "Tokedddn31" with balance 1000
    When the customer has registered with DTUPay
    When the customer asks for 1 tokens
    Then the customer receives tokens

  Scenario: Customer ask tokens when she own more than 1 tokens
    Given the customer "Token11" "Token21" with CPR "Tokedddn31" with balance 1000
    And the customer has registered with DTUPay
    And the customer asks for 2 tokens
    Then the customer receives tokens
    When the customer asks for 1 tokens again
    Then the request for more tokens failed
  
  #Author: Yingli Duan
  Scenario: Customer ask tokens with more than 7 tokens
	  Given the customer "Token11" "Token21" with CPR "Token31" with balance 1000
	  And the customer has registered with DTUPay
	  And the customer asks for 7 tokens
	  Then the request for more tokens failed