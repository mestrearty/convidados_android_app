package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.model.GuestModel

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

    fun insert(guest: GuestModel) {
        val db = guestDataBase.writableDatabase

        //Virify the Boolean "presence" value and transform in Int to save in db
        val presence = if (guest.presence) 1 else 0

        //creating a "ContentValue" val to save on db like a object instead of make a SQL text query
        val values = ContentValues()
        values.put("name", guest.nome)
        values.put("presence", presence)

        db.insert("Guest", null, values)
    }

    fun update() {

    }

}