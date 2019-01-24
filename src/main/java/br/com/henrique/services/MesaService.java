package br.com.henrique.services;

import br.com.henrique.domain.Mesa;
import br.com.henrique.repositories.MesaRepository;
import br.com.henrique.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        return mesaRepository.save(obj);
    }

}
