package com.SoftwareprojektBackend.googlewalletpassbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class EventTicket {

    @Id
    private Long id;

    private String header;
    private String ereignisname;
    private String gate;
    private String abschnitt;
    private String reihe;

    private String sitzplatz;
    private String farbe;


    // @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    //@JoinColumn(nullable = false,name ="user_id")
    //  private User user;


    public EventTicket() {
    }

    public EventTicket(Long id, String header,
                       String ereignisname,
                       String gate, String abschnitt,
                       String reihe, String sitzplatz, String farbe) {
        this.id = id;
        this.header = header;
        this.ereignisname = ereignisname;
        this.gate = gate;
        this.abschnitt = abschnitt;
        this.reihe = reihe;
        this.sitzplatz = sitzplatz;
        this.farbe = farbe;
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

    public String getEreignisname() {
        return ereignisname;
    }

    public void setEreignisname(String ereignisname) {
        this.ereignisname = ereignisname;
    }


    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getAbschnitt() {
        return abschnitt;
    }

    public void setAbschnitt(String abschnitt) {
        this.abschnitt = abschnitt;
    }

    public String getReihe() {
        return reihe;
    }

    public void setReihe(String reihe) {
        this.reihe = reihe;
    }

    public String getSitzplatz() {
        return sitzplatz;
    }

    public void setSitzplatz(String sitzplatz) {
        this.sitzplatz = sitzplatz;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }
}
