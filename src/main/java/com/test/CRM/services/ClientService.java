package com.test.CRM.services;

import com.test.CRM.controllers.Exceptions.ElementNotFoundException;
import com.test.CRM.controllers.Exceptions.WrongIdFormatException;
import com.test.CRM.models.Client;
import com.test.CRM.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClientService
{
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    public Client create(Client client){
        clientRepository.save(client);
        return client;
    }

    public List<Client> retrieveClients(String organizationId){

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

    public Client retrieveClient(String id){

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


    public Client updateClient(String id, Client client){

        clientRepository.save(client);

        return client;
    }

    public void deleteClient(String id){

        try{
            clientRepository.deleteById(Integer.parseInt(id));
        }catch(NumberFormatException e){
            throw new WrongIdFormatException();
        }
    }
}
