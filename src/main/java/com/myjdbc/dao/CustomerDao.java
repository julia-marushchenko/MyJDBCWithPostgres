package com.myjdbc.dao;

import com.myjdbc.entity.Customer;
import com.myjdbc.util.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * CustomerDao class.
 */
public class CustomerDao implements DAO {

    // Constants to store requests to database.
    private static final String GET_ALL = "select * from customers";
    private static final String CREATE = "insert into customers (customer_id, first_name, last_name, email, phone, " +
            "address values (?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "select customer_id, first_name, last_name, email, phone, address " +
            "from customers where customer_id = ?";
    private static final String UPDATE = "update customers set first_name = ?, last_name = ?, email = ?, phone = ?, " +
            "address = ? where customer_id = ?";

    private static final String DELETE = "delete from customers where customer_id = ?";

    // Method to get all customers from database.
    @Override
    public List getAll() {

        // Creating a list to store all customers.
        List<Customer> customers = new ArrayList<>();

        // Creating a connection to database.
        Connection connection = DatabaseUtils.getConnection();

        // Executing GET_ALL statement.
        try(Statement statement = connection.createStatement()) {

            // Geting Result Set.
            ResultSet rs = statement.executeQuery(GET_ALL);
            customers = this.processResultSet(rs);

        } catch (SQLException ex) {

            // Printing stack of exception.
            ex.printStackTrace();

        }

        // Returning all customers.
        return customers;
    }

    // Method to create a new customer.
    @Override
    public Customer create(Object entity) {

        // Generating UUID randomly.
        UUID customerId = UUID.randomUUID();

        // Creating connection to database.
        Connection connection = DatabaseUtils.getConnection();

        try {
            // Making connection transactional.
            connection.setAutoCommit(false);

            // Creating Prepared statement.
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setObject(1, customerId);
            statement.setString(2, ((Customer)entity).getFirstName());
            statement.setString(3, ((Customer) entity).getLastName());
            statement.setString(4, ((Customer) entity).getEmail());
            statement.setString(5, ((Customer) entity).getPhone());
            statement.setString(6, ((Customer) entity).getAddress());
            statement.execute();
            connection.commit();
            statement.close();

        } catch (SQLException ex) {

            //Setting rollback.
            try {

                // Rollback.
                connection.rollback();
            } catch (SQLException e) {

                // Printing stack of exception.
                e.printStackTrace();
            }
        }

        // Creating a new customer.
        Optional<Customer> customer = this.getOne(customerId);

        // Checking if data was saved.
        if (!customer.isPresent()) {
            return null;
        }

        // Returning a new customer.
        return customer.get();
    }

    // Method to get one customer by its id.
    @Override
    public Optional<Customer> getOne(UUID uuid) {

        // Creating prepared statement to get service by its id.
        try(PreparedStatement statement = DatabaseUtils.getConnection().prepareStatement(GET_ONE)) {

            // Setting values of parameters of prepared statement to get service by its id.
            statement.setObject(1, uuid);

            // Getting data from database.
            ResultSet rs = statement.executeQuery();
            List<Customer> customers = this.processResultSet(rs);
            if(customers.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(customers.get(0));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Returns Optional.empty.
        return Optional.empty();
    }

    @Override
    public Customer update(Object entity) {
        // Creation a connection.
        Connection connection = DatabaseUtils.getConnection();

        // Trying to write to database.
        try {
            // Making transactional connection.
            connection.setAutoCommit(false);

            // Creating Prepared statement.
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setObject(1, ((Customer)entity).getCustomerId());
            statement.setString(2, ((Customer)entity).getFirstName());
            statement.setObject(3, ((Customer) entity).getLastName());
            statement.setString(4, ((Customer) entity).getEmail());
            statement.setString(4, ((Customer) entity).getPhone());
            statement.setString(4, ((Customer) entity).getAddress());
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
        return (Customer)entity;
    }


    // Method to delete customer by its id.
    @Override
    public void delete(UUID uuid) {

        // Creating a connection to database.
        Connection connection = DatabaseUtils.getConnection();

        // Deleting a customer.
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

    // Method to store all customers in buffer.
    private List<Customer> processResultSet(ResultSet rs) throws SQLException {
        // Creating a list to store customers.
        List<Customer> customers = new ArrayList<>();

        // Iterating through all customers.
        while (rs.next()) {
            Customer customer = new Customer();
            customer.setCustomerId((UUID) rs.getObject("customer_id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setEmail(rs.getString("email"));
            customer.setPhone(rs.getString("phone"));
            customer.setAddress(rs.getString("address"));

            // Adding customer to a list.
            customers.add(customer);
        }

        // Returning all customers.
        return customers;
    }
}
