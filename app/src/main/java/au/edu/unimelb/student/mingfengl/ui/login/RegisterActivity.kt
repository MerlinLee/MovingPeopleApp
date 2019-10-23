package au.edu.unimelb.student.mingfengl.ui.login

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import au.edu.unimelb.student.mingfengl.R

class RegisterActivity : AppCompatActivity(){
    private lateinit var registerViewModel: RegisterViewModel
    private var uiHandler= object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg?.what){
                LoginActivity.MESSAGE_WHAT->{
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }

                else->{

                }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val username = findViewById<EditText>(R.id.register_username)
        val password = findViewById<EditText>(R.id.register_password)
        val re_password = findViewById<EditText>(R.id.register_re_password)
        val email = findViewById<EditText>(R.id.register_email)
        val submit = findViewById<Button>(R.id.register_submit)
        val loading = findViewById<ProgressBar>(R.id.register_loading)
        registerViewModel = ViewModelProviders.of(this,RegisterViewModelFactory())
            .get(RegisterViewModel::class.java)
        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer
            // disable login button unless both username / password is valid
            submit.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
        })


        submit.setOnClickListener {
            loading.visibility = View.VISIBLE
            if (password.text.toString().equals(re_password.text.toString())){
                Thread(Runnable {
                    kotlin.run {
                        var message = Message()
                        message.what = 1000
                        registerViewModel.register(username.text.toString(),
                            password.text.toString(),email.text.toString())
                        uiHandler.sendMessage(message)
                    }
                }).start()
            }else{
                Toast.makeText(applicationContext, "Passwords are not Equal!", Toast.LENGTH_SHORT).show()
                loading.visibility = View.GONE
            }

        }
    }
}