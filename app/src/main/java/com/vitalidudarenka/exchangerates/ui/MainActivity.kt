package com.vitalidudarenka.exchangerates.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vitalidudarenka.exchangerates.R
import com.vitalidudarenka.exchangerates.databinding.ActivityMainBinding
import com.vitalidudarenka.exchangerates.ui.adapter.MainViewPagerAdapter


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewPager(binding.viewPager)
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_populars -> binding.viewPager.currentItem = 0
                else -> binding.viewPager.currentItem = 1
            }
            false
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = MainViewPagerAdapter(arrayListOf(0, 1), supportFragmentManager, this)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until binding.navView.menu.size())
                    binding.navView.menu.getItem(i).isChecked = false
                binding.navView.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

}