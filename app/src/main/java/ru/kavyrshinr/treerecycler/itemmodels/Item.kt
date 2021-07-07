package ru.kavyrshinr.treerecycler.itemmodels

import ru.kavyrshinr.treerecycler.viewrenderer.VR
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeItemModel

class Item(val name: String, val itemsList: List<Item>) : CompositeItemModel {

    override fun getChildItems(): List<CompositeItemModel>? {
        return itemsList
    }

    override var isExpanded: Boolean = false

    override fun getViewType(): Int {
        return VR.ITEM
    }

    override fun getItemModelId(): Long {
        return name.hashCode().toLong()
    }
}