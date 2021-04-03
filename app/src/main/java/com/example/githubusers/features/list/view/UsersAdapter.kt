package com.example.githubusers.features.list.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubusers.R
import com.example.githubusers.data.db.entities.User

class UsersAdapter(private val users : ArrayList<User>, private val context: Context, private val interaction: Interaction) : RecyclerView.Adapter<UsersAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUsername.text = users[position].login
        Glide.with(context).load(users[position].avatar_url).into(holder.imgUser)
        holder.itemView.setOnClickListener { interaction.onItemClick(users[position].id) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        var imgUser : ImageView = itemView.findViewById(R.id.img_user)
    }

    interface Interaction{
        fun onItemClick(id: Int)
    }
}