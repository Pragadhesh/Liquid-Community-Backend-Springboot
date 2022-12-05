package com.gmentor.spring.jpa.postgresql.controller;

import com.gmentor.spring.jpa.postgresql.model.Event;
import com.gmentor.spring.jpa.postgresql.model.Sponsorship;
import com.gmentor.spring.jpa.postgresql.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents()
    {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
    }

    @PostMapping("/events")
    public ResponseEntity<Event> addEvent(@RequestBody Event ev)
    {
        try {
            Event event = eventRepository.save(
                    new Event(ev.getName(),ev.getTime() ,ev.getDescription())
            );
            return new ResponseEntity<>(event,HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
