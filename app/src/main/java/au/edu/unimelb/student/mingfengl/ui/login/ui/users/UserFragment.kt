package au.edu.unimelb.student.mingfengl.ui.login.ui.users

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.networking.NetworkingManager
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import okhttp3.Request

class UserFragment : Fragment(){
    companion object{
        const val MESSAGE_WHAT = 1000
        const val REQUEST_REGISTER = 1
        const val REQUEST_FORGET = 2
    }
    private lateinit var userViewModel: UserViewModel
    private lateinit var btn_logout:ImageButton
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel =
            ViewModelProviders.of(this).get(UserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_users, container, false)
        btn_logout = root.findViewById(R.id.user_imageButton)
        btn_logout.setOnClickListener {
            Thread(Runnable {
                var response = NetworkingManager.instance.send(
                    Request.Builder()
                        .url(GlobalApplication.getApplication().url+"/app/logout")
                        .build()
                )
                if(response.isSuccessful){
                    val content :String = response.body!!.string()
                    var message = Message()
                    message.what = 1000
                    message.data.putString("response",content)
                    uiHandler.sendMessage(message)
                }
            }).start()
        }
        return root
    }

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
}