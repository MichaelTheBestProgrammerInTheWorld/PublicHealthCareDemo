package com.michaelmagdy.publicHealthCareDemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.adapter.TaskAdapter
import com.michaelmagdy.publicHealthCareDemo.adapter.TaskAdapter.Deletetask
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentListofNoteBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.*

class ListofNoteFragment : BaseFragment(), Deletetask {


    private lateinit var listofNoteBinding: FragmentListofNoteBinding
    private val binding get() = listofNoteBinding

    private lateinit var viewModel: ListofNoteViewModel
    private lateinit var arrayList: List<UserEntity>
    private lateinit var taskAdapter: TaskAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        listofNoteBinding = FragmentListofNoteBinding.inflate(inflater, container, false)
        return listofNoteBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ListofNoteViewModel::class.java]

        binding.recycleview.apply {
            layoutManager =
                LinearLayoutManager(context?.applicationContext, RecyclerView.VERTICAL, true)
            this.setHasFixedSize(true)

        }

        // coroutine implementation
        getdatafrombd()


        binding.febAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mobile_navigation_to_addNoteFragment)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getdatafrombd() {


        GlobalScope.launch {


            withContext(Dispatchers.Main) {
                context?.let {
                    if (HealthCareDatabase(it).userDao().getAllUsers().isNotEmpty()) {

                        binding.recycleview.adapter = TaskAdapter(
                            requireContext(),
                            HealthCareDatabase(it).userDao().getAllUsers(),
                            this@ListofNoteFragment
                        )

                    }
                }

            }



            Log.e("TAG", "onViewCreated: " + HealthCareDatabase(requireContext()).userDao().getAllUsers())
        }

        binding.recycleview.adapter?.notifyDataSetChanged()
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun deletitem(userEntity: UserEntity) {

        GlobalScope.launch {
            HealthCareDatabase(requireContext()).userDao().delete(userEntity)


        }
        getdatafrombd()
        requireContext().toast("Data Deleted Successfully")
    }
}