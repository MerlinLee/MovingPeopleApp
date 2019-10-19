package au.edu.unimelb.student.mingfengl.ui.login.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.data.ServerResponse
import au.edu.unimelb.student.mingfengl.networking.NetworkingManager
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File


class HomeFragment : Fragment() {
    companion object{
        const val MESSAGE_WHAT = 1000
        const val REQUEST_REGISTER = 1
        const val REQUEST_FORGET = 2
    }
    val REQUEST_VIDEO_CAPTURE = 1
    val REQUEST_LOGIN = 2
    val REQUEST_ADMIN = 3
    val REQUEST_TEST = 4
    val VIDEO_TYPE = "video/mp4".toMediaType()
    lateinit var file: File

    lateinit var video : VideoView
    lateinit var videoUri : Uri
    lateinit var btn_capture : Button
    lateinit var btn_cal: Button
    lateinit var loginIntent: Intent
    private lateinit var homeViewModel: HomeViewModel
    private var uiHandler= object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg?.what){
                MESSAGE_WHAT->{
                    Toast.makeText(GlobalApplication.getContext(), msg.data.get("response").toString(), Toast.LENGTH_SHORT).show()
                }

                else->{

                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        video = root.findViewById<VideoView>(R.id.home_view_video)
        btn_capture = root.findViewById(R.id.home_btn_capture)
        btn_capture.setOnClickListener{
            dispatchTakeVideoIntent()
        }
        btn_cal = root.findViewById(R.id.home_btn_calculate)
        val loading = root.findViewById<ProgressBar>(R.id.home_upload_progressBar)
        btn_cal.setOnClickListener {
            Thread(Runnable {
                loading.visibility = View.VISIBLE
                if (ContextCompat.checkSelfPermission(GlobalApplication.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    var formBody : MultipartBody = MultipartBody
                        .Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("file",file.absolutePath,
                            RequestBody.create(VIDEO_TYPE,file))
                        .build()
                    var response = NetworkingManager.instance.send(
                        Request.Builder()
                            .url(GlobalApplication.getApplication().url+"/app/upload")
                            .post(formBody)
                            .build()
                    )
                    if(response.isSuccessful){
                        loading.visibility = View.GONE
                        val content :String = response.body!!.string()
                        var server_msg = Gson().fromJson(content,ServerResponse::class.java)
                        var message = Message()
                        if(server_msg.code==0){
                            message.what = 1000
                            message.data.putString("response",server_msg.errmsg)
                            uiHandler.sendMessage(message)
                        }
                        loading.visibility = View.GONE
                    }
                }


            }).start()
        }
        return root
    }

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            activity?.packageManager?.let {
                takeVideoIntent.resolveActivity(it)?.also {
                    startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            videoUri = data?.data!!
            if (videoUri != null) {
                video.setVideoPath(videoUri.path)
                file = File(getRealPathFromUri(GlobalApplication.getContext(),videoUri))
            }
            video.setVideoURI(videoUri)
            if (video.visibility== View.GONE){
                video.visibility=View.VISIBLE
            }
            video.start()
        }
    }

    fun getRealPathFromUri(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor!!.close()
            }
        }
    }
}