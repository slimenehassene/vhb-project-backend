package com.SoftwareprojektBackend.googlewalletpassbackend.managePass;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Bordkarte;
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
import java.util.HashMap;
import java.util.List;

public class CreateBordkarten {

    public interface CallbackClass {
        void callback(FlightClass newClass);
    }


    public interface CallbackObject {
        void callback(FlightObject newObject);
    }


    private final String keyFilePath = "src/main/resources/arched-gear-411020-c5ab46ee8914.json";
    private final String issuerId = "3388000000022298469";
    private String classSuffix;
    private String objectSuffix;
    private Bordkarte bordkarte;
    public static GoogleCredentials credentials;
    public static Walletobjects service;


    public CreateBordkarten(String classSuffix, String objectSuffix, Bordkarte bordkarte) {
        this.classSuffix = classSuffix;
        this.objectSuffix = objectSuffix;
        this.bordkarte = bordkarte;
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

        FlightClass newClass;
        try {
            newClass = service.flightclass().get(String.format("%s.%s", issuerId, classSuffix)).execute();

            System.out.printf("Class %s.%s already exists!%n", issuerId, classSuffix);
            callbackClass.callback(newClass);
            return;

        } catch (GoogleJsonResponseException ex) {
            if (ex.getStatusCode() != 404) {
                ex.printStackTrace();

            }
        }
        newClass =
                new FlightClass()
                        .setId(String.format("%s.%s", issuerId, classSuffix))
                        .setSecurityAnimation(new SecurityAnimation().setAnimationType("FOIL_SHIMMER"))
                        .setReviewStatus("UNDER_REVIEW")
                        .setIssuerName(bordkarte.getHeader())
                        .setLocalScheduledDepartureDateTime(bordkarte.getDatum() + "T" + bordkarte.getUhrZeit() + ":00")
                        .setFlightHeader(
                                new FlightHeader()
                                        .setCarrier(new FlightCarrier().setCarrierIataCode(bordkarte.getIataCode()))
                                        .setFlightNumber(bordkarte.getFlugnummer()))
                        .setOrigin(new AirportInfo().setAirportIataCode(bordkarte.getStartFlughafencode()).setTerminal(bordkarte.getTerminal()).setGate(bordkarte.getGate()))
                        .setDestination(
                                new AirportInfo().setAirportIataCode(bordkarte.getZielFlughafencode()).setTerminal(bordkarte.getTerminal()).setGate(bordkarte.getGate()));

        FlightClass response = service.flightclass().insert(newClass).execute();

        System.out.println("Class insert response");
        System.out.println(response.toPrettyString());

        callbackClass.callback(newClass);
    }

    public void createObject(CallbackObject callbackObject) throws IOException {

        FlightObject newObject;
        try {
            newObject = service.flightobject().get(String.format("%s.%s", issuerId, objectSuffix)).execute();

            System.out.printf("Object %s.%s already exists!%n", issuerId, objectSuffix);
            callbackObject.callback(newObject);
            return;

        } catch (GoogleJsonResponseException ex) {
            if (ex.getStatusCode() != 404) {
                ex.printStackTrace();

            }
        }
        newObject =
                new FlightObject()
                        .setId(String.format("%s.%s", issuerId, objectSuffix))
                        .setClassId(String.format("%s.%s", issuerId, classSuffix))
                        .setState("ACTIVE")
                        .setHexBackgroundColor(bordkarte.getFarbe())
                        .setHeroImage(
                                new Image()
                                        .setSourceUri(
                                                new ImageUri()
                                                        .setUri(
                                                                "https://img.freepik.com/free-photo/beautiful-collage-travel-concept_23-2149232169.jpg?size=626&ext=jpg"))
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

                        .setImageModulesData(
                                List.of(
                                        new ImageModuleData()
                                                .setMainImage(
                                                        new Image()
                                                                .setSourceUri(
                                                                        new ImageUri()
                                                                                .setUri(
                                                                                        "https://img.freepik.com/free-photo/airplane-flying-clouds-sunset-light_335224-1386.jpg?size=626&ext=jpg"))
                                                                .setContentDescription(
                                                                        new LocalizedString()
                                                                                .setDefaultValue(
                                                                                        new TranslatedString()
                                                                                                .setLanguage("en-US")
                                                                                                .setValue("Image module description"))))
                                                .setId("IMAGE_MODULE_ID")))
                        .setBarcode(new Barcode().setType("QR_CODE").setValue(bordkarte.getId().toString()))
                        .setLocations(
                                List.of(
                                        new LatLongPoint()
                                                .setLatitude(37.424015499999996)
                                                .setLongitude(-122.09259560000001)))
                        .setPassengerName(bordkarte.getPassagiere())
                        .setBoardingAndSeatingInfo(
                                new BoardingAndSeatingInfo().setBoardingGroup(bordkarte.getZone()).setSeatNumber(bordkarte.getSitz()))
                        .setReservationInfo(new ReservationInfo().setConfirmationCode("Confirmation code"));

        FlightObject response = service.flightobject().insert(newObject).execute();

        System.out.println("Object insert response");
        System.out.println(response.toPrettyString());

        callbackObject.callback(newObject);
    }


    public String createJWT(FlightClass newClass, FlightObject newObject) {


        // Create the JWT as a HashMap object
        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("iss", ((ServiceAccountCredentials) credentials).getClientEmail());
        claims.put("aud", "google");
        claims.put("origins", List.of("www.example.com"));
        claims.put("typ", "savetowallet");

        // Create the Google Wallet payload and add to the JWT
        HashMap<String, Object> payload = new HashMap<String, Object>();
        payload.put("flightClasses", List.of(newClass));
        payload.put("flightObjects", List.of(newObject));
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
