package com.agriculture.pyhtecserver.repositories;

import com.agriculture.pyhtecserver.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    List<Customer> findByCustomerType(Customer.CustomerType customerType);

    @Query("SELECT c FROM Customer c WHERE c.active = true")
    List<Customer> findAllActiveCustomers();

    @Query("SELECT c FROM Customer c WHERE c.city = :city")
    List<Customer> findByCity(@Param("city") String city);

    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE %:name% OR c.lastName LIKE %:name%")
    List<Customer> searchByName(@Param("name") String name);
}
