package com.iflytek.cyber.iot.show.core.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.drakeet.multitype.ItemViewBinder
import com.iflytek.cyber.iot.show.core.R
import com.iflytek.cyber.iot.show.core.model.DeskRecommend
import com.iflytek.cyber.iot.show.core.model.DeskRecommendItem
import com.iflytek.cyber.iot.show.core.utils.OnItemClickListener
import com.iflytek.cyber.iot.show.core.utils.OnMultiTypeItemClickListener
import com.iflytek.cyber.iot.show.core.utils.RoundedCornersTransformation
import com.iflytek.cyber.iot.show.core.utils.clickWithTrigger

class RecommendEightItemViewHolder2 : ItemViewBinder<DeskRecommend, RecyclerView.ViewHolder>() {

    var onItemClickListener: OnMultiTypeItemClickListener? = null
    var onMoreClickListener: OnItemClickListener? = null
    var onCardRefreshListener: OnItemClickListener? = null


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DeskRecommend) {
        if (holder is ItemViewHolder) {
            holder.setItem(item)
        }
    }

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder {
        val holder =
            ItemViewHolder(inflater.inflate(R.layout.item_recommend_eight_item_2, parent, false))
        holder.childList.mapIndexed { index, itemGroup ->
            itemGroup.clickableView?.setOnClickListener {
                onItemClickListener?.onItemClick(
                    parent,
                    holder.itemView,
                    holder.adapterPosition,
                    index
                )
            }
        }
        holder.tvMore?.setOnClickListener {
            onMoreClickListener?.onItemClick(parent, it, holder.adapterPosition)
        }
        holder.refresh?.clickWithTrigger {
            onCardRefreshListener?.onItemClick(parent, it, holder.adapterPosition)
        }
        return holder
    }


    private class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBackground: ImageView? = itemView.findViewById(R.id.background)
        val container: View? = itemView.findViewById(R.id.container)
        var tvTitle: TextView? = itemView.findViewById(R.id.title)
        val refresh: ImageView? = itemView.findViewById(R.id.refresh)
        val more: View? = itemView.findViewById(R.id.more)
        val tvMore: TextView? = itemView.findViewById(R.id.tv_more)
        val childList = arrayOf(
            ItemGroup(itemView, R.id.iv_image_0, R.id.tv_title_0, R.id.item_0),
            ItemGroup(itemView, R.id.iv_image_1, R.id.tv_title_1, R.id.item_1),
            ItemGroup(itemView, R.id.iv_image_2, R.id.tv_title_2, R.id.item_2),
            ItemGroup(itemView, R.id.iv_image_3, R.id.tv_title_3, R.id.item_3),
            ItemGroup(itemView, R.id.iv_image_4, R.id.tv_title_4, R.id.item_4),
            ItemGroup(itemView, R.id.iv_image_5, R.id.tv_title_5, R.id.item_5),
            ItemGroup(itemView, R.id.iv_image_6, R.id.tv_title_6, R.id.item_6),
            ItemGroup(itemView, R.id.iv_image_7, R.id.tv_title_7, R.id.item_7)
        )

        var item: DeskRecommend? = null
            private set

        fun setItem(item: DeskRecommend) {
            item.background?.let { background ->
                if (background.isEmpty()) {
                    container?.background?.setColorFilter(
                        itemView.resources.getColor(R.color.campus_blue),
                        PorterDuff.Mode.SRC_IN
                    ) ?: run {
                        val drawable =
                            itemView.resources.getDrawable(R.drawable.bg_white_round_16dp)
                        drawable.setColorFilter(
                            itemView.resources.getColor(R.color.campus_blue),
                            PorterDuff.Mode.SRC_IN
                        )
                        container?.background = drawable
                    }
                } else {
                    var isColor = false
                    try {
                        val color = Color.parseColor(background)
                        container?.background?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                            ?: run {
                                val drawable =
                                    itemView.resources.getDrawable(R.drawable.bg_white_round_16dp)
                                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
                                container?.background = drawable
                            }
                        isColor = true
                    } catch (t: Throwable) {
                        t.printStackTrace()
                    }
                    if (!isColor) {
                        ivBackground?.let { imageView ->
                            Glide.with(ivBackground)
                                .load(background)
                                .into(imageView)
                        }
                    } else {

                    }
                }
            }
            tvTitle?.text = item.title
            try {
                val color = Color.parseColor(item.titleColor)
                tvTitle?.setTextColor(color)

                refresh?.drawable?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            } catch (t: Throwable) {

            }
            item.more?.let { deskRecommendMore ->
                tvMore?.text = deskRecommendMore.text
                more?.isVisible = !deskRecommendMore.text.isNullOrEmpty()

                try {
                    val color = Color.parseColor(deskRecommendMore.textColor)
                    tvMore?.setTextColor(color)
                    refresh?.setColorFilter(color)
                } catch (t: Throwable) {

                }
            } ?: run {
                more?.isVisible = false
            }
            childList.mapIndexed { index, itemGroup ->
                if (!item.items.isNullOrEmpty() && index < item.items.size) {
                    itemGroup.isVisible = true

                    itemGroup.setItem(item.items[index])
                } else {
                    itemGroup.isVisible = false
                }
            }
        }
    }

    private class ItemGroup(itemView: View, imageId: Int, titleId: Int, clickableViewId: Int) {
        val tvTitle: TextView? = itemView.findViewById(titleId)
        val ivImage: ImageView? = itemView.findViewById(imageId)
        val clickableView: View? = itemView.findViewById(clickableViewId)

        var isVisible: Boolean = true
            set(value) {
                field = value

                clickableView?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
            }

        fun setItem(item: DeskRecommendItem) {
            tvTitle?.text = item.title
            try {
                val color = Color.parseColor(item.titleColor)
                tvTitle?.setTextColor(color)
            } catch (t: Throwable) {

            }

            item.cover?.let { url ->
                try {
                    Uri.parse(url)?.let {
                        ivImage?.let { imageView ->
                            Glide.with(imageView)
                                .load(it)
                                .transition(
                                    DrawableTransitionOptions.with(
                                        DrawableCrossFadeFactory.Builder()
                                            .setCrossFadeEnabled(true).build()
                                    )
                                )
                                .placeholder(R.drawable.bg_white_round_6dp)
                                .transform(
                                    MultiTransformation(
                                        CenterCrop(),
                                        RoundedCornersTransformation(
                                            imageView.resources.getDimensionPixelSize(
                                                R.dimen.dp_6
                                            ), 0
                                        )
                                    )
                                )
                                .into(imageView)
                        }
                    }
                } catch (t: Throwable) {

                }
            }

        }
    }
}