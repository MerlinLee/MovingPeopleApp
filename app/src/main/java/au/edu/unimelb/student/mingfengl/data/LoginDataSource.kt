package au.edu.unimelb.student.mingfengl.data

import android.os.Handler
import android.util.Log
import au.edu.unimelb.student.mingfengl.data.model.LoggedInUser
import au.edu.unimelb.student.mingfengl.data.model.UserPwdPair
import au.edu.unimelb.student.mingfengl.networking.NetworkingManager
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.lang.Exception

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    val client = OkHttpClient()
    var isSuccess:Boolean = false
    fun login(username: String, password: String,uiHandler: Handler): Result<LoggedInUser> {
        var loggedInUser = LoggedInUser("","")
        try {
            var formBody = FormBody.Builder().add("username",username)
                .add("password",password)
                .build()
            var request = Request.Builder()
                .url(GlobalApplication.getApplication().url+"/app/login")
                .post(formBody)
                .build()
//            var call = client.newCall(request)
            try {
                var response = NetworkingManager.instance.send(request)
                if(response.isSuccessful){
                    val content :String = response.body!!.string()
                    var msg = Gson().fromJson(content,ServerResponse::class.java)
                    if (msg.code==0){
                        isSuccess = true
                    }
                    if (msg.code==-1){
                        isSuccess = false
                    }
                    loggedInUser = LoggedInUser(java.util.UUID.randomUUID().toString(),
                            msg.errmsg)
//                    var serverResult = gson.fromJson(content,LoginResult::class.java)
//                    if(serverResult.result){
//                        isSuccess = true
//                        loggedInUser = LoggedInUser(java.util.UUID.randomUUID().toString(),
//                            serverResult.displayName)
//                        GlobalApplication.getApplication().cookie = response.headers.get("Set-Cookie")
//                    }else{
//                        isSuccess = false
//                    }
                    Log.i("ServerToClient",content)
                }else{
                    isSuccess = false
                    Log.i("ServerToClient","unSuccess")
                }//To change body of created functions use File | Settings | File Templates.

            }catch (e:Exception){
                Log.e("ServerToClient","Error!!!")
            }

            if(isSuccess){
                return Result.Success(loggedInUser)
            }else{
                return Result.Error(IOException())
            }

        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }


    fun logout() {
        // TODO: revoke authentication
    }

}

