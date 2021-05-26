package com.example.themoviedbapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedbapp.databinding.GenreViewBinding
import com.example.themoviedbapp.model.Genre

/**
 * @author AliAzazAlam on 5/26/2021.
 */
class GenresAdapter : RecyclerView.Adapter<GenresAdapter.GenreViewHolder>() {

    private var tagItems: ArrayList<Genre> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        var binding = GenreViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(tagItems[position], position)
    }

    override fun getItemCount() = tagItems.size

    fun updateItems(tagsList: List<Genre>) {
        tagItems.clear()
        tagItems.addAll(tagsList)
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(val itemBinding: GenreViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(tagModel: Genre, position: Int) {
            itemBinding.apply {
                txtGenreName.text = tagModel.name

            }
        }

    }

}