package com.hijazi.challenge.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hijazi.challenge.R
import com.hijazi.challenge.data.remote.model.mostStarredGithubRepo.GithubRepoResponse
import com.hijazi.challenge.data.remote.model.mostStarredGithubRepo.Item

class GithubRepoAdapter(activity: Activity) : RecyclerView.Adapter<GithubRepoAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(activity)
    private var mClickListener: ItemClickListener? = null

    private var repoResponse: GithubRepoResponse? = null

    init {
        this.repoResponse = GithubRepoResponse()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val view: View = mInflater.inflate(R.layout.item_repo, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(repoResponse!!.items!![position])
        holder.repositoryName.text = holder.item!!.name
        holder.repositoryDesc.text = holder.item!!.description
        holder.ownerUserName.text = holder.item!!.owner!!.login
        holder.starCount.text = holder.item!!.stargazersCount.toString()
        // loading owner avatar
        Glide.with(mInflater.context)
            .load(holder.item!!.owner!!.avatarUrl)
            .placeholder(R.drawable.ic_avatar)
            .fitCenter()
            .thumbnail(0.5f)
            .into(holder.ownerAvatar)
    }

    override fun getItemCount(): Int {
        return repoResponse!!.items!!.size
    }

    fun setRepoResponse(repoResponse: GithubRepoResponse) {
        this.repoResponse = repoResponse
        notifyDataSetChanged()
    }

    fun getItem(index: Int): Item? {
        return repoResponse!!.items!![index]
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        internal var item: Item? = null
            private set
        val repositoryName: TextView
        val repositoryDesc: TextView
        val ownerUserName: TextView
        val starCount: TextView
        val ownerAvatar: ImageView

        init {
            itemView.setOnClickListener(this)

            repositoryName = itemView.findViewById(R.id.repository_name)
            repositoryDesc = itemView.findViewById(R.id.repository_desc)
            ownerUserName = itemView.findViewById(R.id.owner_username)
            ownerAvatar = itemView.findViewById(R.id.owner_avatar)
            starCount = itemView.findViewById(R.id.star_count)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        internal fun bindItem(model: Item) {
            this.item = model
        }
    }
}
