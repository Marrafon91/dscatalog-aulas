package com.devsuperior.bds02.controllers;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService service;

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable("id") Long id,
                                           @RequestBody EventDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }
}
