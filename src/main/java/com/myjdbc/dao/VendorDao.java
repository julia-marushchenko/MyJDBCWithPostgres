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
        return List.of();
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

    @Override
    public void delete(UUID uuid) {

    }
}
