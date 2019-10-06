package au.edu.unimelb.student.mingfengl.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.edu.unimelb.student.mingfengl.data.RegisterDataSource
import au.edu.unimelb.student.mingfengl.data.RegisterRepository

class RegisterViewModelFactory : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                registerRepository = RegisterRepository(
                    dataSource = RegisterDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}