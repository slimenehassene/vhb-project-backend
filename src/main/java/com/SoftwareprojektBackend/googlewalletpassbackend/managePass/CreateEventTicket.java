package com.SoftwareprojektBackend.googlewalletpassbackend.managePass;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.EventTicket;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.walletobjects.Walletobjects;
import com.google.api.services.walletobjects.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CreateEventTicket {

    public interface CallbackClass {
        void callback(EventTicketClass newClass);
    }

    public interface CallbackObject {
        void callback(EventTicketObject newObject);
    }


    private final String keyFilePath = "src/main/resources/arched-gear-411020-c5ab46ee8914.json";
    private final String issuerId = "3388000000022298469";
    private String classSuffix;
    private String objectSuffix;
    private EventTicket eventTicket;
    public static GoogleCredentials credentials;
    public static Walletobjects service;


    public CreateEventTicket(String classSuffix, String objectSuffix, EventTicket eventTicket) {
        this.classSuffix = classSuffix;
        this.objectSuffix = objectSuffix;
        this.eventTicket = eventTicket;
        try {
            auth();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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


    public void createClass(CallbackClass callbackClass) throws IOException {

        EventTicketClass newClass;
        try {
            newClass = service.eventticketclass().get(String.format("%s.%s", issuerId, classSuffix)).execute();

            System.out.printf("Class %s.%s already exists!%n", issuerId, classSuffix);
            callbackClass.callback(newClass);
            return;

        } catch (GoogleJsonResponseException ex) {
            if (ex.getStatusCode() != 404) {
                ex.printStackTrace();

            }
        }
        newClass =
                new EventTicketClass()
                        .setEventId(String.format("%s.%s", issuerId, classSuffix))
                        .setSecurityAnimation(new SecurityAnimation().setAnimationType("FOIL_SHIMMER"))
                        .setEventName(
                                new LocalizedString()
                                        .setDefaultValue(
                                                new TranslatedString().setLanguage("en-US").setValue(eventTicket.getEreignisname())))
                        .setId(String.format("%s.%s", issuerId, classSuffix))
                        .setIssuerName(eventTicket.getHeader())
                        .setReviewStatus("UNDER_REVIEW");

        EventTicketClass response = service.eventticketclass().insert(newClass).execute();

        System.out.println("Class insert response");
        System.out.println(response.toPrettyString());

        callbackClass.callback(newClass);
    }

    public void createObject(CallbackObject callbackObject) throws IOException {

        EventTicketObject newObject;
        try {
            newObject = service.eventticketobject().get(String.format("%s.%s", issuerId, objectSuffix)).execute();

            System.out.printf("Object %s.%s already exists!%n", issuerId, objectSuffix);
            callbackObject.callback(newObject);
            return;

        } catch (GoogleJsonResponseException ex) {
            if (ex.getStatusCode() != 404) {
                ex.printStackTrace();

            }
        }
        newObject =
                new EventTicketObject()
                        .setId(String.format("%s.%s", issuerId, objectSuffix))
                        .setClassId(String.format("%s.%s", issuerId, classSuffix))
                        .setHexBackgroundColor(eventTicket.getFarbe())
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

        EventTicketObject response = service.eventticketobject().insert(newObject).execute();

        System.out.println("Object insert response");
        System.out.println(response.toPrettyString());

        callbackObject.callback(newObject);
    }

    public String createJWT(EventTicketClass newClass, EventTicketObject newObject) {


        // Create the JWT as a HashMap object
        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("iss", ((ServiceAccountCredentials) credentials).getClientEmail());
        claims.put("aud", "google");
        claims.put("origins", List.of("www.example.com"));
        claims.put("typ", "savetowallet");

        // Create the Google Wallet payload and add to the JWT
        HashMap<String, Object> payload = new HashMap<String, Object>();
        payload.put("eventTicketClasses", List.of(newClass));
        payload.put("eventTicketObjects", List.of(newObject));
        claims.put("payload", payload);

        // The service account credentials are used to sign the JWT
        Algorithm algorithm =
                Algorithm.RSA256(
                        null, (RSAPrivateKey) ((ServiceAccountCredentials) credentials).getPrivateKey());
        String token = JWT.create().withPayload(claims).sign(algorithm);

        System.out.println("Add to Google Wallet link");
        System.out.printf("https://pay.google.com/gp/v/save/%s%n", token);

        return String.format("https://pay.google.com/gp/v/save/%s", token);


    }


}
