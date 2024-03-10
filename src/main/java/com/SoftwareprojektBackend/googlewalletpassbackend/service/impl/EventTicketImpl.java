package com.SoftwareprojektBackend.googlewalletpassbackend.service.impl;

import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.CreateEventTicket;
import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.UpdateEventTicket;
import com.SoftwareprojektBackend.googlewalletpassbackend.model.EventTicket;
import com.SoftwareprojektBackend.googlewalletpassbackend.repository.EventTicketRepository;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.EventTicketService;
import com.google.api.services.walletobjects.model.EventTicketClass;
import com.google.api.services.walletobjects.model.EventTicketObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EventTicketImpl implements EventTicketService {

    private EventTicketRepository eventTicketRepository;


    String classSuffix = "class_";

    private String jwt;

    String updateMessage;

    public EventTicketImpl(EventTicketRepository eventTicketRepository) {
        this.eventTicketRepository = eventTicketRepository;
    }

    @Override
    public String createPass(EventTicket eventTicket) {


        CreateEventTicket createEventTicket = new CreateEventTicket(classSuffix + eventTicket.getId(), eventTicket.getId().toString(), eventTicket);
        try {
            createEventTicket.createClass(new CreateEventTicket.CallbackClass() {
                @Override
                public void callback(EventTicketClass newClass) {

                    try {
                        createEventTicket.createObject(new CreateEventTicket.CallbackObject() {
                            @Override
                            public void callback(EventTicketObject newObject) {
                                System.out.println("Pass-Objekt erstellt mit ID: " + newObject.getId());
                                jwt = createEventTicket.createJWT(newClass, newObject);
                                System.out.println("JWT erstellt: " + jwt);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        eventTicketRepository.save(eventTicket);

        return jwt;


    }

    @Override
    public String updatePass(EventTicket eventTicket) {
        UpdateEventTicket updateEventTicket;
        try {
            updateEventTicket = new UpdateEventTicket(eventTicket);
            updateEventTicket.updateClass(classSuffix + eventTicket.getId().toString());
            updateEventTicket.updateObject(eventTicket.getId().toString(), new UpdateEventTicket.CallbackUpdateObject() {
                @Override
                public void callback(String response) {
                    updateMessage = response;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        eventTicketRepository.save(eventTicket);
        return updateMessage;
    }


    @Override
    public EventTicket getEventTicket(Long eventTicketId) {
        return eventTicketRepository.findById(eventTicketId).get();
    }

    @Override
    public List<EventTicket> getAllEventTicket() {
        return eventTicketRepository.findAll();
    }
}
