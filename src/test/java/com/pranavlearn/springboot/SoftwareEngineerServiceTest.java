package com.pranavlearn.springboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SoftwareEngineerServiceTest {

    @Mock
    private SoftwareEngineerRepository mockSfer;

    @Test
    void getAllSoftwareEngineers_returnsListFromRepository() {
        SoftwareEngineerService service = new SoftwareEngineerService(mockSfer);
        List<SoftwareEngineer> fakeList = Arrays.asList(
                new SoftwareEngineer(23,"Ash","JaveScript, React"),
                new SoftwareEngineer(24, "Serena", " Node, Typescript")
        );
        when(mockSfer.findAll()).thenReturn(fakeList);
        List<SoftwareEngineer> result = service.getAllSoftwareEngineers();
        assertEquals(fakeList, result);
    }
    @Test
    void getSoftwareEngineerByID_found_returnsEngineer() {
        // 1. service setup (same pattern as before)
        SoftwareEngineerService service=new SoftwareEngineerService(mockSfer);
        // 2. create one fake SoftwareEngineer
        SoftwareEngineer se=new SoftwareEngineer(24,"Pranav","Java, Spring");
        // 3. stub: when findById(some id) is called, return Optional.of(yourFakeEngineer)
        when(mockSfer.findById(24)).thenReturn(Optional.of(se));
        SoftwareEngineer se1 = service.getSoftwareEngineerByID(24);

        assertEquals(se, se1);
    }
    @Test
    void getSoftwareEngineerByID_notFound_throwsException() {
        SoftwareEngineerService service=new SoftwareEngineerService(mockSfer);
        when(mockSfer.findById(24)).thenReturn(Optional.empty());
        ResponseStatusException rse = assertThrows(
                ResponseStatusException.class, () -> service.getSoftwareEngineerByID(24)
        );
        assertEquals(HttpStatus.NOT_FOUND, rse.getStatusCode());
    }
}