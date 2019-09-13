package com.example.shanu.tutorialdemoapp.VideoView

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shanu.tutorialdemoapp.R
import kotlinx.android.synthetic.main.activity_video_view.*

class VideoViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)


        startStreamBtn.setOnClickListener {

            with(videoView) {
                setVideoURI(
                        Uri.parse("https://www.youtube.com/watch?v=1RoUV9Ho8qI"))
                //.parse("https://firebasestorage.googleapis.com/v0/b/ss-talk.appspot.com/o/video%2FMSG1547992932770?alt=media&token=0eb45fd6-769b-4113-8fb6-3992dbf6441a")) }
                videoView.start()
                videoView.setOnPreparedListener {
                    Log.d("VideoViewActivity", "onCreate: $it")
                }

                setOnErrorListener { mp, what, extra ->
                    Log.d("VideoViewActivity", "onCreate: $mp , $what , $extra")
                    return@setOnErrorListener true
                }

            }
        }
    }
}
