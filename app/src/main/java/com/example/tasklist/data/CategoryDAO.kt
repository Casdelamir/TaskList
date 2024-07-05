package com.example.tasklist.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.tasklist.utils.DatabaseManager

class CategoryDAO(context: Context) {
    private var databaseManager: DatabaseManager = DatabaseManager(context)

    fun insert(category: Category): Category {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Category.COLUMN_NAME_NAME, category.name)

        val newRowId = db.insert(Category.TABLE_NAME, null, values)
        Log.i("DATABASE", "New record id: $newRowId")

        db.close()

        category.id = newRowId.toInt()
        return category
    }

    fun update(category: Category) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Category.COLUMN_NAME_NAME, category.name)

        val updatedRows = db.update(Category.TABLE_NAME, values, "${BaseColumns._ID} = ${category.id}", null)
        Log.i("DATABASE", "Updated records: $updatedRows")

        db.close()
    }

    fun delete(category: Category) {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(Category.TABLE_NAME, "${BaseColumns._ID} = ${category.id}", null)
        Log.i("DATABASE", "Deleted rows: $deletedRows")

        db.close()
    }


    @SuppressLint("Range")
    fun find(id: Int): Category? {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Category.TABLE_NAME,                         // The table to query
            arrayOf(BaseColumns._ID, Category.COLUMN_NAME_NAME),       // The array of columns to return (pass null to get all)
            "${BaseColumns._ID} = $id",  // The columns for the WHERE clause
            null,                    // The values for the WHERE clause
            null,                        // don't group the rows
            null,                         // don't filter by row groups
            null                         // The sort order
        )

        var category: Category? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME_NAME))
            //Log.i("DATABASE", "$id -> Category: $categoryName, Done: $done")

            category = Category(id, name)
        }

        cursor.close()
        db.close()

        return category
    }

    @SuppressLint("Range")
    fun findByName(categoryName: String): Category? {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Category.TABLE_NAME,                         // The table to query
            arrayOf(BaseColumns._ID, Category.COLUMN_NAME_NAME),       // The array of columns to return (pass null to get all)
            "${Category.COLUMN_NAME_NAME} = ?",  // The columns for the WHERE clause
            arrayOf(categoryName),                    // The values for the WHERE clause
            null,                        // don't group the rows
            null,                         // don't filter by row groups
            null                         // The sort order
        )

        var category: Category? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME_NAME))
            //Log.i("DATABASE", "$id -> Category: $name")

            category = Category(id, name)
        }

        cursor.close()
        db.close()

        return category
    }


    @SuppressLint("Range")
    fun findAll(): List<Category> {
        val db = databaseManager.writableDatabase

        val cursor = db.query(
            Category.TABLE_NAME,                 // The table to query
            arrayOf(BaseColumns._ID, Category.COLUMN_NAME_NAME),     // The array of columns to return (pass null to get all)
            null,                // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val list: MutableList<Category> = mutableListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME_NAME))
            //Log.i("DATABASE", "$id -> Category: $categoryName, Done: $done")

            val category = Category(id, name)
            list.add(category)
        }

        cursor.close()
        db.close()

        return list
    }
}