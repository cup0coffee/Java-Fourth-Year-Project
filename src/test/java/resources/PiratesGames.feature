
@tag
Feature: Pirates Game Part 1 Testing

  @line45
  Scenario Outline: line 45: die with 3 skulls 5 swords on first roll: player gets a score of 0
    Given game setup
    Given a player object
    Given default gold card
    When roll is <roll>
    Then death with <roll>
    And <score> is <roll>
    Examples:
      |roll					| score	|
      |"Skull" "Skull" "Skull" "Sword" "Sword" "Sword" "Sword" "Sword" 		|	0			|

  @line46
  Scenario Outline: line 46: roll 1 skull, 4 parrots, 3 swords, reroll 3 swords, get 2 skulls 1 sword  die
    Given game setup
    Given a player object
    Given default gold card
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    Then death with <reroll>
    Examples:
      |roll					| held | reroll	|
      |"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Sword" "Sword" "Sword" 		| "1,2,3,4,5"		|	"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" "Skull" "Sword" 			|


