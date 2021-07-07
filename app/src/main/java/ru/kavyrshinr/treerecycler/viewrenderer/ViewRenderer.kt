package ru.kavyrshinr.treerecycler.viewrenderer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ViewRenderer <M : ItemModel, VH : RecyclerView.ViewHolder>() {

    abstract fun bindView(model: M, viewHolder: VH)
    abstract fun createViewHolder(parent: ViewGroup): VH
    abstract fun getType(): Int
}