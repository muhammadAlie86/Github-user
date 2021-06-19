package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ItemListUserBinding
import com.example.githubuser.model.User
import com.example.githubuser.utils.DataCallback

class ListUserAdapter (val callback : DataCallback) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private val listUser = ArrayList<User>()

    fun setUser(user: List<User>){
        this.listUser.addAll(user)
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

        fun bind(user: User){
            with(binding){
                tvName.text = user.name
                tvLocation.text = user.location
                tvCompany.text = user.company
                imgPoster.setImageResource(user.image)

                imgPoster.setOnClickListener { callback.setData(user) }
            }
        }

    }

}