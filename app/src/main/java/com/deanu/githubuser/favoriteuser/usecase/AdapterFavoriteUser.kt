package com.deanu.githubuser.favoriteuser.usecase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.databinding.ItemFavoriteUserBinding

class AdapterFavoriteUser(
    private val deleteListener: (userId: Int) -> Unit,
    private val clickListener: (username: String) -> Unit
) :
    ListAdapter<UserDetail, AdapterFavoriteUser.FavoriteUserViewHolder>(UserDiffItemCallback()) {

    class FavoriteUserViewHolder(private val binding: ItemFavoriteUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            favoriteUser: UserDetail, deleteListener: (userId: Int) -> Unit,
            clickListener: (username: String) -> Unit
        ) {
            binding.tvUsername.text = favoriteUser.username
            binding.tvGitUrl.text = favoriteUser.githubUrl
            binding.ivDelete.setOnClickListener { deleteListener(favoriteUser.id) }
            binding.cvUser.setOnClickListener { clickListener(favoriteUser.username) }
            Glide.with(binding.root)
                .load(favoriteUser.avatarUrl)
                .circleCrop()
                .into(binding.ivAvatar)
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): FavoriteUserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteUserBinding.inflate(layoutInflater, parent, false)
                return FavoriteUserViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        return FavoriteUserViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val favoriteUser = getItem(position)
        holder.bind(favoriteUser, deleteListener, clickListener)
    }
}

class UserDiffItemCallback : DiffUtil.ItemCallback<UserDetail>() {
    override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
        return oldItem == newItem
    }
}