Feature: Adding videos to playlist

  Background:
    Given I create a 'Video' with data as follows
      | artist    | song       |
      | Lady Gaga | Poker Face |
    And I create a 'Playlist' with data as follows
      | desc           | title      |
      | New Playlist 2 | Playlist 2 |



  Scenario: Adding videos to a playlist

    When I add created video to playlist
    Then the response code is 204


  Scenario: Removing videos from a playlist

    When I add created video to playlist
    Then the response code is 204

    When I remove created video from playlist
    Then the response code is 501



  Scenario Outline: Trying to add invalid videos to a existing playlist

    When I add invalid video <ID> to playlist
    Then the response code is 500
    And the response body contains 'key' to be 'value'
      | key  | value     |
      | name | CastError |

    Examples:
      | ID         |
      | abcdfhxdks |
      |            |


  @failing @patch
  Scenario Outline: Trying to add videos to a non existing playlist

    When I add created video to a non existing playlist with <ID>
    Then the response code is 500

    Examples:
      | ID         |
      | abcdfhxdks |
