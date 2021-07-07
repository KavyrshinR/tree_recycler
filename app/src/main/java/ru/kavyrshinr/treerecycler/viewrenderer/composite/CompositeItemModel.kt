package ru.kavyrshinr.treerecycler.viewrenderer.composite

import ru.kavyrshinr.treerecycler.viewrenderer.ItemModel

interface CompositeItemModel : ItemModel {
    fun getChildItems(): List<CompositeItemModel>?

    var isExpanded: Boolean
}