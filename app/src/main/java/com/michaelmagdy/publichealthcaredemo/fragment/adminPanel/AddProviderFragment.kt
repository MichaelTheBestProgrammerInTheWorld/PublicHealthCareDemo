package com.michaelmagdy.publicHealthCareDemo.fragment.adminPanel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.alert
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAddProviderBinding
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAddServiceBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.service.ServiceEntity
import com.michaelmagdy.publicHealthCareDemo.fragment.BaseFragment
import com.michaelmagdy.publicHealthCareDemo.fragment.booking.ProvidersFragmentDirections
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddProviderFragment : BaseFragment() {


    private lateinit var addProviderBinding: FragmentAddProviderBinding
    private val binding get() = addProviderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        addProviderBinding = FragmentAddProviderBinding.inflate(inflater, container, false)
        return addProviderBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()


    }

    private fun setupSpinners() {


        GlobalScope.launch {
            context?.let {
                //category spinner
                val categoryList =  HealthCareDatabase(it).providerDao().getAllCategories()
                var categories = arrayOfNulls<String>(categoryList.size)
                for (i in 0..categoryList.size-1){
                    categories[i] = categoryList.get(i).title
                }
                val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    categories)
                ad.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item)
                binding.categorySpinner.adapter = ad


                //location spinner
                val locationsList =  HealthCareDatabase(it).locationDao().getAllLocations()
                var locations = arrayOfNulls<String>(locationsList.size)
                for (i in 0..locationsList.size-1){
                    locations[i] = locationsList.get(i).locationName
                }
                val ad2: ArrayAdapter<*> = ArrayAdapter<Any?>(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    locations)
                ad2.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item)
                binding.locationSpinner.adapter = ad2

                binding.button3.setOnClickListener {
                    val title = binding.editTextText3.text.toString().trim()
                    val categoryName = binding.categorySpinner.selectedItem.toString()
                    val locationName = binding.locationSpinner.selectedItem.toString()
                    GlobalScope.launch (Dispatchers.Main) {
                        context?.let {
                            val provider = ProviderEntity(
                                title,
                                HealthCareDatabase(it).providerDao().getCategoryId(categoryName),
                                HealthCareDatabase(it).locationDao().getLocationId(locationName)
                            )
                            HealthCareDatabase(it).providerDao().insertProvider(provider)
                            val action = AddProviderFragmentDirections.actionAddProviderFragmentToAddProviderServicesFragment(provider)
                            findNavController().navigate(action)
                        }
                    }

                }
            }
        }

    }

}