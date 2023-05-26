package com.test.CRM.services;

import com.test.CRM.controllers.Exceptions.ElementNotFoundException;
import com.test.CRM.controllers.Exceptions.WrongIdFormatException;
import com.test.CRM.models.Organization;
import com.test.CRM.models.Role;
import com.test.CRM.models.User;
import com.test.CRM.repositories.OrganizationRepository;
import com.test.CRM.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService( OrganizationRepository  organizationRepository)
    {
        this.organizationRepository = organizationRepository;
    }

    public Organization create(@RequestBody Organization organization){
        organizationRepository.save(organization);
        return organization;
    }

    public List<Organization> retrieveOrganizations(){

        List<Organization> organizations = new ArrayList<Organization>();

        for(Organization o : organizationRepository.findAll()){
            organizations.add(o);
        }

        return organizations;
    }

    public Organization retrieveOrganization(String id){

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

    public Organization updateOrganization(String id, Organization organization){

        organizationRepository.save(organization);

        return organization;
    }

    public void deleteOrganization(String id){
        try{
            organizationRepository.deleteById( Integer.parseInt(id));
        }catch(NumberFormatException e){
            throw new WrongIdFormatException();
        }
    }

    public Organization save(Organization organization){
        organizationRepository.save(organization);
        return organization;
    }

}
