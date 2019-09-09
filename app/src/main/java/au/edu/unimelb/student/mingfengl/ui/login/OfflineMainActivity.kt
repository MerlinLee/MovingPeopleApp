package au.edu.unimelb.student.mingfengl.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import au.edu.unimelb.student.mingfengl.R

class OfflineMainActivity  : AppCompatActivity(){

    val REQUEST_VIDEO_CAPTURE = 1
    lateinit var video : VideoView
    lateinit var btn_capture : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_main)
        video = findViewById<VideoView>(R.id.view_video)
        btn_capture = findViewById<Button>(R.id.btn_capture)
        btn_capture.setOnClickListener{
            dispatchTakeVideoIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val videoUri: Uri? = intent.data
            video.setVideoURI(videoUri)
        }
    }

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }
}