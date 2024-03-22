Feature: Settings

@m1
  Scenario: Verify Terms & Condition functionality
    Given LeapReward url is up and running
    When try to navigate Terms and Conditions in Settings
    Then I should able to navigate to the respective Term Condition Page

@m1
  Scenario: Verify Privacy policy functionality
    Given LeapReward url is up and running
    When try to navigate Privacy Policy in Settings
    Then I should able to navigate to the respective Privacy Page

@m1
  Scenario: Verify change password functionality
    Given LeapReward url is up and running
    When try to change password in Settings
    Then I should able to change the password for the account

  @m1
  Scenario: Verify change Name functionality
    Given LeapReward url is up and running
    When try to change name in Settings
    Then I should able to change the name for the account
