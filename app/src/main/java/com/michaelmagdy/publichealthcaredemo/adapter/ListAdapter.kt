package com.michaelmagdy.publicHealthCareDemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.michaelmagdy.publicHealthCareDemo.fragment.HomeFragment
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.user.UserEntity
import com.michaelmagdy.publicHealthCareDemo.databinding.TasklistitemBinding
import com.michaelmagdy.publicHealthCareDemo.dbDirectery.provider.Category

class ListAdapter(
    private val getallItemlist: List<String>
) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

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

    private lateinit var onItemClickListener: ListAdapter.OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titel.setText(getallItemlist.get(position))
        //holder.binding.descrepthion.setText(getallItemlist.get(position).password)
//        holder.binding.delet.setOnClickListener {
//            listofNoteFragment1.deletitem(getallItemlist.get(position))
//            notifyItemRemoved(position)
//            notifyDataSetChanged()
//
//        }

        holder.binding.root.setOnClickListener {

            onItemClickListener.onItemClick(position)
//            val action = ListofNoteFragmentDirections.actionMobileNavigationToAddNoteFragment().setUserEntity(getallItemlist[position])
//            findNavController(it).navigate(action)
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    interface DeleteItem{
        fun deletitem(userEntity: UserEntity)
    }
}