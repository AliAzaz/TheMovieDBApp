package com.example.themoviedbapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedbapp.model.MoviesResult
import com.example.themoviedbapp.viewholder.MovieViewHolder
import kotlinx.android.synthetic.main.product_view.view.*

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class MovieDBListAdapter(private val clickListener: OnItemClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    var productItems: ArrayList<MoviesResult> = ArrayList()
        set(value) {
            field = value
            val diffCallback = MovieViewHolder.ChildViewDiffUtils(filteredProductItems, productItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            if (filteredProductItems.size > 0)
                filteredProductItems.clear()
            filteredProductItems.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    private var filteredProductItems: ArrayList<MoviesResult> = ArrayList()
        set(value) {
            field = value
            val diffCallback = MovieViewHolder.ChildViewDiffUtils(filteredProductItems, productItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    fun clearProductItem(){
        filteredProductItems.clear()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        return MovieViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, i: Int) {
        val item = filteredProductItems[i]
        holder.bind(item)
        holder.itemView.parentLayout.setOnClickListener {
            clickListener.onItemClick(item, i)
        }
    }

    override fun getItemCount(): Int = filteredProductItems.size

    interface OnItemClickListener {
        fun onItemClick(item: MoviesResult, position: Int)
    }
}