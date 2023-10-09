package com.michaelmagdy.publicHealthCareDemo.fragment.adminPanel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.adapter.ListAdapter
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAdminPanelBinding
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentHomeBinding
import com.michaelmagdy.publicHealthCareDemo.fragment.BaseFragment
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.DelicateCoroutinesApi


class AdminPanelFragment : BaseFragment() {


    private lateinit var adminPanelBinding: FragmentAdminPanelBinding
    private val binding get() = adminPanelBinding

    private lateinit var listAdapter: ListAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        adminPanelBinding = FragmentAdminPanelBinding.inflate(inflater, container, false)
        return adminPanelBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val options = arrayOf(
            "Add Category", "Add Provider", "Add Service",
            "Add Location", "View Statistics"
        )
        val arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1, options)
        binding.panelList.adapter = arrayAdapter
        binding.panelList.setOnItemClickListener { adapterView, view, i, l ->
            context?.toast("${options[i]} is clicked")
        }

        // coroutine implementation
        //getdatafrombd()

    }

}