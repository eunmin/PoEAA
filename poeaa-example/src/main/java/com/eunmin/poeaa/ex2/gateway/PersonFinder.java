package com.eunmin.poeaa.ex2.gateway;

import com.eunmin.poeaa.ex2.Registry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonFinder {
    private final static String findStatementString =
            "SELECT id, lastname, firstname, number_of_dependents " +
                    " from people " +
                    " where id = ?";

    private Connection db;

    public PersonGateway find(Long id) {
        PersonGateway result = (PersonGateway) Registry.getPerson(id);
        if (result != null) {
            return result;
        }
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = db.prepareStatement(findStatementString);
            findStatement.setLong(1, id.longValue());
            rs = findStatement.executeQuery();
            rs.next();
            result = PersonGateway.load(rs);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
