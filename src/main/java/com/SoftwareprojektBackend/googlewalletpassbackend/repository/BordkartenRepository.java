package com.SoftwareprojektBackend.googlewalletpassbackend.repository;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Bordkarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BordkartenRepository extends JpaRepository<Bordkarte, Long> {


}
