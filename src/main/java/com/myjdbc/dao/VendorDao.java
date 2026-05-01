package com.myjdbc.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * VendorDao class.
 */
public class VendorDao implements DAO {

    // Constants to store requests to database.
    private static final String GET_ALL = "select * from vendor";
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

    @Override
    public Object create(Object entity) {
        return null;
    }

    @Override
    public Optional getOne(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Object update(Object entity) {
        return null;
    }

    processResultSet(

    @Override
    public void delete(UUID uuid) {

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
