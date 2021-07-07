package ru.kavyrshinr.treerecycler.viewrenderer.tree

import androidx.recyclerview.widget.DiffUtil

class TreeNodeDiffCallback : DiffUtil.Callback() {

    var oldList: MutableList<TreeNode> = mutableListOf()
    var newList: MutableList<TreeNode> = mutableListOf()

    fun setItems(oldList: List<TreeNode>, newList: List<TreeNode>) {
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

        return oldItemModel.data.getViewType() == newItemModel.data.getViewType() &&
                oldItemModel.data.getItemModelId() == newItemModel.data.getItemModelId()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItemModel = oldList[oldItemPosition].data
        val newItemModel = newList[newItemPosition].data

        return oldItemModel == newItemModel
    }
}