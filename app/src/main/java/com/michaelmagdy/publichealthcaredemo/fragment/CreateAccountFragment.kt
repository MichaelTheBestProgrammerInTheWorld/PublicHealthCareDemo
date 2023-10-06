package com.michaelmagdy.publicHealthCareDemo.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentCreateAccountBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateAccountFragment : BaseFragment(), AdapterView.OnItemSelectedListener {
    private var utaskItem: UserEntity? = null

    var locationId = 0

        companion object {
        fun newInstance() = CreateAccountFragment()
    }

    private lateinit var addNoteBinding: FragmentCreateAccountBinding
    private val binding get() = addNoteBinding

    private lateinit var viewModel: CreateAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        addNoteBinding = FragmentCreateAccountBinding.inflate(layoutInflater, container, false)
        setLocationSpinner()
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?,
                                view: View, position: Int,
                                id: Long) {

        getLocationId(parent?.selectedItem.toString())
    }

    private fun getLocationId(location: String) {
        GlobalScope.launch {
            context?.let {
              locationId =  HealthCareDatabase(it).locationDao().getLocationId(location)
            }
        }
    }

    private fun getAllLocations() {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun setLocationSpinner() {


        //TODO: LOCATIONS LIST BUG FIX
        GlobalScope.launch {
            context?.let {
                val locationsList =  HealthCareDatabase(it).locationDao().getAllLocations()
                var locations = arrayOfNulls<String>(locationsList.size)
                for (i in 0..locationsList.size-1){
                    locations[i] = locationsList.get(i).locationName
                }
                val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    locations)
                ad.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item)
                binding.spnLocation.adapter = ad

            }
        }

        binding.spnLocation.onItemSelectedListener = this

        // Create the instance of ArrayAdapter
        // having the list of courses


        // set simple layout resource file
        // for each item of spinner


        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateAccountViewModel::class.java]
        arguments?.let {
            utaskItem = CreateAccountFragmentArgs.fromBundle(it).userEntity
            binding.etName.setText(utaskItem?.username)
            binding.etDescreptiuon.setText(utaskItem?.password)
        }

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment_to_signInFragment)
        }

        binding.btnAdd.setOnClickListener(View.OnClickListener {
            if (validation()) {
                GlobalScope.launch {


                    context?.let {
                        val userEntity = UserEntity(
                            binding.etName.text.toString().lowercase(),
                            binding.etDescreptiuon.text.toString().lowercase(),
                            HealthCareDatabase(it).locationDao()
                                .getLocationId(binding.spnLocation.selectedItem.toString())
                        );
                        if (utaskItem == null) {

                            HealthCareDatabase(it).userDao().createUser(userEntity)


                        } else {
                             userEntity.id= utaskItem?.id!!
                            //HealthCareDatabase(it).userDao().update(userEntity)



                        }

                    }


                }
                if(utaskItem==null){
                    context?.toast("Data Insert Successfully")
                }else{
                    context?.toast("Data Updated Successfully")
                }

                findNavController().navigate(R.id.action_createAccountFragment_to_homeFragment)


            }
        })


    }


// add data uesing  AsyncTask
//    private fun addtask() {
//        class datainsert : AsyncTask<Void,Void,Void>(){
//            override fun doInBackground(vararg params: Void?): Void? {
//                val taskItem=TaskItem(binding.etName.text.toString(),binding.etDescreptiuon.text.toString(),0);
//                TaskDatabase(requireContext()).TaskDao().inserttaskItem(taskItem)
//               return null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//                Toast.makeText(context,"Save data",Toast.LENGTH_LONG).show()
//                binding.etName.text?.clear()
//                binding.etDescreptiuon.text?.clear()
//            }
//        }
//        datainsert().execute()
//
//    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(binding.etName.text)) {
            context?.toast("Please enter username")


            return false
        } else if (TextUtils.isEmpty(binding.etDescreptiuon.text)) {
            context?.toast("Please enter password")



            return false
        } else if (binding.etName.text.toString().lowercase() == "admin") {
            context?.toast("You cant use this username")



            return false
        }
        return true

    }

}