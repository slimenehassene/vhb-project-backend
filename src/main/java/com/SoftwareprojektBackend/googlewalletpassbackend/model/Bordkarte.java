package com.SoftwareprojektBackend.googlewalletpassbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bordkarte")
public class Bordkarte {


    @Id
    private Long id;
    private String header;
    private String flugnummer;
    private String iataCode;
    private String startflughafen;

    private String startFlughafencode;


    private String zielflughafen;

    private String zielFlughafencode;

    private String terminal;

    private String gate;

    private String kabine;



    private String passagiere;

    private String zone;

    private String sitz;

    private String farbe;



    public Bordkarte() {
    }

    public Bordkarte(Long id, String header, String flugnummer,
                     String startflughafen, String startFlughafencode,
                     String zielflughafen, String zielFlughafencode,String iataCode,
                     String terminal, String gate, String kabine,
                     String passagiere, String zone, String sitz, String farbe) {
        this.id = id;
        this.header = header;
        this.flugnummer = flugnummer;
        this.startflughafen = startflughafen;
        this.startFlughafencode = startFlughafencode;
        this.zielflughafen = zielflughafen;
        this.zielFlughafencode = zielFlughafencode;
        this.terminal = terminal;
        this.gate = gate;
        this.kabine = kabine;
        this.iataCode = iataCode;
        this.passagiere = passagiere;
        this.zone = zone;
        this.sitz = sitz;
        this.farbe = farbe;
    }

    public Long getId() {
        return id;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFlugnummer() {
        return flugnummer;
    }

    public void setFlugnummer(String flugnummer) {
        this.flugnummer = flugnummer;
    }

    public String getStartflughafen() {
        return startflughafen;
    }

    public void setStartflughafen(String startflughafen) {
        this.startflughafen = startflughafen;
    }

    public String getStartFlughafencode() {
        return startFlughafencode;
    }

    public void setStartFlughafencode(String startFlughafencode) {
        this.startFlughafencode = startFlughafencode;
    }

    public String getZielflughafen() {
        return zielflughafen;
    }

    public void setZielflughafen(String zielflughafen) {
        this.zielflughafen = zielflughafen;
    }

    public String getZielFlughafencode() {
        return zielFlughafencode;
    }

    public void setZielFlughafencode(String zielFlughafencode) {
        this.zielFlughafencode = zielFlughafencode;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getKabine() {
        return kabine;
    }

    public void setKabine(String kabine) {
        this.kabine = kabine;
    }


    public String getPassagiere() {
        return passagiere;
    }

    public void setPassagiere(String passagiere) {
        this.passagiere = passagiere;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSitz() {
        return sitz;
    }

    public void setSitz(String sitz) {
        this.sitz = sitz;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
}
