package com.example.titinmoney

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.titinmoney.databinding.ActivityMainBinding
import com.example.titinmoney.models.Transaction
import com.example.titinmoney.providers.TransactionProvider
import com.example.titinmoney.reciclerview.ReciclerViewAdapter
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidThreeTen.init(this);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.btnBills.setOnClickListener {
            val intent = Intent(this, CreateBillActivity::class.java)
            startActivity(intent)
        }

        binding.btnIncome.setOnClickListener {
            val intent = Intent(this, CreateIncominActivity::class.java)
            startActivity(intent)
        }
        binding.swiperefresh.setOnRefreshListener {
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        lifecycleScope.launch {
            binding.swiperefresh.isRefreshing = true

            val transactions = withContext(Dispatchers.IO) {
                TransactionProvider.getTransactions()
            }

            binding.recyclerView.adapter = ReciclerViewAdapter(transactions)
            binding.swiperefresh.isRefreshing = false
        }

        val linearLayoutManager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.addItemDecoration(decoration)
    }

    override fun onResume() {
        super.onResume()

        initRecyclerView()
    }
}