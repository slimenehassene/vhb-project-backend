package com.SoftwareprojektBackend.googlewalletpassbackend.managePass;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.EventTicket;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.walletobjects.Walletobjects;
import com.google.api.services.walletobjects.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.scheduling.annotation.Async;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UpdateEventTicket {


    public interface CallbackUpdateObject {
        void callback(String response);
    }

    public static GoogleCredentials credentials;

    public static Walletobjects service;
    private final String issuerId = "3388000000022298469";
    private final String keyFilePath = "src/main/resources/arched-gear-411020-c5ab46ee8914.json";

    private EventTicket eventTicket;


    public UpdateEventTicket(EventTicket eventTicket) throws Exception {

        this.eventTicket = eventTicket;
        auth();
    }

    @Async
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

    @Async
    public void updateClass(String classSuffix) throws IOException {
        EventTicketClass updatedClass;

        try {
            updatedClass =
                    service.eventticketclass().get(String.format("%s.%s", issuerId, classSuffix)).execute();
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
                .setEventName(
                        new LocalizedString()
                                .setDefaultValue(
                                        new TranslatedString().setLanguage("en-US").setValue(eventTicket.getEreignisname())))
                .setId(String.format("%s.%s", issuerId, classSuffix))
                .setIssuerName(eventTicket.getHeader())
                .setReviewStatus("UNDER_REVIEW");


        EventTicketClass response =
                service
                        .eventticketclass()
                        .update(String.format("%s.%s", issuerId, classSuffix), updatedClass)
                        .execute();
    }


    @Async
    public void updateObject(String objectSuffix, UpdateEventTicket.CallbackUpdateObject callbackupdateObject) throws IOException {
        EventTicketObject updatedObject;

        try {
            updatedObject =
                    service.eventticketobject().get(String.format("%s.%s", issuerId, objectSuffix)).execute();
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
                .setHexBackgroundColor(eventTicket.getFarbe())
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
                .setBarcode(new Barcode().setType("QR_CODE").setValue(eventTicket.getId().toString()))
                .setLocations(
                        List.of(
                                new LatLongPoint()
                                        .setLatitude(37.424015499999996)
                                        .setLongitude(-122.09259560000001)))
                .setSeatInfo(
                        new EventSeat()
                                .setSeat(
                                        new LocalizedString()
                                                .setDefaultValue(
                                                        new TranslatedString().setLanguage("en-US").setValue(eventTicket.getSitzplatz())))
                                .setRow(
                                        new LocalizedString()
                                                .setDefaultValue(
                                                        new TranslatedString().setLanguage("en-US").setValue(eventTicket.getReihe())))
                                .setSection(
                                        new LocalizedString()
                                                .setDefaultValue(
                                                        new TranslatedString().setLanguage("en-US").setValue(eventTicket.getAbschnitt())))
                                .setGate(
                                        new LocalizedString()
                                                .setDefaultValue(
                                                        new TranslatedString().setLanguage("en-US").setValue(eventTicket.getGate()))))
                .setTicketHolderName("Ticket holder name")
                .setTicketNumber("Ticket number");
        EventTicketObject response =
                service
                        .eventticketobject()
                        .update(String.format("%s.%s", issuerId, objectSuffix), updatedObject)
                        .execute();

        callbackupdateObject.callback(response.toPrettyString());

    }


}
