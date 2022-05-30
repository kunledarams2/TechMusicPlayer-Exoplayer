package com.example.technplayer

sealed class NavigationItem (var title:String, var icon:Int, var route:String){
    object Home: NavigationItem("Home", R.drawable.ic_baseline_home_24, "home")
    object Search: NavigationItem("Search", R.drawable.ic_baseline_search_24, "search")
    object MusicLibrary : NavigationItem("Music", R.drawable.ic_baseline_library_music_24,"music")

}
