@nexus-mobile @search @stops
Feature: Ambiguous Stop Board Search

@nexus-mobile @search @stops
Scenario Outline: As a user I want to select a stop from a list of stops nearby location of my choice
	Given I opened home page
	And I searched for <location> from the home page
	When I select the following stop from results
	| name 			| id 		|
	| <stop-name>	| <stop-id>	|
	Then the stop board label informs the service matched stop
	| name			| id		| code 			|
	| <stop-name>	| <stop-id> | <short-code>	|
	
	Examples: Locations
	| location 									| stop-name 			| stop-id 		| short-code 	|
	| Newcastle Upon Tyne, Tyne and Wear, UK 	| St Thomas Street-Rvi 	| 41000008NC06 	| twrgwdmg		|
	
@nexus-mobile @search @stops
Scenario Outline: As a user I want to select a stop from a list of matched locations
	Given I opened home page
	And I searched for <location> from the home page
	And I selected the following location from results
	| name 				|
	| <exact-location>	|
	When I select the following stop from results
	| name 			| id 		|
	| <stop-name>	| <stop-id>	|
	Then the stop board label informs the service matched stop
	| name			| id		| code 			|
	| <stop-name>	| <stop-id> | <short-code>	|
	And the current view adjusted to the screen
	
	Examples: Locations
	| location 		| exact-location										| stop-name		| stop-id 		| short-code 	|
	| The Orchard	| The Orchard, East Boldon, Tyne and Wear NE36 0ST, UK	| East Boldon	| 410000017030 	| twrdmdwm 		|
	
@nexus-mobile @search @stops
Scenario Outline: As a user I want to select a stop from a list of stops nearby postcode of my choice
	Given I opened home page
	And I searched for <postcode> from the home page
	When I select the following stop from results
	| name 			| id 		|
	| <stop-name>	| <stop-id>	|
	Then the stop board label informs the service matched stop
	| name			| id		| code 			|
	| <stop-name>	| <stop-id> | <short-code>	|
	And the current view adjusted to the screen
	
	Examples: Postcodes
	| postcode 	| stop-name 					| stop-id 		| short-code 	|
	| NE16 		| Church Street-Cuthbert Street | 410000021008 	| twrdpgmd 		|