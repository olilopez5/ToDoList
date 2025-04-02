package com.example.todolist.data

data class Category(
    val id : Int,
    var name: String
){
    companion object{
        const val TABLE_NAME = "Categories"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_NAME = "name"


    }
}
