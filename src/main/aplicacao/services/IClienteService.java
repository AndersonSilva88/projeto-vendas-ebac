package main.aplicacao.services;

import main.aplicacao.domain.Cliente;
import main.aplicacao.exceptions.DAOException;
import main.aplicacao.exceptions.TipoChaveNaoEncontradaException;
import main.aplicacao.services.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {
   // Boolean cadastrar(Cliente cliente) throws TipoChaveNaoEncontradaException;

    Cliente buscarPorCpf(Long cpf) throws DAOException;

   // Cliente buscarPorCPF(Long cpf);

    // void excluir(Long cpf) throws DAOException;

   // void alterar(Cliente cliente) throws TipoChaveNaoEncontradaException, DAOException;
}
