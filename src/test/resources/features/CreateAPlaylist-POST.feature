Feature: creating a playlist

  Scenario Outline: Create a new playlist

    When I create a 'Playlist' with data as follows
      | desc   | title   |
      | <desc> | <title> |
    Then the response code is 201
    And the response body contains 'key' to be 'value'
      | key          | value        |
      | desc         | new playlist |
      | title        | Test1        |
      | __v          | 0            |
      | _id          | 'NOT_NULL'   |
      | videos       | 0            |
      | date_created | 'NOT_NULL'   |
    Examples:
      | desc         | title |
      | new playlist | Test1 |


  @failing
  Scenario Outline: creating a playlist with invalid values

    When I create a 'Playlist' with data as follows
      | desc   | title   |
      | <desc> | <title> |
    Then the response code is 500
    Examples:
      | desc         | title |
#      passing empty values
      |              |       |
#      passing existing playlist values
      | new playlist | Test1 |
#      passing special chars
      | !@££$        | £$$%  |
