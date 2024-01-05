Feature: hello service
Scenario: hello service returns correct answer
When I call the hello service
Then I get the answer "Hello Course"

Scenario: person service returns correct answer
When I call the person service
Then I get "John Doe"