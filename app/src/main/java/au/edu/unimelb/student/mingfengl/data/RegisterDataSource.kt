package au.edu.unimelb.student.mingfengl.data

import android.util.Log
import au.edu.unimelb.student.mingfengl.data.model.LoggedInUser
import au.edu.unimelb.student.mingfengl.data.model.RegisterUser
import au.edu.unimelb.student.mingfengl.networking.NetworkingManager
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.Exception
import java.util.*

class RegisterDataSource {
    var isSuccess:Boolean = false
    fun register(username: String, password: String,email:String):Result<RegisterUser> {
        var registerUser = RegisterUser("", "", "")
        try {
            var formBody = FormBody.Builder().add("username", username)
                .add("password", password)
                .add("email", email)
                .build()
            var request = Request.Builder()
                .url(GlobalApplication.getApplication().url + "/app/register")
                .post(formBody)
                .build()
            try {
                var response = NetworkingManager.instance.send(request)
                if (response.isSuccessful) {
                    val content: String = response.body!!.string()
                    isSuccess = true
                    registerUser = RegisterUser(
                        "",
                        content, ""
                    )
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

