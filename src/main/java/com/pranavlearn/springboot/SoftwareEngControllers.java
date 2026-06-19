package com.pranavlearn.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/software-engineers")
public class SoftwareEngControllers {
    private final SoftwareEngineerService softwareEngineerService;

    public SoftwareEngControllers(SoftwareEngineerService softwareEngineerService) {
        this.softwareEngineerService = softwareEngineerService;
    }

    @GetMapping("/get-engineers")
    public List<SoftwareEngineer> getEngineers() {
        return softwareEngineerService.getAllSoftwareEngineers();
    }

    @PostMapping("/post-engineer")
    public ResponseEntity<SoftwareEngineer> postEngineers(@RequestBody SoftwareEngineer se) {
        softwareEngineerService.insertSoftwareEngineer(se);
        return ResponseEntity.status(HttpStatus.CREATED).body(se);
    }

    @GetMapping("/get-by-id/{id}")
    public SoftwareEngineer getSoftwareEngineerByID(@PathVariable Integer id) {
        return softwareEngineerService.getSoftwareEngineerByID(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        softwareEngineerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
