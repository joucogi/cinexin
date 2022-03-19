Feature: Cinexin Health Check

  Scenario: Health Check
    Given account balance is 0.0
    When the account is credited with 10.0
    Then account should have a balance of 10.0