package com.example.titinmoney.providers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.titinmoney.models.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import org.threeten.bp.LocalDateTime

class TransactionProvider {

    companion object {
        private val db = Firebase.firestore

        suspend fun getTransactions(): List<Transaction> {
            val transactions = mutableListOf<Transaction>()

            val a = db.collection("transactions").get().await()

            for (document in a) {
                val id = document.data["id"].toString().toInt()
                val description = document.data["description"].toString()
                val quantity = document.data["quantity"].toString().toDouble()
                val type = document.data["type"].toString().toInt()

                val date = document.data["date"] as HashMap<*, *>
                val month = date["monthValue"].toString().toInt()
                val year = date["year"].toString().toInt()
                val day = date["dayOfMonth"].toString().toInt()
                val hour = date["hour"].toString().toInt()
                val minute = date["minute"].toString().toInt()

                val dateResult = LocalDateTime.of(year, month, day, hour, minute)

                val transaction = Transaction(id, dateResult, description, quantity, type)

                transactions.add(transaction)
            }
            return transactions
        }

        fun create(transaction: Transaction) {
            val transactionToFireBase = hashMapOf(
                "id" to transaction.id,
                "date" to transaction.date,
                "description" to transaction.description,
                "quantity" to transaction.quantity,
                "type" to transaction.type
            )

            db.collection("transactions")
                .add(transactionToFireBase)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }
}