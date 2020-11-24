package com.example.bookhub.Activity

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.Adapter.DashboardAdpter
import com.example.bookhub.Database.BookDatabase
import com.example.bookhub.Database.BookEntity
import com.example.bookhub.R
import com.example.bookhub.model.Book
import com.example.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class DescuriptionActivity : AppCompatActivity() {


    lateinit var bookImageView: ImageView
    lateinit var bookName:TextView
    lateinit var bookAuthorName:TextView
    lateinit var bookPrice:TextView
    lateinit var bookRating:TextView
    lateinit var bookDescuription:TextView
    lateinit var progressBar: ProgressBar
    lateinit var progressBarLayout: RelativeLayout
  lateinit var  toolbar: Toolbar
    lateinit var AddFvbtn:Button
    var bookId :String? ="100"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descuription)

        bookImageView = findViewById(R.id.d_bookimage)
        bookName = findViewById(R.id.d_bookName)
        bookAuthorName = findViewById(R.id.d_bookAuthor)
        bookPrice = findViewById(R.id.d_bookprice)
        bookRating = findViewById(R.id.d_ratingview)
        bookDescuription = findViewById(R.id.d_bookdesc)
        progressBar = findViewById(R.id.d_progress_id)
        progressBar.visibility = View.VISIBLE
        progressBarLayout = findViewById(R.id.d_rlprogressbar)
        progressBarLayout.visibility = View.VISIBLE
        AddFvbtn=findViewById(R.id.d_btnadd)





        toolbar=findViewById(R.id.d_toolbarLayoutDetails)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Details"

        println("D_oncreate is $bookId")

        if (intent != null) {

            bookId = intent.getStringExtra("book_id")
            println("D_intent is $bookId")
        } else {
            finish()
            Toast.makeText(this@DescuriptionActivity, "Error Occurred", Toast.LENGTH_SHORT).show()
        }

        if (bookId == "100") {

            println("D_BOOKID is $bookId")
            finish()
            Toast.makeText(this@DescuriptionActivity, "Error Occurred", Toast.LENGTH_SHORT).show()
        }


        val queue = Volley.newRequestQueue(this@DescuriptionActivity)
        println("D_queue is $queue")
        val url = "http://13.235.250.119/v1/book/get_book/"
       // val url ="http://13.235.250.119/v1/book/fetch_books/"
        println("D_url is $url")

        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        println("D_json_params is $jsonParams")

        if(ConnectionManager().checkConnection(this@DescuriptionActivity)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(Method.POST, url, jsonParams, Response.Listener {
                    println("D_response is $it")

                    //  try {

                    progressBarLayout.visibility = View.GONE
                    val success = it.getBoolean("success")

                    if (success) {
                        // val data = it.getJSONArray("data")


                        println("response_D is $success")
                        println("response is $it")


                        val bookJsonObjects = it.getJSONObject("book_data")

                        val bookImageUrl =bookJsonObjects.getString("image")

                        Picasso.get().load(bookJsonObjects.getString("image"))
                            .error(R.drawable.default_book_cover).into(bookImageView)
                        bookName.text = bookJsonObjects.getString("name")
                        bookAuthorName.text = bookJsonObjects.getString("author")
                        bookPrice.text = bookJsonObjects.getString("price")
                        bookRating.text = bookJsonObjects.getString("rating")
                        bookDescuription.text = bookJsonObjects.getString("description")


                        val bookEntity=BookEntity(
                            bookId?.toInt() as Int,
                            bookName.text.toString(),
                            bookAuthorName.text.toString(),
                            bookPrice.text.toString(),
                            bookRating.text.toString(),
                            bookDescuription.text.toString() ,
                            bookImageUrl
                        )

                    val checkFav =DBAsynkTask(applicationContext,bookEntity,1).execute()
                    val isFav=checkFav.get()

                        if (isFav){

                            AddFvbtn.text="Remove From Favourites"
                            val favcolor= ContextCompat.getColor(applicationContext,R.color.colorfaurites)
                            AddFvbtn.setBackgroundColor(favcolor)
                            print("isfav$isFav")

                        }else{


                            AddFvbtn.text="Add to Favourites"
                            val favcolor= ContextCompat.getColor(applicationContext,R.color.colorAccent)
                            AddFvbtn.setBackgroundColor(favcolor)



                        }

                        AddFvbtn.setOnClickListener {


                            if(!DBAsynkTask(applicationContext,bookEntity,1).execute().get())
                            {

                                val async= DBAsynkTask(applicationContext,bookEntity,2).execute()

                                val result= async.get()


                                if(result){
                                    Toast.makeText(this@DescuriptionActivity, "Book Added to  Favourites", Toast.LENGTH_SHORT).show()
                                     AddFvbtn.text ="Remove From Favourites"
                                    val favcolor=ContextCompat.getColor(applicationContext,R.color.colorfaurites)
                                    AddFvbtn.setBackgroundColor(favcolor)
                                }
                                else{

                                    Toast.makeText(this@DescuriptionActivity, "Some Error Occurred", Toast.LENGTH_SHORT).show()
                                }


                            }else{


                                val async =DBAsynkTask(applicationContext,bookEntity,3).execute()
                                val result=async.get()
                                if(result){

                                    Toast.makeText(this@DescuriptionActivity, "Book Remove From Favourites", Toast.LENGTH_SHORT).show()
                                    AddFvbtn.text ="Add to Favourites"
                                    val favcolor=ContextCompat.getColor(applicationContext,R.color.colorAccent)
                                    AddFvbtn.setBackgroundColor(favcolor)

                                }else{

                                    Toast.makeText(this@DescuriptionActivity, "Some Error Occurred", Toast.LENGTH_SHORT).show()

                                }


                            }
                        }





                    } else {
                        Toast.makeText(this@DescuriptionActivity, "some error!!", Toast.LENGTH_SHORT).show()
                    }
//                } catch (e: JSONException) {
//
//                    Toast.makeText(this, "Unexpected error occurred!!", Toast.LENGTH_SHORT).show()
//                }


                },
                    Response.ErrorListener {

                        println("Error is $it")

                        Toast.makeText(this@DescuriptionActivity, "Volley error occurred!!", Toast.LENGTH_SHORT).show()


                    }) {

                    override fun getHeaders(): MutableMap<String, String> {

                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "5e1516eadd3d48"
                        return headers
                        // return super.getHeaders()
                    }

                }

            queue.add(jsonObjectRequest)
        }else{


            val dialog = AlertDialog.Builder(this@DescuriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet connection  Not Found")
            dialog.setPositiveButton("open setting"){text, listener->

                val SettingIntent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(SettingIntent)
                 finish()

            }
            dialog.setNegativeButton("Exit"){text,listener->

                ActivityCompat.finishAffinity(this@DescuriptionActivity)
            }

            dialog.create()
            dialog.show()


        }
    }




    class DBAsynkTask(val context:Context,val bookEntity: BookEntity,val mode:Int) : AsyncTask<Void,Void,Boolean>() {


        val db = Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()





        override fun doInBackground(vararg params: Void?): Boolean {
/*
* mode1:check DB if book is favurites or not
* mode2: save book into DB  as  favurite
* mode3:Remove book from favurite
*
* */
            when(mode){
                1 ->{

                    val book:BookEntity? =db.bookDao().getBooksById(bookEntity.bookId.toString())
                    db.close()
                    return book!= null
                }
                2->{

                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3->{
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true

                }
            }





          return false

        }




    }

}


