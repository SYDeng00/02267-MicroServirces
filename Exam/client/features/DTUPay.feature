#Author: Siyuan Deng

Feature: Account

  Scenario: Create customer DTUPay account 
    Given the customer "First_C0000" "Last_C0000" with CPR "1230000" with balance 1000
    When the customer has registered with DTUPay
    Then we receive a customer dtuPayId
    
  Scenario: Create merchant DTUPay account
    Given the merchant "First_M0000" "Last_M0000" with CPR "45460000" with balance 1000
    When the merchant has registered with DTUPay
    Then we receive a merchant dtuPayId



