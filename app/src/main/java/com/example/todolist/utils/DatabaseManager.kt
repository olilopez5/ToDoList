package com.example.todolist.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.todolist.data.Category
import com.example.todolist.data.Task

class DatabaseManager (context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION
) {
    //busca db y si no existe lo crea OnCreate para inicializar
    // y si existe cambiar la version +1 (2) ejecuta onUpgrade

    companion object {
        const val DATABASE_NAME = "todolist.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_TASK =
            "CREATE TABLE ${Task.TABLE_NAME} (" +
                    "${Task.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Task.COLUMN_NAME_TITLE} TEXT," +
                    "${Task.COLUMN_NAME_DONE} BOOLEAN)" +
                    "${Task.COLUMN_NAME_CATEGORY} INTEGER " +
                    "FOREING KEY(${Task.COLUMN_NAME_CATEGORY})" +
                    "REFERENCES ${Category.TABLE_NAME}(${Category.COLUMN_NAME_ID}))"

        private const val SQL_DROP_TABLE_TASK = "DROP TABLE IF EXISTS ${Task.TABLE_NAME}"


        private const val SQL_CREATE_TABLE_CATEGORY =
            "CREATE TABLE ${Task.TABLE_NAME} (" +
                    "${Task.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Task.COLUMN_NAME_TITLE} TEXT,"


        private const val SQL_DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS ${Task.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.i("DATABASE", "Create table categories")
        db.execSQL(SQL_CREATE_TABLE_CATEGORY)
        Log.i("DATABASE", "Create table task")
        db.execSQL(SQL_CREATE_TABLE_TASK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)
    }

    fun onDestroy(db: SQLiteDatabase){
        db.execSQL(SQL_DROP_TABLE_TASK)
        db.execSQL(SQL_DROP_TABLE_CATEGORY)
    }
}