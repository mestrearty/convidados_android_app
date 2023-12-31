package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.model.GuestModel
import java.lang.Exception

class GuestRepository private constructor(context: Context) {
    private val guestDataBase = GuestDataBase(context)

    //Singleton
    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        try {
            val db = guestDataBase.writableDatabase

            //Virify the Boolean "presence" value and transform in Int to save in db
            val presence = if (guest.presence) 1 else 0

            //creating a "ContentValue" val to save on db like a object instead of make a SQL text query
            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            return true
        } catch (error: Exception) {
            return false
        }
    }

    fun update(guest: GuestModel): Boolean {
        try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ? "
            val args = arrayOf(guest.id.toString())
            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)

            return true
        } catch (error: Exception) {
            return false
        }

    }

    fun delete(id: Int): Boolean {
        try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ? "
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)

            return true
        } catch (error: Exception) {
            return false
        }
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null

        try {
            val db = guestDataBase.readableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ? "
            val selectionArgs = arrayOf(id.toString())

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )


            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count != 0) {
                while (cursor.moveToNext()) {
                    val idCursorIndex = cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)
                    val nameCursorIndex =
                        cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)
                    val presenceCursorIndex =
                        cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)

                    val id = cursor.getInt(idCursorIndex)
                    val name = cursor.getString(nameCursorIndex)
                    val presence = cursor.getInt(presenceCursorIndex)

                    guest = GuestModel(id, name, presence == 1)
                }
            }

            return guest
        } catch (error: Exception) {
            return null
        }
    }

    fun getAll(): List<GuestModel> {
        return getFromDatabaseMiddleware(null, null)
    }

    fun getPresent(): List<GuestModel> {

        val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
        val selectionArgs = arrayOf("1")
        return getFromDatabaseMiddleware(selection, selectionArgs)
    }

    fun getAbsent(): List<GuestModel> {

        val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
        val selectionArgs = arrayOf("0")

        return getFromDatabaseMiddleware(selection, selectionArgs)
    }

    private fun getFromDatabaseMiddleware(
        selection: String?,
        selectionArgs: Array<String>?
    ): List<GuestModel> {

        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase


            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
                )

            if (cursor != null && cursor.count != 0) {
                while (cursor.moveToNext()) {
                    val idCursorIndex = cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)
                    val nameCursorIndex =
                        cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)
                    val presenceCursorIndex =
                        cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)

                    val id = cursor.getInt(idCursorIndex)
                    val name = cursor.getString(nameCursorIndex)
                    val presence = cursor.getInt(presenceCursorIndex)

                    val guest = GuestModel(id, name, presence == 1)
                    list.add(guest)
                }
            }

            cursor.close()


            return list
        } catch (e: Exception) {
            return list
        }
    }

}