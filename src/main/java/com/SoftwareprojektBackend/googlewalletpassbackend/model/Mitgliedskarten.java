package com.SoftwareprojektBackend.googlewalletpassbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "mitgliedskarten")
public class Mitgliedskarten {

    @Id
    private Long id;
    private String header;
    private String programmname;
    private String treuepunkteArt;
    private int guthabenPunkte;

    private String farbe;


    public Mitgliedskarten(Long id, String header, String programmname,
                           String treuepunkteArt, int guthabenPunkte, String farbe) {
        this.id = id;
        this.header = header;
        this.programmname = programmname;
        this.treuepunkteArt = treuepunkteArt;
        this.guthabenPunkte = guthabenPunkte;
        this.farbe = farbe;
    }

    public Mitgliedskarten() {
    }

    public Long getId() {
        return id;
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

    public String getProgrammname() {
        return programmname;
    }

    public void setProgrammname(String programmname) {
        this.programmname = programmname;
    }

    public String getTreuepunkteArt() {
        return treuepunkteArt;
    }

    public void setTreuepunkteArt(String treuepunkteArt) {
        this.treuepunkteArt = treuepunkteArt;
    }

    public int getGuthabenPunkte() {
        return guthabenPunkte;
    }

    public void setGuthabenPunkte(int guthabenPunkte) {
        this.guthabenPunkte = guthabenPunkte;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
}
