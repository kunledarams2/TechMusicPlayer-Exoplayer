package com.example.technplayer

import java.io.Serializable


data class MusicDatum (
    val title: String,
    val artist: String,
    val artwork: String,
    val url: String,
    val id: String
):Serializable
