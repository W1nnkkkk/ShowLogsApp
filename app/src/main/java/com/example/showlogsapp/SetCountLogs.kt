package com.example.showlogsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.showlogsapp.databinding.FragmentSetCountLogsBinding


class SetCountLogs : Fragment() {
    lateinit var binding: FragmentSetCountLogsBinding
    val requester: Requester = Requester("http://31.128.41.7:7979/logs")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetCountLogsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button2.setOnClickListener {
            onSendButtonClick(it)
        }
    }

    fun onSendButtonClick(view: View) {
        if (binding.textInputEditText.text!!.isEmpty()) {
            Toast.makeText(this.context, R.string.toastEmptyText, Toast.LENGTH_SHORT).show()
        }
        else {
            Adapters.logsAdapter.logList.clear()
            if (Requester.isInternetConnect(view.context)) {
                Adapters.logsAdapter.updateLogList(
                    requester.getLastNLogsFromServer(
                        binding.textInputEditText.text.toString().toInt()
                    )
                )
                binding.textInputEditText.text!!.clear()

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, LogsList.newInstance())?.commit()
            }
            else {
                Toast.makeText(view.context, R.string.noInternetText, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SetCountLogs()
    }
}