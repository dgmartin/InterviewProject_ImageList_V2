# InterviewProject_ImageList_V2

**PLEASE NOTE:** This project originated as a direct copy of the project here: [Interview_Project_Imagelist]. This project is an update to the original project so it is important to understand the original goal before looking at the changes that where made. It is highly recommended that you view the ReadMe file for InterviewProject_ImageList before reading further.

For this project I was given the following task:
>Flickr has a great public api for photo feeds available.
>All the documentation is at: www.flickr.com/services/feeds/docs/photos_public/.

>Please build an Android app that:

>- Loads the public photos feed.
>- Displays the photos in a grid layout
>- Allows you to type a “Tag” into a search field to show a new feed of tagged photos

>Build it however you’d like but try not to spend more than 4­5 hours.
>Try to make it easy for us to import it into Android Studio

From the original code the first step of loading a public feed was completed. I then needed to update the code to display the content in a grid as well as allow the users to enter a custom tag. 

To get the code to display in a grid view I updated the ImageListView to set the prefered layout via the constructor. I choose to do it this way in the event I ever need to go back to the original linear layout. Once this was complete it was simply a matter of using a GridLayoutManager in the place of the current LinearLayoutManager for the RecyclerView.

For the custom tag requirement I added an EditText and clear button to the main layout. I then connected a TextWatcher to the EditText so that as the user types in a tag the list is automatically updated. Hitting the clear button resets the EditText and the list data to use the default empty string (""). I then updated the request call to take in a users tag instead of just defaulting to "boston" like the original code required. There is a section in the code that can be used for validating the string (marked with a TODO) however I decided not to add any validation for the moment. In addition I have the tags being saved to the shared preferences so that when the app is shutdown and restarted the same tags are entered as when the user left.

**Other Notes and Changes**
- I changed the package name from the original code as these should now be considered two different projects.
- In the original code I omitted an placeholder image if they images failed to load. This is still true however I noticed more and more there are images that fail to load via Flickr. This holds true even on a browser confirming it is not my code. Please be aware that if you click on an image and it does not load right away it is most likely a broken link. I will try and updated this in the next day or so.
- While I don't agree with the method flickr's method of retriving image sizes is to trail the filename with a letter indicating which size to return (You can read about it here: [Flickr Services]), I have added a string replace on the image url to increase the resolution when viewing a photo in full screen. Currently this only applies to jpg files.
- My normal testing device (my personal phone) died on me two days ago so almost all testing was done on the Genymotion emulator.
- While my time on this project was broken up throughout the day I would say total time working on this was about 3.5 hours to comply with the basic tasks and another hour that I spent making smaller updates. This total does not include the time it took me to copy the original code to a new repository (which is much more time consuming than you would think) or the time to update this ReadMe file.


 [Interview_Project_Imagelist]: <https://github.com/dgmartin/InterviewProject_ImageList>
 [Flickr Services]: <https://www.flickr.com/services/api/misc.urls.html>
