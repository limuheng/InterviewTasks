package com.muheng.media17task.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, avatarUrl: String) {
    Glide.with(view.context)
        .load(avatarUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view);
}
