package com.desertfox.couplelink.banned

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.desertfox.couplelink.BaseRecyclerViewHolder
import com.desertfox.couplelink.model.responses.BannedTermModel

class BannedAdapter : RecyclerView.Adapter<BaseRecyclerViewHolder>() {

    enum class ViewType {
        MY, PARTNER, ADD, HIDE
    }

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ViewType.MY.ordinal, ViewType.PARTNER.ordinal -> BannedViewHolder(parent)
        ViewType.ADD.ordinal -> BannedAddViewHolder(parent)
        ViewType.HIDE.ordinal -> BannedHideViewHolder(parent)
        else -> throw IllegalArgumentException("Unknown viewType=$viewType")
    }

    override fun onViewRecycled(holder: BaseRecyclerViewHolder) = holder.recycler()
    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) = holder.bindData(items[position])
    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = items[position].viewType.ordinal

    fun appendData(model: BannedTermModel, isMy: Boolean) {
        val addIndex = items.size - 1
        items.add(addIndex, Item(if (isMy) ViewType.MY else ViewType.PARTNER, model))
        notifyItemInserted(addIndex)
    }

    fun setData(models: List<BannedTermModel>, isMy: Boolean) {
        items.addAll(models.map {
            if (isMy) Item(ViewType.MY, it)
            else {
                if (it.count >= 10) Item(ViewType.HIDE, it)
                else Item(ViewType.PARTNER, it)
            }
        })
        items.add(Item(ViewType.ADD, null))
        notifyDataSetChanged()
    }

    fun deleteData(id: Int) {
        val removeIndex = items.indexOf(items.find { it.model?.id == id })
        items.removeAt(removeIndex)
        notifyItemRemoved(removeIndex)
    }

    data class Item(
            val viewType: ViewType,
            val model: BannedTermModel?
    )
}