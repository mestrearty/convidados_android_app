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
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.nome)
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
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.nome)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ? "
            val args = arrayOf(guest.id.toString())
            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)

            return true
        } catch (error: Exception) {
            return false
        }

    }  fun delete(id: Int): Boolean {
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

}