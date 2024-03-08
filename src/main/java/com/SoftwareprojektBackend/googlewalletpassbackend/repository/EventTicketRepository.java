package com.SoftwareprojektBackend.googlewalletpassbackend.repository;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.EventTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTicketRepository extends JpaRepository<EventTicket, Long> {
}
