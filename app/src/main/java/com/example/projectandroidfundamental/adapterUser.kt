package com.example.projectandroidfundamental

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class adapterUser(val listUser:List<ItemsItem?>?):RecyclerView.Adapter<adapterUser.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val avatar:ImageView = itemView.findViewById(R.id.avatar)
        val username:TextView = itemView.findViewById(R.id.username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.listcard,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (listUser!=null){
            return listUser.size
        }
        else{
            return 0
        }

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.username.text = listUser?.get(position)?.login
        Glide.with(holder.avatar)
            .load(listUser?.get(position)?.avatarUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.avatar)

        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(listUser?.get(holder.adapterPosition))}
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem?)
    }


}