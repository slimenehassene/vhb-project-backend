package com.SoftwareprojektBackend.googlewalletpassbackend.service;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.EventTicket;

import java.util.List;

public interface EventTicketService {

    public String createPass(EventTicket eventTicket);

    public String updatePass(EventTicket eventTicket);

    public EventTicket getEventTicket(Long eventTicketId);

    public List<EventTicket> getAllEventTicket();

}
