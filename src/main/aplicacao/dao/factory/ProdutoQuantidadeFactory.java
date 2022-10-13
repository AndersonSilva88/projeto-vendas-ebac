package main.aplicacao.dao.factory;

import main.aplicacao.domain.Produto;
import main.aplicacao.domain.ProdutoQuantidade;

import javax.swing.plaf.PanelUI;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoQuantidadeFactory {

    public static ProdutoQuantidade convert(ResultSet rs) throws SQLException {
        Produto produto = ProdutoFactory.convert(rs);
        ProdutoQuantidade produtoQuantidade = new ProdutoQuantidade();

        produtoQuantidade.setProduto(produto);
        produtoQuantidade.setId(rs.getLong("ID"));
        produtoQuantidade.setQuantidade(rs.getInt("QUANTIDADE"));
        produtoQuantidade.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));

        return produtoQuantidade;
    }
}
