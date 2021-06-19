package com.example.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.R
import com.example.consumerapp.databinding.ItemListUserBinding
import com.example.consumerapp.model.entity.User
import com.example.consumerapp.utils.DataCallback


class ListUserAdapter (val callback : DataCallback) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private var listUser = ArrayList<User> ()

    fun setUser(user: List<User>){
        listUser.clear()
        this.listUser.addAll(user)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemListUserBinding = ItemListUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(itemListUserBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listUser.size

    inner class ListViewHolder (private val binding: ItemListUserBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user : User){
            with(binding){
                tvName.text = user.name
                tvGithub.text = user.github
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_image_broken))
                    .into(imgPoster)

                imgPoster.setOnClickListener { callback.setData(user) }
            }
        }

    }

}