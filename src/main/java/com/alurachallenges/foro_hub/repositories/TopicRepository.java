package com.alurachallenges.foro_hub.repositories;

import com.alurachallenges.foro_hub.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicRepository extends JpaRepository <Topic, Long>
{
    // Busca todos los topicos activos
    Page<Topic> findByStatusTrue(Pageable pageable);
}
