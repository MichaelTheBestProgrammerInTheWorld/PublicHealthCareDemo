package com.michaelmagdy.publicHealthCareDemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.adapter.ListAdapter
import com.michaelmagdy.publicHealthCareDemo.adapter.ListAdapter.DeleteItem
import com.michaelmagdy.publicHealthCareDemo.currentUserId
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentHomeBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.Category
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderServices
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.service.ServiceEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.*

class HomeFragment : BaseFragment(), DeleteItem {

    private lateinit var homeBinding: FragmentHomeBinding
    private val binding get() = homeBinding

    private lateinit var arrayList: List<UserEntity>
    private lateinit var listAdapter: ListAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
//        GlobalScope.launch {
//            context?.let {
//                HealthCareDatabase(it).providerDao()
//                    .insertCategory(Category("Hospital"))
//                HealthCareDatabase(it).providerDao()
//                    .insertCategory(Category("Clinic"))
//                HealthCareDatabase(it).providerDao()
//                    .insertCategory(Category("Pharmacy"))
//            }
//        }


//        GlobalScope.launch {
//            context?.let {
//                HealthCareDatabase(it).providerDao()
//                    .insertProvider(ProviderEntity(
//                        "Dar Al-Fouad",
//                        1,
//                        1
//                    ))
//                HealthCareDatabase(it).serviceDao()
//                    .insertService(ServiceEntity(
//                        "ICU",
//                        1
//                    ))
//                HealthCareDatabase(it).providerDao()
//                    .insertProviderService(ProviderServices(
//                        HealthCareDatabase(it).providerDao().getProviderId("Dar Al-Fouad"),
//                        HealthCareDatabase(it).serviceDao().getServiceId("ICU")
//                    ))
//            }
//        }
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("currentUserId", currentUserId.toString())
        if (currentUserId == 1){

            binding.febAdd.visibility = View.VISIBLE
        }
        binding.btnProfile.visibility = View.VISIBLE
        binding.btnProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        binding.recycleview.apply {
            layoutManager =
                LinearLayoutManager(context?.applicationContext, RecyclerView.VERTICAL, true)
            this.setHasFixedSize(true)

        }

        // coroutine implementation
        getdatafrombd()


        binding.febAdd.setOnClickListener {
            findNavController().navigate(R.id.action_home_fragment_to_adminPanelFragment)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getdatafrombd() {


        GlobalScope.launch {


            withContext(Dispatchers.Main) {
                context?.let {
                    if (HealthCareDatabase(it).providerDao().getAllCategories().isNotEmpty()) {

                        var list = HealthCareDatabase(it).providerDao().getAllCategoriesTitles()
                        listAdapter = ListAdapter(
                            list
                        )

                        listAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
                            override fun onItemClick(position: Int) {
                                GlobalScope.launch (Dispatchers.Main) {
                                    context?.let {
                                        val action = HomeFragmentDirections.actionHomeFragmentToProvidersFragment()
                                            .setCategoryId(HealthCareDatabase(it).providerDao().getCategoryId(list[position]))
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

    @OptIn(DelicateCoroutinesApi::class)
    override fun deletitem(userEntity: UserEntity) {

        GlobalScope.launch {
            HealthCareDatabase(requireContext()).userDao().delete(userEntity)


        }
        getdatafrombd()
        requireContext().toast("Data Deleted Successfully")
    }
}