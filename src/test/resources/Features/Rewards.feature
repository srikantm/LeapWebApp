Feature: Rewards

  @smoke
  Scenario: Verify Donate a Meal functionality
    Given LeapReward url is up and running
    When try to donate a meal in Do Good Rewards section
    Then I should able to do donate a meal


#  @smoke @ignore
#  Scenario: Verify Plant tree functionality
#    Given LeapReward url is up and running
#    When try to plant a tree for the rewards
#    Then I should able use plant a tree