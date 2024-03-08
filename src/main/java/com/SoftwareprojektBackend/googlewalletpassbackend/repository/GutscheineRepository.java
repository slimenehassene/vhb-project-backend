package com.SoftwareprojektBackend.googlewalletpassbackend.repository;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Gutscheine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GutscheineRepository extends JpaRepository<Gutscheine, Long> {
}
