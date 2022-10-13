package main.aplicacao.services;

import main.aplicacao.dao.IProdutoDAO;
import main.aplicacao.dao.generic.IGenericDAO;
import main.aplicacao.domain.Produto;
import main.aplicacao.services.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {


    public ProdutoService(IProdutoDAO dao) {
        super(dao);
    }
}
