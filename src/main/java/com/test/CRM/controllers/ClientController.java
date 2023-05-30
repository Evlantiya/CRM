package com.test.CRM.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.test.CRM.controllers.Exceptions.ElementNotFoundException;
import com.test.CRM.controllers.Exceptions.WrongIdFormatException;

import com.test.CRM.models.Client;
import com.test.CRM.repositories.ClientRepository;
import com.test.CRM.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClientController {

    //TODO Инкапсулировать
    @Autowired
    ClientService clientService;

    //TODO переместить реализацию методов в сервис
    @PostMapping("/resources/clients")
    public Client create(@RequestBody Client client){
        return clientService.create(client);
    }

    @GetMapping("/resources/clients")
    public List<Client> retrieveClients(@RequestParam(value="organizationId", required=false) String organizationId){
        return  clientService.retrieveClients(organizationId);
    }

    @GetMapping("/resources/clients/{id}")
    public Client retrieveClient(@PathVariable String id){

        return clientService.retrieveClient(id);
    }

    @PutMapping("/resources/clients/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client client){

        return clientService.updateClient(id,client);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/resources/clients/{id}")
    public void deleteClient(@PathVariable String id){

        clientService.deleteClient(id);
    }
}