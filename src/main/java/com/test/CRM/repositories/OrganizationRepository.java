package com.test.CRM.repositories;

import com.test.CRM.models.Organization;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Integer>{

    public List<Organization> findByNameContainingIgnoreCase(String name);

}
