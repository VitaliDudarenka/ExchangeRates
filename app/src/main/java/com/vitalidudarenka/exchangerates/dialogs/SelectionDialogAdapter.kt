package com.vitalidudarenka.exchangerates.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vitalidudarenka.exchangerates.R
import java.text.FieldPosition

class SelectionDialogAdapter (
    private val items: Array<String>,
    val context: Context,
    val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<SelectionDialogAdapter.ViewHolder>() {

    private var selectedPosition = 0

    fun setSelectedPosition(selectedPosition: Int) {
        this.selectedPosition = selectedPosition
        for (item in items.indices){
            notifyItemChanged(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.bottom_dialog_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: String = items[position]
        holder.periodTitle.text = item
        holder.periodTitle.setTextColor(context.getColor(if (position == selectedPosition) R.color.purple_200 else R.color.black))
        holder.periodLL.setOnClickListener {
            listener(position)
        }

    }

    override fun getItemCount() = items.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val periodTitle: TextView = v.findViewById(R.id.periodTitle)
        val periodLL: LinearLayout = v.findViewById(R.id.periodLL)

    }
}