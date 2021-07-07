package ru.kavyrshinr.treerecycler.itemmodels

import ru.kavyrshinr.treerecycler.viewrenderer.VR
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeItemModel

class Dad(val name: String, val sons: List<Son>) : CompositeItemModel {

    override fun getChildItems(): List<CompositeItemModel>? {
        return sons
    }

    override var isExpanded: Boolean = false

    override fun getViewType(): Int {
        return VR.DAD
    }

    override fun getItemModelId(): Long {
        return name.hashCode().toLong()
    }
}