Feature: Gilded Rose behaviour of the legacy code

// Example of a scenario
Scenario: Aged Brie increases in quality the older it gets
	Given an item named "Aged Brie" with quality 9 and which has to be sold in 11 days
	When a day has passed
	Then it has to be sold in 10 days
	And its quality is 10
	
