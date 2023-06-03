package com.example.erp.repository;

import com.example.erp.database.Database;
import com.example.erp.repository.implementaion.CustomerRepository;

public class CustomerLogic implements CustomerRepository {

    @Override
    public boolean insert(com.example.erp.models.Customer customer) {

        String[] values = {
                String.valueOf(0),
                customer.getName(),
                customer.getPhone(),
                String.valueOf(customer.getDebit_status())
        };

        return Database.insert("customers", values);

    }

    @Override
    public boolean delete(int id) {

        return Database.deleteByOneKey("customers", "id", String.valueOf(id));
    }

    @Override
    public boolean update(com.example.erp.models.Customer customer) {
        String[] values = {
                customer.getName(),
                customer.getPhone(),
                String.valueOf(customer.getDebit_status())
        };

        String[] cols ={"name", "phone", "debit_status"};
        return Database.update("customers", cols, values, "id", String.valueOf(customer.getId()) );
    }
}
