package com.michaelmagdy.publicHealthCareDemo.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAddNoteBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {
    private var utaskItem: UserEntity? = null

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    private lateinit var addNoteBinding: FragmentAddNoteBinding
    private val binding get() = addNoteBinding

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        addNoteBinding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]
        arguments?.let {
            utaskItem = AddNoteFragmentArgs.fromBundle(it).taskItem
            binding.etName.setText(utaskItem?.username)
            binding.etDescreptiuon.setText(utaskItem?.password)
        }

        binding.btnAdd.setOnClickListener(View.OnClickListener {
            if (validation()) {
                GlobalScope.launch {


                    context?.let {
                        val userEntity = UserEntity(
                            binding.etName.text.toString(),
                            binding.etDescreptiuon.text.toString(),
                            0
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

                findNavController().navigate(R.id.action_addNoteFragment_to_home_fragment)


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
            context?.toast("Please enter task title")


            return false
        } else if (TextUtils.isEmpty(binding.etDescreptiuon.text)) {
            context?.toast("Please enter task description")



            return false
        }
        return true

    }

}