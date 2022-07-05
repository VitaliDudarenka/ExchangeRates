package com.vitalidudarenka.exchangerates.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vitalidudarenka.exchangerates.R
import com.vitalidudarenka.exchangerates.ui.favorites.FavoritesFragment
import com.vitalidudarenka.exchangerates.ui.populars.PopularsFragment

class MainViewPagerAdapter(
    private val items: List<Int>,
    manager: FragmentManager,
    val context: Context
) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            val fragment = PopularsFragment.newInstance()
            fragment
        } else {
            val fragment = FavoritesFragment.newInstance()
            fragment
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (position == 0) context.getString(R.string.populars) else context.getString(R.string.favorites)
    }

}