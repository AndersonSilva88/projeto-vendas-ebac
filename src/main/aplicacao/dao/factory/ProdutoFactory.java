package main.aplicacao.dao.factory;

import main.aplicacao.domain.Produto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoFactory {

    public static Produto convert(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getLong("ID_PRODUTO"));
        produto.setCodigo(rs.getString("CODIGO"));
        produto.setNome(rs.getString("NOME"));
        produto.setDescricao(rs.getString("DESCRICAO"));
        produto.setPeso(rs.getDouble("PESO"));
        produto.setValor(rs.getBigDecimal("VALOR"));
        return produto;
    }
}
