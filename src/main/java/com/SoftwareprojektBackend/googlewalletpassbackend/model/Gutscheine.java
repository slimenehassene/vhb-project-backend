package com.SoftwareprojektBackend.googlewalletpassbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Gutscheine {


    @Id
    private Long id;
    private String header;


    private String angebotstitel;


    private String farbe;


    public Gutscheine(Long id, String header, String angebotstitel, String farbe) {
        this.id = id;
        this.header = header;
        this.angebotstitel = angebotstitel;
        this.farbe = farbe;
    }

    public Gutscheine() {
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

    public String getAngebotstitel() {
        return angebotstitel;
    }

    public void setAngebotstitel(String angebotstitel) {
        this.angebotstitel = angebotstitel;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
}
