package com.muheng.m800task

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.muheng.m800task.retrofit.Item
import com.muheng.m800task.rxjava.Event
import com.muheng.rxjava.RxEventBus

/**
 * Created by Muheng Li on 2018/8/15.
 */

class ItemListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val root = inflater.inflate(R.layout.layout_item, parent, false)

        return ViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = data[position]
        (holder as ViewHolder).bindView(item)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var container: ViewGroup? = null
        private var image: ImageView? = null
        private var text: TextView? = null

        init {
            container = itemView.findViewById(R.id.container)
            image = itemView.findViewById(R.id.image)
            text = itemView.findViewById(R.id.text)
        }

        fun bindView(item: Item) {
            container?.setOnClickListener {
                RxEventBus.get()?.send(Event.ItemClicked(item))
            }

            text?.text = item.trackName

            try {
                val photoUrl: String = item.artworkUrl100
                Glide.with(container?.context).load(photoUrl).centerCrop()
                        .placeholder(R.drawable.image_coming_soon).into(image)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}