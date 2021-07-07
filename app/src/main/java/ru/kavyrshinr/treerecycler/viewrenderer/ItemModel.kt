package ru.kavyrshinr.treerecycler.viewrenderer

interface ItemModel {
    fun getViewType(): Int
    fun getItemModelId(): Long
}