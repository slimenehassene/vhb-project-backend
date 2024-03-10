package com.SoftwareprojektBackend.googlewalletpassbackend.service.impl;

import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.CreateBordkarten;
import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.UpdateBordkarten;
import com.SoftwareprojektBackend.googlewalletpassbackend.model.Bordkarte;
import com.SoftwareprojektBackend.googlewalletpassbackend.repository.BordkartenRepository;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.BordkarteService;
import com.google.api.services.walletobjects.model.FlightClass;
import com.google.api.services.walletobjects.model.FlightObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BordkarteImpl implements BordkarteService {


    private BordkartenRepository bordkartenRepository;


    String classSuffix = "class_";
    String objectSuffix = "object_";

    private String jwt;

    String updateMessage;

    public BordkarteImpl(BordkartenRepository bordkartenRepository) {
        this.bordkartenRepository = bordkartenRepository;
    }


    @Override
    @Async
    public String createPass(Bordkarte bordkarte) {

        CreateBordkarten createBordkarten = new CreateBordkarten(classSuffix + bordkarte.getId().toString(), objectSuffix + bordkarte.getId().toString(), bordkarte);
        try {
            createBordkarten.createClass(new CreateBordkarten.CallbackClass() {
                @Override
                public void callback(FlightClass newClass) {

                    try {
                        createBordkarten.createObject(new CreateBordkarten.CallbackObject() {
                            @Override
                            public void callback(FlightObject newObject) {
                                System.out.println("Pass-Objekt erstellt mit ID: " + newObject.getId());
                                jwt = createBordkarten.createJWT(newClass, newObject);
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
        bordkartenRepository.save(bordkarte);

        return jwt;


    }

    @Override
    @Async
    public String updatePass(Bordkarte bordkarte) {

        UpdateBordkarten updateBordkarten;
        try {
            updateBordkarten = new UpdateBordkarten(bordkarte);
            updateBordkarten.updateClass(classSuffix + bordkarte.getId().toString());
            updateBordkarten.updateObject(objectSuffix + bordkarte.getId().toString(), new UpdateBordkarten.CallbackUpdateObject() {
                @Override
                public void callback(String response) {
                    updateMessage = response;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        bordkartenRepository.save(bordkarte);
        return updateMessage;

    }


    @Override
    @Async
    public Bordkarte getBordkarte(Long bordkarteId) {
        return bordkartenRepository.findById(bordkarteId).get();
    }

    @Override
    @Async
    public List<Bordkarte> getAllBordkarten() {
        return bordkartenRepository.findAll();
    }
}
