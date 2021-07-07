package ru.kavyrshinr.treerecycler.viewrenderer.composite

import androidx.recyclerview.widget.RecyclerView
import ru.kavyrshinr.treerecycler.viewrenderer.ItemModel
import ru.kavyrshinr.treerecycler.viewrenderer.ViewRenderer

abstract class CompositeViewRenderer<M : ItemModel, VH : RecyclerView.ViewHolder>
    : ViewRenderer<M, VH>() {

    abstract var expandListener: (Int) -> Unit
}