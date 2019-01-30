package br.com.henrique.repositories;

import br.com.henrique.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Transactional
    @Query("SELECT e FROM Endereco e WHERE e.id=?1 AND e.usuario.id=?2")
    Optional<Endereco> findByIdAndUsuario(Long idEndereco, Long idUsuario);


}
