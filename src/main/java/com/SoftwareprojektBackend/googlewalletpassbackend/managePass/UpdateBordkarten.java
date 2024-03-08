package com.SoftwareprojektBackend.googlewalletpassbackend.managePass;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Bordkarte;
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

public class UpdateBordkarten {


    public interface CallbackUpdateObject {
        void callback(String response);
    }

    public static GoogleCredentials credentials;

    public static Walletobjects service;
    private final String issuerId = "3388000000022298469";
    private final String keyFilePath = "src//main//resources//arched-gear-411020-c5ab46ee8914.json";

    private Bordkarte bordkarte;


    public UpdateBordkarten(Bordkarte bordkarte) throws Exception {

        this.bordkarte = bordkarte;
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
        FlightClass updatedClass;

        try {
            updatedClass =
                    service.flightclass().get(String.format("%s.%s", issuerId, classSuffix)).execute();
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


        FlightClass response =
                service
                        .flightclass()
                        .update(String.format("%s.%s", issuerId, classSuffix), updatedClass)
                        .execute();
    }


    public void updateObject(String objectSuffix, CallbackUpdateObject callbackupdateObject) throws IOException {
        FlightObject updatedObject;

        try {
            updatedObject =
                    service.flightobject().get(String.format("%s.%s", issuerId, objectSuffix)).execute();
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

        FlightObject response =
                service
                        .flightobject()
                        .update(String.format("%s.%s", issuerId, objectSuffix), updatedObject)
                        .execute();

        callbackupdateObject.callback(response.toPrettyString());

    }

}
