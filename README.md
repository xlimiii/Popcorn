# Popcorn
**Popcorn** is a project written for **Mobile Applications for Android** course during 
the 5th semester of Computer Science at the Silesian University of Technology.

### Table of contents
* [Project description](#project-description)
* [Used technologies](#used-technologies)
* [How to compile it?](#how-to-compile-it)
* [How to use it?](#how-to-use-it)

### Project description
**Popcorn** is a mobile application that uses **The Movie Database API** to present information **about movies, people and TV Shows**.
You can **search for** specific title / person or check what is in **lists of popular** ones. Each movie and TV Show is presented really 
precisely - you can get to know what is it about, when and where it was produced, which languages are used in it, how long it lasts, 
which genres it represents, who plays in it, who produced it and what rating it has. You can also check when and where specific person
was born, when he/she died, with which movies and TV shows is connected and read his/her biography. Another functionality offered by Popcorn 
is **adding to favourites** thanks to local database. 

### Used technologies
* Kotlin 1.4.30 - langauge in which the project has been written,
* Room 2.2.6 - library responsible for storing data in local database,
* Retrofit 2.9.0 - library responsible for communicating with API,
* Glide 4.11.0 - library responsible for displaying pictures with given url.

### How to compile it?
Popcorn uses REST API with API KEY. If you just want to test it on your mobile phone, you can download an .apk from Releases and install it.
Otherwise, if you want to change something in the code and compile it by yourself, you'll have to **generate your own API KEY** as it is not contained 
in this repository. To do it, follow the steps from this [instruction](https://developers.themoviedb.org/3/getting-started/introduction) and replace
the value of apiKey in the **line 68 of model/api/ApiRequest.kt file** with your key:
```
private const val apiKey: String = "YOUR_API_KEY"    // PLACE YOUR API KEY HERE
```

### How to use it?
Application consists of **five tabs**: Home, Movies, People, TV Shows and Favourite. In first tab, by default, there are displayed **popular movies, 
popular people and popular TV Shows**, but you can use search view placed at the top of the screen to **browse items by their names**. And of course
there are usually a lot of results so you can scroll left or right. Using search view in this tab causes in searching in all three lists at the same time.

<p align="center">
<img src="https://user-images.githubusercontent.com/43967269/109326571-a7f0dc00-7857-11eb-8f8c-61517c37a4e5.png" alt="HomeFragment">
<img src="https://user-images.githubusercontent.com/43967269/109327372-8e9c5f80-7858-11eb-9455-e36b87744d61.png" alt="SearchingInHomeFragment">
</p>

In second, third and fourth tabs there are almost **the same lists** as in first tab but shown in different way - you have to scroll up or down.
In these tabs you can also **add items to favourites** and get to know about the specific movie's / TV Show's release year.

<p align="center">
<img src="https://user-images.githubusercontent.com/43967269/109326642-b939e880-7857-11eb-980c-40db2a5fb7df.png" alt="MovieListFragment">
<img src="https://user-images.githubusercontent.com/43967269/109326712-d1116c80-7857-11eb-80b2-5b489587221a.png" alt="TVShowListFragment">
<img src="https://user-images.githubusercontent.com/43967269/109326675-c48d1400-7857-11eb-82b6-6f76bdca8d0b.png" alt="PeopleListFragment">
</p>

In last tab you can see a **list of your favourite movies and TV Shows**. There you can of course delete them from the list or search for specific one.

<p align="center">
<img src="https://user-images.githubusercontent.com/43967269/109326778-e1294c00-7857-11eb-9b98-60f0e650b18a.png" alt="FavListFragment">
</p>

If you click a row or tile with specific movie / TV Show, you'll go to the screen presenting **it's details**. 
Information which is displayed here has been already mentioned in [Project description](#project-description).
You can also click the name of company responsible for this title and check the details of the company.

<p align="center">
<img src="https://user-images.githubusercontent.com/43967269/109326951-12098100-7858-11eb-863d-29b1a645db7b.png" alt="WWDetails">
<img src="https://user-images.githubusercontent.com/43967269/109327007-23eb2400-7858-11eb-8c7d-c277391017b1.png" alt="WWCompany">
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/43967269/109327110-42511f80-7858-11eb-8905-af035aa51094.png" alt="WVDetails">
<img src="https://user-images.githubusercontent.com/43967269/109327148-4c731e00-7858-11eb-80ff-48da8e6162d6.png" alt="WVCompany">
</p>

And if you click a row or tile with specific person, you'll go to the screen with **his/her details** too.

<p align="center">
<img src="https://user-images.githubusercontent.com/43967269/109327248-69a7ec80-7858-11eb-9cfc-d05abcf10167.png" alt="OlsenDetails1">
<img src="https://user-images.githubusercontent.com/43967269/109327278-73c9eb00-7858-11eb-9085-a5632f88c804.png" alt="OlsenDetails2">
</p>
