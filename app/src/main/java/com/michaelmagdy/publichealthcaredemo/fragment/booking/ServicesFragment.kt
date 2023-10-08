package com.michaelmagdy.publicHealthCareDemo.fragment.booking

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
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
import com.michaelmagdy.publicHealthCareDemo.alert
import com.michaelmagdy.publicHealthCareDemo.currentUserId
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentHomeBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.booking.BookingEntity
import com.michaelmagdy.publicHealthCareDemo.fragment.BaseFragment
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

class ServicesFragment : BaseFragment() {


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

        val provId = ServicesFragmentArgs.fromBundle(arguments!!).providerId

        GlobalScope.launch {


            withContext(Dispatchers.Main) {
                context?.let {
                    var list = HealthCareDatabase(it).providerDao()
                        .getProviderServices(provId)
                    if (list.isNotEmpty()) {

                        var strList: ArrayList<String> = arrayListOf()
                        for (p in list){
                            strList.add(
                                HealthCareDatabase(it).serviceDao().getServiceNameById(p)
                            )

                        }

                        listAdapter = ListAdapter(
                            strList
                        )

                        listAdapter.setOnItemClickListener(object : ListAdapter.OnItemClickListener{
                            override fun onItemClick(position: Int) {

                                GlobalScope.launch {
                                    context?.let {
                                        val bookingEntity = BookingEntity(
                                            SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z").format(
                                                Date()
                                            ),
                                            HealthCareDatabase(it).serviceDao().getServiceId(strList[position]),
                                            provId,
                                            currentUserId
                                            )
                                        HealthCareDatabase(it).bookingDao().insertBooking(bookingEntity)

                                    }
                                }
                                context?.alert("Booking placed successfully", DialogInterface.OnClickListener(
                                    { dialog, which ->
                                        //context?.toast("alert dialog action done")
                                        findNavController().navigate(R.id.action_servicesFragment_to_homeFragment)
                                    }
                                ))
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