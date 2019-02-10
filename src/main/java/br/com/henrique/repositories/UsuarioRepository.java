package br.com.henrique.repositories;

import br.com.henrique.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Transactional(readOnly = true)
    Usuario findByEmail(String email);

    @Transactional(readOnly=true)
    @Query("SELECT DISTINCT u FROM Usuario u WHERE UPPER(u.email) LIKE UPPER(?1)")
    List<Usuario> findLikeEmail(String email);

}
