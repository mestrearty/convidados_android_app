package com.example.convidados.view.viewholder

import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.R
import com.example.convidados.databinding.RowGuestBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.view.listener.IOnGuestListener

class GuestViewHolder(
    private val rowGuestBinding: RowGuestBinding,
    private val listener: IOnGuestListener
) : RecyclerView.ViewHolder(rowGuestBinding.root) {
    fun bind(guest: GuestModel) {
        rowGuestBinding.textName.text = guest.name

        rowGuestBinding.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        rowGuestBinding.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context).setTitle("Remoção de Convidado")
                .setMessage("Tem certeza que deseja remover?")
                .setPositiveButton(
                    "Oh YEAH!"
                ) { dialogInterface, which -> listener.onDelete(guest.id,guest.name)}
                .setNegativeButton(
                    "Não!"
                ) { dialogInterface, which -> null }
                .create().show()

            true
        }
    }
}