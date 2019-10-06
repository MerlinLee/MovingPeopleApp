package au.edu.unimelb.student.mingfengl.ui.login

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.app.AlertDialog
import android.util.Log
import au.edu.unimelb.student.mingfengl.R
import cn.jpush.android.api.JPushInterface
import okhttp3.MediaType.Companion.toMediaType
import java.io.File




class OfflineMainActivity  : AppCompatActivity(){

    val REQUEST_VIDEO_CAPTURE = 1
    val REQUEST_LOGIN = 2
    val REQUEST_ADMIN = 3
    val REQUEST_TEST = 4
    val VIDEO_TYPE = "video/mp4".toMediaType()
    lateinit var file: File
    lateinit var video : VideoView
    lateinit var btn_capture : Button
    lateinit var btn_cal:Button
    lateinit var loginIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_main)
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        var rid = JPushInterface.getRegistrationID(getApplicationContext());
        Log.d("JI GUANG",rid)
        video = findViewById(R.id.view_video)
        btn_capture = findViewById(R.id.btn_capture)
        btn_capture.setOnClickListener{
            dispatchTakeVideoIntent()
        }
        btn_cal = findViewById(R.id.btn_calculate)
        btn_cal.setOnClickListener {
            showListDialog()
        }
    }


    private fun showListDialog() {
        val listItems = arrayOf(
            "Offline",
            "Online"
        )

        val listDialog = AlertDialog.Builder(this)
        listDialog.setTitle("Choose a calculation mode")

        listDialog.setItems(listItems, DialogInterface.OnClickListener { dialogInterface, i ->
            switchMod(i)
        })

        listDialog.create().show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            var videoUri = data?.data
            if (videoUri != null) {
                video.setVideoPath(videoUri.path)
                file = File(videoUri.path)
            }
            video.setVideoURI(videoUri)
            if (video.visibility== View.GONE){
                video.visibility=View.VISIBLE
            }
            video.start()
        }
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK){
//            Toast.makeText(
//                applicationContext,
//                data?.extras?.getString("loggedUserData"),
//                Toast.LENGTH_LONG
//            ).show()
            this.loginIntent = Intent()
            loginIntent.setAction("au.edu.success.admin")
            startActivityForResult(this.loginIntent,REQUEST_ADMIN)
            uploadMethod()
        }

    }


    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }

    private fun switchMod(i:Int){
        if (i==0){
            this.loginIntent = Intent()
            loginIntent.setAction("au.edu.offline.detect")
            startActivityForResult(this.loginIntent,REQUEST_LOGIN)

        }
        if (i==1){
            this.loginIntent = Intent()
            loginIntent.setAction("au.edu.login")
            startActivityForResult(this.loginIntent,REQUEST_LOGIN)
        }
    }

    private fun uploadMethod(){

    }

}

