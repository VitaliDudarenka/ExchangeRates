package com.vitalidudarenka.exchangerates.ui.favorites

import android.os.Bundle
import android.view.View
import com.vitalidudarenka.exchangerates.base.BaseListFragment

class FavoritesFragment : BaseListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.initListener {
            if (viewModel.favoritesRates.value?.find { rate -> rate.name == it.name } != null) {
                viewModel.removeFavorite(it)
                adapter.removeItem(it)
            }
        }
    }

}