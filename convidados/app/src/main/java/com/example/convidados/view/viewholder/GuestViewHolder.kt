package com.example.convidados.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel

class GuestViewHolder(private val rowGuestBinding: RowGuestBinding) : RecyclerView.ViewHolder(rowGuestBinding.root) {
    fun bind(guest: GuestModel) {
       rowGuestBinding.textName.text = guest.name
    }
}