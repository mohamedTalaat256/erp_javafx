
package  com.example.erp.repository.implementaion;

import com.example.erp.models.Supplier;

public interface SupplierRepository {

    boolean insert(Supplier supplier);
    boolean delete(int id);

    boolean update(Supplier supplier);



}
