package ru.kavyrshinr.treerecycler.itemmodels

import ru.kavyrshinr.treerecycler.viewrenderer.VR
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeItemModel

class Son(val name: String) : CompositeItemModel {

    override fun getChildItems(): List<CompositeItemModel>? {
        return null
    }

    override var isExpanded: Boolean = false

    override fun getViewType(): Int {
        return VR.SON
    }

    override fun getItemModelId(): Long {
        return name.hashCode().toLong()
    }
}