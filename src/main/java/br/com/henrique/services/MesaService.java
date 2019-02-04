package br.com.henrique.services;

import br.com.henrique.domain.Mesa;
import br.com.henrique.domain.enums.StatusMesa;
import br.com.henrique.repositories.MesaRepository;
import br.com.henrique.services.exceptions.DataIntegrityException;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    @Transactional(readOnly = true)
    public List<Mesa> findAll(){
        return mesaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mesa find(Long id) {
        Optional<Mesa> obj = mesaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: "+id+", Tipo: "+Mesa.class.getName()));
    }

    public Mesa insert(Mesa obj){
        obj.setId(null);
        obj.setStatus(StatusMesa.DISPONIVEL);
        return mesaRepository.save(obj);
    }

    public Mesa update(Mesa obj){
        Mesa newObj = find(obj.getId());
        BeanUtils.copyProperties(obj, newObj,"id");
        return mesaRepository.save(newObj);
    }

    public void delete(Long id){
        Mesa obj = find(id);
        if(obj == null){
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: "+id+", Tipo: "+Mesa.class.getName());
        }
        try{
            mesaRepository.delete(obj);
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel mesa com pedidos vinculados");
        }
    }

    public void updateStatus(Integer status, Long id){
         mesaRepository.updateStatus(status,id);
    }

}
