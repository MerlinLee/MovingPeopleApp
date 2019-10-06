package au.edu.unimelb.student.mingfengl.data

import android.util.Log
import au.edu.unimelb.student.mingfengl.data.model.LoggedInUser
import au.edu.unimelb.student.mingfengl.data.model.RegisterUser
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.Exception
import java.util.*

class RegisterDataSource {
    val client = OkHttpClient()
    var isSuccess:Boolean = false
    fun register(username: String, password: String,email:String):Result<RegisterUser> {
        var registerUser = RegisterUser("", "", "")
        try {
            var formBody = FormBody.Builder().add("username", username)
                .add("password", password)
                .add("email", email)
                .build()
            var request = Request.Builder()
                .url(GlobalApplication.getApplication().url + "/register")
                .post(formBody)
                .build()
            var call = client.newCall(request)
            try {
                var response = call.execute()
                if (response.isSuccessful) {
                    val content: String = response.body!!.string()
                    isSuccess = true
                    registerUser = RegisterUser(
                        "",
                        content, ""
                    )
//                    var serverResult = gson.fromJson(content,LoginResult::class.java)
//                    if(serverResult.result){
//                        isSuccess = true
//                        loggedInUser = LoggedInUser(java.util.UUID.randomUUID().toString(),
//                            serverResult.displayName)
//                        GlobalApplication.getApplication().cookie = response.headers.get("Set-Cookie")
//                    }else{
//                        isSuccess = false
//                    }
                    Log.i("ServerToClient", content)
                } else {
                    isSuccess = false
                    Log.i("ServerToClient", "unSuccess")
                }//To change body of created functions use File | Settings | File Templates.

            } catch (e: Exception) {
                Log.e("ServerToClient", "Error!!!")
            }
            if (isSuccess) {
                return Result.Success(registerUser)
            } else {
                return Result.Error(IOException())
            }
        }catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout(){

    }
}

