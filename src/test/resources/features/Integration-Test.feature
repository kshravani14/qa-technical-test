Feature: Integration tests between playlist and video services

  Background:
    Given I create a 'Video' with data as follows
      | artist    | song       |
      | Lady Gaga | Poker Face |
    And I create a 'Playlist' with data as follows
      | desc           | title      |
      | New Playlist 2 | Playlist 2 |
    And I add created video to playlist

  @failing
  Scenario: checking videos in playlist after deleting created video

    When I delete the video
    And  I get a created playlist
    Then the response code is 200
    And the response body contains 'key' to be 'value'
      | key          | value          |
      | videos       | 0              |
