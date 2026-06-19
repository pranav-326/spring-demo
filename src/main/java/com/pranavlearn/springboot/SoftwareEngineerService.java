package com.pranavlearn.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SoftwareEngineerService {
    private final SoftwareEngineerRepository sfer;

    public SoftwareEngineerService(SoftwareEngineerRepository sfer) {
        this.sfer = sfer;
    }

    public List<SoftwareEngineer> getAllSoftwareEngineers() {
        return sfer.findAll();
    }

    public void insertSoftwareEngineer(SoftwareEngineer se) {
        sfer.save(se);
    }

    public SoftwareEngineer getSoftwareEngineerByID(Integer id) {
         return sfer.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id+" not found"));
    }


    public void deleteById(Integer id) {
        sfer.deleteById(id);
    }
}
