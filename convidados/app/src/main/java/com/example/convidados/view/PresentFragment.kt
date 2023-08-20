package com.example.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.constants.DataBaseConstants
import com.example.convidados.databinding.FragmentPresentBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.IOnGuestListener
import com.example.convidados.viewmodel.GuestsViewModel

class PresentFragment : Fragment() {

    private var _binding: FragmentPresentBinding? = null

    private val binding get() = _binding!!
    private lateinit var guestsViewModel: GuestsViewModel
    private val guestsAdapter = GuestsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        guestsViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)

        _binding = FragmentPresentBinding.inflate(inflater, container, false)
        // Layout
        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)


        // Adapter
        binding.recyclerGuests.adapter = guestsAdapter

        // Listener
        val listener = object : IOnGuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int, name: String) {
                Toast.makeText(context, "Convidado $name de ID $id removido", Toast.LENGTH_SHORT)
                    .show()
                guestsViewModel.delete(id)
                guestsViewModel.getPresent()
            }

        }

        guestsAdapter.attachListener(listener)

        guestsViewModel.getPresent()

        observe()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        guestsViewModel.getPresent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observe() {
        guestsViewModel.guests.observe(viewLifecycleOwner) {
            guestsAdapter.updatedGuests(it)
        }
    }
}