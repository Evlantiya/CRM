package com.test.CRM.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.test.CRM.controllers.Exceptions.ElementNotFoundException;
import com.test.CRM.controllers.Exceptions.WrongIdFormatException;

import com.test.CRM.models.Organization;
import com.test.CRM.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrganizationController {

    //TODO Инкапсулировать
    @Autowired
    OrganizationRepository organizationRepository;

    //TODO переместить реализацию методов в сервис
    @PostMapping("/resources/organizations")
    public Organization create(@RequestBody Organization organization){
        organizationRepository.save(organization);
        return organization;
    }

    @GetMapping("/resources/organizations")
    public List<Organization> retrieveOrganizations(){

        List<Organization> organizations = new ArrayList<Organization>();

        for(Organization o : organizationRepository.findAll()){
            organizations.add(o);
        }

        return organizations;
    }

    @GetMapping("/resources/organizations/{id}")
    public Organization retrieveOrganization(@PathVariable String id){

        Optional<Organization> organization;
        try{
            organization = organizationRepository.findById(Integer.parseInt(id));
        }catch(NumberFormatException e){
            throw new WrongIdFormatException();
        }

        if(organization == null || organization.isEmpty()){
            throw new ElementNotFoundException(id);
        }

        return organization.get();
    }

    @PutMapping("/resources/organizations/{id}")
    public Organization updateOrganization(@PathVariable String id, @RequestBody Organization organization){

        organizationRepository.save(organization);

        return organization;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/resources/organizations/{id}")
    public void deleteOrganization(@PathVariable String id){
        try{
            organizationRepository.deleteById( Integer.parseInt(id));
        }catch(NumberFormatException e){
            throw new WrongIdFormatException();
        }
    }
}
