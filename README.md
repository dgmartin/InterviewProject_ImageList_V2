# InterviewProject_ImageList

This project was originally done for a job interview. I was given the following requirments:

> Please send us a sample project that displays a scrolling list of images populated from: http://api.flickr.com/services/feeds/photos_public.gne?tags=boston&amp;format=json

> This project should keep a local cache of the images in device storage so that they are available in future launches of the application without needing to be downloaded again. The application should asynchronously load the photos from the web if they are not in the cache, add them to the cache, and display them when they become available.
 
> Please take about 1 day to complete this project, and send back a zipped up project file, so we can run in and assess.

With my submission I added the following comments:

- I forgot to ask if this was handset or tablet.  I assumed handset so that is what I designed for. The majority of my apps also call for locked portrait rotation so I didn't think until last minute to design for rotation changes. I've allowed the app to rotate by default but you will may notice design flaws when going to landscape.  you will also return to the top of the list after rotating. If this is something you would want corrected let me know and I can get those to you.
- I used the volley library as it is becoming the standard for network requests on android. It caches all images by default. All I had to manually cache was the response data with the image links. Keep in mind I'm using the default volley caching configuration which is typically based on the data the server provides on length of validity. In other words it's caching the image until the server says they are old.
- I was planning on using a Flickr library however the one that Flickr recommends seems out dated.  I found it just as convenient to use the JSON request with volley to parse the data I needed.
- I used the JSON response instead of the XML. In doing this I found a major bug on the Flickr site.  The call is not returning valid JSON. I was able to circumvent this with my code however you will find extra code in the "FlickrListRequest.java" file. I left this code so that in the event Flickr fixes the bug it will be easy to swap back to the correct code.
- I decided to leave the error logs for testing purposes but would normally comment them out, wrap in a if statement with a debug flag, or remove them all together.

I am sharing this on github for any others that would like to see a sample of my current coding style.
