package com.test.CRM.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.test.CRM.controllers.Exceptions.ElementNotFoundException;
import com.test.CRM.controllers.Exceptions.WrongIdFormatException;

import com.test.CRM.models.Client;
import com.test.CRM.repositories.ClientRepository;
import com.test.CRM.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController

public class ClientController {

    //TODO Инкапсулировать
    @Autowired
    ClientRepository clientRepository;

    //TODO переместить реализацию методов в сервис
    @PostMapping("/resources/clients")
    public Client create(@RequestBody Client client){
        clientRepository.save(client);
        return client;
    }

    @GetMapping("/resources/clients")
    public List<Client> retrieveClients(@RequestParam(value="organizationId", required=false) String organizationId){

        List<Client> Clients = new ArrayList<Client>();

        if(organizationId != null){

            try{
                Clients = clientRepository.findByOrganizationId(Integer.parseInt(organizationId));
            }catch(NumberFormatException e){
                throw new WrongIdFormatException();
            }

        }else{
            for(Client c : clientRepository.findAll()){
                Clients.add(c);
            }
        }


        return Clients;
    }

    @GetMapping("/resources/clients/{id}")
    public Client retrieveClient(@PathVariable String id){

        Optional<Client> client;
        try{
            client = clientRepository.findById(Integer.parseInt(id));
        }catch(NumberFormatException e){
            throw new WrongIdFormatException();
        }

        if(client == null || client.isEmpty()){
            throw new ElementNotFoundException(id);
        }

        return client.get();
    }

    @PutMapping("/resources/clients/{id}")
    public Client updateClient(@PathVariable String id, @RequestBody Client client){

        clientRepository.save(client);

        return client;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/resources/clients/{id}")
    public void deleteClient(@PathVariable String id){

        try{
            clientRepository.deleteById(Integer.parseInt(id));
        }catch(NumberFormatException e){
            throw new WrongIdFormatException();
        }
    }
}