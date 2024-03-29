package au.edu.unimelb.student.mingfengl.ui.login

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import au.edu.unimelb.student.mingfengl.data.LoginRepository
import au.edu.unimelb.student.mingfengl.data.Result

import au.edu.unimelb.student.mingfengl.R

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String,uiHandler: Handler) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password,uiHandler)

        if (result is Result.Success) {
            try {
                _loginResult.postValue( LoginResult(success = LoggedInUserView(displayName = result.data.displayName)))

            }catch (e:Exception){
                Log.e("LoginViewModel",e.toString())
            }

        } else {
            _loginResult.postValue(LoginResult(error = R.string.login_failed))
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
