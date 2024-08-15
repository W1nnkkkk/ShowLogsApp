package com.example.showlogsapp

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.showlogsapp.databinding.FragmentLogsListBinding
import java.sql.Time

class LogsList : Fragment() {
    lateinit var logsListBinding : FragmentLogsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logsListBinding = FragmentLogsListBinding.inflate(inflater)
        return logsListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init(view: View) {
        logsListBinding.apply {
            logsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            logsRecyclerView.adapter = Adapters.logsAdapter
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = LogsList()
    }
}