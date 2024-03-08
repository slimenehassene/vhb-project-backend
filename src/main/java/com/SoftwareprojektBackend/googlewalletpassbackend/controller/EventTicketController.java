package com.SoftwareprojektBackend.googlewalletpassbackend.controller;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.EventTicket;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.EventTicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventTicket")
public class EventTicketController {

    EventTicketService eventTicketService;

    public EventTicketController(EventTicketService eventTicketService) {
        this.eventTicketService = eventTicketService;
    }


    @PostMapping
    public String postEventTicket(@RequestBody EventTicket eventTicket) {
        return eventTicketService.createPass(eventTicket);
    }

    @GetMapping("{id}")
    public EventTicket getBordkarte(@PathVariable("id") Long id) {
        return eventTicketService.getEventTicket(id);
    }

    @GetMapping()
    public List<EventTicket> getAllBordkarten() {
        return eventTicketService.getAllEventTicket();
    }

    @PutMapping
    public String updateEventTicket(@RequestBody EventTicket eventTicket) {
        return eventTicketService.updatePass(eventTicket);
    }

}
