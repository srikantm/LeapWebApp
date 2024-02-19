Feature: Coupons

  @smoke @tc003
  Scenario: Verify Claim functionality
    Given LeapReward url is up and running
    When try to claim coupons
    Then I should able to claim coupons sucessfully
