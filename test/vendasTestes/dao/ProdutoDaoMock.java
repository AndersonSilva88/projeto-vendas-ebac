package vendasTestes.dao;

import main.aplicacao.dao.IProdutoDAO;
import main.aplicacao.domain.Produto;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.MaisDeUmRegistroException;
import main.aplicacao.exceptions.TableException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;

import java.sql.SQLException;
import java.util.Collection;

public class ProdutoDaoMock implements IProdutoDAO {
    @Override
    public Boolean cadastrar(Produto entity) throws TipoChaveNaoEncontradaException, DAOException, SQLException {
        return null;
    }

    @Override
    public void excluir(String valor) throws DAOException {

    }

    @Override
    public void alterar(Produto entity) throws TipoChaveNaoEncontradaException, DAOException {

    }

    @Override
    public Produto consultar(String valor) throws MaisDeUmRegistroException, TableException, DAOException {
        Produto produto = new Produto();
        produto.setCodigo(valor);
        return produto;
    }

    @Override
    public Collection<Produto> buscarTodos() throws DAOException {
        return null;
    }
}
