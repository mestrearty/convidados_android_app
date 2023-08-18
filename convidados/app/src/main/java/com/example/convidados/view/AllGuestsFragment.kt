package com.example.convidados.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestsAdapter
import com.example.convidados.view.listener.IOnGuestListener
import com.example.convidados.viewmodel.AllGuestsViewModel

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null

    private val binding get() = _binding!!
    private lateinit var allGuestsViewModel: AllGuestsViewModel
    private val guestsAdapter = GuestsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        allGuestsViewModel =
            ViewModelProvider(this).get(AllGuestsViewModel::class.java)

        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Layout
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context)

        // Adapter
        binding.recyclerAllGuests.adapter = guestsAdapter

        // Listener
        val listener = object : IOnGuestListener{
            override fun onClick(id: Int) {
                Toast.makeText(context,"Id do convidado: $id", Toast.LENGTH_SHORT).show()
            }

            override fun onDelete(id: Int,name: String) {
                Toast.makeText(context,"Convidado $name de ID $id removido",Toast.LENGTH_SHORT).show()
                allGuestsViewModel.delete(id)
                allGuestsViewModel.getAll()
            }

        }

        guestsAdapter.attachListener(listener)

        allGuestsViewModel.getAll()

        observe()
        return root
    }

    override fun onResume() {
        super.onResume()
        allGuestsViewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observe() {
        allGuestsViewModel.guests.observe(viewLifecycleOwner) {
            guestsAdapter.updatedGuests(it)
        }
    }
}