package br.com.henrique.repositories;

import br.com.henrique.domain.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Mesa m SET m.status=?1 WHERE m.id=?2")
    void updateStatus(Integer status, Long id);
}
