Feature: Report Generation and Retrieval

  Scenario: Generating reports for a customer
    Given a customer with ID
    When I generate reports for the customer
    Then the reports for the customer should be successfully generated

  Scenario: Retrieving the latest five reports for a customer
    Given a customer with ID
    When I retrieve the latest five reports for the customer
    Then I should receive the latest five reports for the customer

  Scenario: Generating reports for a merchant
    Given a merchant with ID
    When I generate reports for the merchant
    Then the reports for the merchant should be successfully generated

  Scenario: Retrieving the latest five reports for a merchant
    Given a merchant with ID
    When I retrieve the latest five reports for the merchant
    Then I should receive the latest five reports for the merchant

  Scenario: Generating a summary report for the manager
    Given a manager requests a summary report
    Then the summary report should be successfully provided to the manager
