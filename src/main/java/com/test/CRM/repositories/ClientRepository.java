package com.test.CRM.repositories;

import com.test.CRM.models.Client;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {

    public List<Client> findByFirstNameContainingIgnoreCase(String firstName);

    public List<Client> findByOrganizationId(Integer id);

}
