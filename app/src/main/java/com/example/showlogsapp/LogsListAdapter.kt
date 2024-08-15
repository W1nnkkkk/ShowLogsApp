package com.example.showlogsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.showlogsapp.databinding.LogDelegateBinding

class LogsListAdapter(var logList: ArrayList<Log>) : RecyclerView.Adapter<LogsListAdapter.LogsHolder>() {

    class LogsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = LogDelegateBinding.bind(view)
        fun bind(log: Log) = with(binding) {

            logText.text = log.text
            logTime.text = log.date + ' ' + log.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.log_delegate, parent, false)

        return LogsHolder(view)
    }

    override fun onBindViewHolder(holder: LogsHolder, position: Int) {
        holder.bind(logList[position])
    }

    override fun getItemCount(): Int = logList.size

    public fun updateLogList(logList: ArrayList<Log>) {
        notifyDataSetChanged()
        this.logList = logList
    }

    public fun addLog(log: Log) {
        notifyDataSetChanged()
        this.logList.add(log)
    }
}