Feature: As a user I want to find places and nearby stops

@web @mobile
Scenario Outline: As a user I want to find places matching my choice
	When I search for <place> from the home page
	Then the following list of matched places is returned
	| name			| postcode			|
	| <first-name>	| <first-postcode>	|
	| <second-name>	| <second-postcode>	|
	| <third-name>	| <third-postcode>	|
	| <fourth-name>	| <fourth-postcode>	|
	
	Examples: Places
	| place		| first-name		| first-postcode 	| second-name 					| second-postcode 	| third-name		| third-postcode 	| fourth-name		| fourth-postcode 	|
	| Aldwych	| Aldwych (closed)	| WC2R 1 			| Aldwych 						| WC2A 2 			| Aldwych Avenue 	| IG6 1 			| Aldwych Close 	| RM12 4 			|
	| West Ham	| West Ham			|					| West Ham Substation (WHAM)	| E16 4				| West Ham 			| E15 3 			| West Ham Lane 	| E15 4 			|	

@mobile
Scenario Outline: As a user I want to find nearby stops to post code of my choice
	When I search for <postcode> from the home page
	Then the following list of nearby stops is returned
	| stopname			| stopcode			| towards			| routes			|
	| <first-stopname>	| <first-stopcode>	| <first-towards>	| <first-routes>	|
	| <second-stopname>	| <second-stopcode>	| <second-towards>	| <second-routes>	|
	| <third-stopname>	| <third-stopcode>	| <third-towards>	| <third-routes>	|
	| <fourth-stopname>	| <fourth-stopcode>	| <fourth-towards>	| <fourth-routes>	|
	
	Examples: Stops nearby a postcode
	| postcode 	| first-stopname 		| first-stopcode 	| first-towards 						| first-routes 										| second-stopname 		| second-stopcode 	| second-towards 							| second-routes 									| third-stopname 		| third-stopcode 	| third-towards 					| third-routes 	| fourth-stopname 				| fourth-stopcode 	| fourth-towards 			| fourth-routes 			|
	| N17 		| Scotland Green (B) 	| 57704 			| towards Manor House or Stamford Hill. | 149,  259,  279,  341,  349,  476,  N76,  N279 	| Lordship Lane (R) 	| 73479 			| towards Northumberland Park or Edmonton. 	| 149,  259,  279,  341,  349,  476,  N76,  N279 	| Pembury Road (X) 		| 47842 			| towards North Middlesex Hospital. | 318 			| Pembury Road (T) 				| 75293 			| towards Stamford Hill. 	| 318 						|
	| WC1 		| Russell Square (J) 	| 59177 			| towards Aldwych. 						| 59,  68,  91,  168,  N91 							| Southampton Row (B) 	| 76501 			| towards Waterloo or Trafalgar Square. 	| 59,  68,  91,  168,  188,  X68,  N91 				| Russell Square (C) 	| 55923 			|  									|				| Russell Square Station (H) 	| 91209 			| towards Euston. 			| 59,  68,  91,  168,  N91 	|

@mobile
Scenario Outline: As a user I want to look for stops nearby a location of my choice
	Given I searched for <location> from the home page
	And the following list of matched places is returned
	| name				| postcode			|
	| <first-location>	| <first-postcode>	|
	| <second-location>	| <second-postcode>	|
	| <third-location>	| <third-postcode>	|
	| <fourth-location>	| <fourth-postcode>	|
	When I select the following place from the list
	| name 					| postcode 				|
	| <selected-location>	| <selected-postcode>	|
	Then the following list of nearby stops is returned
	| stopname			| stopcode			| towards			| routes			|
	| <first-stopname>	| <first-stopcode>	| <first-towards>	| <first-routes>	|
	| <second-stopname>	| <second-stopcode>	| <second-towards>	| <second-routes>	|
	| <third-stopname>	| <third-stopcode>	| <third-towards>	| <third-routes>	|
	| <fourth-stopname>	| <fourth-stopcode>	| <fourth-towards>	| <fourth-routes>	|
	
	Examples: Stops nearby a location
	| location 		| selected-location	| selected-postcode | first-location 	| first-postcode 	| second-location 	| second-postcode 	| third-location 							| third-postcode 	| fourth-location 							| fourth-postcode 	| first-stopname 							| first-stopcode 	| first-towards 								| first-routes 										| second-stopname 			| second-stopcode 	| second-towards 							| second-routes 		| third-stopname 			| third-stopcode 	| third-towards 					| third-routes 	| fourth-stopname 			| fourth-stopcode 	| fourth-towards 		| fourth-routes |
	| Aldwych 		| Aldwych Avenue	| IG6 1 			| Aldwych (closed) 	| WC2R 1 			| Aldwych 			| WC2A 2			| Aldwych Avenue 							| IG6 1 			| Aldwych Close 							| RM12 4 			| Aldwych Avenue 							| 74258 			| towards The Glade. 							| 169 												| Aldwych Avenue 			| 59375 			| towards Ilford. 							| 169 					| Princes Road 				| 73909 			| towards Ilford. 					| 169 			| Hamilton Avenue 			| 52789 			| towards Barkingside. 	| 169 			|
	| Hammersmith 	| Hammersmith 		| W6 7 				| Hammersmith 		|  					| Hammersmith 		| W6 7 				| Paddington (Hammersmith and City Line) 	| W2 1				| Hammersmith (District & Piccadilly Lines) | W6 7 				| Hammersmith Stn/H'smith & City Line (W) 	| 57309 			| towards Fulham, Kensington or West Brompton.	| 27,  190,  266,  267,  391,  H91,  N9,  N11		| Hammersmith Library (Q) 	| 55306 			| towards Ladbroke Grove or White City. 	| 72,  220,  283,  295 	| Hammersmith Library (P)	| 47200 			| towards Fulham or Putney Bridge.	| 220,  295 	| Hammersmith Library (R)	| 50905 			| towards Barnes. 		| 72,  283		|