package com.example.erp.repository.implementaion;

import com.example.erp.models.Customer;

public interface CustomerRepository {

    boolean insert(Customer customer);
    boolean delete(int id);

    boolean update(Customer customer);



}
