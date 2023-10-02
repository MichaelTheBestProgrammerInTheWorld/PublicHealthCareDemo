package com.michaelmagdy.publicHealthCareDemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.adapter.TaskAdapter
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentSignInBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
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

    private lateinit var viewModel: ListofNoteViewModel
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
        viewModel = ViewModelProvider(this)[ListofNoteViewModel::class.java]

        // coroutine implementation
        getDataFromDb()


        binding.btnCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_createAccount)
        }

        binding.btnLogin.setOnClickListener {
            signInUser()
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

                        findNavController().navigate(R.id.action_signIn_to_homeScreen)
                    } else {
                        Toast.makeText(
                            it,
                            "Wrong username or password",
                            Toast.LENGTH_LONG
                        ).show()
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
                            0
                        )
                        HealthCareDatabase(it).userDao().createUser(userEntity)
                    }
                }

            }



            Log.e("TAG", "onViewCreated: " + HealthCareDatabase(requireContext()).userDao().getAllUsers())
        }


    }

}