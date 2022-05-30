package com.example.technplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val composeView = findViewById<ComposeView>(R.id.composeWrapper)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SetComposeView {
                    startActivity(Intent(this@MainActivity, MPlayerActivity::class.java).apply {
                        putExtra("musicData", it)
                    })
                }
            }
        }


    }


}

@Composable
private fun SetComposeView(navigateToPlayer: (MusicDatum) -> Unit) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },


        content = {
            MNavigation(navController, navigateToPlayer)

//            contentColorFor(backgroundColor = MaterialTheme.colors.primary)


        })

}

@Composable
fun HomeScreen(navigateToPlayer: (MusicDatum) -> Unit) {
    Column(
        modifier = Modifier
            .background(Color(0xFF333533))
            .fillMaxHeight()
    ) {

        Card(
            shape = RoundedCornerShape(bottomEnd = 15f, bottomStart = 15f),
            backgroundColor = Color(0xFF202220),
            elevation = 5.dp,

            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(
                    text = "RADIOHEAD", fontSize = 20.sp,
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.width(150.dp))

                Image(
                    painterResource(id = R.drawable.simi),
                    contentDescription = "content",
                    alignment = Alignment.TopEnd,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)


                )
            }

        }

        Text(
            text = "Today's Hit",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(20.dp)
        )

        OutlinedTextField(
            value = "", onValueChange = {},
            Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .border(color = Color(0xFF202220), shape = CircleShape, width = 1.dp),

            placeholder = {
                Text(
                    text = "Search",
                    color = Color.LightGray,
                    fontFamily = FontFamily.Serif
                )
            },
            visualTransformation = VisualTransformation.None,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),

            shape = CircleShape,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search", tint = Color.LightGray
                )
            },
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /*TODO*/ }, Modifier.size(width = 180.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF5566D1))
            ) {
                Text(
                    text = "Shuffle Play",
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Button(
                onClick = { /*TODO*/ }, Modifier.size(width = 120.dp, height = 50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF202220))
            ) {
                Text(
                    text = "Play",
                    fontFamily = FontFamily.Serif,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        GetMusicData(navigateToPlayer)

    }
}

@Composable
fun SearchScreen(){
    Column(
        modifier = Modifier
            .background(Color(0xFF333533))
            .fillMaxHeight()
    ) {

        Card(
            shape = RoundedCornerShape(bottomEnd = 15f, bottomStart = 15f),
            backgroundColor = Color(0xFF202220),
            elevation = 5.dp,

            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()

        ){

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(
                    text = "RADIOHEAD", fontSize = 20.sp,
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.width(150.dp))

                Image(
                    painterResource(id = R.drawable.simi),
                    contentDescription = "content",
                    alignment = Alignment.TopEnd,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)


                )
            }


        }

        Text(
            text = "Today's Hit",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            modifier = Modifier.padding(20.dp)
        )
    }
}


@Composable
fun GetMusicData(navigateToPlayer: (MusicDatum) -> Unit) {
    val musicList = remember { DataProvider.musicList2 }
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(
            items = musicList,
            itemContent = {
                musicListHolder(musicData = it, navigateToPlayer = navigateToPlayer)
            }
        )
    }
}


@Composable
fun musicListHolder(musicData: MusicDatum, navigateToPlayer: (MusicDatum) -> Unit) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { navigateToPlayer(musicData) },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Row() {
            Spacer(modifier = Modifier.width(20.dp))
            AsyncImage(
                model = musicData.artwork, contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
            )
            /* Image(painterResource(id = R.drawable.simi)  ,
                 contentDescription ="musicImage",
                 modifier = Modifier
                     .size(60.dp)
                     .clip(CircleShape)
             )*/
            Spacer(modifier = Modifier.width(10.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(musicData.title, color = Color.White, fontWeight = FontWeight.Bold)
                Text(musicData.artist, color = Color.LightGray)
            }
        }


        Spacer(modifier = Modifier.width(150.dp))
        Icon(
            imageVector = Icons.Default.MoreVert,
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier.padding(20.dp)
        )

    }
}


// bottomBar


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Search,
        NavigationItem.MusicLibrary
    )

    Card(
        shape = RoundedCornerShape(topEnd = 18f, topStart = 18f),
        backgroundColor = Color(0xFF202220),
        elevation = 5.dp,

        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    ) {
        BottomNavigation(
            backgroundColor = Color(0xFF202220),
            contentColor = Color(0xFFDBDEF3)
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    /*  label = { Text(text = item.title) },*/
                    selectedContentColor = Color(0xFFDBDEF3),
                    unselectedContentColor = Color(0xFFDBDEF3).copy(0.4f),
                    alwaysShowLabel = true,
                    selected = false,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                    })


            }

        }
    }
    



}

@Composable
fun MNavigation(navController: NavHostController,  navigateToPlayer: (MusicDatum) -> Unit){
    NavHost(navController = navController, startDestination =NavigationItem.Home.route  ){
        composable(NavigationItem.Home.route){
            HomeScreen(navigateToPlayer =navigateToPlayer )
        }
        composable(NavigationItem.Search.route){
            SearchScreen()
        }

        composable(NavigationItem.MusicLibrary.route){
            SearchScreen()
        }
    }
}