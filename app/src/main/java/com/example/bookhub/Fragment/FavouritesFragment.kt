package com.example.bookhub.Fragment


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.bookhub.Adapter.RecyclerFavouritesAdapter
import com.example.bookhub.Database.BookDatabase
import com.example.bookhub.Database.BookEntity

import com.example.bookhub.R
import kotlinx.android.synthetic.main.fragment_favourites.*


class FavouritesFragment : Fragment() {

    lateinit var progressLayout: RelativeLayout
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: RecyclerFavouritesAdapter
    var dbbooklist= listOf<BookEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_favourites, container, false)


       progressBar = view.findViewById(R.id.favProgressBar)
       progressLayout = view.findViewById(R.id.favprogressLayout)
        recyclerView = view.findViewById(R.id.favrecyclerview)

        layoutManager = GridLayoutManager(activity as Context, 2)

        dbbooklist=retriveFavourites(activity as Context).execute().get()



        if(dbbooklist!=null && activity != null){


            progressLayout.visibility=View.GONE
            recyclerAdapter=RecyclerFavouritesAdapter(activity as Context,dbbooklist)
            recyclerView.adapter=recyclerAdapter
            recyclerView.layoutManager=layoutManager

       }else{


        }

        return view

        //return inflater.inflate(R.layout.fragment_favourites, container, false)
    }


    class retriveFavourites(val context: Context):AsyncTask<Void,Void,List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
          //  val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()

            val db = Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            println("database $db.bookDao().getAllBooks()")

            return db.bookDao().getAllBooks()
            println("database ${db.bookDao().getAllBooks()}")
        }
    }


}
