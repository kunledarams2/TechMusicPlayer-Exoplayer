package com.example.technplayer

/*import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView*/
import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import coil.compose.AsyncImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.util.Util
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_mplayer.*
import kotlinx.android.synthetic.main.m_controller_layout.*


//@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class MPlayerActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private var mAudioManager:AudioManager? = null

    val musicData: MusicDatum by lazy {
        intent.getSerializableExtra("musicData") as MusicDatum
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mplayer)

        mAudioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        compose_view_holder.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PlayerScreen(musicData)
            }
        }




    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->

                player_view!!.player = exoPlayer
                exo_text.text = musicData.title
                exo_sub_text.text = musicData.artist
                Log.d("ImageLoad", musicData.artwork)
                Picasso.get().load(musicData.artwork).into(image).apply {

                }
                initControls(volume_seek_bar, AudioManager.STREAM_MUSIC)

//                exo_artwork.load(musicData.artwork)


                /*
                player_view.controllerHideOnTouch = false
                player_view.controllerAutoShow = true*/

                val mediaItem = MediaItem.fromUri(musicData.url)
                exoPlayer.setMediaItem(mediaItem)

                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

  /*  override fun onRestart() {
        super.onRestart()
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
//            initializePlayer()
            releasePlayer()
        }
    }*/

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, player_view).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    private fun checkPlayer(){

    }


    private fun initControls(seek: SeekBar, stream: Int) {
        seek.max = mAudioManager!!.getStreamMaxVolume(stream)
        seek.progress = mAudioManager!!.getStreamVolume(stream)
        seek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(bar: SeekBar, progress: Int, fromUser: Boolean) {
                mAudioManager!!.setStreamVolume(stream, progress, AudioManager.FLAG_PLAY_SOUND)
            }

            override fun onStartTrackingTouch(bar: SeekBar) {}
            override fun onStopTrackingTouch(bar: SeekBar) {}
        })
    }
}

@Composable
fun PlayerScreen(musicData: MusicDatum ) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF333533))
    ) {

        BoxWithConstraints() {
            Surface {

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .background(Color(0xFF333533))
                ) {
                    playerHeader(musicData, this@BoxWithConstraints.maxHeight)
                    musicTitle(musicData)
                }
            }
        }
    }
}

@Composable
fun playerHeader(musicData: MusicDatum, constraintHeight: Dp) {
    AsyncImage(
        model = musicData.artwork, contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(constraintHeight / 2),
        contentScale = ContentScale.FillHeight
    )
}

@Composable
fun musicTitle(musicData: MusicDatum) {

    Spacer(modifier = Modifier.height(40.dp))

    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = musicData.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = musicData.artist,
                color = Color.LightGray,
                fontSize = 10.sp,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.width(120.dp))
        Icon(
            imageVector = Icons.Default.MoreVert,
            tint = Color.White,
            contentDescription = "",
            modifier = Modifier.padding(20.dp)
        )

    }

}