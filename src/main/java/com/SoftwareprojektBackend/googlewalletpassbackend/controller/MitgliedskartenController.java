package com.SoftwareprojektBackend.googlewalletpassbackend.controller;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Mitgliedskarten;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.MitgliedskartenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mitgliedskarte")
public class MitgliedskartenController {

    MitgliedskartenService mitgliedskartenService;

    public MitgliedskartenController(MitgliedskartenService mitgliedskartenService) {
        this.mitgliedskartenService = mitgliedskartenService;
    }

    @PostMapping
    public String postMitgliedskarte(@RequestBody Mitgliedskarten mitgliedskarten) {
        return mitgliedskartenService.createPass(mitgliedskarten);
    }

    @GetMapping("{id}")
    public Mitgliedskarten getBordkarte(@PathVariable("id") Long id) {
        return mitgliedskartenService.getMitgliedskarte(id);
    }

    @GetMapping()
    public List<Mitgliedskarten> getAllBordkarten() {
        return mitgliedskartenService.getAllMitgliedskarten();
    }

    @PutMapping
    public String updateMitgliedskarten(@RequestBody Mitgliedskarten mitgliedskarten) {
        return mitgliedskartenService.updatePass(mitgliedskarten);
    }


}
