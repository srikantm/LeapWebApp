Feature: Upload functionality

  Scenario: Verify the upload functionality
    Given LeapReward url is up and running
    When try to upload a script
    Then I should able to upload script successfully