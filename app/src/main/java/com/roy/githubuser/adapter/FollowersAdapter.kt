package com.roy.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.roy.githubuser.databinding.ItemRowUserBinding
import com.roy.githubuser.dataclass.User

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    private lateinit var onItemCallback: UserAdapter.OnItemCallback
    private val listFollowers = ArrayList<User>()

    class FollowersViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            Glide.with(binding.root)
                .load(user.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(binding.imgAvatar)
            binding.tvUsername.text = user.login
        }
    }

    fun setList(items: ArrayList<User>) {
        listFollowers.clear()
        listFollowers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(listFollowers[position])
        holder.itemView.setOnClickListener {onItemCallback.onItemClicked(listFollowers[position])}
    }

    override fun getItemCount(): Int = listFollowers.size

    fun setOnItemClickCallback(onItemCLickCallback: UserAdapter.OnItemCallback) {
        this.onItemCallback = onItemCLickCallback
    }
}