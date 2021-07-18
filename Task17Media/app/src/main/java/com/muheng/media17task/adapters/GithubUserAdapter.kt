package com.muheng.media17task.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muheng.media17task.data.GithubUser
import com.muheng.media17task.databinding.ListItemGithubUser2x1Binding
import com.muheng.media17task.databinding.ListItemGithubUser2x2Binding
import com.muheng.media17task.databinding.ListItemGithubUserBinding

enum class ViewType {
    ONE_BY_ONE,
    TWO_BY_ONE,
    TWO_BY_TWO
}

class GithubUserAdapter: PagedListAdapter<GithubUser, RecyclerView.ViewHolder>(GitHubUserDiffCallback()) {

    companion object {
        val Tag = GithubUserAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.ONE_BY_ONE.ordinal -> {
                ViewHolder(
                    ListItemGithubUserBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            ViewType.TWO_BY_ONE.ordinal -> {
                ViewHolder2x1(
                    ListItemGithubUser2x1Binding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                ViewHolder2x2(
                    ListItemGithubUser2x2Binding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val githubUser = getItem(position)
        when (getItemViewType(position)) {
            ViewType.ONE_BY_ONE.ordinal -> {
                (holder as ViewHolder).bind(githubUser)
            }
            ViewType.TWO_BY_ONE.ordinal -> {
                (holder as ViewHolder2x1).bind(githubUser)
            }
            else -> {
                (holder as ViewHolder2x2).bind(githubUser)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position % 10) {
            2, 3, 6, 7 -> ViewType.ONE_BY_ONE.ordinal
            0, 4, 8 -> ViewType.TWO_BY_ONE.ordinal
            else -> ViewType.TWO_BY_TWO.ordinal
        }
    }

    class ViewHolder(private val binding: ListItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubUser?) {
            binding.apply {
                if (item != null) {
                    githubUser = item
                    executePendingBindings()
                } else {
                    // TODO shows placeholder
                    Log.w(Tag, "ViewHolder.bind : item is null")
                }
            }
        }
    }

    class ViewHolder2x1(private val binding: ListItemGithubUser2x1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubUser?) {
            binding.apply {
                if (item != null) {
                    githubUser = item
                    executePendingBindings()
                } else {
                    // TODO shows placeholder
                    Log.w(Tag, "ViewHolder2x1.bind : item is null")
                }
            }
        }
    }

    class ViewHolder2x2(private val binding: ListItemGithubUser2x2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubUser?) {
            binding.apply {
                if (item != null) {
                    githubUser = item
                    executePendingBindings()
                } else {
                    // TODO shows placeholder
                    Log.w(Tag, "ViewHolder2x2.bind : item is null")
                }
            }
        }
    }
}

private class GitHubUserDiffCallback : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }
}