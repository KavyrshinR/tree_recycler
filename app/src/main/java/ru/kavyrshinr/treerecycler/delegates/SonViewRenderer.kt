package ru.kavyrshinr.treerecycler.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.son_list_item.view.*
import ru.kavyrshinr.treerecycler.R
import ru.kavyrshinr.treerecycler.itemmodels.Son
import ru.kavyrshinr.treerecycler.viewrenderer.VR
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeViewRenderer

class SonViewRenderer : CompositeViewRenderer<Son, SonViewHolder>() {

    override var expandListener: (Int) -> Unit = {}

    override fun bindView(model: Son, viewHolder: SonViewHolder) {
        viewHolder.itemView.textViewName.text = model.name
    }

    override fun createViewHolder(parent: ViewGroup): SonViewHolder {
        return SonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.son_list_item, parent, false)
        )
    }

    override fun getType(): Int {
        return VR.SON
    }
}

class SonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}