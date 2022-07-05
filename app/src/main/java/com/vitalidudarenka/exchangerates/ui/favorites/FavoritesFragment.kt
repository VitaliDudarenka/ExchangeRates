package com.vitalidudarenka.exchangerates.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.vitalidudarenka.exchangerates.base.BaseListFragment

class FavoritesFragment : BaseListFragment() {

    companion object {
        fun newInstance(bundle: Bundle? = null) =
            FavoritesFragment().apply { arguments = bundle }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.initListener {
            if (viewModel.favoritesRates.value?.find { rate -> rate.name == it.name } != null) {
                viewModel.removeFavorite(it)
                adapter.removeItem(it)
            }
        }

        viewModel.selectedCurrency.observe(viewLifecycleOwner) {
            binding.tvCurrency.text = StringBuilder(it.code).append(" ").append(it.name)
            (viewModel as FavoritesViewModel).getRates(it, true)
        }
        viewModel.favoritesRates.observe(viewLifecycleOwner) {
            if (viewModel.selectedCurrency.value != null)
                (viewModel as FavoritesViewModel).getRates(
                    viewModel.selectedCurrency.value!!,
                    false
                )
        }
    }

}