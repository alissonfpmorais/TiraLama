package br.com.alissonfpmorais.tiralama.main.listtransaction

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alissonfpmorais.tiralama.R
import br.com.alissonfpmorais.tiralama.common.data.local.DatabaseHolder
import br.com.alissonfpmorais.tiralama.common.data.local.entity.Transaction
import br.com.alissonfpmorais.tiralama.common.toCurrency
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_transaction.*

class TransactionsAdapter(val context: Context) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {
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
        val value = transaction.value.toCurrency()

        holder.transactionColor.setBackgroundColor(color)
        holder.transactionName.text = name
        holder.transactionDate.text = date
        holder.transactionValue.text = value

        holder.deleteBt.setOnClickListener {
            AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme_ConfirmationDialog))
                    .setMessage(R.string.confirm_delete_msg)
                    .setCancelable(true)
                    .setPositiveButton(R.string.yes_msg) { _, _ ->
                        DatabaseHolder.getSingleInstance(context)
                                .subscribe { database -> database.transactionDao().deleteTransaction(transaction) }
                                .dispose()
                    }
                    .setNegativeButton(R.string.no_msg) { _, _ -> }
                    .create()
                    .show()
        }
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}