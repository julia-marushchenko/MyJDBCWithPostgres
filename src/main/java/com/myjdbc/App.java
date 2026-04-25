// Java application to use JDBC with Postgres Database.
package com.myjdbc;

import com.myjdbc.dao.ServiceDao;
import com.myjdbc.dao.SimpleProductDao;
import com.myjdbc.entity.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * App class.
 */
public class App {

    // JVM entry point.
    public static void main(String[] args) {

        // Creating ServiceDao instance.
        ServiceDao serviceDao = new ServiceDao();

        // Getting all tuples from table 'services'.
        List<Service> services = serviceDao.getAll();
        System.out.println("******** SERVICES ********");
        System.out.println("\n*** GET_ALL ***");
        services.forEach(System.out::println);

        // Getting a service by uuid.
        Optional<Service> service = serviceDao.getOne(services.get(0).getServiceId());
        System.out.println("\n *** GET ONE ***\n" + service.get());

        // Inserting a service in Postgres database.
        Service newService = new Service();
        newService.setName("delivery");
        newService.setPrice(new BigDecimal("50"));
        serviceDao.create(newService);

        // Getting all tuples from table 'services'.
        List<Service> servicesNew = serviceDao.getAll();
        System.out.println("******** SERVICES ********");
        System.out.println("\n*** GET_ALL ***");
        servicesNew.forEach(System.out::println);
        newService.setPrice(new BigDecimal(14.35));

        // Updating a service.
        serviceDao.update(newService);;

        // Printing a service to console.
        System.out.println("\n*** UPDATE ***\n" + newService);

        // Deleting a service.
        serviceDao.delete(UUID.fromString("29125293-8c26-4bc2-801d-065a9b52e537"));

        // Displaying the result.
        System.out.println("\n *** DELETE IS DONE ***\n");

        // Getting all tuples from table 'services'.
        List<Service> servicesNew1 = serviceDao.getAll();
        System.out.println("******** SERVICES ********");
        System.out.println("\n*** GET_ALL ***");
        servicesNew1.forEach(System.out::println);

        // Creating SimpleProduct.
        System.out.println("\n\n*** SIMPLE PRODUCT ***");
        SimpleProductDao spdao = new SimpleProductDao();
        UUID productID = spdao.createProduct("product");
        System.out.println(productID);

        // Limit example.
        System.out.println("\n\n*** LIMIT ***");
        serviceDao.getAllLimit(2).forEach(System.out::println);
    }
}
