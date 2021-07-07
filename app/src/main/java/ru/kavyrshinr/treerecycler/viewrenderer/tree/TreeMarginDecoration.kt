package ru.kavyrshinr.treerecycler.viewrenderer.tree

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TreeMarginDecoration(private val levelMargin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position != RecyclerView.NO_POSITION
            && parent.adapter is TreeRendererRecyclerViewAdapter) {
            val adapter = parent.adapter as TreeRendererRecyclerViewAdapter
            outRect.left = levelMargin * adapter.getItemByPosition(position).getLevelNest()
        }
    }
}