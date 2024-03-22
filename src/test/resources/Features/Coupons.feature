Feature: Coupons

@m1
  Scenario: Verify Claim functionality for 1$
    Given LeapReward url is up and running
    When try to claim coupons for 1Dollar
    Then I should able to claim coupons successfully
@m1
  Scenario: Verify Claim functionality for 2$
    Given LeapReward url is up and running
    When try to claim coupons for 2Dollar
    Then I should able to claim coupons successfully
