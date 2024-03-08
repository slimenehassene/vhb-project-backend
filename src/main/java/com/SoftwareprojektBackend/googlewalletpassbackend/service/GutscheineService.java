package com.SoftwareprojektBackend.googlewalletpassbackend.service;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Gutscheine;

import java.util.List;

public interface GutscheineService {

    public String createPass(Gutscheine gutscheine);

    public String updatePass(Gutscheine gutscheine);

    public Gutscheine getGutscheine(Long gutscheinId);

    public List<Gutscheine> getAllGutscheine();

}
