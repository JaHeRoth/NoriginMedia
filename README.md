# NoriginMedia
---------------------

## Overview

As part of the recruitment process at NoriginMedia, I was asked to do the native part of their [candidate-tester](https://github.com/NoriginMedia/candidate-tester/).

This project is my solution to that challenge, and was created over the weekend of 21 July to 23 July. It was made in Android Studio, and displays its data with help from the GitHub repo [android-tv-epg](https://github.com/korre/android-tv-epg).

## Running

There are two alternative ways of running this Android-app:

 1. Download and install the .apk file: `app/build/outputs/apk/app-debug.apk`
 2. Clone the repo, open it in Android Studio, and hit run

**WARNING**, the following might hinder the application from functioning correctly:

 1. During development, Genymotion was used as the runtime-environment. In Genymotion, `localhost` is accessed through `10.0.3.2` Thus, it might be necessary to replace the constant `EPG_FILE_REMOTE` in `app/src/main/java/rothschild/henning/jacob/noriginmedia/controller/EPGFragment.java` when running the app in another environment.
 2. All the epg-data I was provided had start and end dates on 18 March. For the sake of demonstration, all dates are skewed to be in the current day. As the offset is based on the time between the current day and *18.03.2017*, this will not work as inteded if the dates are changed. To eliminate this functionality, replace the code in `hackyTimeRecenter()` in `app/src/main/java/rothschild/henning/jacob/noriginmedia/model/EPGDataCreator.java` with `return 0`

## Philosophy

As time was limited I had to focus my efforts. I believe my various apps show I can work with UI and solve complex tasks using complex technologies. What they do not show is how I structure my projects, or whether I write understandable, testable and decoupled code. As a young developer with little team-experience I notice that this is the exact area where my skills are usually questioned. Therefore, I have focused this project on displaying a good project structure, rather than having the prettiest UI or most complex or optimized functionality.

## Problems

Due to time-constraints, the following problems were not prioritized, and thus went unsolved:

 - Limited testing: At first, everything was unit- and integration-tested, but it took too much time to keep up. Thus, test-coverage is currently low.
 - Exception-throwing: I am no fan of unnecessary exception-throwing. Excessive exception-throwing is slow, and not the intended way of doing things. Currently the data-fetchers perform no checks before performing actions that might throw exceptions. Thus, exceptions are thrown unnecessarily. These exceptions are later caught, so no real harm is done to the user, but this excessive try-catch structure is neither fast nor elegant.
 - Fetch-retry: If the app fails at fetching data during launch, it will not retry until restart. A *BroadcastReceiver* could be set up to notice when the user turns on their internet, and fetch data from server if a missing network-connection caused the last attempt to fail.
 - Fetch on changes: Currently, the app only fetches server-data on start/restart, but ideally it should fetch whenever the servers data changes. I have already implemented this functionality in my previous app SKAMvarsler, so code could easily be copy-pasted to the current project. It is simply a matter of running a *Google App Engine servlet* regularly from a *cron-job*. This servlet will fetch data from the server, compare it with previously fetched data, and notify the app through *Firebase Cloud Messaging* if the servers data has changed. If the app is in the foreground when it is notified, it will fetch this new data from the epg-server. This solution is free and puts no excessive load on users' batteries or networks.
 - Toasts: The user should see a toast whenever the server-data is not available, so they know the data displayed is possibly outdated. 
 - Unswipeable ViewPager: The swipe-functionality of the normal *ViewPager* overpowers and breaks the pan/scroll functionality of the epg-view. Thus, it was necessary to remove this swipe-functionality from the epg-tab. It seems possible and preferred to make only the epg-tab unswipeable, while the other tabs stay swipeable.
 - Drawable resolution: Only one drawable resolution is provided, and it does not fit any of the normally requested resolutions.
 - Library limitations: The epg-library I decided to use had certain limitations and problems. Some of these were solved with band-aid solutions, while others were ignored. The problems discovered were:
 - Memory leak in example code (fixed)
 - Miscalculation of own height (band-aid)
 - Downloaded images are not cached (ignored)
 - Current-time only updates on restart (ignored)
 - Missing some desired functionality (ignored)

## Note

As I use *Dropbox* and was the only person working on this project, I saw no purpose in pushing to a remote repo during development. Therefore, once I finished the project, everything was simultaneously pushed to remote. Locally, however, *Git* was used heavily during development. After cloning the repo, you can type `git log`, `git reflog`, or use any git-tool of your choice to see the whole development-process. This might give a feel for how I typically work.

## Author

| Jacob Henning Rothschild           |
| ---------------------------------- |
| linkedin.com/in/jacob-h-rothschild |
| Jacob.R.Developer@gmail.com        |
