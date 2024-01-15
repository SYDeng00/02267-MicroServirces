#Author: Siyuan Deng

Feature: Account

  Scenario: Create customer DTUPay account 
    Given the customer "First_C048021010" "Last_C081240010" with CPR "123041200180" with balance 1000
    When the customer has registered with DTUPay
    Then we receive a customer dtuPayId

  Scenario: Create merchant DTUPay account
    Given the merchant "First_M048010210" "Last_M080104210" with CPR "4541642080010" with balance 1000
    When the merchant has registered with DTUPay
    Then we receive a merchant dtuPayId



