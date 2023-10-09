package com.michaelmagdy.publicHealthCareDemo.fragment.adminPanel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.michaelmagdy.publicHealthCareDemo.R
import com.michaelmagdy.publicHealthCareDemo.alert
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAddCategoryAndLocationBinding
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAddServiceBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.location.LocationEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.Category
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.service.ServiceEntity
import com.michaelmagdy.publicHealthCareDemo.fragment.BaseFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddServiceFragment : BaseFragment() {


    private lateinit var addServiceBinding: FragmentAddServiceBinding
    private val binding get() = addServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        addServiceBinding = FragmentAddServiceBinding.inflate(inflater, container, false)
        return addServiceBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCategorySpinner()


    }

    private fun setCategorySpinner() {


        GlobalScope.launch {
            context?.let {
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
                binding.spinner.adapter = ad


                binding.button2.setOnClickListener {
                    val title = binding.editTextText2.text.toString().trim()
                    val categoryName = binding.spinner.selectedItem.toString()
                    GlobalScope.launch {
                        context?.let {
                            val service = ServiceEntity(
                                title,
                                HealthCareDatabase(it).providerDao().getCategoryId(categoryName)
                            )
                            HealthCareDatabase(it).serviceDao().insertService(service)
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

    }

}