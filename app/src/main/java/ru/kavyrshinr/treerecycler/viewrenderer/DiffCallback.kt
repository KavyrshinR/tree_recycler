package ru.kavyrshinr.treerecycler.viewrenderer

import androidx.recyclerview.widget.DiffUtil

class DiffCallback : DiffUtil.Callback() {

    var oldList: MutableList<ItemModel> = mutableListOf()
    var newList: MutableList<ItemModel> = mutableListOf()

    fun setItems(oldList: List<ItemModel>, newList: List<ItemModel>) {
        this.oldList.clear()
        this.oldList.addAll(oldList)

        this.newList.clear()
        this.newList.addAll(newList)
    }

    override fun getOldListSize(): Int {
        return this.oldList.size
    }

    override fun getNewListSize(): Int {
        return this.newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemModel = oldList[oldItemPosition]
        val newItemModel = newList[newItemPosition]

        return oldItemModel.getViewType() == newItemModel.getViewType() &&
                oldItemModel.getItemModelId() == newItemModel.getItemModelId()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemModel = oldList[oldItemPosition]
        val newItemModel = newList[newItemPosition]

        return oldItemModel == newItemModel
    }
}