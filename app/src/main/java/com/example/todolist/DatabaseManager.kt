package com.example.todolist

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseManager (context: Context) : SQLiteOpenHelper(context,"DATABASE_NAME", null, DATABASE_VERSION) {
    //busca db y si no existe lo crea OnCreate para inicializar
    // y si existe cambiar la version +1 (2) ejecuta onUpgrade
    companion object {
        const val DATABASE_NAME = "todolist.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_TASK =
            "CREATE TABLE ${Task.TABLE_NAME} (" +
                    "${Task.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Task.COLUMN_NAME_TITLE} TEXT," +
                    "${Task.COLUMN_NAME_DONE} BOOLEAN)"

        private const val SQL_DROP_TABLE_TASK = "DROP TABLE IF EXISTS ${Task.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.i("DATABASE", "Create table task")
        db.execSQL(SQL_CREATE_TABLE_TASK)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onDestroy(db)
        onCreate(db)

    }

    fun onDestroy(db: SQLiteDatabase){
        db.execSQL(SQL_DROP_TABLE_TASK)
    }
}