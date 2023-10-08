package com.michaelmagdy.publicHealthCareDemo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.databinding.TasklistitemBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.HealthCareDatabase
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.booking.BookingEntity
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileAdapter (
    private val bookingsList: List<BookingEntity>
) : RecyclerView.Adapter<ProfileAdapter.BookingViewHolder>()  {

    class BookingViewHolder(val binding: TasklistitemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {

        return BookingViewHolder(
            TasklistitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bookingsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.binding.delet.visibility = View.VISIBLE
        //holder.binding.descrepthion.setText(getallItemlist.get(position).password)
//        holder.binding.delet.setOnClickListener {
//            listofNoteFragment1.deletitem(getallItemlist.get(position))
//            notifyItemRemoved(position)
//            notifyDataSetChanged()
//
//        }
        GlobalScope.launch (Dispatchers.Main) {
            holder.itemView.context.let {
                holder.binding.titel.setText(
                    HealthCareDatabase(it).providerDao().getProviderById(bookingsList.get(position).providerId).providerName
                )
                holder.binding.descrepthion.setText(
                    "${HealthCareDatabase(it).serviceDao().getServiceNameById(bookingsList.get(position).serviceId)} at ${bookingsList.get(position).dateAndTime}"
                )
                holder.binding.delet.setOnClickListener {
                    GlobalScope.launch (Dispatchers.Main) {
                        holder.itemView.context.let {
                            HealthCareDatabase(it).bookingDao().delete(bookingsList.get(position))
                            it.toast("booking deleted successfully")
                        }
                    }
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
            }
        }


    }

}