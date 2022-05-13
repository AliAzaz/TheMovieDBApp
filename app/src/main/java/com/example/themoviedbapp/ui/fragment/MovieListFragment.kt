package com.example.themoviedbapp.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedbapp.MainApp
import com.example.themoviedbapp.R
import com.example.themoviedbapp.adapters.MovieDBListAdapter
import com.example.themoviedbapp.base.FragmentBase
import com.example.themoviedbapp.base.repository.ResponseStatus
import com.example.themoviedbapp.base.viewmodel.MovieViewModel
import com.example.themoviedbapp.databinding.FragmentMovieListBinding
import com.example.themoviedbapp.model.MoviesResult
import com.example.themoviedbapp.ui.MainActivity
import com.example.themoviedbapp.utils.*
import com.google.android.material.snackbar.Snackbar
import com.kennyc.view.MultiStateView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MovieListFragment : FragmentBase() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieDBListAdapter
    private lateinit var bi: FragmentMovieListBinding
    private var actionBarHeight = 0
    private var scrollFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
        * Initializing databinding
        * */
        bi = FragmentMovieListBinding.inflate(inflater, container, false)
        bi.setVariable(BR.callback, this)
        activity?.let {
            bi.edtStart.manager = it.supportFragmentManager
            bi.edtEnd.manager = it.supportFragmentManager
        }

        /*
        * Get actionbar height for use in translation
        * */
        context?.let { item ->
            actionBarHeight = with(TypedValue().also {
                item.theme.resolveAttribute(
                    android.R.attr.actionBarSize,
                    it,
                    true
                )
            }) {
                TypedValue.complexToDimensionPixelSize(this.data, resources.displayMetrics)
            }
        }

        /*
        * Translate items on menu click
        * */
        actionBarHeight *= -1
        bi.fldGrpSearchPhotos.translationY = actionBarHeight.toFloat()

        return bi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        * Obtaining ViewModel
        * */
        viewModel = obtainViewModel(
            activity as MainActivity,
            MovieViewModel::class.java,
            viewModelFactory
        )

        /*
        * Initiating recyclerview
        * */
        callingRecyclerView()

        /*
        * Fetch movie list
        * */
        viewModel.moviesResponse.observe(viewLifecycleOwner, {
            when (it.status) {
                ResponseStatus.SUCCESS -> {
                    it.data?.apply {
                        movieAdapter.productItems =
                            it.data.moviesInfo?.filter { item ->
                                item.poster_path != null
                            } as ArrayList<MoviesResult>
                        bi.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                    }
                    bi.nestedScrollView.dismissSnackBar()
                    scrollFlag = false
                }
                ResponseStatus.ERROR -> {
                    it.data?.let { item ->
                        if (item.page == 1) {
                            bi.nestedScrollView.showSnackBar(
                                message = getString(R.string.movies_not_found)
                            )
                            bi.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                        } else
                            bi.nestedScrollView.showSnackBar(
                                message = it.message.toString()
                            )
                    } ?: run {
                        bi.multiStateView.viewState = MultiStateView.ViewState.ERROR
                        bi.nestedScrollView.showSnackBar(
                            message = getString(R.string.internet_na),
                            action = getString(R.string.retry)
                        ) {
                            viewModel.retryConnection()
                        }

                    }
                }
                ResponseStatus.LOADING -> {
                    it.data?.let { item ->
                        if (item.page == 1) {
                            bi.multiStateView.viewState =
                                MultiStateView.ViewState.LOADING
                            scrollFlag = true
                        } else {
                            bi.nestedScrollView.showSnackBar(
                                message = getString(R.string.loading_more_mov),
                                duration = Snackbar.LENGTH_INDEFINITE
                            )
                            scrollFlag = true
                        }
                    }
                }
            }

        })

        /*
        * Checking scrollview scroll end
        * */
        bi.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (!scrollFlag && scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                viewModel.loadNextPagePhotos()
            }
        }


        /*
        * Movie search clear
        * */
        bi.inputSearchPhotos.setEndIconOnClickListener {
            bi.edtStart.clearFocus()
            bi.edtEnd.clearFocus()
            bi.edtStart.text = null
            bi.edtEnd.text = null
            movieAdapter.clearProductItem()
            bi.populateTxt.text = getString(R.string.search_latest)
            viewModel.fetchMoviesFromRemoteServer(1)
        }

    }


    /*
    * Initialize recyclerView with onClickListener
    * */
    private fun callingRecyclerView() {
        movieAdapter = MovieDBListAdapter(object : MovieDBListAdapter.OnItemClickListener {
            override fun onItemClick(item: MoviesResult, position: Int) {
                viewModel.setSelectedProduct(item)
                findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment())
            }
        })
        bi.productList.apply {
            setHasFixedSize(true)
            movieAdapter.setHasStableIds(true)
            movieAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }

        bi.productList.adapter = movieAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_menu).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu -> {
                bi.fldGrpSearchPhotos.animate().apply {
                    duration = CONSTANTS.Numbers._1000
                    translationY(if (bi.fldGrpSearchPhotos.translationY == actionBarHeight.toFloat()) 10f else actionBarHeight.toFloat())
                }.start()

                bi.nestedScrollView.animate().apply {
                    duration = CONSTANTS.Numbers._1000
                    translationY(if (bi.nestedScrollView.translationY == 0f) bi.fldGrpSearchPhotos.height.toFloat() * 2 else 0f)
                }.start()

                if (bi.fldGrpSearchPhotos.translationY == actionBarHeight.toFloat())
                    bi.fldGrpDt.fadeVisibility(View.VISIBLE)
                else
                    bi.fldGrpDt.fadeVisibility(View.GONE)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    /*
     * On text changed listener for searching movies
     * */
    fun startDateTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        bi.edtEnd.isEnabled = false
        bi.edtEnd.text = null
        if (TextUtils.isEmpty(bi.edtStart.text)) return
        bi.edtEnd.minDate = bi.edtStart.text.toString().getCalendarDate().addDays(0)
        bi.edtEnd.maxDate = bi.edtStart.text.toString().getCalendarDate().addDays(14)
        bi.edtEnd.isEnabled = true
    }

    fun endDateTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (TextUtils.isEmpty(bi.edtEnd.text)) return
        val s = bi.edtStart.text.toString()
        val e = bi.edtEnd.text.toString()
        movieAdapter.clearProductItem()
        bi.populateTxt.text = "Search: $s -> $e"
        viewModel.searchMoviesFromRemote(e, s)
    }

}