# Github API Challenge

This folder contains the source code for an Android App to retrieve a list of Developers using the [Github API](https://developer.github.com/v3/search/#search-users)

 ### Releases
  
Download the APK from [Releases](https://github.com/TheDancerCodes/Github-Api-Challenge/releases/)
  
### Challenge

This challenge is to give us an idea of your programming experience on the Android platform.

Build an Android app to retrieve a list of Java Developers in Lagos using the Github API:
* Display the list of developers on a list. Each item on the list should have:
  * User’s profile image
  * User’s GitHub username
* Clicking each item on the list should show their profile details.
* The profile screen should contain:
  * Username
  * Profile photo
  * Github profile URL
  * Button to share their profile, and it should launch a share intent and the content of the share should be “Check out this awesome developer @[github username], [github profile url].”
  * Clicking on the Github url should launch the browser and link to their Github page.
  
  
  ### Screenshots

  ![Grid View](app/src/main/res/drawable/screen_shot1.png?raw=true "ScreenShot1") ![Detail View](app/src/main/res/drawable/screen_shot2.png?raw=true "ScreenShot2") ![Detail View](app/src/main/res/drawable/screen_shot3.png?raw=true "ScreenShot3")

  
  ### TODOs
  Here are the next things that I plan on working on:
  
  * Implement Constraint Layout.
  * [FIX] Saving and Restoring Activity State Correctly.
  * Implement Pagination.
  * Reference dependencies in root gradle file.
  * Write Tests.