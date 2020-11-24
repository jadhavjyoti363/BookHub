package com.example.bookhub.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhub.Activity.DescuriptionActivity
import com.example.bookhub.R
import com.example.bookhub.model.Book
import com.squareup.picasso.Picasso

class DashboardAdpter ( val context:Context , val itemList:ArrayList<Book> ): RecyclerView.Adapter<DashboardAdpter.DashboardAdpterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardAdpterViewHolder {
   val view =LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard,parent,false)

        return DashboardAdpterViewHolder(view)

    }

    override fun getItemCount(): Int {
        return  itemList.size
    }

    override fun onBindViewHolder(holder: DashboardAdpterViewHolder, position: Int) {

        val book=itemList[position]
        holder.textBookName.text=book.bookName
        holder.textBookAuthour.text=book.bookAuthor
        holder.textBookCost.text=book.bookPrice
        holder.textBookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imagebook);
      //  holder.imagebook.setImageResource(book.bookImage)




        holder.llcontend.setOnClickListener(){

             val intent = Intent(context,DescuriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)


          //  Toast.makeText(context,"clicked On ${holder.textBookName.text}",Toast.LENGTH_SHORT).show()
        }
    }

    class DashboardAdpterViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val textBookName: TextView =view.findViewById(R.id.txtBookName)
        val textBookAuthour:TextView=view.findViewById(R.id.txtBookAuthor)
        val textBookCost:TextView=view.findViewById(R.id.txtBookPrice)
        val textBookRating:TextView=view.findViewById(R.id.txtBookRating)
        val imagebook:ImageView=view.findViewById(R.id.imgBookImage)
        val llcontend:LinearLayout=view.findViewById(R.id.llcontend)
    }


}
