package ru.kavyrshinr.treerecycler.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dad_list_item.view.*
import ru.kavyrshinr.treerecycler.R
import ru.kavyrshinr.treerecycler.itemmodels.Item
import ru.kavyrshinr.treerecycler.viewrenderer.VR
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeViewRenderer

class ItemViewRenderer : CompositeViewRenderer<Item, ItemViewHolder>() {

    override var expandListener: (Int) -> Unit = {}

    override fun bindView(model: Item, viewHolder: ItemViewHolder) {
        viewHolder.itemView.textViewName.text = model.name
        viewHolder.itemView.checkboxDropdown.isChecked = model.isExpanded
    }

    override fun createViewHolder(parent: ViewGroup): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.dad_list_item, parent, false),
            expandListener
        )
    }

    override fun getType(): Int {
        return VR.ITEM
    }
}

class ItemViewHolder(itemView: View, expandListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.checkboxDropdown.setOnClickListener {
            expandListener(adapterPosition)
        }
    }
}