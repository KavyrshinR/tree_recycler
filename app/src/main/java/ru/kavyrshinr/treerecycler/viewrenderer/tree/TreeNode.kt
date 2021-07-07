package ru.kavyrshinr.treerecycler.viewrenderer.tree

import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeItemModel

class TreeNode(val parent: TreeNode?, val data: CompositeItemModel) {

    var isExpanded: Boolean
        set(value) { data.isExpanded = value }
        get() { return data.isExpanded }

    val childs: MutableList<TreeNode> = mutableListOf()

    init {
        val items = data.getChildItems()
        if (items != null) {
            for (item: CompositeItemModel in items) {
                childs.add(TreeNode(this, item))
            }
        }
    }

    fun collapseExpandedItems(): Int {
        var result = 0
        if (data.isExpanded) {
            result = childs.size

            for (item in childs) {
                if (item.isExpanded) {
                    result += item.collapseExpandedItems()
                    item.isExpanded = false
                }
            }
        }
        isExpanded = false
        return result
    }

    fun getLevelNest(): Int {
        if (parent != null) {
            return 1 + parent.getLevelNest()
        } else {
            return 0
        }
    }
}