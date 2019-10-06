package au.edu.unimelb.student.mingfengl.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.data.ServerResponse
import au.edu.unimelb.student.mingfengl.networking.NetworkingManager
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request

class ForgetPWD  : AppCompatActivity(){
    companion object{
        const val MESSAGE_WHAT = 1000
    }
    private var uiHandler= object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg?.what){
                ForgetPWD.MESSAGE_WHAT->{
                    finish()
                }

                else->{

                }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetpwd)
        val email = findViewById<EditText>(R.id.forget_email)
        val submit = findViewById<Button>(R.id.forget_submit)
        val loading = findViewById<ProgressBar>(R.id.forget_loading)

        submit.setOnClickListener {
            loading.visibility = View.VISIBLE
            Thread(Runnable {
                kotlin.run {
                    var message = Message()
                    message.what = 1000
                    var response = NetworkingManager.instance.send(requestBody(
                        FormBody.Builder()
                            .add("email",email.text.toString()).build()
                    ))
                    var strResponse = response.body!!.string()
                    var msg = Gson().fromJson(strResponse,ServerResponse::class.java)
                    if (msg.response.equals("True")){
                        Log.i("Forget Password","Success!!")
                    }else{
                        Log.i("Forget Password","Failed!!")
                    }

                    uiHandler.sendMessage(message)
                }
            }).start()
        }

    }
    private fun requestBody(formBody:FormBody):Request{
        return Request.Builder()
            .url(GlobalApplication.getApplication().url+"/forget")
            .post(formBody)
            .build()
    }

}