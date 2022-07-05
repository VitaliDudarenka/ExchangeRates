package com.vitalidudarenka.exchangerates.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vitalidudarenka.exchangerates.R
import com.vitalidudarenka.exchangerates.databinding.FragmentRatesListBinding
import com.vitalidudarenka.exchangerates.dialogs.SelectionDialog
import com.vitalidudarenka.exchangerates.states.ResultsState
import com.vitalidudarenka.exchangerates.ui.adapter.CurrenciesAdapter
import com.vitalidudarenka.exchangerates.ui.populars.PopularsViewModel
import kotlinx.coroutines.launch
import java.lang.StringBuilder

open class BaseListFragment : Fragment() {

    protected val binding: FragmentRatesListBinding by viewBinding(CreateMethod.INFLATE)
    protected val adapter = CurrenciesAdapter()
    protected lateinit var viewModel: BaseListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResults.adapter = adapter

        binding.tvCurrency.setOnClickListener {
            onOpenCurrenciesDialog()
        }
        binding.ivSort.setOnClickListener {
            onOpenSortDialog()
        }

        lifecycleScope.launchWhenCreated {
            collectSymbolsState()
        }
        lifecycleScope.launchWhenCreated {
            collectRatesState()
        }

        viewModel.selectedSortType.observe(viewLifecycleOwner) {
            viewModel.onRatesLoaded()
        }
        viewModel.favoritesRates.observe(viewLifecycleOwner) {
            adapter.initFavorites(it)
        }

        initData()
    }

    open fun initData() {
        viewModel.getSymbols()
        viewModel.getFavorites()
    }

    private fun onOpenCurrenciesDialog() {
        SelectionDialog().create(
            requireContext(),
            viewModel.initialSymbolsList.indexOf(viewModel.selectedCurrency.value),
            viewModel.initialSymbolsList.map {
                StringBuilder(it.code).append(" ").append(it.name).toString()
            }.toTypedArray(),
            getString(R.string.select_currency)
        ) { position, title -> onCurrencySelected(position) }
    }

    private fun onOpenSortDialog() {
        SelectionDialog().create(
            requireContext(),
            getSortTypePosition(viewModel.selectedSortType.value),
            arrayOf(
                getString(R.string.name_up_sort),
                getString(R.string.name_down_sort),
                getString(R.string.value_up_sort),
                getString(R.string.value_down_sort)
            ),
            getString(R.string.select_sorting)
        ) { position, title -> onSortingSelected(position) }
    }

    private fun onCurrencySelected(position: Int) {
        val selectedCurrency = viewModel.initialSymbolsList[position]
        viewModel.selectedCurrency.postValue(selectedCurrency)
    }

    private fun onSortingSelected(position: Int) {
        viewModel.selectedSortType.postValue(getSortType(position))
    }

    private suspend fun collectSymbolsState() {
        viewModel.symbolsState.collect {
            if (it is ResultsState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
            }
            if (it is ResultsState.SymbolsLoaded) {
                binding.progressBar.visibility = View.GONE
            }
            if (it is ResultsState.Error) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun collectRatesState() {
        viewModel.ratesState.collect {
            if (it is ResultsState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvResults.visibility = View.GONE
            }
            if (it is ResultsState.RatesLoaded) {
                adapter.setItems(it.data)
                binding.progressBar.visibility = View.GONE
                binding.rvResults.visibility = View.VISIBLE
            }
            if (it is ResultsState.Error) {
                binding.progressBar.visibility = View.GONE
                binding.rvResults.visibility = View.GONE
                Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getSortType(position: Int): BaseListViewModel.SortType {
        val sortType = when (position) {
            0 -> BaseListViewModel.SortType.NAME_UP
            1 -> BaseListViewModel.SortType.NAME_DOWN
            2 -> BaseListViewModel.SortType.VALUE_UP
            else -> BaseListViewModel.SortType.VALUE_DOWN
        }
        return sortType
    }

    private fun getSortTypePosition(sortType: BaseListViewModel.SortType?): Int {
        val selectedPosition = when (sortType) {
            BaseListViewModel.SortType.NAME_UP -> 0
            BaseListViewModel.SortType.NAME_DOWN -> 1
            BaseListViewModel.SortType.VALUE_UP -> 2
            else -> 3
        }
        return selectedPosition
    }

}