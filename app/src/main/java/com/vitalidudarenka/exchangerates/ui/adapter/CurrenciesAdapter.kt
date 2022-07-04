package com.vitalidudarenka.exchangerates.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vitalidudarenka.domain.entities.Rate
import com.vitalidudarenka.domain.entities.Symbol
import com.vitalidudarenka.exchangerates.R
import com.vitalidudarenka.exchangerates.databinding.ItemResultBinding

class CurrenciesAdapter :
    RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    private val items: MutableList<Rate> = mutableListOf()
    private lateinit var listener: (Rate) -> Unit
    private val favorites = mutableListOf<Symbol>()

    fun initListener(listener: (Rate) -> Unit) {
        this.listener = listener
    }

    fun initFavorites(items: List<Symbol>) {
        favorites.clear()
        favorites.addAll(items)
        notifyDataSetChanged()
    }

    fun setItems(items: List<Rate>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val itemResultBinding = ItemResultBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemResultBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Rate = items[position]
        val context: Context = holder.itemView.context

        holder.tvValue.text = item.value.toString()
        holder.tvCode.text = item.name
        val isFavorite = favorites.map { it.code }.contains(item.name)
        holder.ivFavorite.setImageResource(if (isFavorite) R.drawable.ic_favorite_enabled else R.drawable.ic_favorite_disabled)

        holder.ivFavorite.setOnClickListener {
            listener(item)
        }

    }

    override fun getItemCount() = items.size

    class ViewHolder(itemResultBinding: ItemResultBinding) :
        RecyclerView.ViewHolder(itemResultBinding.root) {

        val tvValue: TextView = itemResultBinding.tvValue
        val tvCode: TextView = itemResultBinding.tvCode
        val ivFavorite: ImageView = itemResultBinding.ivFavorite

    }

}