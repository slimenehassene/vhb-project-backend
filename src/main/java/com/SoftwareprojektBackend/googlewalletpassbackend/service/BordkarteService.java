package com.SoftwareprojektBackend.googlewalletpassbackend.service;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Bordkarte;

import java.util.List;

public interface BordkarteService {

    public String createPass(Bordkarte bordkarte);

    public String updatePass(Bordkarte bordkarte);

    public Bordkarte getBordkarte(Long bordkarteId);

    public List<Bordkarte> getAllBordkarten();

}
