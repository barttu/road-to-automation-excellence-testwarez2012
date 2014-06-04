@nexus-mobile @search @localities
Feature: Ambiguous Location Lookup

@nexus-mobile @search @localities
Scenario Outline: As a user I want to search for locations of to interest
	Given I opened home page
	When I search for <location> from the home page
	Then the results label informs the service matched locations for <location>
	And the following list of matched locations appears
	| name				|
	| <first-result>	|
	| <second-result>	|
	| <third-result>	|
	And the current view adjusted to the screen
	
	Examples: Locations
	| location		| first-result													| second-result									| third-result												|
	| The Orchard	| The Orchard, Hepscott, Morpeth, Northumberland NE61 6HT, UK	| The Orchard, Wylam, Northumberland NE41, UK	| The Orchard, Acomb, Hexham, Northumberland NE46 4SZ, UK	|