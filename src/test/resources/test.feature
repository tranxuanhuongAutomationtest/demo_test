@test
Feature: QA Test Survey

  Scenario: QA Test Survey
    Given Open web page monkeysurvey successfully
    When  Click to Good raidobutton
    And Click to Next Button
    Then Verify "Have a nice day." exists

  Scenario: QA Test Survey2
    Given Open web page monkeysurvey successfully
    When  Click to Good raidobutton
    And Click to Next Button
    Then Verify "Have a nice day." exists