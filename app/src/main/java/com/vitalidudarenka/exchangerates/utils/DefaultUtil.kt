package com.vitalidudarenka.exchangerates.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class DefaultUtil {

    fun scrollRecyclerAtBottomSheet(
        rvList: RecyclerView?,
        dialog: BottomSheetDialog?,
        selectedPosition: Int
    ) {
        rvList?.post {
            val layoutManager: LinearLayoutManager =
                rvList.layoutManager as LinearLayoutManager
            val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
            if (selectedPosition < lastPosition)
                return@post
            rvList.scrollToPosition(selectedPosition)
            dialog?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

}