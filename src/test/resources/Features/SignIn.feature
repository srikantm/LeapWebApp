Feature: SignIn

  @smoke
    @TC001
  Scenario: Sign in with ValidData
    Given LeapReward url is up and running
    When try to sign in with user id and password
    Then I should able to logged in to the application
