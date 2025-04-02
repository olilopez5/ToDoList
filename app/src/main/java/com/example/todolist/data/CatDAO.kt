package com.example.todolist.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.todolist.utils.DatabaseManager

class CatDAO (context: Context) {

    val databaseManager = DatabaseManager(context)

    fun insert(category: Category){
        //data in write mode
        val db = databaseManager.writableDatabase
        //new map of values column name is key
        val values = ContentValues().apply {
            put(Category.COLUMN_NAME_NAME,category.name)

        }
        // new row, return PK od new row (insert ? error)
        try {
            val newRowId = db.insert(Category.TABLE_NAME, null,values)

            Log.i("DATABASE", "Insert : $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

    }
    fun update(category: Category){

        val db = databaseManager.writableDatabase

        val values = ContentValues().apply {
            put(Category.COLUMN_NAME_NAME,category.name)

        }

        try {
            //  whereClause = ? , whereArgs arrayOf("${category.id}")
            val newRowId = db.update(Category.TABLE_NAME, values, "${Category.COLUMN_NAME_ID} = ${category.id} ", null)

            Log.i("DATABASE", "Update category: ${category.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

    }
    fun delete(category: Category){

        val db = databaseManager.writableDatabase

        //
        try {
            val deleteRows = db.delete(Category.TABLE_NAME,"${Category.COLUMN_NAME_ID} = ${category.id}", null)

            Log.i("DATABASE", "Delete category: ${category.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }
    fun findById(id: Int): Category?{
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Category.COLUMN_NAME_ID,
            Category.COLUMN_NAME_NAME
        )


        val selection = "${Category.COLUMN_NAME_ID} = $id"

        var category: Category? = null

        try {
            val cursor = db.query(
                Category.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )

            // moveToNext(Boolean) data true, no data false
            //INDEX all columns

            if (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_NAME))
                var category = Category(id, name)
                TODO()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return category
    }

    fun findAll(): List<Category> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Category.COLUMN_NAME_ID,
            Category.COLUMN_NAME_NAME,

        )

        var categoryList: MutableList<Category> = mutableListOf()

        try {
            val cursor = db.query(
                Category.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups

            )

            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_NAME))
                val category = Category(id, name)
                categoryList.add(category)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return categoryList
    }
}