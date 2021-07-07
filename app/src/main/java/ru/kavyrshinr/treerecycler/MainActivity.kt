package ru.kavyrshinr.treerecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.kavyrshinr.treerecycler.delegates.DadViewRenderer
import ru.kavyrshinr.treerecycler.delegates.ItemViewRenderer
import ru.kavyrshinr.treerecycler.delegates.SonViewRenderer
import ru.kavyrshinr.treerecycler.itemmodels.Dad
import ru.kavyrshinr.treerecycler.itemmodels.Item
import ru.kavyrshinr.treerecycler.itemmodels.Son
import ru.kavyrshinr.treerecycler.viewrenderer.composite.CompositeItemModel
import ru.kavyrshinr.treerecycler.viewrenderer.tree.TreeMarginDecoration
import ru.kavyrshinr.treerecycler.viewrenderer.tree.TreeNode
import ru.kavyrshinr.treerecycler.viewrenderer.tree.TreeRendererRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private val adapter = TreeRendererRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter.registerRenderer(DadViewRenderer())
        adapter.registerRenderer(SonViewRenderer())
        adapter.registerRenderer(ItemViewRenderer())

        val data: MutableList<TreeNode> = mutableListOf()
        for (item in getDads()) {
            data.add(TreeNode(null, item))
        }
        adapter.addItems(data)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            TreeMarginDecoration(
                LayoutUtils.getPxFromDp(this, 16).toInt()
            )
        )
    }

    fun getDads(): List<CompositeItemModel> {
        val list: MutableList<CompositeItemModel> = mutableListOf()

        list.add(
            Dad(
                "Zero Dad", listOf(
                    Son("0Zero Son"),
                    Son("1Zero Son"),
                    Son("2Zero Son"),
                    Son("3Zero Son"),
                    Son("4Zero Son"),
                    Son("5Zero Son"),
                    Son("6Zero Son"),
                    Son("7Zero Son"),
                    Son("8Zero Son"),
                    Son("9Zero Son"),
                    Son("10Zero Son"),
                    Son("11Zero Son"),
                    Son("12Zero Son"),
                    Son("13Zero Son"),
                    Son("14Zero Son"),
                    Son("15Zero Son"),
                    Son("16Zero Son")
                )
            )
        )
        list.add(
            Dad(
                "First Dad",
                listOf(
                    Son("1First Son"),
                    Son("1Second Son"),
                    Son("1Third Son")
                )
            )
        )
        list.add(
            Dad(
                "Second Dad",
                listOf(
                    Son("2First Son"),
                    Son("2Second Son"),
                    Son("2Third Son")
                )
            )
        )
        list.add(
            Dad(
                "Third Dad",
                listOf(
                    Son("3First Son"),
                    Son("3Second Son"),
                    Son("3Third Son")
                )
            )
        )
        list.add(
            Dad(
                "Fourth Dad",
                listOf(Son("4First Son"))
            )
        )
        list.add(Dad("Fifth Dad", listOf()))

        list.add(
            Item(
                "Item1", listOf(
                    Item(
                        "sub1Item1", listOf(
                            Item("sub1sub1Item1", listOf()),
                            Item("sub2sub1Item1", listOf())
                        )
                    ),
                    Item(
                        "sub2Item1", listOf(
                            Item("sub1sub2Item1", listOf())
                        )
                    ),
                    Item("sub3Item1", listOf())
                )
            )
        )

        list.add(
            Item(
                "Item2", listOf(
                    Item("sub1Item2", listOf()),
                    Item("sub2Item2", listOf())
                )
            )
        )

        return list
    }
}
