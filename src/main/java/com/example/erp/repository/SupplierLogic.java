package com.example.erp.repository;

import com.example.erp.database.Database;
import com.example.erp.repository.implementaion.SupplierRepository;
import com.example.erp.models.Supplier;

public class SupplierLogic implements SupplierRepository {

    @Override
    public boolean insert(Supplier supplier) {

        String[] values = {
                String.valueOf(0),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getAddress(),
                String.valueOf(supplier.getDebit_status())
        };

        return Database.insert("suppliers", values);

    }

    @Override
    public boolean delete(int id) {

        return Database.deleteByOneKey("suppliers", "id", String.valueOf(id));
    }

    @Override
    public boolean update(Supplier supplier) {
        String[] values = {
                supplier.getName(),
                supplier.getPhone(),
                supplier.getAddress(),

                String.valueOf(supplier.getDebit_status())
        };

        String[] cols ={"name", "phone", "address","debit_status"};
        return Database.update("suppliers", cols, values, "id", String.valueOf(supplier.getId()) );
    }
}
