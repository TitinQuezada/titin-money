package com.example.titinmoney.reciclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.titinmoney.R
import com.example.titinmoney.models.Transaction

class ReciclerViewAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<ReciclerViewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReciclerViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return ReciclerViewViewHolder(layoutInflater.inflate(R.layout.item_transaction, parent , false))
    }

    override fun onBindViewHolder(holder: ReciclerViewViewHolder, position: Int) {
        val transaction = transactions[position]

        holder.render(transaction)
    }

    override fun getItemCount(): Int = transactions.size
}