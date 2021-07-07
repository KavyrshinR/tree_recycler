package ru.kavyrshinr.treerecycler.viewrenderer.tree

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeItemModel
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeViewRenderer

class TreeRendererRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewRenderers: SparseArray<CompositeViewRenderer<CompositeItemModel, RecyclerView.ViewHolder>> = SparseArray()
    private val items: MutableList<TreeNode> = mutableListOf()

    var globalExpandListener: ((position: Int, isExpand: Boolean) -> Unit)? = null

    fun expandItem(from: Int) {
        items[from].isExpanded = true
        items.addAll(from + 1, items[from].childs)

        notifyItemRangeInserted(from + 1, items[from].childs.size)
        notifyItemChanged(from)

        globalExpandListener?.invoke(from, true)
    }

    fun collapseItem(from: Int) {
        val count = items[from].collapseExpandedItems()

        val iterator = items.listIterator(from + 1)
        var c = count
        while (iterator.hasNext() && c > 0) {
            iterator.next()
            iterator.remove()
            c--
        }

        notifyItemRangeRemoved(from + 1, count)
        notifyItemChanged(from)

        globalExpandListener?.invoke(from, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewRenderer = viewRenderers.get(viewType)
        if (viewRenderer != null) {
            return viewRenderer.createViewHolder(parent)
        }
        throw IllegalStateException("ViewRenderer doesn't register")
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val viewRenderer = viewRenderers[viewType]
        if (viewRenderer != null) {
            viewRenderer.bindView(items[position].data, viewHolder)
        } else {
            throw IllegalStateException("ViewRenderer doesn't register")
        }
    }

    fun registerRenderer(renderer: CompositeViewRenderer<out CompositeItemModel, out RecyclerView.ViewHolder>) {
        val type = renderer.getType()

        if (viewRenderers.get(type) == null) {
            viewRenderers.put(type, renderer as CompositeViewRenderer<CompositeItemModel, RecyclerView.ViewHolder>)
        }

        renderer.expandListener = { pos: Int ->
            if (getItemByPosition(pos).isExpanded) collapseItem(pos) else expandItem(pos) }
    }

    fun unregisterRenderer(renderer: CompositeViewRenderer<CompositeItemModel, RecyclerView.ViewHolder>) {
        viewRenderers.remove(renderer.getType())
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItemByPosition(position: Int): TreeNode {
        return items[position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (0 <= position && position < items.size) {
            items[position].data.getViewType()
        } else {
            -1
        }
    }

    fun getPositionByViewTypeAndId(viewType: Int, itemId: Long): Int {
        return items.indexOfFirst {
            it.data.getViewType() == viewType
                    && it.data.getItemModelId() == itemId
        }
    }

    fun addItems(nodes: List<TreeNode>) {
        val expandFrom = items.size
        items.addAll(nodes)

        val iterator = items.listIterator(expandFrom)

        while (iterator.hasNext()) {
            val node = iterator.next()

            if (node.isExpanded) {
                addAllExpandedChilds(iterator, node)
            }
        }
    }

    fun setItems(nodes: MutableList<TreeNode>, treeNodeDiffCallback: TreeNodeDiffCallback) {
        val iterator = nodes.listIterator()

        val expandableStatuses: MutableMap<Int, MutableMap<Long, Boolean>> = mutableMapOf()

        for (item in items) {
            if (item.isExpanded.not()) continue

            if (expandableStatuses.containsKey(item.data.getViewType()).not()) {
                expandableStatuses[item.data.getViewType()] = mutableMapOf()
            }
            expandableStatuses[item.data.getViewType()]?.set(item.data.getItemModelId(), true)
        }

        while (iterator.hasNext()) {
            val node = iterator.next()
            val isPreviousExpanded
                    = expandableStatuses[node.data.getViewType()]?.get(node.data.getItemModelId()) == true
            if (node.isExpanded || isPreviousExpanded) {
                node.isExpanded = true
                addAllExpandedChilds(iterator, node)
            }
        }

        treeNodeDiffCallback.setItems(items, nodes)

        val diffResult = DiffUtil.calculateDiff(treeNodeDiffCallback, true)

        items.clear()
        items.addAll(nodes)

        diffResult.dispatchUpdatesTo(this)
    }

    fun getItems(): List<TreeNode> = items


    private fun addAllExpandedChilds(iterator: MutableListIterator<TreeNode>, node: TreeNode) {
        for (child in node.childs) {
            iterator.add(child)
            if (child.isExpanded) {
                addAllExpandedChilds(iterator, child)
            }
        }
    }

    fun clearItems() {
        this.items.clear()
    }
}