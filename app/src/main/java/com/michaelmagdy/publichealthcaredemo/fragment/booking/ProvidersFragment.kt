package com.michaelmagdy.publicHealthCareDemo.fragment.booking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.adapter.ListAdapter
import com.michaelmagdy.publicHealthCareDemo.currentUserId
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentHomeBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.fragment.BaseFragment
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProvidersFragment : BaseFragment() {


    private lateinit var homeBinding: FragmentHomeBinding
    private val binding get() = homeBinding

    private lateinit var listAdapter: ListAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleview.apply {
            layoutManager =
                LinearLayoutManager(context?.applicationContext, RecyclerView.VERTICAL, true)
            this.setHasFixedSize(true)

        }

        // coroutine implementation
        getdatafrombd()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getdatafrombd() {

        val catId = ProvidersFragmentArgs.fromBundle(arguments!!).categoryId

        GlobalScope.launch {


            withContext(Dispatchers.Main) {
                context?.let {
                    var list = HealthCareDatabase(it).providerDao()
                        .getAllProvidersByCategoryAndLocation(catId!!, currentUserId)
                    if (list.isNotEmpty()) {

                        var strList: ArrayList<String> = arrayListOf()
                        for (p in list){
                            strList.add(p.providerName)
                        }

                        listAdapter = ListAdapter(
                            strList
                        )

                        listAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
                            override fun onItemClick(position: Int) {
                                GlobalScope.launch (Dispatchers.Main) {
                                    context?.let {
                                        val action = ProvidersFragmentDirections.actionProvidersFragmentToServicesFragment()
                                            .setProviderId(HealthCareDatabase(it).providerDao().getProviderId(strList[position]))
                                        findNavController().navigate(action)
                                    }
                                }
                            }
                        })

                        binding.recycleview.adapter = listAdapter

                    } else {
                        context?.toast("Nothing to show")
                    }
                }

            }



            Log.e("TAG", "onViewCreated: " + HealthCareDatabase(requireContext()).userDao().getAllUsers())
        }

        binding.recycleview.adapter?.notifyDataSetChanged()
    }

}