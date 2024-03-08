package com.SoftwareprojektBackend.googlewalletpassbackend.service;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Mitgliedskarten;

import java.util.List;

public interface MitgliedskartenService {

    public String createPass(Mitgliedskarten mitgliedskarten);

    public String updatePass(Mitgliedskarten mitgliedskarten);

    public Mitgliedskarten getMitgliedskarte(Long mitgliedskartenId);

    public List<Mitgliedskarten> getAllMitgliedskarten();

}
