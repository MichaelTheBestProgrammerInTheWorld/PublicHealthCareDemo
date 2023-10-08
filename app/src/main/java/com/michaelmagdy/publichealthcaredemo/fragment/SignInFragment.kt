package com.michaelmagdy.publicHealthCareDemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.currentUserId
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentSignInBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignInFragment : BaseFragment() {




    private lateinit var signInBinding: FragmentSignInBinding
    private val binding get() = signInBinding

    private lateinit var arrayList: List<UserEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        signInBinding = FragmentSignInBinding.inflate(inflater, container, false)
        return signInBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // coroutine implementation
        getDataFromDb()


        binding.btnCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_createAccount)
        }

        binding.btnLogin.setOnClickListener {
            if (validation()){
                signInUser()
            }
        }

    }

    private fun signInUser() {
        GlobalScope.launch{
            withContext(Dispatchers.Main) {
                context?.let {
                    val loggedUser = HealthCareDatabase(it).userDao()
                        .getUser(binding.etName.text.toString().trim(), binding.etPassword.text.toString().trim())
                    if (loggedUser
                            != null) {

                        currentUserId = loggedUser.id
                        findNavController().navigate(R.id.action_signIn_to_homeScreen)
                    } else {
                        context?.toast("Wrong username or password")
                    }
                }

            }



            Log.e("TAG", "onViewCreated: " + HealthCareDatabase(requireContext()).userDao().getAllUsers())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataFromDb() {


        GlobalScope.launch {


            withContext(Dispatchers.Main) {
                context?.let {
                    if (HealthCareDatabase(it).userDao().getAllUsers().isNotEmpty()) {


                    } else {
                        val userEntity = UserEntity(
                            "admin",
                            "admin",
                            1
                        )
                        HealthCareDatabase(it).userDao().createUser(userEntity)
                    }
                    if (HealthCareDatabase(it).locationDao().getAllLocations().isNotEmpty()) {


                    } else {
                        val locationEntity = LocationEntity(
                            "Heliopolis"
                        )
                        HealthCareDatabase(it).locationDao().insertLocation(locationEntity)
                    }
                }

            }



            Log.e("TAG", "onViewCreated: " + HealthCareDatabase(requireContext()).userDao().getAllUsers())
        }


    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding.etName.text)) {
            context?.toast("Please enter username")


            return false
        } else if (TextUtils.isEmpty(binding.etPassword.text)) {
            context?.toast("Please enter password")



            return false
        }
        return true

    }

}