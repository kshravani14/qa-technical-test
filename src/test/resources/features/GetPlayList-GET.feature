Feature: Get playlist

  Background:

    Given I create a 'Video' with data as follows
      | artist    | song       |
      | Lady Gaga | Poker Face |
    And I create a 'Playlist' with data as follows
      | desc           | title      |
      | New Playlist 2 | Playlist 2 |
    And I add created video to playlist

  @GetAll @CleanPlayList
  Scenario: Get all Playlists created
    And I create a 'Playlist' with data as follows
      | desc           | title      |
      | New playlist 3 | Playlist 3 |
    When I get all creted playlists
    Then the response code is 200
    And the response body contains all created playlists

  @GetAPlaylist
  Scenario: Get an existing playlist
    When I get a created playlist
    Then the response code is 200
    And the response body contains 'key' to be 'value'
      | key          | value          |
      | desc         | New Playlist 2 |
      | title        | Playlist 2     |
      | __v          | 0              |
      | _id          | 'NOT_NULL'     |
      | videos       | 1              |
      | date_created | 'NOT_NULL'     |

  @Negative
  Scenario Outline: Trying to get a non existing playlist
    When I try to get a non existing playlist with <ID>
    Then the response code is 500
    And the response body contains 'key' to be 'value'
      | key  | value     |
      | name | CastError |
    Examples:
      | ID      |
      | abcdsef |
