package br.com.alissonfpmorais.tiralama.main.help

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alissonfpmorais.tiralama.R
import kotlinx.android.synthetic.main.fragment_help.*

class HelpFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = HelpAdapter()
        adapter.data = getQuestions()

        helpList.adapter = adapter
        helpList.setHasFixedSize(true)
        helpList.layoutManager = LinearLayoutManager(activity)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getQuestions(): List<ItemHelp> = listOf(
            ItemHelp("O que são os valores na tela de transações?",
                    "Os valores representados são:\n" +
                            "- Em verde todas as entradas de dinheiro;\n" +
                            "- Em vermelho todas as saídas de dinheiro;\n" +
                            "- Ao centro o saldo final, resultado das entradas e saídas."),
            ItemHelp("Se der logout minhas informações serão perdidas?",
                    "Não! Todas as informações são mantidas mesmo que você dê logout, " +
                            "saia do aplicativo, feche o aplicativo, reinicie ou desligue o aparelho."),
            ItemHelp("As transações inseridas são vistas por outros usuários?",
                    "Não! Cada conta de usuário possui sua própria lista de transações. " +
                            "As alterações em uma conta não interferem em nada em outra conta, " +
                            "além disto um usuário não tem acesso as transações realizadas por outro usuário."),
            ItemHelp("Como insiro uma entrada?",
                    "Basta seguir os passos:\n" +
                            "- Na tela de transações, clique no botão '+';\n" +
                            "- Adicione um nome para sua transação;\n" +
                            "- Adicione um valor para sua transação;\n" +
                            "- Clique em salvar."),
            ItemHelp("Como insiro uma saída?",
                    "Basta seguir os passos:\n" +
                            "- Na tela de transações, clique no botão '+';\n" +
                            "- Adicione um nome para sua transação;\n" +
                            "- Clique no símbolo de negativo e, em seguida, adicione um valor para sua transação.\n" +
                            "- Clique em salvar.")
    )
}
