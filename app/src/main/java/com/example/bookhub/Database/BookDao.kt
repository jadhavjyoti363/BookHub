package com.example.bookhub.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {

    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks():List<BookEntity>


    @Query("SELECT * FROM bookS WHERE bookId=:bookId ")
    fun getBooksById(bookId:String):BookEntity
}