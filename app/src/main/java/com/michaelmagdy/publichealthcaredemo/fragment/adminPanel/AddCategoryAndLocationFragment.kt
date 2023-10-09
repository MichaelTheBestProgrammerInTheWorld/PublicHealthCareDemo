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
import com.michaelmagdy.publicHealthCareDemo.adapter.ListAdapter
import com.michaelmagdy.publicHealthCareDemo.alert
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAddCategoryAndLocationBinding
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAdminPanelBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.Category
import com.michaelmagdy.publicHealthCareDemo.fragment.booking.ProvidersFragmentArgs
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddCategoryAndLocationFragment : Fragment() {


    private lateinit var addCategoryAndLocationBinding: FragmentAddCategoryAndLocationBinding
    private val binding get() = addCategoryAndLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        addCategoryAndLocationBinding = FragmentAddCategoryAndLocationBinding.inflate(inflater, container, false)
        return addCategoryAndLocationBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val position = AddCategoryAndLocationFragmentArgs.fromBundle(requireArguments()).position
        binding.button.setOnClickListener {
            val title = binding.editTextText.text.toString().trim()
            if (position == 0){
                val category = Category(title)
                GlobalScope.launch {
                    context?.let {
                        HealthCareDatabase(it).providerDao().insertCategory(category)
                    }
                }
            } else {
                val location = LocationEntity(title)
                GlobalScope.launch {
                    context?.let {
                        HealthCareDatabase(it).locationDao().insertLocation(location)
                    }
                }
            }
            context?.alert("added successfully",
                { dialog, which ->
                    dialog.dismiss()
                }
                )
        }
    }

}