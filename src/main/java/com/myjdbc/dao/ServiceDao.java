package com.myjdbc.dao;

import com.myjdbc.entity.Service;
import com.myjdbc.util.DatabaseUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ServiceDao class.
 */
public class ServiceDao implements DAO<Service, UUID> {

    // Constants to store requests to database.
    private static final String GET_ALL = "select service_id, name, price from services";
    private static final String GET_BY_ID = "select service_id, name, price from services where service_id = ?";
    private static final String CREATE = "insert into services (service_id, name, price) values (?, ?, ?)";
    private static final String UPDATE = "update sevices set name = ?, price = ? where service_id = ?";
    private static final String DELETE = "delete from services where service_id = ?";

    // Method to get all services from database.
    @Override
    public List<Service> getAll() {

        // Creating a list to store all services.
        List<Service> services = new ArrayList<>();

        // Connecting to database.
        Connection connection = DatabaseUtils.getConnection();
        try(Statement statement = connection.createStatement()) {

            // Getting the results.
            ResultSet rs = statement.executeQuery(GET_ALL);
            services = this.processResultSet(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        // Returns a list of all services.
        return services;
    }

    @Override
    public Service create(Service entity) {

        // Generating UUID randomly.
        UUID serviceId = UUID.randomUUID();

        // Creating connection to database.
        Connection connection = DatabaseUtils.getConnection();

        try {
            // Making connection transactional.
            connection.setAutoCommit(false);

            // Creating Prepared statement.
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setObject(1, serviceId);
            statement.setString(2, entity.getName());
            statement.setBigDecimal(3, entity.getPrice());
            statement.execute();
            connection.commit();
            statement.close();

        } catch (SQLException ex) {

            //Setting rollback.
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Creating a list of services.
        Optional<Service> service = this.getOne(serviceId);

        // Checking if data was not saved into database.
        if(!service.isPresent()) {
            return null;
        }
        return service.get();
    }

    // Method to get one service by its id.
    @Override
    public Optional<Service> getOne(UUID id) {

        // Creating prepared statement to get service by its id.
        try(PreparedStatement statement = DatabaseUtils.getConnection().prepareStatement(GET_BY_ID)) {

            // Setting values of parameters of prepared statement to get service by its id.
            statement.setObject(1, id);

            // Getting data from database.
            ResultSet rs = statement.executeQuery();
            List<Service> services = this.processResultSet(rs);
            if(services.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(services.get(0));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Returns Optional.empty.
        return Optional.empty();
    }

    @Override
    public Service update(Service entity) {

        // Creation a connection.
        Connection connection = DatabaseUtils.getConnection();

        // Trying to write to database.
        try {
            // Making transactional connection.
            connection.setAutoCommit(false);

            // Creating Prepared statement.
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getPrice());
            statement.setObject(3, entity.getServiceId());
            statement.execute();
            connection.commit();
            statement.close();
        } catch (SQLException ex) {

            //Setting rollback.
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Return statement.
        return entity;
    }

    // Method to delete the row with specified id.
    @Override
    public void delete(UUID uuid) {

        // Creating a connection to database.
        Connection connection = DatabaseUtils.getConnection();

        // Trying to update a row.
        try {
            // Making transactional connection.
            connection.setAutoCommit(false);

            // Creating Prepared statement.
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setObject(1, uuid);
            statement.execute();
            connection.commit();
            statement.close();

        } catch (SQLException ex) {

            //Setting rollback.
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Storing all data from database in our buffer in view of ArrayList<>().
    private List<Service> processResultSet(ResultSet rs) throws SQLException {
        List<Service> services = new ArrayList<>();

        // Iterating through all tuples.
        while (rs.next()) {
            Service service = new Service();
            service.setServiceId((UUID)rs.getObject("service_id"));
            service.setName(rs.getString("name"));
            service.setPrice(rs.getBigDecimal("price"));
            services.add(service);
        }
        return services;
    }
}
