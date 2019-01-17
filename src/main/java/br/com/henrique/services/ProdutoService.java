package br.com.henrique.services;

import br.com.henrique.DTO.ProdutoDTO;
import br.com.henrique.domain.Produto;
import br.com.henrique.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }



}
