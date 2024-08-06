package com.example.projectandroidfundamental

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide


class adapterFollow(val listFollower:List<ResponseFollow?>?): RecyclerView.Adapter<adapterFollow.ListViewHolder>() {

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
        val username: TextView = itemView.findViewById(R.id.username)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterFollow.ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.listcard,parent,false)
        return adapterFollow.ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterFollow.ListViewHolder, position: Int) {
        holder.username.text = listFollower?.get(position)?.login
        Glide.with(holder.avatar)
            .load(listFollower?.get(position)?.avatarUrl)
            .error(R.drawable.ic_launcher_background)
            .into(holder.avatar)





    }

    override fun getItemCount(): Int {
        if (listFollower!=null){
            return listFollower.size
        }
        else{
            return 0
        }
    }




}
