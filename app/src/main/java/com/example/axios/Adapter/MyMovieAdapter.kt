package com.example.axios.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.axios.Model.Movie
import com.example.axios.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_movie_item.view.*

class MyMovieAdapter(private val movieList: MutableList<Movie>): RecyclerView.Adapter<MyMovieAdapter.MyViewHolder>() {
    class MyViewHolder (itemView:View): RecyclerView.ViewHolder(itemView){
        var image_movie: ImageView
        var txt_name: TextView
        var txt_team: TextView
        var txt_createdBy: TextView

        init {
            image_movie = itemView.image_movie
            txt_name = itemView.txt_name
            txt_team = itemView.txt_team
            txt_createdBy = itemView.txt_createdBy
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(movieList[position].imageUrl).into(holder.image_movie)
        holder.txt_name.text = movieList[position].name
        holder.txt_team.text = movieList[position].team
        holder.txt_createdBy.text = movieList[position].createdBy
    }
}