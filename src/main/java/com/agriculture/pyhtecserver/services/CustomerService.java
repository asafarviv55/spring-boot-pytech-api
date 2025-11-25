package com.agriculture.pyhtecserver.services;

import com.agriculture.pyhtecserver.models.Customer;
import com.agriculture.pyhtecserver.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setAddress(customerDetails.getAddress());
        customer.setCity(customerDetails.getCity());
        customer.setCountry(customerDetails.getCountry());
        customer.setCustomerType(customerDetails.getCustomerType());
        customer.setActive(customerDetails.isActive());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getActiveCustomers() {
        return customerRepository.findAllActiveCustomers();
    }

    public List<Customer> getCustomersByType(Customer.CustomerType type) {
        return customerRepository.findByCustomerType(type);
    }

    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.searchByName(name);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
