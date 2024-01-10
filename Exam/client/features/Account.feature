#Author: Siyuan Deng

Feature: Account

  Scenario: Create customer DTUPay account 
    Given the customer "First_C00010" "Last_C00010" with CPR "12300010" with balance 1000
    When the customer has registered with DTUPay
    Then we receive a customer dtuPayId

  Scenario: Create merchant DTUPay account
    Given the merchant "First_M00010" "Last_M00010" with CPR "454600010" with balance 1000
    When the merchant has registered with DTUPay
    Then we receive a merchant dtuPayId



