package com.example.todolist.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.todolist.utils.DatabaseManager
import com.example.todolist.utils.Security

class NoteDAO(context: Context) {

    val databaseManager = DatabaseManager(context)

    fun insert(note: Note){
        //data in write mode
        val db = databaseManager.writableDatabase
        //new map of values column name is key
        val values = ContentValues().apply {
            put(Note.COLUMN_NAME_TITLE,note.title)
            put(Note.COLUMN_NAME_DESCRIPTION,note.description)
            put(Note.COLUMN_NAME_DATE, note.date)
            put(Note.COLUMN_NAME_PRIVATE, if (note.private) 1 else 0)

            if (note.private && !note.password.isNullOrEmpty()) {
                put(Note.COLUMN_NAME_PASSWORD, Security.encryptPassword(note.password!!))
            }
        }
        // new row, return PK od new row (insert ? error)
        try {
            val newRowId = db.insert(Note.TABLE_NAME, null,values)

            Log.i("DATABASE", "Insert : $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }


    }
    fun update(note: Note){

        val db = databaseManager.writableDatabase

        val values = ContentValues().apply {
            put(Note.COLUMN_NAME_TITLE,note.title)
            put(Note.COLUMN_NAME_DESCRIPTION,note.description)
            put(Note.COLUMN_NAME_DATE,note.date)

            put(Note.COLUMN_NAME_PRIVATE, if (note.private) 1 else 0)
            if (note.private && !note.password.isNullOrEmpty()) {
                put(Note.COLUMN_NAME_PASSWORD, Security.encryptPassword(note.password!!))
            }
        }
        TODO()

        try {
            //  whereClause = ? , whereArgs arrayOf("${note.id}")
            val newRowId = db.update(Note.TABLE_NAME, values, "${Note.COLUMN_NAME_ID} = ${note.id} ", null)

            Log.i("DATABASE", "Update note: ${note.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

    }
    fun delete(note: Note){

        val db = databaseManager.writableDatabase

        try {
            val deleteRows = db.delete(Note.TABLE_NAME,"${Note.COLUMN_NAME_ID} = ${note.id}", null)

            Log.i("DATABASE", "Delete note: ${note.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }
    fun findById(id: Long): Note?{
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Note.COLUMN_NAME_ID,
            Note.COLUMN_NAME_TITLE,
            Note.COLUMN_NAME_DESCRIPTION,
            Note.COLUMN_NAME_DATE)

        val selection = "${Note.COLUMN_NAME_ID} = $id"

        var note: Note? = null

        try {
            val cursor = db.query(
                Note.TABLE_NAME,   // The table to query
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
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_DESCRIPTION))
                val date = cursor.getLong(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_DATE))
                note = Note(id, title, description, date)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return note
    }

    fun findAll(): List<Note> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Note.COLUMN_NAME_ID,
            Note.COLUMN_NAME_TITLE,
            Note.COLUMN_NAME_DESCRIPTION,
            Note.COLUMN_NAME_DATE
        )

        var noteList: MutableList<Note> = mutableListOf()

        try {
            val cursor = db.query(
                Note.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                Note.COLUMN_NAME_DESCRIPTION             // The sort order
            )

            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_DESCRIPTION))
                val date = cursor.getLong(cursor.getColumnIndexOrThrow(Note.COLUMN_NAME_DATE))

                val note = Note(id, title, description, date)
                noteList.add(note)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return noteList
    }
}
