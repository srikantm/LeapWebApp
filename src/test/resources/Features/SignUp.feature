Feature: Signup

  @smoke
  Scenario: Sign up with ValidData
    Given LeapReward url is up and running
    When try to signup with valid data
    Then I should able to sin up successfully
