package ru.kavyrshinr.treerecycler.viewrenderer

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class RendererRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewRenderers: SparseArray<ViewRenderer<ItemModel, RecyclerView.ViewHolder>> = SparseArray()
    private val items: MutableList<ItemModel> = mutableListOf()

    private val universalDiffCallback = DiffCallback()

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
            viewRenderer.bindView(items[position], viewHolder)
        } else {
            throw IllegalStateException("ViewRenderer doesn't register")
        }
    }

    fun registerRenderer(renderer: ViewRenderer<out ItemModel, out RecyclerView.ViewHolder>) {
        val type = renderer.getType()

        if (viewRenderers.get(type) == null) {
            viewRenderers.put(type, renderer as ViewRenderer<ItemModel, RecyclerView.ViewHolder>)
        }
    }

    fun unregisterRenderer(renderer: ViewRenderer<ItemModel, RecyclerView.ViewHolder>) {
        viewRenderers.remove(renderer.getType())
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItemByPosition(position: Int): ItemModel {
        return items[position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (0 <= position && position < items.size) {
            items[position].getViewType()
        } else {
            -1
        }
    }

    fun addItems(items: List<ItemModel>) {
        this.items.addAll(items)
    }

    fun setItems(newList: List<ItemModel>) {
        this.setItems(newList, universalDiffCallback)
    }

    fun setItems(newList: List<ItemModel>, diffCallback: DiffCallback) {
        diffCallback.setItems(items, newList)

        val diffResult = DiffUtil.calculateDiff(diffCallback, true)

        items.clear()
        items.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

    fun clearItems() {
        this.items.clear()
    }
}