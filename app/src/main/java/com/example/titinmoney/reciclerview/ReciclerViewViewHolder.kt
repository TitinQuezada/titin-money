package com.example.titinmoney.reciclerview

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.titinmoney.R
import com.example.titinmoney.databinding.ItemTransactionBinding
import com.example.titinmoney.models.Transaction
import org.threeten.bp.format.DateTimeFormatter

class ReciclerViewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemTransactionBinding.bind(view)

    fun render(transaction: Transaction) {
        binding.txtDate.text = transaction.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

        setTransactionQuantity(transaction)

        binding.description.text = transaction.description
        binding.txtId.text = transaction.id.toString()
    }

    private fun setTransactionQuantity(transaction: Transaction) {
        when (transaction.type) {
            1 -> setText(transaction.quantity, "+", R.color.green)
            2 -> setText(transaction.quantity, "-", R.color.red)
        }
    }

    private fun setText(quantity: Double, operator: String, color: Int) {
        val text = "$operator $quantity"
        binding.txtQuantity.setTextColor(getColor(color))
        binding.txtQuantity.text = text
    }

    private fun getColor(color: Int): Int {
        return ContextCompat.getColor(
            view.context,
            color
        )
    }
}