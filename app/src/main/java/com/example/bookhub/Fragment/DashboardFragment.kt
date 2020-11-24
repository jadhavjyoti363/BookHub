package com.example.bookhub.Fragment


import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.Adapter.DashboardAdpter

import com.example.bookhub.R
import com.example.bookhub.model.Book
import com.example.bookhub.util.ConnectionManager
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap


class DashboardFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
   // lateinit var button:Button

    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

   val booklist= arrayListOf("P.S.I Love You","The Great Gatsby","Anna karnina","The Loard of Ring","war and peace","lolita");

    lateinit var recyclerAdapter:DashboardAdpter

    val bookInfoList= arrayListOf<Book>(

//        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
//        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
//        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
//        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
//        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
//        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
//        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
//        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
//        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
//        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)

    )

    var ratingComparator=Comparator<Book>{
            book1, book2->

        if(book1.bookRating.compareTo(book2.bookName,true)==0)
        {
            book1.bookName.compareTo(book2.bookRating,true)
        }
        else{
             book1.bookRating.compareTo(book2.bookRating,true)
        }


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view=inflater.inflate(R.layout.fragment_dashboard, container, false)

          //button=view.findViewById(R.id.btncheckinternet)
          recyclerView=view.findViewById(R.id.recyclerview)
          layoutManager= LinearLayoutManager(activity)
          progressLayout=view.findViewById(R.id.progressLayout)
          progressBar=view.findViewById(R.id.progress_id)

           progressLayout.visibility = View.VISIBLE

           setHasOptionsMenu(true)

        val queue= Volley.newRequestQueue(activity as Context)
        val url ="http://13.235.250.119/v1/book/fetch_books/"

       // val url = "​http://13.235.250.119/v2/restaurants/fetch_result/"




        if(ConnectionManager().checkConnection(activity as Context)){

            val jsonObjectRequest=object:JsonObjectRequest(Method.GET,url,null,Response.Listener {
                println("response is $it")

               try {

                    progressLayout.visibility = View.GONE
                    val success = it.getBoolean("success")

                Log.d("data::: ","jsonArray :: "+ success);


                if (success) {
                    val data = it.getJSONArray("data")

                    Log.d("data::: ","jsonArray :: "+data);

                    for (i in 0 until data.length()) {

                        val bookJsonObject = data.getJSONObject(i)


                        val bookobject = Book(
                            bookJsonObject.getString("book_id"),
                            bookJsonObject.getString("name"),
                            bookJsonObject.getString("author"),
                            bookJsonObject.getString("rating"),
                            bookJsonObject.getString("price"),
                            bookJsonObject.getString("image")
                        )
                        bookInfoList.add(bookobject)


                        recyclerAdapter = DashboardAdpter(activity as Context, bookInfoList)

                        recyclerView.adapter = recyclerAdapter
                        recyclerView.layoutManager = layoutManager
//                        recyclerView.addItemDecoration(
//                            DividerItemDecoration(
//                                recyclerView.context,
//                                (layoutManager as LinearLayoutManager).orientation
//                            )
//                        )


                    }
                } else {
                    Toast.makeText(activity as Context, "some error!!", Toast.LENGTH_SHORT).show()
                }
            }catch (e:JSONException){

                    Toast.makeText(activity as Context, "Unexpected error occurred!!", Toast.LENGTH_SHORT).show()
                }


            },
                Response.ErrorListener {

                    println("Error is $it")

                    Toast.makeText(activity as Context, "Volley error occurred!!", Toast.LENGTH_SHORT).show()


                }){

                override fun getHeaders(): MutableMap<String, String> {

                    val headers=HashMap<String,String>()
                   headers["content-type"]="application/json"
                    headers["token"]="5e1516eadd3d48"
                    return headers
                    // return super.getHeaders()
                }

            }

            queue.add(jsonObjectRequest)

        }else{


            val dialog =AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet connection  Not Found")
            dialog.setPositiveButton("open setting"){text, listener->

                val SettingIntent=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(SettingIntent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){text,listener->

                ActivityCompat.finishAffinity(activity as Activity)
            }

            dialog.create()
            dialog.show()



        }





//    button.setOnClickListener(){
//    if(ConnectionManager().checkConnection(activity as Context)){
//
//        val dialog =AlertDialog.Builder(activity as Context)
//        dialog.setTitle("Success")
//        dialog.setMessage("Internet connection Found")
//        dialog.setPositiveButton("ok"){text, listener->}
//        dialog.setNegativeButton("cancle"){text,listener->}
//
//        dialog.create()
//        dialog.show()
//
//    }else
//    {
//
//        val dialog =AlertDialog.Builder(activity as Context)
//        dialog.setTitle("Error")
//        dialog.setMessage("Internet connection  Not Found")
//        dialog.setPositiveButton("ok"){text, listener->}
//        dialog.setNegativeButton("cancle"){text,listener->}
//
//        dialog.create()
//        dialog.show()
//
//
//    }
//}




        return view
       // return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       // super.onCreateOptionsMenu(menu, inflater)


        inflater?.inflate(R.menu.menu_dashboard,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id=item?.itemId

        if(id==R.id.sort){
            Collections.sort(bookInfoList,ratingComparator)
            bookInfoList.reverse()

        }
//        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)

    }
}

