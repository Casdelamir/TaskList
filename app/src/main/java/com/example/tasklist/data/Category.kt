package com.example.tasklist.data

import android.provider.BaseColumns

data class Category(val id: Int, val tasksId: Int) {
    companion object {
        const val TABLE_NAME = "Categories"
        const val COLUMN_NAME_TASK = "task"

        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME_TASK INTEGER)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}