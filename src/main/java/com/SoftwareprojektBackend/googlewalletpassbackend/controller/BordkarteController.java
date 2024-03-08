package com.SoftwareprojektBackend.googlewalletpassbackend.controller;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Bordkarte;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.BordkarteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bordkarte")
public class BordkarteController {

    BordkarteService bordkarteService;

    public BordkarteController(BordkarteService bordkarteService) {
        this.bordkarteService = bordkarteService;
    }


    @PostMapping
    public String postbordkarte(@RequestBody Bordkarte bordkarte) {
        return bordkarteService.createPass(bordkarte);
    }

    @GetMapping("{id}")
    public Bordkarte getBordkarte(@PathVariable("id") Long id) {
        return bordkarteService.getBordkarte(id);
    }

    @GetMapping()
    public List<Bordkarte> getAllBordkarten() {
        return bordkarteService.getAllBordkarten();
    }

    @PutMapping
    public String updateBordkarte(@RequestBody Bordkarte bordkarte) {
        return bordkarteService.updatePass(bordkarte);
    }
}
