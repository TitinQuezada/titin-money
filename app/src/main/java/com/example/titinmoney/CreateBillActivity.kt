package com.example.titinmoney

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.titinmoney.databinding.ActivityCreateBillBinding
import com.example.titinmoney.models.Transaction
import com.example.titinmoney.providers.TransactionProvider
import com.google.android.material.snackbar.Snackbar
import org.threeten.bp.LocalDateTime


class CreateBillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateBillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddBill.setOnClickListener {

            if (isValidForm()) createBill()
        }
    }

    private fun isValidForm(): Boolean {
        if (binding.txtDescription.isValid() && binding.txtQuantity.isValid()) {
            return true
        }

        return false
    }

    private fun createBill() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        val transaction = Transaction(
            (1000..9000).random(),
            LocalDateTime.now(),
            binding.txtDescription.getValue(),
            binding.txtQuantity.getValue().toDouble(),
            2
        )

        TransactionProvider.create(transaction)

        resetForm()

        Snackbar.make(binding.root, getString(R.string.bill_added), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.do_you_leave)) {
                finish()
            }.show()
    }

    private fun resetForm() {
        binding.txtDescription.clear()
        binding.txtQuantity.clear()
    }
}
