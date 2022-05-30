package com.example.technplayer

import java.io.Serializable


data class MusicData (
    val id: String,
    val artist: String,
    val artist_name: String,
    val artwork: String,
    val album: String,
    val name: String,
    val duration: String,
    val genre_name: String,
    val sharelink: String,
    val releasedate: Long,
    val status: String,
    val source: String,
    val preview: String
):Serializable


