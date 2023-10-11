package com.michaelmagdy.publicHealthCareDemo.fragment.adminPanel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michaelmagdy.publicHealthCareDemo.alert
import com.michaelmagdy.publicHealthCareDemo.databinding.FragmentAddProviderServicesBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.ProviderServices
import com.michaelmagdy.publicHealthCareDemo.fragment.BaseFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Collections


class AddProviderServicesFragment : BaseFragment() {


    private lateinit var addProviderServicesBinding: FragmentAddProviderServicesBinding
    private val binding get() = addProviderServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        addProviderServicesBinding = FragmentAddProviderServicesBinding.inflate(inflater, container, false)
        return addProviderServicesBinding.root
    }


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMultiSelectSpinner()


    }

    private fun setupMultiSelectSpinner() {

        val provider = AddProviderServicesFragmentArgs.fromBundle(requireArguments())
            .provider

        Log.d("providerObject", provider.toString())
        GlobalScope.launch {
            context?.let {
                val servicesList =  HealthCareDatabase(it).serviceDao().getAllServicesByCategory(provider.categoryId)
                var services = arrayOfNulls<String>(servicesList.size)
                for (i in 0..servicesList.size-1){
                    services[i] = servicesList.get(i).serviceName
                }

                var selectedLanguage: BooleanArray
                val langList = ArrayList<Int>()
                //val langArray = arrayOf("Java", "C++", "Kotlin", "C", "Python", "Javascript")
                selectedLanguage = BooleanArray(services.size)
                var serviceIdList = arrayListOf<Int>()
                binding.tView.setOnClickListener {


                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)

                    // set title

                    // set title
                    builder.setTitle("Select Language")

                    // set dialog non cancelable

                    // set dialog non cancelable
                    builder.setCancelable(false)

                    builder.setMultiChoiceItems(services, selectedLanguage,
                        OnMultiChoiceClickListener { dialogInterface, i, b ->
                            // check condition
                            if (b) {
                                // when checkbox selected
                                // Add position  in lang list
                                langList.add(i)
                                // Sort array list
                                Collections.sort(langList)
                            } else {
                                // when checkbox unselected
                                // Remove position from langList
                                langList.remove(Integer.valueOf(i))
                            }
                        })


                    builder.setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialogInterface, i -> // Initialize string builder
                            val stringBuilder = StringBuilder()
                            // use for loop
                            serviceIdList.clear()
                            for (j in 0 until langList.size) {
                                // concat array value
                                stringBuilder.append(services.get(langList[j]))
                                // check condition
                                if (j != langList.size - 1) {
                                    // When j value  not equal
                                    // to lang list size - 1
                                    // add comma
                                    stringBuilder.append(", ")
                                }
                                GlobalScope.launch (Dispatchers.Main) {
                                    context?.let {
                                        Log.d("serviceIdList", services.get(langList[j])!!)
                                        serviceIdList.add(
                                            HealthCareDatabase(it).serviceDao()
                                                .getServiceId(services.get(langList[j])!!)
                                        )
                                        Log.d("serviceIdList", serviceIdList.size.toString())
                                        Log.d("serviceIdList", serviceIdList.toString())
                                    }
                                }
                            }

                            // set text on textView
                            binding.tView.setText(stringBuilder.toString())
                        })

                    builder.setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialogInterface, i -> // dismiss dialog
                            dialogInterface.dismiss()
                        })
                    builder.setNeutralButton("Clear All",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            // use for loop
                            for (j in 0 until selectedLanguage.size) {
                                // remove all selection
                                selectedLanguage[j] = false
                                // clear language list
                                langList.clear()
                                serviceIdList.clear()
                                // clear text view value
                                binding.tView.setText("")
                            }
                        })
                    // show dialog
                    // show dialog
                    builder.show()
                    //end of alert builder

                    Log.d("serviceIdListLast", serviceIdList.size.toString())
                }


                binding.saveBtn.setOnClickListener {
                    Log.d("serviceIdListBtn", serviceIdList.size.toString())
                    GlobalScope.launch (Dispatchers.Main) {
                        context?.let {
                            val prov = HealthCareDatabase(it).providerDao().getProviderId(provider.providerName)
                            for (id in serviceIdList){
                                Log.d("providerId", prov.toString())
                                val providerServices = ProviderServices(prov, id)
                                HealthCareDatabase(it).providerDao().insertProviderService(providerServices)
                            }
                            it.alert("Added successfully",
                                { dialog, which ->
                                    dialog.dismiss()
                                }
                                )
                        }
                    }
                }
            }
        }


    }

}