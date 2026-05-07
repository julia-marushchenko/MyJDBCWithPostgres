package com.myjdbc.dao;

import com.myjdbc.entity.Vendor;
import com.myjdbc.util.DatabaseUtils;
import java.util.List;
import java.sql.*;
import java.util.*;



/**
 * VendorDao class.
 */
public class VendorDao implements DAO<Vendor, UUID> {

    // Constants to store requests to database.
    private static final String GET_ALL = "select * from vendors";
    private static final String CREATE = "insert into vendors (vendors_id, name, contact, phone, email," +
            "address values (?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "select vendor_id, name, contact, phone, email, address " +
            "from vendors where vendor_id = ?";
    private static final String UPDATE = "update vendors set name = ?, contact = ?, phone = ?, email = ?, " +
            "address = ? where vendor_id = ?";

    private static final String DELETE = "delete from vendors where vendorr_id = ?";
    private static final String GET_All_PAGED = "select vendor_id, name, contact, phone, email, address " +
            "from vendors order by name LIMIT ? OFFSET ?";

    @Override
    public List getAll() {

        // Creating a list to store all vendors.
        List<Vendor> vendors = new ArrayList<>();

        // Creating a connection to database.
        Connection connection = DatabaseUtils.getConnection();

        // Execution GET_ALL statement.
        try(Statement statement = connection.createStatement()) {

            // Getting Result Set.
            ResultSet rs = statement.executeQuery(GET_ALL);
            vendors = this.processResultSet(rs);
        } catch (SQLException ex) {

            // Printing stack of exception.
            ex.printStackTrace();
        }
        return vendors;
    }

    // Method to create a new vendor.
    @Override
    public Vendor create(Vendor entity) {

        // Generating UUID randomly.
        UUID vendorId = UUID.randomUUID();

        // Creating connection to database.
        Connection connection = DatabaseUtils.getConnection();

        try {
            // Making connection transactional.
            connection.setAutoCommit(false);

            // Creating Prepared statement.
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setObject(1, vendorId);
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getContact());
            statement.setString(4, entity.getPhone());
            statement.setString(5, entity.getEmail());
            statement.setString(6, entity.getAddress());
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

        Optional<Vendor> vendor = this.getOne(vendorId);

        // Checking if data was saved.
        if (!vendor.isPresent()) {
            return null;
        }

        return vendor.get();
    }

    // Method to get one vendor by its id.
    @Override
    public Optional getOne(UUID uuid) {

        // Creating prepared statement to get vendor by its id.
        try(PreparedStatement statement = DatabaseUtils.getConnection().prepareStatement(GET_ONE)) {

            // Setting values of parameters of prepared statement to get vendor by its id.
            statement.setObject(1, uuid);

            // Getting data from database.
            ResultSet rs = statement.executeQuery();
            List<Vendor> vendors = this.processResultSet(rs);
            if(vendors.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(vendors.get(0));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Returns Optional.empty.
        return Optional.empty();
    }

    // Method to update vendor entity.
    @Override
    public Vendor update(Vendor vendor) {

        // Creation a connection.
        Connection connection = DatabaseUtils.getConnection();

        // Trying to write to database.
        try {
            // Creating Prepared statement.
            PreparedStatement statement = connection.prepareStatement(CREATE);

            // Making transactional connection.
            connection.setAutoCommit(false);

            // Creating Prepared statement.
            statement.setObject(1, vendor.getVendorId());
            statement.setString(2, vendor.getName());
            statement.setString(3, vendor.getContact());
            statement.setString(4, vendor.getPhone());
            statement.setString(5, vendor.getEmail());
            statement.setString(6, vendor.getAddress());
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
        return vendor;
    }

    // Method to delete vendor by its id.
    @Override
    public void delete(UUID uuid) {

        // Creating a connection to database.
        Connection connection = DatabaseUtils.getConnection();

        // Deleting a vendor.
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

    // Method to store all vendors in buffer.
    private List<Vendor> processResultSet(ResultSet rs) throws SQLException {
        // Creating a list to store customers.
        List<Vendor> vendors = new ArrayList<>();

        // Iterating through all customers.
        while (rs.next()) {
            Vendor vendor = new Vendor();
            vendor.setVendorId((UUID) rs.getObject("vendor_id"));
            vendor.setName(rs.getString("name"));
            vendor.setContact(rs.getString("contact"));
            vendor.setPhone(rs.getString("phone"));
            vendor.setEmail(rs.getString("email"));
            vendor.setAddress(rs.getString("address"));

            // Adding vendor to a list.
            vendors.add(vendor);
        }

        // Returning all vendors.
        return vendors;
    }
}
