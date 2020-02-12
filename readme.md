### App general flow

[![](http://img.youtube.com/vi/TNF79CbXICU/0.jpg)](https://www.youtube.com/watch?v=TNF79CbXICU "")

### App user flow and mock checkout

[![](http://img.youtube.com/vi/-41XZlTxcyk/0.jpg)](https://www.youtube.com/watch?v=-41XZlTxcyk "")

There is 1 predefined users in the app
name        |pw
"ac@mail"  "123456"

Backend server can be found at https://mockcommercebackend.herokuapp.com/api/products . Because it is heroku it might be sleeping so connection may fail, try again in few minutes.

Backend repo can be found at https://github.com/abdullahcanakci/MockCommerceBackend. 





new users can be added from register screen
added users and changes made to a user like adding an address or completing an order will persist through changing user session


This is a Mock commerce application I'm developing to advance my Android-Kotlin skills. 

I'm using

Fragment+Viewmodel for persistence across state changes.

Livedata for passing for consumers.

Jetpack Navigation for handling navigation.

Okhttp3 for network calls. Some files images etc are on github repository and I'm requesting them to create a good interface instead of using local resources.

Glide for image loading

Koin for DSL, I have used Dagger2 but it seemed to complicated for such projects. If we were talking about multi million line java enterprise application it is fine but for android Koin is good.

Timber for logging.

Gson for de/serialization

