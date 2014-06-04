@nexus-mobile @search @stops
Feature: Exact Stop Board Search

@nexus-mobile @search @stops
Scenario Outline: As a user I want to find stop of my choice by its short code
	Given I opened home page
	When I search for <short-code> from the home page
	Then the stop board label informs the service matched stop
	| name			| id		| code 			|
	| <stop-name>	| <stop-id> | <short-code>	|
	And the current view adjusted to the screen
	
	Examples: Locations
	| short-code	| stop-name						| stop-id		|
	| twrdpgma		| Church Street-Cuthbert Street	| 410000021007	|