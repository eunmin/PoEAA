package com.eunmin.poeaa.ex2.gateway;

import com.eunmin.poeaa.ex2.Registry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonGateway {
    private Long id;
    private String lastName;
    private String firstName;
    private int numberOfDependents;

    private Connection db;

    private static final String updateStatementString =
            "UPDATE people " +
                    " set lastname = ?, firstname = ?, number_of_dependents = ? " +
                    " where id = ?";

    public PersonGateway(Connection db) {
        this.db = db;
    }

    public PersonGateway(Long id, String lastName, String firstName, int numberOfDependents) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.numberOfDependents = numberOfDependents;
    }

    public static PersonGateway load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        PersonGateway result = (PersonGateway) Registry.getPerson(id);
        if (result != null) {
            return result;
        }
        String lastNameArg = rs.getString(2);
        String firstNameArg = rs.getString(3);
        int numDependentsArg = rs.getInt(4);
        result = new PersonGateway(id, lastNameArg, firstNameArg, numDependentsArg);
        Registry.addPerson(result);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getNumberOfDependents() {
        return numberOfDependents;
    }

    public void setNumberOfDependents(int numberOfDependents) {
        this.numberOfDependents = numberOfDependents;
    }

    public void update() {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = db.prepareStatement(updateStatementString);
            updateStatement.setString(1, lastName);
            updateStatement.setString(2, firstName);
            updateStatement.setInt(3, numberOfDependents);
            updateStatement.setInt(4, getId().intValue());
            updateStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
