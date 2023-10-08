package com.michaelmagdy.publicHealthCareDemo.fragment.profile

import android.annotation.SuppressLint
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
import com.michaelmagdy.publicHealthCareDemo.adapter.ProfileAdapter
import com.michaelmagdy.publicHealthCareDemo.alert
import com.michaelmagdy.publicHealthCareDemo.currentUserId
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentHomeBinding
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentProfileBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.booking.BookingEntity
import com.michaelmagdy.publicHealthCareDemo.fragment.BaseFragment
import com.michaelmagdy.publicHealthCareDemo.fragment.booking.ServicesFragmentArgs
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date


class ProfileFragment : BaseFragment() {


    private lateinit var profileBinding: FragmentProfileBinding
    private val binding get() = profileBinding

    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return profileBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileBooking.apply {
            layoutManager =
                LinearLayoutManager(context?.applicationContext, RecyclerView.VERTICAL, false)
            this.setHasFixedSize(true)

        }

        // coroutine implementation
        getdatafrombd()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getdatafrombd() {

        GlobalScope.launch {


            withContext(Dispatchers.Main) {
                context?.let {
                   val username = HealthCareDatabase(it).userDao().getUserById(currentUserId).username
                   val location = HealthCareDatabase(it).locationDao().getLocationName(
                       HealthCareDatabase(it).userDao().getUserById(currentUserId).locationId
                   )
                    val bookings = HealthCareDatabase(it).bookingDao().getAllBookingsByUser(currentUserId)

                    binding.textView2.text = username
                    binding.textView4.text = location
                    //Log.d("currentProfile", location)
                    //Log.d("currentProfile", HealthCareDatabase(it).providerDao().getProviderById(bookings.get(0).providerId).providerName)
                    Log.d("currentProfile", HealthCareDatabase(it).providerDao().getAllProviders().toString())
                    Log.d("currentProfile", HealthCareDatabase(it).serviceDao().getAllServices().toString())
                    profileAdapter = ProfileAdapter(bookings)
                    binding.profileBooking.adapter = profileAdapter
                }

            }



            Log.e("TAG", "onViewCreated: " + HealthCareDatabase(requireContext()).bookingDao().getAllBookings())
        }


        binding.profileBooking.adapter?.notifyDataSetChanged()
    }

}