@nexus-mobile
Feature: Recent Stops List

@nexus-mobile
Scenario Outline: As a user I want to see on home page a stop I recently searched for using its short code
	Given I opened home page
	And I searched for <short-code> from the home page
	When I open home page
	Then the following list of recent stops appears on the home page
	| name			| id		|
	| <stop-name>	| <stop-id> |
	And the current view adjusted to the screen
	
	Examples: Locations
	| short-code	| stop-name						| stop-id		|
	| twrdpgma		| Church Street-Cuthbert Street	| 410000021007	|
	
@nexus-mobile
Scenario Outline: As a user I want to on home page stops I recently searched for using theirs short code
	Given I opened home page
	And I searched for <first-code> from the home page
	And I searched for <second-code> from the stop board page
	When I open home page
	Then the following list of recent stops appears on the home page
	| name			| id			|
	| <second-name>	| <second-id> 	|
	| <first-name>	| <first-id>	|
	And the current view adjusted to the screen
	
	Examples: Locations
	| first-code	| first-name					| first-id		| second-code	| second-name					| second-id		|
	| twrdpgma		| Church Street-Cuthbert Street	| 410000021007	| twrdpgmd 		| Church Street-Cuthbert Street | 410000021008 	|  	