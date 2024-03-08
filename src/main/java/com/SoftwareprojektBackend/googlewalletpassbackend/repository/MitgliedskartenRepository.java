package com.SoftwareprojektBackend.googlewalletpassbackend.repository;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Mitgliedskarten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MitgliedskartenRepository extends JpaRepository<Mitgliedskarten, Long> {

}
