
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
    And <score> is <reroll>
    Examples:
      |roll					| held | reroll	| score |
      |"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Sword" "Sword" "Sword" 		| "1,2,3,4,5"		|	"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" "Skull" "Sword" 			| 0 |

  @line47
  Scenario Outline: line 47: roll 2 skulls, 4 parrots, 2 swords, reroll swords, get 1 skull 1 sword  die (all code exists already for test)
    Given game setup
    Given a player object
    Given default gold card
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    Then death with <reroll>
    And <score> is <reroll>
    Examples:
      |roll					| held | reroll	| score |
      |"Skull" "Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Sword" "Sword" 		| "1,2,3,4,5,6"		|	"Skull" "Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" "Sword" 			| 0 |

  @line48
  Scenario Outline: line 48: roll 1 skull, 4 parrots, 3 swords, reroll swords, get 1 skull 2 monkeys reroll 2 monkeys, get 1 skull 1 monkey and die (all code exists already for test)
    Given game setup
    Given a player object
    Given default gold card
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    Then death with <reroll2>
    And <score> is <reroll2>
    Examples:
      |roll					| held | reroll	| held2 |reroll2	|score |
      |"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Sword" "Sword" "Sword"  		| "1,2,3,4,5"		|	"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" "Monkey" "Monkey" | "1,2,3,4,5,6" |	"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" "Skull" "Monkey" 			| 0 |

  @line50
  Scenario Outline: line 50: roll 1 skull, 2 parrots, 3 swords, 2 coins, reroll parrots get 2 coins reroll 3 swords, get 3 coins (SC 4000 for seq of 8 (with FC coin) + 8x100=800 = 4800)
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    And <score> is <reroll2>
    Examples:
      |card |roll					| held | reroll	| held2 |reroll2	|score |
      | 4 |"Skull" "Parrot" "Parrot" "Sword" "Sword" "Sword" "Coin" "Coin"  		| "1,4,5,6,7,8"		|	"Skull" "Coin" "Coin" "Sword" "Sword" "Sword" "Coin" "Coin" | "1,2,3,7,8" |	"Skull" "Coin" "Coin" "Sword" "Sword" "Sword" "Coin" "Coin"			| 4800 |
