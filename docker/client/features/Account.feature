#Author: Siyuan Deng

Feature: Account

  Scenario: Create customer DTUPay account 
    Given the customer "First_C0480211010" "Last_C0811240010" with CPR "12304d111200180" with balance 1000
    When the customer has registered with DTUPay
    Then we receive a customer dtuPayId

  Scenario: Create merchant DTUPay account
    Given the merchant "First_M0148010210" "Last_M0810104210" with CPR "454161d420800110" with balance 1000
    When the merchant has registered with DTUPay
    Then we receive a merchant dtuPayId



