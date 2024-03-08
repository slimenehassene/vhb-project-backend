package com.SoftwareprojektBackend.googlewalletpassbackend.managePass;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Mitgliedskarten;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.walletobjects.Walletobjects;
import com.google.api.services.walletobjects.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class UpdateMitgliedskarten {

    public interface CallbackUpdateObject {
        void  callback(String response);
    }
    public static GoogleCredentials credentials;

    public static Walletobjects service;
    private final String issuerId = "3388000000022298469";
    private final String keyFilePath = "C:/Users/slime/Downloads/google-wallet-pass-backend/google-wallet-pass-backend/src/main/resources/arched-gear-411020-c5ab46ee8914.json";

    private Mitgliedskarten mitgliedskarten;



    public UpdateMitgliedskarten(Mitgliedskarten mitgliedskarten) throws Exception {

        this.mitgliedskarten = mitgliedskarten;
        auth();
    }

    public void auth() throws Exception {
        String scope = "https://www.googleapis.com/auth/wallet_object.issuer";

        credentials =
                GoogleCredentials.fromStream(new FileInputStream(keyFilePath))
                        .createScoped(List.of(scope));
        credentials.refresh();

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        service =
                new Walletobjects.Builder(
                        httpTransport,
                        GsonFactory.getDefaultInstance(),
                        new HttpCredentialsAdapter(credentials))
                        .setApplicationName("APPLICATION_NAME")
                        .build();
    }

    public void updateClass(String classSuffix) throws IOException {
        LoyaltyClass updatedClass;

        try {
            updatedClass =
                    service.loyaltyclass().get(String.format("%s.%s", issuerId, classSuffix)).execute();
        } catch (GoogleJsonResponseException ex) {
            if (ex.getStatusCode() == 404) {
                System.out.printf("Class %s.%s not found!%n", issuerId, classSuffix);
                return;
            } else {
                ex.printStackTrace();
                return;
            }
        }

        updatedClass
                .setId(String.format("%s.%s", issuerId, classSuffix))
                .setIssuerName(mitgliedskarten.getHeader())
                .setReviewStatus("UNDER_REVIEW")
                .setHexBackgroundColor(mitgliedskarten.getFarbe())
                .setProgramName(mitgliedskarten.getProgrammname());


        LoyaltyClass response =
                service
                        .loyaltyclass()
                        .update(String.format("%s.%s", issuerId, classSuffix), updatedClass)
                        .execute();
    }




    public void updateObject(String objectSuffix, UpdateMitgliedskarten.CallbackUpdateObject callbackupdateObject) throws IOException {
        LoyaltyObject updatedObject;

        try {
            updatedObject =
                    service.loyaltyobject().get(String.format("%s.%s", issuerId, objectSuffix)).execute();
        } catch (GoogleJsonResponseException ex) {
            if (ex.getStatusCode() == 404) {
                System.out.printf("Object %s.%s not found!%n", issuerId, objectSuffix);
                return;
            } else {
                ex.printStackTrace();
                return;
            }
        }

        updatedObject
                .setState("ACTIVE")
                .setBarcode(new Barcode().setType("QR_CODE").setValue(mitgliedskarten.getId().toString()))
                .setLoyaltyPoints(
                        new LoyaltyPoints()
                                .setLabel(mitgliedskarten.getTreuepunkteArt())
                                .setBalance(new LoyaltyPointsBalance().setInt(mitgliedskarten.getGuthabenPunkte())));
        LoyaltyObject response =
                service
                        .loyaltyobject()
                        .update(String.format("%s.%s", issuerId, objectSuffix), updatedObject)
                        .execute();

        callbackupdateObject.callback(response.toPrettyString());

    }


}
