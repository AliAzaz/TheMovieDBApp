package com.example.themoviedbapp.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedbapp.R
import com.example.themoviedbapp.databinding.ProductViewBinding
import androidx.databinding.library.baseAdapters.BR
import com.example.themoviedbapp.model.MoviesResult

/**
 * @author AliAzazAlam on 5/16/2021.
 */
class MovieViewHolder(private val bi: ProductViewBinding) :
    RecyclerView.ViewHolder(bi.root) {

    fun bind(item: MoviesResult) {
        bi.apply {
            bi.setVariable(BR.movieItem, item)
            bi.executePendingBindings()
        }
    }


    companion object {
        fun create(viewGroup: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.product_view, viewGroup, false)
            val binding = ProductViewBinding.bind(view)
            return MovieViewHolder(binding)
        }
    }

    class ChildViewDiffUtils(
        private val oldList: ArrayList<MoviesResult>,
        private val newList: ArrayList<MoviesResult>
    ) :
        DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}