# Take Away Assignment

## Running

You don't have to do anything special to run the app, just hit the run button and try it out!

## Remote Data Source

Because I didn't have real endpoints to obtain the restaurant list, I faked the API calls with a mocked Remote DataSource, in the debug variant.
So in order to get the restaurants from the fake remote, please use the debug build variant to run the app.
You can also set the variable shouldFail (within the RestaurantsRemoteDataSource) to true, in order to see the behaviour when the remote data source fails trying to retrieve the updated restaurant list

## Local Data Source

I've used a sqlite DB with ROOM in order to have a local DataSource, to store the restaurant list and maintain the favourites within that list.
Every time the main activity gets created, and when you swipe down the restaurant list to refresh, the app will try to get the updates on the restaurant list from the remote DataSource, and then update the DB with the new data. If something fails during that process, a Toast will appear informing the user of the failure on the refresh.

## Testing

The test is organized in two groups: test (unit tests) and androidTest (UI automation tests)

## Unit Testing

The unit testing are built around the use case classes. This is one of the greatest things about having a separate "layer" for the domain (even if it's not all the clean it could be, sorry Uncle Bob).
To run the unit tests, just go to any of the UseCaseTest classes (for example, the more interesting one: ObserveRestaurantListUseCaseTest), and hit the run button. You can run all the tests at once by using the gradle task testDebugUnitTest

## UI Testing

The UI Automation test is running under Espresso, so you'll need to setup an emulator to run this block of testing. After that, you can just go to the RestaurantsActivityTest class within the androidTest location, and just hit the run button once more. Normally you would have UI tests for each fragment and one E2E automation test for the activity, to wrap up the whole flow, but due to the nature of this assignment, I thought a single UI test block for the whole activity had more sense.
