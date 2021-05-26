package com.example.themoviedbapp.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import com.example.themoviedbapp.R
import com.example.themoviedbapp.adapters.GenresAdapter
import com.example.themoviedbapp.base.FragmentBase
import com.example.themoviedbapp.base.repository.ResponseStatus
import com.example.themoviedbapp.base.viewmodel.MovieViewModel
import com.example.themoviedbapp.databinding.FragmentMovieDetailBinding
import com.example.themoviedbapp.ui.MainActivity
import com.example.themoviedbapp.utils.obtainViewModel
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MovieDetailFragment : FragmentBase() {

    lateinit var viewModel: MovieViewModel
    lateinit var bi: FragmentMovieDetailBinding
    lateinit var tagsAdapter: GenresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false);
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_menu).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
        * Initializing databinding
        * */
        bi = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
        * Obtaining ViewModel
        * */
        viewModel = obtainViewModel(
            activity as MainActivity,
            MovieViewModel::class.java,
            viewModelFactory
        )

        /*
        * Fetch product list
        * */
        viewModel.selectedMoviesResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    val data = it.data
                    data?.let { item ->

                        /*
                        * Passing data to view
                        * */
                        bi.setVariable(BR.selMovieItem, item)


                        lifecycleScope.launch {
                            /*
                            * Show loading and wait until view is not ready
                            * */
                            delay(500)
                            bi.productImg.visibility = View.VISIBLE


                            /*
                            * Dealing with tags
                            * */
                            tagsAdapter = GenresAdapter()
                            val flexboxLayoutManager = FlexboxLayoutManager(context).apply {
                                flexWrap = FlexWrap.WRAP
                                flexDirection = FlexDirection.ROW
                                alignItems = AlignItems.STRETCH
                            }
                            bi.genreList.layoutManager = flexboxLayoutManager
                            bi.genreList.adapter = tagsAdapter
                            tagsAdapter.updateItems(item.genres)

                        }

                    }
                }
                ResponseStatus.ERROR -> {
                }
                ResponseStatus.LOADING -> {
                }
            }

        })
    }

}