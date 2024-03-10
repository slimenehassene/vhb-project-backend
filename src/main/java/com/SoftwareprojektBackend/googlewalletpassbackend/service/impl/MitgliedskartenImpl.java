package com.SoftwareprojektBackend.googlewalletpassbackend.service.impl;

import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.CreateMitgliedskarten;
import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.UpdateMitgliedskarten;
import com.SoftwareprojektBackend.googlewalletpassbackend.model.Mitgliedskarten;
import com.SoftwareprojektBackend.googlewalletpassbackend.repository.MitgliedskartenRepository;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.MitgliedskartenService;
import com.google.api.services.walletobjects.model.LoyaltyClass;
import com.google.api.services.walletobjects.model.LoyaltyObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MitgliedskartenImpl implements MitgliedskartenService {


    private MitgliedskartenRepository mitgliedskartenRepository;


    String classSuffix = "class_";
    String objectSuffix = "object_";

    private String jwt;

    String updateMessage;


    public MitgliedskartenImpl(MitgliedskartenRepository mitgliedskartenRepository) {
        this.mitgliedskartenRepository = mitgliedskartenRepository;
    }

    @Override
    @Async
    public String createPass(Mitgliedskarten mitgliedskarten) {


        CreateMitgliedskarten createMitgliedskarten = new CreateMitgliedskarten(classSuffix + mitgliedskarten.getId(), objectSuffix + mitgliedskarten.getId(), mitgliedskarten);
        try {
            createMitgliedskarten.createClass(new CreateMitgliedskarten.CallbackClass() {
                @Override
                public void callback(LoyaltyClass newClass) {

                    try {
                        createMitgliedskarten.createObject(new CreateMitgliedskarten.CallbackObject() {
                            @Override
                            public void callback(LoyaltyObject newObject) {
                                System.out.println("Pass-Objekt erstellt mit ID: " + newObject.getId());
                                jwt = createMitgliedskarten.createJWT(newClass, newObject);
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
        mitgliedskartenRepository.save(mitgliedskarten);

        return jwt;


    }

    @Override
    @Async
    public String updatePass(Mitgliedskarten mitgliedskarten) {
        UpdateMitgliedskarten updateMitgliedskarten;
        try {
            updateMitgliedskarten = new UpdateMitgliedskarten(mitgliedskarten);
            updateMitgliedskarten.updateClass(classSuffix + mitgliedskarten.getId().toString());
            updateMitgliedskarten.updateObject(objectSuffix + mitgliedskarten.getId().toString(), new UpdateMitgliedskarten.CallbackUpdateObject() {
                @Override
                public void callback(String response) {
                    updateMessage = response;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        mitgliedskartenRepository.save(mitgliedskarten);
        return updateMessage;
    }


    @Override
    @Async
    public Mitgliedskarten getMitgliedskarte(Long mitgliedskartenId) {
        return mitgliedskartenRepository.findById(mitgliedskartenId).get();
    }

    @Override
    @Async
    public List<Mitgliedskarten> getAllMitgliedskarten() {
        return mitgliedskartenRepository.findAll();
    }
}
