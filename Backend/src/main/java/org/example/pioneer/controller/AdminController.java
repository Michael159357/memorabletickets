package org.example.pioneer.controller;

import org.example.pioneer.domain.Event;
import org.example.pioneer.domain.Ticket;
import org.example.pioneer.domain.User;
import org.example.pioneer.service.EventService;
import org.example.pioneer.service.TicketService;
import org.example.pioneer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("@roleValidator.hasAllRoles(authentication, {'ADMIN', 'USER'})")
public class AdminController {

    @Autowired
    private  TicketService ticketService;

    @Autowired
    private  UserService userService;

    @Autowired
    private EventService eventService;


    /* Gestión de usuarios
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable Long id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "User role updated successfully";
    }*/


    /* Gestión de tickets
    @PutMapping("/tickets/{id}")
    public String updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        ticketService.updateTicket(id, ticket);
        return "Ticket updated successfully";
    }*/
}
