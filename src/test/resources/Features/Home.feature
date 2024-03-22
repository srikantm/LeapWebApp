Feature: Home


  Scenario: Verify Most Popular functionality
    Given LeapReward url is up and running
    When try to navigate to Most popular See All section
    Then I should able to navigate successfully