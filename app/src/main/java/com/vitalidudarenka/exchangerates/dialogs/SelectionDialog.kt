package com.vitalidudarenka.exchangerates.dialogs

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vitalidudarenka.exchangerates.databinding.DialogSelectionBinding
import com.vitalidudarenka.exchangerates.utils.DefaultUtil

class SelectionDialog {

    private lateinit var context: Context

    fun create(
        context: Context,
        selectedPosition: Int,
        itemsArray: Array<String>,
        title: String,
        listener: (Int, String) -> Unit
    ) {
        this.context = context
        val dialog = BottomSheetDialog(context)
        dialog.setCancelable(true)
        val binding = DialogSelectionBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvTitle.text = title
        binding.itemsRv.layoutManager = LinearLayoutManager(context)
        val adapter = SelectionDialogAdapter(itemsArray, context) { position ->
            dialog.dismissWithAnimation = true
            listener.invoke(position, itemsArray[position])
            dialog.dismiss()
        }
        adapter.setSelectedPosition(selectedPosition)
        binding.itemsRv.adapter = adapter
        if (selectedPosition >= 0) {
            DefaultUtil().scrollRecyclerAtBottomSheet(binding.itemsRv, dialog, selectedPosition)
        }
        dialog.show()
    }

}