package br.com.henrique.services;

import br.com.henrique.domain.Categoria;
import br.com.henrique.repositories.CategoriaRepository;
import br.com.henrique.services.exceptions.DataIntegrityException;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria find(Long id) {
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+", Tipo: "+Categoria.class.getName()));
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria insert(Categoria obj){
        obj.setId(null);
        return categoriaRepository.save(obj);
    }

    public Categoria update(Categoria obj){
        Categoria newObj = find(obj.getId());
        if(newObj == null){
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: "+newObj.getId()+", Tipo: "+Categoria.class.getName());
        }
        BeanUtils.copyProperties(obj, newObj,"id");
        return categoriaRepository.save(newObj);
    }

    public void delete(Long id){
        Categoria obj = find(id);
        if(obj == null){
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: "+obj.getId()+", Tipo: "+Categoria.class.getName());
        }
        try{
            categoriaRepository.delete(obj);
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
        }
    }




}
