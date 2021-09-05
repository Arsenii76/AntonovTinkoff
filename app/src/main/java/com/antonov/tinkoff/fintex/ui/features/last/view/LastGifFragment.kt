package com.antonov.tinkoff.fintex.ui.features.last.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antonov.tinkoff.fintex.R
import com.antonov.tinkoff.fintex.data.api.categories.CategoriesGifRetrofitBuilder
import com.antonov.tinkoff.fintex.data.networkerror.RequestError
import com.antonov.tinkoff.fintex.data.repository.categories.CategoriesGIfRepository
import com.antonov.tinkoff.fintex.databinding.FragmentLastBinding
import com.antonov.tinkoff.fintex.ui.ViewState.Success
import com.antonov.tinkoff.fintex.ui.ViewState.Error
import com.antonov.tinkoff.fintex.ui.ViewState.Loading
import com.antonov.tinkoff.fintex.ui.common.CategoriesGifName
import com.antonov.tinkoff.fintex.ui.common.adapter.CategoriesGIfAdapter
import com.antonov.tinkoff.fintex.ui.common.viewmodel.CategoriesGifViewModel
import com.antonov.tinkoff.fintex.ui.common.viewmodel.CategoriesGifViewModelFactory
import com.antonov.tinkoff.fintex.utils.toast
import java.net.UnknownHostException


class LastGifFragment : Fragment() {

    private var _binding: FragmentLastBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesGifViewModel: CategoriesGifViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupCategoriesObserver()

        binding.apply {

            repeatNoInternetText.setOnClickListener {
                getGifs()
            }

            repeatEmptyResultText.setOnClickListener {
                getGifs()
            }

            newGifFab.setOnClickListener {
                getGifs()
            }
        }
    }

    private fun getGifs(){
        categoriesGifViewModel.getCategoriesGif(
            categoriesName = CategoriesGifName.LAST,
            page = (0..2000).random()
        )
    }

    private fun setupViewModel() {
        categoriesGifViewModel = ViewModelProvider(
            this,
            CategoriesGifViewModelFactory(
                CategoriesGIfRepository(CategoriesGifRetrofitBuilder.categoriesGifApiService),
                categoriesName = CategoriesGifName.LAST,
                page = (0..2000).random()
            )
        ).get(CategoriesGifViewModel::class.java)
    }

    private fun setupCategoriesObserver() {
        categoriesGifViewModel.categoriesGifLiveData.observe(viewLifecycleOwner, { results ->
            when (results) {
                is Success -> {
                    binding.progressBar.isVisible = false

                    if (results.data?.result.isNullOrEmpty()) {
                        binding.apply {
                            categoriesRecycler.isVisible = false
                            errorWithEmptyResultLinear.isVisible = true
                            binding.newGifFab.isVisible = false
                        }
                    } else {
                        binding.categoriesRecycler.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = results.data?.result?.let { CategoriesGIfAdapter(it) }
                            binding.newGifFab.isVisible = true
                        }
                    }
                }
                is Loading -> {
                    binding.apply {
                        binding.newGifFab.isVisible = true
                        errorNoInternetLinear.isVisible = false
                        errorWithEmptyResultLinear.isVisible = false
                        categoriesRecycler.isVisible = true
                        progressBar.isVisible = true
                    }
                }
                is Error -> {
                    binding.newGifFab.isVisible = false
                    binding.progressBar.isVisible = false

                    val errorMsg = RequestError.checkException(results.exception)

                    when (results.exception) {
                        is UnknownHostException -> {
                            binding.apply {
                                categoriesRecycler.isVisible = false
                                errorNoInternetLinear.isVisible = true
                                errorWithEmptyResultLinear.isVisible = false
                            }
                        }
                        else -> requireContext().toast(errorMsg)
                    }
                }
            }
        })
    }
}
