package com.vega.android.ceiling.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vega.android.R
import com.vega.android.ceiling.CeilingAdapter
import com.vega.android.ceiling.CeilingItemDecoration
import com.vega.android.ceiling.DataItem
import kotlin.random.Random

class CeilingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ceiling)

        findViewById<RecyclerView>(R.id.ceiling_recycler_view).apply {
            addItemDecoration(CeilingItemDecoration())
            adapter = CeilingAdapter().also {
                it.notifyDataSetChanged(generationData())
            }
            layoutManager = LinearLayoutManager(this@CeilingActivity)
        }
    }

    private fun generationData(): List<DataItem> {
        return arrayListOf<DataItem>().apply {
            var groupIndex = -1
            var indexOfGroup = 0
            for (i in 0 .. 100) {
                createDataItem(groupIndex, position = indexOfGroup).also {
                    groupIndex = it.groupIndex
                    indexOfGroup = if (it.isGroupHeader) 1 else indexOfGroup + 1
                    add(it)
                }
            }
        }
    }

    private fun createDataItem(groupIndex: Int, position: Int): DataItem {
        val isGroup = Random.nextBits(20) % 10 == 0 || groupIndex == -1
        val index = if (isGroup) 0 else position
        val gIndex = if (isGroup) groupIndex + 1 else groupIndex
        return DataItem("第 $gIndex 组", "第 $gIndex 组 第 $index 项内容", isGroup, gIndex)
    }
}