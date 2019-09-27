package au.edu.unimelb.student.mingfengl.ui.login.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import au.edu.unimelb.student.mingfengl.R

class UserFragment : Fragment(){
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel =
            ViewModelProviders.of(this).get(UserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_users, container, false)
        val textView: TextView = root.findViewById(R.id.text_users)
        userViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}