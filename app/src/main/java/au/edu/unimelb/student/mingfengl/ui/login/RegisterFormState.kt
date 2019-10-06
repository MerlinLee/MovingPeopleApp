package au.edu.unimelb.student.mingfengl.ui.login

data class RegisterFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)