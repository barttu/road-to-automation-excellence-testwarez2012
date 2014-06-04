@nexus-mobile @search @localities
Feature: Exact Location Lookup

@nexus-mobile @search @localities
Scenario Outline: As a user I want to find stops nearby location of my choice
	Given I opened home page
	When I search for <location> from the home page
	Then the results label informs the service matched stops for <location>
	And the following list of matched stops appears
	| name			| id			|
	| <first-stop>	| <first-id>	|
	| <second-stop>	| <second-id>	|	
	| <third-stop>	| <third-id>	|
	And the current view adjusted to the screen
	
	Examples: Locations
	| location									| first-stop			| first-id 		| second-stop			| second-id		| third-stop					| third-id		|
	| Newcastle Upon Tyne, Tyne and Wear, UK	| St Thomas Street-Rvi	| 41000008NC06	| St Thomas Street-Rvi	| 41000008NC08	| Newcastle St Thomas Street	| 41000008NC07	|
		
@nexus-mobile @search @localities
Scenario Outline: As a user I want to find stops nearby postcode of my choice
	Given I opened home page
	When I search for <postcode> from the home page
	Then the results label informs the service matched stops for <location>
	And the following list of matched stops appears
	| name			| id			|
	| <first-stop>	| <first-id>	|
	| <second-stop>	| <second-id>	|	
	| <third-stop>	| <third-id>	|
	And the current view adjusted to the screen
	
	Examples: Postcodes
	| postcode	| location						| first-stop					| first-id 		| second-stop					| second-id		| third-stop			| third-id		|
	| NE16		| Newcastle upon Tyne NE16, UK	| Church Street-Cuthbert Street	| 410000021007	| Church Street-Cuthbert Street	| 410000021008	| Marley Hill School	| 410000021107	|
