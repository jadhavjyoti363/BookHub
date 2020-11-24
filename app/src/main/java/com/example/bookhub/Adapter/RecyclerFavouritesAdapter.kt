package com.example.bookhub.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.Database.BookEntity
import com.example.bookhub.R
import com.squareup.picasso.Picasso

class RecyclerFavouritesAdapter(val context: Context,val bookList: List<BookEntity>):RecyclerView.Adapter<RecyclerFavouritesAdapter.FavouriteViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_favorite_single_row,parent,false)

        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {

        val book=bookList[position]
        holder.textBookName.text=book.bookName
        holder.textAuthorName.text=book.bookAuthor
        holder.textBookPrice.text=book.bookPrice
        holder.textBookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imageBook);



    }


    class FavouriteViewHolder(view : View):RecyclerView.ViewHolder(view){
        val textBookName:TextView =view.findViewById(R.id.txtBookName)
        val textAuthorName:TextView=view.findViewById(R.id.txtFavBookAuthor)
        val textBookPrice:TextView=view.findViewById(R.id.txtFavBookPrice)
        val textBookRating:TextView=view.findViewById(R.id.txtFavBookRating)
        val imageBook:ImageView=view.findViewById(R.id.imgFavBook)
        val llcontent:LinearLayout=view.findViewById(R.id.llFavContent)
    }



}