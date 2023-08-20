package com.example.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.convidados.R
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.ActivityGuestFormBinding
import com.example.convidados.model.GuestModel
import com.example.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var guestFormViewModel: GuestFormViewModel
    var guestId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        guestFormViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)
        binding.buttonSave.setOnClickListener(this)

        binding.radioPresent.isChecked = true


        observe()
        //loading data case was called by a edit view
        loadData()
    }

    override fun onClick(view: View) {
        val bundle = intent.extras
        if (view.id == R.id.button_save) {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked

            if (bundle != null) {
                guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            }
            if (name != "") {
                guestFormViewModel.save(GuestModel(guestId, name, presence))
            }
        }

    }

    fun observe() {
        guestFormViewModel.guest.observe(this, Observer {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else
                binding.radioAbsent.isChecked = true
        })

        guestFormViewModel.saveGuest.observe(this, Observer {
            if(it){
                Toast.makeText(applicationContext, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            val guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            guestFormViewModel.get(guestId)
        }
//forma burra sem o observer:
//        if (bundle != null) {
//            val guestIdFromBundle = bundle.getInt(DataBaseConstants.GUEST.ID)
//            val guestData = guestFormViewModel.get(guestIdFromBundle)
//
//            if (guestData != null) {
//                val guestName = guestData.name
//                val guestPresence = guestData.presence
//                binding.editName.setText(guestName)
//                if (!guestPresence)
//                    binding.radioAbsent.isChecked = true
//            } else {
//                Toast.makeText(
//                    view.context,
//                    "Falha no servidor. Ternte novamente mais tarde",
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//
//        }
    }
}