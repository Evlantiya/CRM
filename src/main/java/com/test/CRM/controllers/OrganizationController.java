package com.test.CRM.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.test.CRM.controllers.Exceptions.ElementNotFoundException;
import com.test.CRM.controllers.Exceptions.WrongIdFormatException;

import com.test.CRM.models.Organization;
import com.test.CRM.repositories.OrganizationRepository;
import com.test.CRM.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService){
        this.organizationService=organizationService;
    }

    @PostMapping("/resources/organizations")
    public Organization create(@RequestBody Organization organization){
        return organizationService.create(organization);
    }

    @GetMapping("/resources/organizations")
    public List<Organization> retrieveOrganizations(){
        return organizationService.retrieveOrganizations();
    }

    @GetMapping("/resources/organizations/{id}")
    public Organization retrieveOrganization(@PathVariable String id){

        return organizationService.retrieveOrganization(id);
    }

    @PutMapping("/resources/organizations/{id}")
    public Organization updateOrganization(@PathVariable String id, @RequestBody Organization organization){

        return organizationService.updateOrganization(id,organization);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/resources/organizations/{id}")
    public void deleteOrganization(@PathVariable String id){
        try{
            organizationService.deleteOrganization(id);
        }catch(NumberFormatException e){
            throw new WrongIdFormatException();
        }
    }
}
