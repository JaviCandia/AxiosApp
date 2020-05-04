package com.example.axios.ui.views.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.axios.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var providers: List<AuthUI.IdpConfig>
    private val MY_REQUEST_CODE: Int = 1996

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == MY_REQUEST_CODE){
            val response = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser //get current user
                Toast.makeText(activity, ""+user!!.email,Toast.LENGTH_SHORT).show() // AQUI PUEDE HABER UN ERROR, MUCHO OJO!!! debe ser this
                btn_sing_out.isEnabled = true
            }
            else{
                Toast.makeText(activity, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSignInOptions(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setTheme(R.style.MyTheme)
            .build(), MY_REQUEST_CODE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init
        providers = Arrays.asList<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(), // Email Login
            //AuthUI.IdpConfig.FacebookBuilder().build(), // Facebook Login
            AuthUI.IdpConfig.GoogleBuilder().build(), // Google Login
            AuthUI.IdpConfig.PhoneBuilder().build() // Phone Login
        )

        showSignInOptions()
        // Event
        btn_sing_out.setOnClickListener{
            //SignOut
            AuthUI.getInstance().signOut(activity!!) // AQUÍ PUEDE HABER ERROR, DEBE SER this@MainActivity
                .addOnCompleteListener{
                    btn_sing_out.isEnabled = false
                    showSignInOptions()
                }
                .addOnFailureListener{
                        e->Toast.makeText(activity!!, e.message,Toast.LENGTH_SHORT).show() // AQUI PUEDE HABER ERROR!!, debe ser this@MainActivity
                }
        }

        button.setOnClickListener{

            // Jalo el texto
                //val caja: EditText = view!!.findViewById(R.id.editText)
                //val paraEnvio = caja.text.toString()

            // Se lo envío en la navegación, esto viene en documentación
            val action = HomeFragmentDirections.actionHomeFragmentToCardsFragment()
            view.findNavController().navigate(action)
        }
    }
}
