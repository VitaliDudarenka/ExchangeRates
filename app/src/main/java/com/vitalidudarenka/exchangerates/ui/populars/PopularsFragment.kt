package com.vitalidudarenka.exchangerates.ui.populars

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.vitalidudarenka.exchangerates.base.BaseListFragment
import com.vitalidudarenka.exchangerates.ui.favorites.FavoritesFragment

class PopularsFragment : BaseListFragment() {

    companion object {
        fun newInstance(bundle: Bundle? = null) =
            PopularsFragment().apply { arguments = bundle }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[PopularsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.initListener {
            if (viewModel.favoritesRates.value?.find { rate -> rate.name == it.name } != null) {
                viewModel.removeFavorite(it)
            } else
                viewModel.saveFavorite(it)
        }

        viewModel.selectedCurrency.observe(viewLifecycleOwner) {
            binding.tvCurrency.text = StringBuilder(it.code).append(" ").append(it.name)
            (viewModel as PopularsViewModel).getRates(it)
        }
    }

}