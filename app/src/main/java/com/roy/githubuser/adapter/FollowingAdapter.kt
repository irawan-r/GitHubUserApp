package com.roy.githubuser.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.roy.githubuser.databinding.ItemRowUserBinding
import com.roy.githubuser.api.User

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private lateinit var onItemCallback: UserAdapter.OnItemCallback
    private val listFollowing = ArrayList<User>()

    class FollowingViewHolder(private val binding: ItemRowUserBinding) :
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

    @SuppressLint("NotifyDataSetChanged")
    fun setList(items: ArrayList<User>) {
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowingViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(listFollowing[position])
        holder.itemView.setOnClickListener {onItemCallback.onItemClicked(listFollowing[position])}
    }

    override fun getItemCount(): Int = listFollowing.size

    fun setItemClickCallback(onItemCallback: UserAdapter.OnItemCallback) {
        this.onItemCallback = onItemCallback
    }
}