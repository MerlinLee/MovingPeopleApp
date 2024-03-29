package au.edu.unimelb.student.mingfengl.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*

import au.edu.unimelb.student.mingfengl.R
import au.edu.unimelb.student.mingfengl.services.GlobalApplication
import cn.jpush.android.api.JPushInterface
import com.google.gson.Gson
import okhttp3.OkHttpClient
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    companion object{
        const val MESSAGE_WHAT = 1000
        const val REQUEST_REGISTER = 1
        const val REQUEST_FORGET = 2
        const val REQUEST_ADMIN = 3
    }

    private lateinit var loginViewModel: LoginViewModel
    lateinit var loginIntent: Intent
    lateinit var forgetIntent: Intent
    private var uiHandler= object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg?.what){
                LoginActivity.MESSAGE_WHAT->{
                    JPushInterface.setAlias(GlobalApplication.getContext(), 0, "merlin");
                    loginIntent = Intent()
                    loginIntent.setAction("au.edu.success.admin")
                    startActivityForResult(loginIntent,REQUEST_ADMIN)
                }
                else->{
                }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intent = Intent()
        var bundle = Bundle()
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplicationContext());
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val forget_password = findViewById<TextView>(R.id.forget_pwd)
        forget_password.isClickable = true

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
        register.setOnClickListener {
            this.loginIntent = Intent()
            loginIntent.setAction("au.edu.register")
            startActivityForResult(this.loginIntent,REQUEST_REGISTER)
        }
        forget_password.setOnClickListener {
            this.forgetIntent = Intent()
            this.forgetIntent.setAction("au.edu.forget")
            startActivityForResult(this.forgetIntent, REQUEST_FORGET)
        }
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                bundle.putString("loggedUserData",Gson().toJson(loginResult))
                intent.putExtras(bundle)
                setResult(Activity.RESULT_OK,intent)
            }

            //Complete and destroy login activity once successful
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString(),uiHandler
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                Thread(Runnable {
                    kotlin.run {
                        //
                        loginViewModel.login(username.text.toString(), password.text.toString(),uiHandler)
                       //
                    }
                }).start()
            }


        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_REGISTER && resultCode == RESULT_OK){
                //TODO
//            setResult(Activity.RESULT_OK,intent)
//
//
//            finish()
        }
        if (requestCode == REQUEST_FORGET && resultCode == RESULT_OK){
            onResume()
        }
    }
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(applicationContext, welcome+displayName, Toast.LENGTH_SHORT).show()
        var message = Message()
        message.what=1000
        uiHandler.sendMessage(message)
        // TODO : initiate successful logged in experience

    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })



}
