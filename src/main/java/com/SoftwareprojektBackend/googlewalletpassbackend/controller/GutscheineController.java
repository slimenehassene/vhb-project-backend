package com.SoftwareprojektBackend.googlewalletpassbackend.controller;

import com.SoftwareprojektBackend.googlewalletpassbackend.model.Gutscheine;
import com.SoftwareprojektBackend.googlewalletpassbackend.service.GutscheineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gutscheine")
public class GutscheineController {

    GutscheineService gutscheineService;

    public GutscheineController(GutscheineService gutscheineService) {
        this.gutscheineService = gutscheineService;
    }


    @PostMapping
    public String postGutscheine(@RequestBody Gutscheine gutscheine) {
        return gutscheineService.createPass(gutscheine);
    }

    @GetMapping("{id}")
    public Gutscheine getBordkarte(@PathVariable("id") Long id) {
        return gutscheineService.getGutscheine(id);
    }

    @GetMapping()
    public List<Gutscheine> getAllBordkarten() {
        return gutscheineService.getAllGutscheine();
    }

    @PutMapping
    public String updateGutscheine(@RequestBody Gutscheine gutscheine) {
        return gutscheineService.updatePass(gutscheine);
    }

}
