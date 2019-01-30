package br.com.henrique.services;

import br.com.henrique.domain.Produto;
import br.com.henrique.repositories.ProdutoRepository;
import br.com.henrique.services.exceptions.DataIntegrityException;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Page<Produto> findAll(String nome, Integer page,Integer linesPorPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPorPage,Sort.Direction.valueOf(direction), orderBy);
        return produtoRepository.findByNome("%"+nome+"%", pageRequest);
    }

    public Page<Produto> findByCategoria(Long categoria, String nome, Integer page,Integer linesPorPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPorPage, Sort.Direction.valueOf(direction), orderBy);
        return produtoRepository.findByNomeAndCategoria(categoria,"%"+nome+"%", pageRequest);
    }

    public Produto find(Long id) {
        Optional<Produto> obj = produtoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+", Tipo: "+Produto.class.getName()));
    }

    public Produto insert(Produto obj){
        obj.setId(null);
        return produtoRepository.save(obj);
    }

    public Produto update(Produto obj){
        Produto newObj = find(obj.getId());
        if(newObj == null){
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: "+newObj.getId()+", Tipo: "+Produto.class.getName());
        }
        BeanUtils.copyProperties(obj, newObj,"id");
        return produtoRepository.save(newObj);
    }

    public void delete(Long id){
        Produto obj = find(id);
        if(obj == null){
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: "+obj.getId()+", Tipo: "+Produto.class.getName());
        }
        try{
            produtoRepository.delete(obj);
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir um produto que possui itens pedidos");
        }
    }




}
