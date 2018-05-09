-------------------------------------
PRESS ASSOCIATION - QA TECHNICAL TASK
-------------------------------------

Your task is to test and automate the API available at the url which you have been provided

Documentation describing the implementation and spec of this API is available here: [LINK TO PUBLICLY ACCESSIBLE STORE or PASTED DOCUMENTATION BELOW]


----
TASK
----

Using this Java project as a starting point, create a BDD style test suite using Cucumber/Gherkin syntax, utilising Apache fluent-hc to interrogate the API.

Your PASSING tests should FAIL if working functionality becomes broken.

Any FAILING tests should PASS if any broken functionality gets fixed.


----------------
HOW TO RUN TESTS
----------------

Test can be run 3 ways

1) mvn clean test
if we need to run specific tag tests we can run mvn clean test -Dtag=@get

2) By running the cukes runner we can run the tests

3) If we have any IDE we can run the tests from the feature file