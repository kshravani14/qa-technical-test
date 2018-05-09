Feature: Delete a Playlist

  Background:
    Given I create a 'Video' with data as follows
      | artist    | song       |
      | Lady Gaga | Poker Face |
    And I create a 'Playlist' with data as follows
      | desc           | title      |
      | New Playlist 2 | Playlist 2 |


  Scenario: Deleting a playlist successfully

    And I add created video to playlist
    When I delete created playlist
    Then the response code is 204


  @failing @negative
  Scenario Outline: Try to delete non existing playlist

    When I delete non existing playlist with <Id>
    Then the response code is 404

    Examples:
      | Id     |
      | abcdf" |

