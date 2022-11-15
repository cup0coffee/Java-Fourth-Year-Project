
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
      | 4 |"Skull" "Parrot" "Parrot" "Sword" "Sword" "Sword" "Coin" "Coin"  		| "1,4,5,6,7,8"		|	"Skull" "Coin" "Coin" "Sword" "Sword" "Sword" "Coin" "Coin" | "1,2,3,7,8" |	"Skull" "Coin" "Coin" "Coin" "Coin" "Coin" "Coin" "Coin"			| 4800 |

  @line52
  Scenario Outline: line 52: score first roll with 2 (monkeys/parrot/diamonds/coins) and FC is captain (SC 800) (all code exists already for test)
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 1 |"Monkey" "Monkey" "Parrot" "Parrot" "Diamond" "Diamond" "Coin" "Coin" 		| 800 |

  @line53
  Scenario Outline: line 53: roll 2 (monkeys/skulls/swords/parrots), reroll parrots and get 1 sword & 1 monkey (SC 300 since FC is coin) (all code exists already for test)
    Given game setup
    Given a player object
    Given default gold card
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |roll					| held | reroll	| score |
      |"Monkey" "Monkey" "Skull" "Skull" "Parrot" "Parrot" "Sword" "Sword" 		| "1,2,3,4,7,8"	|	"Monkey" "Monkey" "Skull" "Skull" "Sword" "Monkey" "Sword" "Sword" 			| 300 |

  @line54
  Scenario Outline: line 54: roll 3 (monkey, swords) + 2 skulls and score   (SC 300)
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Monkey" "Monkey" "Monkey" "Sword" "Sword" "Sword" "Skull" "Skull" | 300|

  @line55
  Scenario Outline: line 55: roll 3 diamonds, 2 skulls, 1 monkey, 1 sword, 1 parrot, score (diamonds = 100 + 300 points)   (SC 500)
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Diamond" "Diamond" "Diamond" "Monkey" "Sword" "Parrot" "Skull" "Skull" 		| 500 |

  @line56
  Scenario Outline: line 56: roll 4 coins, 2 skulls, 2 swords and score (coins: 200 + 400 points) with FC is a diamond (SC 700) (all code exists already for test)
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 5 |"Coin" "Coin" "Coin" "Coin" "Sword" "Sword" "Skull" "Skull" 		| 700 |

  @line57
  Scenario Outline: line 57: roll 3 swords, 4 parrots, 1 skull and score (SC 100+200+100= 400) (all code exists already for test)
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Parrot" "Parrot" "Parrot" "Parrot" "Sword" "Sword" "Sword" "Skull" 		| 400 |

  @line58
  Scenario Outline: line 58: roll 1 skull, 2 coins/parrots & 3 swords, reroll parrots, get 1 coin and 1 sword, score (SC = 200+400+200 = 800) - ALL CODE EXISTS
    Given game setup
    Given a player object
    Given default gold card
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |roll					| held | reroll	| score |
      |"Skull" "Coin" "Coin" "Sword" "Parrot" "Parrot" "Sword" "Sword" 		| "1,2,3,4,7,8"	|	"Skull" "Coin" "Coin" "Sword" "Coin" "Sword" "Sword" "Sword"			| 800 |

  @line59
  Scenario Outline: line 59: same as previous row but with captain fortune card  (SC = (100 + 300 + 200)*2 = 1200)
    Given game setup
    Given a player object
    Given card is <fcard>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |fcard |roll					| held | reroll	| score |
      | 1 |"Skull" "Coin" "Coin" "Sword" "Parrot" "Parrot" "Sword" "Sword" 		| "1,2,3,4,7,8"	|	"Skull" "Coin" "Coin" "Sword" "Coin" "Sword" "Sword" "Sword"			| 1200 |

  @line60
  Scenario Outline: line 60: roll 1 skull, 2 (monkeys/parrots) 3 swords, reroll 2 monkeys, get 1 skull 1 sword, then reroll parrots get 1 sword 1 monkey (SC 600) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <fcard>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    And <score> is <reroll2>
    Examples:
      |fcard |roll					| held | reroll	|  held2 | reroll2	|score |
      | 4 |"Skull" "Monkey" "Monkey" "Parrot" "Parrot" "Sword" "Sword" "Sword" 		| "1,4,5,6,7,8"	|	"Skull" "Skull" "Sword" "Parrot" "Parrot" "Sword" "Sword" "Sword"	| "1,2,3,6,7,8"	|	"Skull" "Skull" "Sword" "Sword" "Monkey" "Sword" "Sword" "Sword"		| 600 |


  @line62
  Scenario Outline: line 62: score set of 6 monkeys and 2 skulls on first roll (SC 1100) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Monkey" "Monkey" "Monkey" "Monkey" "Monkey" "Monkey" "Skull" "Skull" 		| 1100 |

  @line63
  Scenario Outline: line 63: score set of 7 parrots and 1 skull on first roll (SC 2100) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Parrot" "Parrot" "Parrot" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" 		| 2100 |

  @line64
  Scenario Outline: line 64: score set of 8 coins on first roll (SC 5400)  seq of 8 + 9 coins(FC is coin) +  full chest  (no extra points for 9 coins) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Coin" "Coin" "Coin" "Coin" "Coin" "Coin" "Coin" "Coin" 		| 5400 |

  @line65
  Scenario Outline: line 65: score set of 8 coins on first roll and FC is diamond (SC 5400) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 5 |"Coin" "Coin" "Coin" "Coin" "Coin" "Coin" "Coin" "Coin" 		| 5400 |

  @line66
  Scenario Outline: line 66: score set of 8 swords on first roll and FC is captain (SC 4500x2 = 9000) since full chest - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 1 |"Sword" "Sword" "Sword" "Sword" "Sword" "Sword" "Sword" "Sword" 		| 9000 |

  @line67
  Scenario Outline: line 67: roll 6 monkeys and 2 swords, reroll swords, get 2 monkeys, score (SC 4600 because of FC is coin and full chest)  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given default gold card
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |roll					| held | reroll	| score |
      |"Monkey" "Monkey" "Monkey" "Monkey" "Monkey" "Monkey" "Sword" "Sword" 		| "1,2,3,4,5,6"	|	"Monkey" "Monkey" "Monkey" "Monkey" "Monkey" "Monkey" "Monkey" "Monkey"			| 4600 |

  @line68
  Scenario Outline: line 68: roll 2 (monkeys/skulls/swords/parrots), reroll parrots, get 2 diamonds, score with FC is diamond (SC 400)  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |5   | "Monkey" "Monkey" "Skull" "Skull" "Parrot" "Parrot" "Sword" "Sword" 		| "1,2,3,4,7,8"	|	"Monkey" "Monkey" "Skull" "Skull" "Diamond" "Diamond" "Sword" "Sword"			| 400 |

  @line69
  Scenario Outline: line 69: roll 2 (monkeys, skulls, swords), 1 diamond, 1 parrot, reroll 2 monkeys, get 2 diamonds, score 500 - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |4   | "Monkey" "Monkey" "Skull" "Skull" "Diamond" "Parrot" "Sword" "Sword" 		| "3,4,5,6,7,8"	|	"Diamond" "Diamond" "Skull" "Skull" "Diamond" "Parrot" "Sword" "Sword"			| 500 |

  @line70
  Scenario Outline: line 70: roll 1 skull, 2 coins, 1 (monkey/parrot), 3 swords, reroll 3 swords, get 1 (coin/monkey/parrot)  (SC 600) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |4   | "Skull" "Coin" "Coin" "Monkey" "Parrot" "Sword" "Sword" "Sword" 		| "1,2,3,4,5"	|	"Skull" "Coin" "Coin" "Monkey" "Parrot" "Coin" "Monkey" "Parrot"			| 600 |

  @line71
  Scenario Outline: line 71: roll 1 skull, 2 coins, 1 (monkey/parrot), 3 swords, reroll swords, get 1 (coin/monkey/parrot) with FC is diamond (SC 500) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |5   | "Skull" "Coin" "Coin" "Monkey" "Parrot" "Sword" "Sword" "Sword" 		| "1,2,3,4,5"	|	"Skull" "Coin" "Coin" "Monkey" "Parrot" "Coin" "Monkey" "Parrot"			| 500 |

  @line72
  Scenario Outline: line 72: get 4 monkeys, 2 coins and 2 skulls with FC coin. Score 600 - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Monkey" "Monkey" "Monkey" "Monkey" "Coin" "Coin" "Skull" "Skull" 		| 600 |

  @line77
  Scenario Outline: line 77: roll 2 diamonds, 1 (sword/monkey/coin), 3 parrots, reroll 3 parrots, get 1 skull, 2 monkeys, reroll skull, get monkey (SC 500) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    And <score> is <reroll2>
    Examples:
      |card |roll					| held | reroll	| held2| reroll2| score |
      | 2 |"Diamond" "Diamond" "Sword" "Monkey" "Coin" "Parrot" "Parrot" "Parrot" 		| "1,2,3,4,5"	|	"Diamond" "Diamond" "Sword" "Monkey" "Coin" "Skull" "Monkey" "Monkey"			| "1,2,3,4,5,7,8"	|	"Diamond" "Diamond" "Sword" "Monkey" "Coin" "Monkey" "Monkey" "Monkey"		| 500 |

  @line78
  Scenario Outline: line 78: roll 3 skulls, 3 parrots, 2 swords, reroll skull, get parrot, reroll 2 swords, get parrots, score (SC 1000) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    And <score> is <reroll2>
    Examples:
      |card |roll					| held | reroll	| held2| reroll2| score |
      | 2 |"Skull" "Skull" "Skull" "Parrot" "Parrot" "Parrot" "Sword" "Sword" 		| "2,3,4,5,6,7,8"	|	"Parrot" "Skull" "Skull" "Parrot" "Parrot" "Parrot" "Sword" "Sword" 			| "1,2,3,4,5,6"	|	"Parrot" "Skull" "Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Parrot"		| 1000 |

  @line79
  Scenario Outline: line 79: roll 1 skull, 4 parrots, 3 monkeys, reroll 3 monkeys, get 1 skull, 2 parrots, reroll skull, get parrot, score (SC 2000) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    And <score> is <reroll2>
    Examples:
      |card |roll					| held | reroll	| held2| reroll2| score |
      | 2 |"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Monkey" "Monkey" "Monkey" 		| "1,2,3,4,5"	|	"Skull" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" "Parrot" "Parrot" 			| "2,3,4,5,6,7,8"	|	"Parrot" "Parrot" "Parrot" "Parrot" "Parrot" "Skull" "Parrot" "Parrot"		| 2000 |

  @line82
  Scenario Outline: line 82: roll 3 monkeys 3 parrots  1 skull 1 coin  SC = 1100  (i.e., sequence of of 6 + coin) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 6 |"Monkey" "Monkey" "Monkey" "Parrot" "Parrot" "Parrot" "Skull" "Coin" 		| 1100 |

  @line83
  Scenario Outline: line 83: roll 2 (monkeys/swords/parrots/coins), reroll 2 swords, get 1 monkey, 1 parrot, score 1700 - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      | 6 |"Monkey" "Monkey" "Sword" "Sword" "Parrot" "Parrot" "Coin" "Coin" 		| "1,2,5,6,7,8"	|	"Monkey" "Monkey" "Monkey" "Parrot" "Parrot" "Parrot" "Coin" "Coin"			| 1700 |

  @line84
  Scenario Outline: line 84: roll 3 skulls, 3 monkeys, 2 parrots => die scoring 0 - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    Then death with <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 6 |"Monkey" "Monkey" "Monkey" "Skull" "Parrot" "Parrot" "Skull" "Skull" 		| 0 |

  @line97
  Scenario Outline: line 97: 3 monkeys, 3 swords, 1 diamond, 1 parrot FC: coin   => SC 400  (ie no bonus) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Monkey" "Monkey" "Monkey" "Sword" "Sword" "Sword" "Parrot" "Diamond" 		| 400 |

  @line98
  Scenario Outline: line 98: 3 monkeys, 3 swords, 2 coins FC: captain   => SC (100+100+200+500)*2 =  1800 - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 1 |"Monkey" "Monkey" "Monkey" "Sword" "Sword" "Sword" "Coin" "Coin" 		| 1800 |

  @line99
  Scenario Outline: line 99: 3 monkeys, 4 swords, 1 diamond, FC: coin   => SC 1000  (ie 100++200+100+100+bonus) - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 4 |"Monkey" "Monkey" "Monkey" "Sword" "Sword" "Sword" "Sword" "Diamond" 		| 1000 |

  @line100
  Scenario Outline: line 100: FC: 2 sword sea battle, first  roll:  4 monkeys, 1 sword, 2 parrots and a coin then reroll 2 parrots and get 2nd coin and 2nd sword score is: 200 (coins) + 200 (monkeys) + 300 (swords of battle) + 500 (full chest) = 1200  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |3  | "Monkey" "Monkey" "Monkey" "Monkey" "Sword" "Parrot" "Parrot" "Coin" 		| "1,2,3,4,5,8"	|	"Monkey" "Monkey" "Monkey" "Monkey" "Sword" "Coin" "Sword" "Coin" 			| 1200 |

  @line103
  Scenario Outline: line 103: FC: monkey business and roll 2 monkeys, 1 parrot, 2 coins, 3 diamonds   SC 1200   - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 6 |"Monkey" "Monkey" "Parrot" "Coin" "Coin" "Diamond" "Diamond" "Diamond" 		| 1200 |

  @line106
  Scenario Outline: line 106: roll one skull and 7 swords with FC with two skulls => die
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    Then death with <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 8 |"Skull" "Sword" "Sword" "Sword" "Sword" "Sword" "Sword" "Sword" 		| 0 |

  @line107
  Scenario Outline: line 107: roll 2 skulls and 6 swords with FC with 1 skull  => die  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    Then death with <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 7 |"Skull" "Skull" "Sword" "Sword" "Sword" "Sword" "Sword" "Sword" 		| 0 |

  @line108
  Scenario Outline: line 108: roll 2 skulls  3(parrots/monkeys) with FC with two skulls: reroll 3 parrots get 2 skulls, 1 sword reroll sword and 3 monkeys, get 3 skulls and 1 sword, stop => -900 for other players (no negative score) & you score 0
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    And <score> is <reroll2>
    And <skullIslandScore> deduction is <reroll2>
    Examples:
      |card |roll					| held | reroll	| held2| reroll2| score | skullIslandScore |
      | 8 |"Skull" "Skull" "Monkey" "Monkey" "Monkey" "Parrot" "Parrot" "Parrot"		| "1,2,3,4,5"	|	"Skull" "Skull" "Monkey" "Monkey" "Monkey" "Skull" "Skull" "Sword" 			| "1,2,6,7"	|	"Skull" "Skull" "Skull" "Skull" "Skull" "Skull" "Skull" "Sword"		| 0 | -900 |


  @line110
  Scenario Outline: line 110: roll 5 skulls, 3 monkeys with FC Captain, reroll 3 monkeys, get 2 skulls, 1 coin, stop => -1400 for other players
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <skullIslandScore> deduction is <reroll>
    Examples:
      |card |roll					| held | reroll	| score | skullIslandScore |
      |1  | "Monkey" "Monkey" "Monkey" "Skull" "Skull" "Skull" "Skull" "Skull" 		| "4,5,6,7,8"	|	"Skull" "Skull" "Monkey" "Skull" "Skull" "Skull" "Skull" "Skull" 			| 0 | -1400 |

  @line111
  Scenario Outline: line 111: roll 3 skulls and 5 swords with FC with two skulls: reroll 5 swords, get 5 coins, must stop  => -500 for other players  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <skullIslandScore> deduction is <reroll>
    Examples:
      |card |roll					| held | reroll	| skullIslandScore |
      |8  | "Sword" "Sword" "Sword" "Sword" "Sword" "Skull" "Skull" "Skull" 		| "6,7,8"	|	"Coin" "Coin" "Coin" "Coin" "Coin" "Skull" "Skull" "Skull" | -500 |

  @line114
  Scenario Outline: line 114: FC 2 swords, roll 4 monkeys, 3 skulls & 1 sword and die   => die and lose 300 points  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    Then death with <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 3 |"Skull" "Skull" "Skull" "Sword" "Monkey" "Monkey" "Monkey" "Monkey" 		| -300 |

  @line115
  Scenario Outline: line 115: FC 3 swords, have 2 swords, 2 skulls and 4 parrots, reroll 4 parrots, get 4 skulls=> die and lose 500 points
    Given game setup
    Given player has 1000 points
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    Then death with <reroll>
    And player <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |9  | "Sword" "Sword" "Skull" "Skull" "Parrot" "Parrot" "Parrot" "Parrot" 		| "1,2,3,4"	|	"Sword" "Sword" "Skull" "Skull" "Skull" "Skull" "Skull" "Skull" 			| 500 |

  @line115B
  Scenario Outline: line 115B: FC 3 swords, have 2 swords, 2 skulls and 4 parrots, reroll 4 parrots, get 4 skulls=> die and lose 500 points (THE SAME TEST AS LINE 115 BUT TESTING WITH 0 AS A STARTING POINT TO SHOW THAT THE PLAYER WON'T GET A NEGATIVE SCORE WHEN THEY LOSE POINTS WITH 0)
    Given game setup
    Given player has 0 points
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    Then death with <reroll>
    And player <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |9  | "Sword" "Sword" "Skull" "Skull" "Parrot" "Parrot" "Parrot" "Parrot" 		| "1,2,3,4"	|	"Sword" "Sword" "Skull" "Skull" "Skull" "Skull" "Skull" "Skull" 			| 0 |


  @line116
  Scenario Outline: line 116: FC 4 swords, die on first roll with 2 monkeys, 3 (skulls/swords)  => die and lose 1000 points  - ALL CODE EXISTED
    Given game setup
    Given player has 1000 points
    Given card is <card>
    When roll is <roll>
    Then death with <roll>
    And player <score> is <roll>
    Examples:
      |card |roll			|score |
      | 10 |"Sword" "Sword" "Sword" "Skull" "Skull" "Skull" "Monkey" "Monkey" 		| 0 |

  @line117
  Scenario Outline: line 117: FC 2 swords, roll 3 monkeys 2 swords, 1 coin, 2 parrots  SC = 100 + 100 + 300 = 500  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 3 |"Parrot" "Parrot" "Sword" "Sword" "Monkey" "Monkey" "Monkey" "Coin" 		| 500 |

  @line118
  Scenario Outline: line 118: FC 2 swords, roll 4 monkeys 1 sword, 1 skull & 2 parrots then reroll 2 parrots and get 1 sword and 1 skull   SC = 200 +  300 = 500  - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |3  | "Monkey" "Monkey" "Monkey" "Monkey" "Sword" "Skull" "Parrot" "Parrot" 		| "1,2,3,4,5,6"	|	"Monkey" "Monkey" "Monkey" "Monkey" "Sword" "Skull" "Sword" "Skull" 			| 500 |

  @line120
  Scenario Outline: line 120: FC 3 swords, roll 3 monkeys 4 swords 1 skull SC = 100 + 200 + 500 = 800   - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 9 |"Monkey" "Monkey" "Monkey" "Sword" "Sword" "Sword" "Sword" "Skull" 		| 800 |

  @line121
  Scenario Outline: line 121: FC 3 swords, roll 4 monkeys 2 swords 2 skulls then reroll 4 monkeys and get  2 skulls and 2 swords   => die and lose 500 points    - ALL CODE EXISTED
    Given game setup
    Given player has 1000 points
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    Then death with <reroll>
    And player <score> is <reroll>
    Examples:
      |card |roll					| held | reroll	| score |
      |9  | "Sword" "Sword" "Monkey" "Monkey" "Monkey" "Monkey" "Skull" "Skull" 		| "1,2,7,8"	|	"Sword" "Sword" "Sword" "Sword" "Skull" "Skull" "Skull" "Skull" 			| 500 |

  @line123
  Scenario Outline: line 123: FC 4 swords, roll 3 monkeys 4 swords 1 skull  SC = 100 +200 + 1000 = 1300   - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And <score> is <roll>
    Examples:
      |card |roll			|score |
      | 10 |"Monkey" "Monkey" "Monkey" "Sword" "Sword" "Sword" "Sword" "Skull" 		| 1300 |

  @line124
  Scenario Outline: line 124: FC 4 swords, roll 3 monkeys, 1 sword, 1 skull, 1 diamond, 2 parrots then reroll 2 parrots and get 2 swords thus you have 3 monkeys, 3 swords, 1 diamond, 1 skull then reroll 3 monkeys and get  1 sword and 2 parrots  SC = 200 + 100 + 1000 = 1300   - ALL CODE EXISTED
    Given game setup
    Given a player object
    Given card is <card>
    When roll is <roll>
    And player wants to hold <held> and reroll is <reroll>
    And player wants to hold <held2> and reroll is <reroll2>
    And <score> is <reroll2>
    Examples:
      |card |roll					| held | reroll	| held2| reroll2| score |
      | 10 |"Monkey" "Monkey" "Monkey" "Sword" "Skull" "Parrot" "Parrot" "Diamond"		| "1,2,3,4,5,8"	|	"Monkey" "Monkey" "Monkey" "Sword" "Skull" "Sword" "Sword" "Diamond" 			| "4,5,6,7,8"	|	"Sword" "Parrot" "Parrot" "Sword" "Skull" "Sword" "Sword" "Diamond"		| 1300 |
