package com.SoftwareprojektBackend.googlewalletpassbackend.service.impl;

import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.CreateGutscheine;
import com.SoftwareprojektBackend.googlewalletpassbackend.managePass.UpdateGutscheine;
import com.SoftwareprojektBackend.googlewalletpassbackend.model.Gutscheine;
import com.SoftwareprojektBackend.googlewalletpassbackend.repository.GutscheineRepository;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.GutscheineService;
import com.google.api.services.walletobjects.model.OfferClass;
import com.google.api.services.walletobjects.model.OfferObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GutscheineImpl implements GutscheineService {

    private GutscheineRepository gutscheineRepository;


    String classSuffix = "class_";
    String objectSuffix = "object_";

    private String jwt;
    String updateMessage;


    public GutscheineImpl(GutscheineRepository gutscheineRepository) {
        this.gutscheineRepository = gutscheineRepository;
    }

    @Override
    public String createPass(Gutscheine gutscheine) {

        CreateGutscheine createGutscheine = new CreateGutscheine(classSuffix + gutscheine.getId(), objectSuffix + gutscheine.getId(), gutscheine);
        try {
            createGutscheine.createClass(new CreateGutscheine.CallbackClass() {
                @Override
                public void callback(OfferClass newClass) {

                    try {
                        createGutscheine.createObject(new CreateGutscheine.CallbackObject() {
                            @Override
                            public void callback(OfferObject newObject) {
                                System.out.println("Pass-Objekt erstellt mit ID: " + newObject.getId());
                                jwt = createGutscheine.createJWT(newClass, newObject);
                                System.out.println("JWT erstellt: " + jwt);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        gutscheineRepository.save(gutscheine);

        return jwt;


    }

    @Override
    public String updatePass(Gutscheine gutscheine) {
        UpdateGutscheine updateGutscheine;
        try {
            updateGutscheine = new UpdateGutscheine(gutscheine);
            updateGutscheine.updateClass(classSuffix + gutscheine.getId().toString());
            updateGutscheine.updateObject(objectSuffix + gutscheine.getId().toString(), new UpdateGutscheine.CallbackUpdateObject() {
                @Override
                public void callback(String response) {
                    updateMessage = response;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        gutscheineRepository.save(gutscheine);
        return updateMessage;
    }


    @Override
    public Gutscheine getGutscheine(Long gutscheinId) {
        return gutscheineRepository.findById(gutscheinId).get();
    }

    @Override
    public List<Gutscheine> getAllGutscheine() {
        return gutscheineRepository.findAll();
    }
}
