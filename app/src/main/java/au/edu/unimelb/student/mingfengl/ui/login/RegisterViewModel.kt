package au.edu.unimelb.student.mingfengl.ui.login

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.data.RegisterRepository
import au.edu.unimelb.student.mingfengl.data.Result

class RegisterViewModel(private val registerRepository: RegisterRepository) :ViewModel(){
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm
    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(username: String, password: String,email:String) {
        // can be launched in a separate asynchronous job
        val result = registerRepository.register(username, password,email)

        if (result is Result.Success) {
            try {
                _registerResult.postValue( RegisterResult(success = RegisterUserView(displayName = result.data.username)))

            }catch (e:Exception){
                Log.e("RegisterViewModel",e.toString())
            }

        } else {
            _registerResult.value = RegisterResult(error = R.string.login_failed)
        }
    }
}