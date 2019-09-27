package au.edu.unimelb.student.mingfengl.data

import android.os.Handler
import android.os.Message
import android.util.Log
import au.edu.unimelb.student.mingfengl.data.model.LoggedInUser
import au.edu.unimelb.student.mingfengl.data.model.UserPwdPair
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.internal.wait
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    val client = OkHttpClient()
    val gson = Gson()
    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    var isSuccess:Boolean = false
    fun login(username: String, password: String): Result<LoggedInUser> {
        var loggedInUser:LoggedInUser = LoggedInUser("","")
        try {
            // TODO: handle loggedInUser authentication
            var json = gson.toJson(UserPwdPair(username,password))
            var body = RequestBody.create(JSON,json)
            var request = Request.Builder()
                .url("http://192.168.86.27:8080/login")
                .post(body)
                .build()
            var call = client.newCall(request)
            call.enqueue(object :Callback{
                override fun onFailure(call: Call, e: IOException) {
                    isSuccess = false//To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        val content :String = response.body!!.string()
                        var serverResult = gson.fromJson(content,LoginResult::class.java)
                        if(serverResult.result){
                            isSuccess = true
                            loggedInUser = LoggedInUser(java.util.UUID.randomUUID().toString(),
                                serverResult.displayName)
                        }else{
                            isSuccess = false
                        }
                        Log.i("ServerToClient",content)
                    }else{
                        isSuccess = false
                        Log.i("ServerToClient","unSuccess")
                    }//To change body of created functions use File | Settings | File Templates.
                }

            })
            
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

