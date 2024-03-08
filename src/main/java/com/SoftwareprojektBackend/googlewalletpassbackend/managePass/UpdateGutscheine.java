package com.SoftwareprojektBackend.googlewalletpassbackend.managePass;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Gutscheine;
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
import java.util.Arrays;
import java.util.List;

public class UpdateGutscheine {


    public interface CallbackUpdateObject {
        void callback(String response);
    }

    public static GoogleCredentials credentials;

    public static Walletobjects service;
    private final String issuerId = "3388000000022298469";
    private final String keyFilePath = "C:/Users/slime/Downloads/google-wallet-pass-backend/google-wallet-pass-backend/src/main/resources/arched-gear-411020-c5ab46ee8914.json";

    private Gutscheine gutscheine;


    public UpdateGutscheine(Gutscheine gutscheine) throws Exception {

        this.gutscheine = gutscheine;
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
        OfferClass updatedClass;

        try {
            updatedClass =
                    service.offerclass().get(String.format("%s.%s", issuerId, classSuffix)).execute();
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
                .setHexBackgroundColor(gutscheine.getFarbe())
                .setIssuerName(gutscheine.getHeader())
                .setReviewStatus("UNDER_REVIEW")
                .setProvider("Provider name")
                .setTitle(gutscheine.getAngebotstitel())
                .setRedemptionChannel("ONLINE");


        OfferClass response =
                service
                        .offerclass()
                        .update(String.format("%s.%s", issuerId, classSuffix), updatedClass)
                        .execute();
    }


    public void updateObject(String objectSuffix, UpdateGutscheine.CallbackUpdateObject callbackupdateObject) throws IOException {
        OfferObject updatedObject;

        try {
            updatedObject =
                    service.offerobject().get(String.format("%s.%s", issuerId, objectSuffix)).execute();
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
                .setHeroImage(
                        new Image()
                                .setSourceUri(
                                        new ImageUri()
                                                .setUri(
                                                        "https://farm4.staticflickr.com/3723/11177041115_6e6a3b6f49_o.jpg"))
                                .setContentDescription(
                                        new LocalizedString()
                                                .setDefaultValue(
                                                        new TranslatedString()
                                                                .setLanguage("en-US")
                                                                .setValue("Hero image description"))))
                .setTextModulesData(
                        List.of(
                                new TextModuleData()
                                        .setHeader("Text module header")
                                        .setBody("Text module body")
                                        .setId("TEXT_MODULE_ID")))
                .setLinksModuleData(
                        new LinksModuleData()
                                .setUris(
                                        Arrays.asList(
                                                new Uri()
                                                        .setUri("http://maps.google.com/")
                                                        .setDescription("Link module URI description")
                                                        .setId("LINK_MODULE_URI_ID"),
                                                new Uri()
                                                        .setUri("tel:6505555555")
                                                        .setDescription("Link module tel description")
                                                        .setId("LINK_MODULE_TEL_ID"))))
                .setImageModulesData(
                        List.of(
                                new ImageModuleData()
                                        .setMainImage(
                                                new Image()
                                                        .setSourceUri(
                                                                new ImageUri()
                                                                        .setUri(
                                                                                "http://farm4.staticflickr.com/3738/12440799783_3dc3c20606_b.jpg"))
                                                        .setContentDescription(
                                                                new LocalizedString()
                                                                        .setDefaultValue(
                                                                                new TranslatedString()
                                                                                        .setLanguage("en-US")
                                                                                        .setValue("Image module description"))))
                                        .setId("IMAGE_MODULE_ID")))
                .setBarcode(new Barcode().setType("QR_CODE").setValue(gutscheine.getId().toString()))
                .setLocations(
                        List.of(
                                new LatLongPoint()
                                        .setLatitude(37.424015499999996)
                                        .setLongitude(-122.09259560000001)))
                .setValidTimeInterval(
                        new TimeInterval()
                                .setStart(new DateTime().setDate("2023-06-12T23:20:50.52Z"))
                                .setEnd(new DateTime().setDate("2026-12-12T23:20:50.52Z")));
        OfferObject response =
                service
                        .offerobject()
                        .update(String.format("%s.%s", issuerId, objectSuffix), updatedObject)
                        .execute();

        callbackupdateObject.callback(response.toPrettyString());

    }

}
