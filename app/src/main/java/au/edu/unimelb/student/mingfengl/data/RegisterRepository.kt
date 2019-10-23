package au.edu.unimelb.student.mingfengl.data

import au.edu.unimelb.student.mingfengl.data.model.RegisterUser

class RegisterRepository (val dataSource: RegisterDataSource){
    var user: RegisterUser? = null
        private set

    val isRegisterUser: Boolean
        get() = user != null
    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }
    fun logout() {
        user = null
        dataSource.logout()
    }

    fun register(username: String, password: String,email:String):Result<RegisterUser>{
        val result = dataSource.register(username,password,email)
        if (result is Result.Success) {
            setLRegisterUser(result.data)
        }

        return result
    }

    private fun setLRegisterUser(registerUser: RegisterUser) {
        this.user = registerUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}