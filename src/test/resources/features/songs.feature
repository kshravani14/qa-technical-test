Feature: Songs api tests


  @get @api @songs
  Scenario Outline: On performing POST song and GET api call for song by id then I should get all the details

#    Given I create default song record using 'VideoResource' endpoint with POST method
    Given I create songs records for below details using 'VideoResource' endpoint with POST method
      | <Singer_Name> | <Song_Name> |
    When I try do a 'VideoResource' GET by id call to fetch above created song
    Then the response code is 200
    And the response body has 'artist' to be '<Singer_Name>'
    And the response body has 'song' to be '<Song_Name>'
    And the response body has '__v' to be '0'
    And the response body has '_id' is 'NOT_NULL'
    And the response body has 'publishDate' is 'NOT_NULL'
    And the response body has 'date_created' is 'NOT_NULL'
    And I delete the created record using 'VideoResource' endpoint with DELETE method

    Examples:
      | Singer_Name | Song_Name  |
      | Lady Gaga   | Poker Face |
      | 1xx23£$%!   | 23&^%$ab1  |


  @getall @post @api @songs @get @clerdata
  Scenario: On performing POST songs and GET api call then I should get all songs the details

    Given I create songs records for below details using 'VideoResource' endpoint with POST method
      | Lady Gaga     | Poker Face   |
      | Katy Perry    | Dark Horse   |
      | Ariana Grande | Side to Side |
    When I try do a 'VideoResource' GET call to fetch all above created song
    Then the response code is 200
    And the response body has all the songs created
    And I delete  all the created record using 'VideoResource' endpoint with DELETE method


  @getall @api @songs @patch
  Scenario: On performing PATCH api call on song api then the song be patched appropriatly

    Given I create songs records for below details using 'VideoResource' endpoint with POST method
      | Ella | Cry Me A River |
    When I try do a 'VideoResource' PATCH call to fetch all above created song
    Then the response code is 501
    And the response body has message 'Not implemented.'


  @post @api @songs
  Scenario Outline: On performing POST song then response to be as expected

    Given I create songs records for below details using 'VideoResource' endpoint with POST method
      | <Singer_Name> | <Song_Name> |
    Then the response code is 201
    And the response body has 'artist' to be '<Singer_Name>'
    And the response body has 'song' to be '<Song_Name>'
    And the response body has '__v' to be '0'
    And the response body has '_id' is 'NOT_NULL'
    And the response body has 'publishDate' is 'NOT_NULL'
    And the response body has 'date_created' is 'NOT_NULL'

    Examples:
      | Singer_Name | Song_Name  |
      | Lady Gaga   | Poker Face |

  @delete @api @songs
Scenario: On performing DELETE song then response to be as expected
  Given I create songs records for below details using 'VideoResource' endpoint with POST method
    | Lady Gaga     | Poker Face   |
  When I delete the created record using 'VideoResource' endpoint with DELETE method
  Then the response code is 204
  And the response body has message ''


  @negative @post
  Scenario: On performing POST song then Negative response to be as expected

    Given I create json body with below details for 'VideoResource' endpoint with POST method
      | song        | Poker Face |
      | publishDate | 10         |
    Then the response code is 500
    And the response body has message 'Video validation failed'



