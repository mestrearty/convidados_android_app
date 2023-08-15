package com.example.convidados.ui.guestform

class GuestRepository private constructor(){
    //Singleton
    companion object{
        private lateinit var repository: GuestRepository

        fun getInstance(): GuestRepository{
            if(!::repository.isInitialized){
                repository= GuestRepository()
            }
            return repository
        }
    }
}