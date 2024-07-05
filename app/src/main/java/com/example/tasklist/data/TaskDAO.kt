package com.example.tasklist.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.tasklist.data.Task.Companion.COLUMN_NAME_DONE
import com.example.tasklist.utils.DatabaseManager

class TaskDAO(val context: Context) {

    private val databaseManager: DatabaseManager = DatabaseManager(context)

    fun insert(task: Task) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Task.COLUMN_NAME_TITLE, task.name)
        values.put(Task.COLUMN_NAME_DESCRIPTION, task.description)
        values.put(Task.COLUMN_NAME_CATEGORY, task.category?.id)
        values.put(COLUMN_NAME_DONE, task.done)

        val newRowId = db.insert(Task.TABLE_NAME, null, values)
        task.id = newRowId.toInt()
    }

    fun update(task: Task) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Task.COLUMN_NAME_TITLE, task.name)
        values.put(Task.COLUMN_NAME_DESCRIPTION, task.description)
        values.put(Task.COLUMN_NAME_CATEGORY, task.category?.id)
        values.put(COLUMN_NAME_DONE, task.done)

        val updatedRows = db.update(
            Task.TABLE_NAME,
            values,
            "${BaseColumns._ID} = ${task.id}",
            null
        )
    }

    fun delete(task: Task) {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(Task.TABLE_NAME, "${BaseColumns._ID} = ${task.id}", null)
    }

    @SuppressLint("Range")
    fun find(id: Int): Task? {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(BaseColumns._ID, Task.COLUMN_NAME_TITLE, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_CATEGORY, COLUMN_NAME_DONE)

        val cursor = db.query(
            Task.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            "${BaseColumns._ID} = $id",      // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            null   // The sort order
        )

        var task: Task? = null
        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DESCRIPTION))
            val categoryId = cursor.getInt(cursor.getColumnIndex(Task.COLUMN_NAME_CATEGORY))
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_DONE)) == 1

            Log.i("DATABASE", categoryId.toString())
            val category = CategoryDAO(context).find(categoryId)!!
            task = Task(id, name, description, category, done)
        }
        cursor.close()
        db.close()
        return task
    }

    @SuppressLint("Range")
    fun findAll(): List<Task> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(BaseColumns._ID, Task.COLUMN_NAME_TITLE, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_CATEGORY, COLUMN_NAME_DONE)

        val cursor = db.query(
            Task.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            null,                            // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            "$COLUMN_NAME_DONE ASC"                           // The sort order
        )

        var tasks = mutableListOf<Task>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DESCRIPTION))
            val categoryId = cursor.getInt(cursor.getColumnIndex(Task.COLUMN_NAME_CATEGORY))
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_DONE)) == 1

            val category = CategoryDAO(context).find(categoryId)!!
            val task = Task(id, name, description, category, done)
            tasks.add(task)
        }
        cursor.close()
        db.close()
        return tasks
    }
}