package br.com.alissonfpmorais.tiralama.main.components.listtransaction

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_transaction.*
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {
    var data: List<Transaction> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = data[position]

        val color = if (transaction.value >= 0) Color.GREEN else Color.RED
        val name = transaction.name
        val date = transaction.date.toString()
        val value = BigDecimal(transaction.value.toDouble())

        holder.transactionColor.setBackgroundColor(color)
        holder.transactionName.text = name
        holder.transactionDate.text = date
        holder.transactionValue.text = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(value)
        holder.containerView.deleteBt.setOnClickListener {
            Log.d("truta", "containerView funciona!")
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}