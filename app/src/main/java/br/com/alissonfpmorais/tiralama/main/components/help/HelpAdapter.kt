package br.com.alissonfpmorais.tiralama.main.components.help

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alissonfpmorais.tiralama.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_help.*

class HelpAdapter : RecyclerView.Adapter<HelpAdapter.ViewHolder>() {
    var data: List<ItemHelp> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_help, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.question.text = item.question
        holder.answer.text = item.answer
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}