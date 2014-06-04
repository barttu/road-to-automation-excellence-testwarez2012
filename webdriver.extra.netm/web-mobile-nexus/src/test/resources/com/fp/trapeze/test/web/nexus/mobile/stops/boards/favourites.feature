@nexus-mobile @stops @boards
Feature: Favourite Stops List

@nexus-mobile @stops @boards
Scenario Outline: As a user I want to see on home page a stop I favourited using its short code
	Given I opened home page
	And I searched for <short-code> from the home page
	And I add the stop to favourites
	When I open home page
	Then the following list of favourite stops appears on the home page
	| name			| id		|
	| <stop-name>	| <stop-id> |
	And the current view adjusted to the screen
	
	Examples: Locations
	| short-code	| stop-name						| stop-id		|
	| twrdpgma		| Church Street-Cuthbert Street	| 410000021007	|
	
@nexus-mobile @stops @boards
Scenario Outline: As a user I want to see on home page stops I favourited using theirs short code
	Given I opened home page
	And I searched for <first-code> from the home page
	And I added the stop to favourites
	And I searched for <second-code> from the stop board page
	And I added the stop to favourites
	When I open home page
	Then the following list of favourite stops appears on the home page
	| name			| id			|
	| <first-name>	| <first-id>	|
	| <second-name>	| <second-id> 	|
	And the current view adjusted to the screen
	
	Examples: Locations
	| first-code	| first-name					| first-id		| second-code	| second-name					| second-id		|
	| twrdpgma		| Church Street-Cuthbert Street	| 410000021007	| twrdpgmd 		| Church Street-Cuthbert Street | 410000021008 	|  	