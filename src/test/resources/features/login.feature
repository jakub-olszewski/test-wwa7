@featureTest
Feature: Login page
  I want to test login page security

  @Scenario1
  Scenario: Login with only filled username
    Given the login page
    When I write a username "administrator"
    And I write a password "administrator"
    And I click a button login
    Then I see title page which contain "Zaloguj się do Facebooka | Facebook"

  @Scenario2
  Scenario: Register account
    Given the login page

