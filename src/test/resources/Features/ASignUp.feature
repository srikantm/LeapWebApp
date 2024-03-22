Feature: Signup

  @m1 @TC001
  Scenario: Sign up with ValidData
    Given LeapReward url is up and running
    When try to signup with valid data
    Then I should able to sign up successfully
