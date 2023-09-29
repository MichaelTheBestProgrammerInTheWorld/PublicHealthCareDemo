package com.michaelmagdy.publicHealthCareDemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.fragment.ListofNoteFragment
import com.michaelmagdy.publicHealthCareDemo.fragment.ListofNoteFragmentDirections
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.databinding.TasklistitemBinding

class TaskAdapter(
    private val listofNoteFragment: Context,
    private val getallItemlist: List<UserEntity>,
    private val listofNoteFragment1: ListofNoteFragment
) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: TasklistitemBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            TasklistitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return getallItemlist.size
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titel.setText(getallItemlist.get(position).username)
        holder.binding.descrepthion.setText(getallItemlist.get(position).password)
        holder.binding.delet.setOnClickListener {
            listofNoteFragment1.deletitem(getallItemlist.get(position))
            notifyItemRemoved(position)
            notifyDataSetChanged()

        }

        holder.binding.root.setOnClickListener {

            val action = ListofNoteFragmentDirections.actionMobileNavigationToAddNoteFragment().setUserEntity(getallItemlist[position])


            findNavController(it).navigate(action)
        }
    }
 public   interface Deletetask{
        fun deletitem(userEntity: UserEntity)
    }
}