package ru.kavyrshinr.treerecycler

import android.content.Context

object LayoutUtils {

    fun getDpFromPx(context: Context, px: Int): Float {
        return px / context.resources.displayMetrics.density
    }

    fun getPxFromDp(context: Context, dp: Int): Float {
        return dp * context.resources.displayMetrics.density
    }
}