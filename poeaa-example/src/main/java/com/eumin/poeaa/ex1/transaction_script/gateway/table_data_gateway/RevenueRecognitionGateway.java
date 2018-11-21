package com.eumin.poeaa.ex1.transaction_script.gateway.table_data_gateway;

import com.eumin.poeaa.ex1.transaction_script.util.MfDate;
import com.eumin.poeaa.ex1.transaction_script.util.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RevenueRecognitionGateway {

    private static final  String findRecognitionsStatement =
            "SELECT amount " +
            " FROM revenueRecognitions" +
            " WHERE contract = ? AND recognizedOn <= ?";

    private static final String findContractStatement =
            "SELECT * " +
            " FROM contracts c, products p " +
            " WHERE ID = ? AND c.product = p.ID";

    private String insertRecognitionStatement =
            "INSERT INTO revenueRecognitions VALUES (?, ?, ?)";

    private Connection db;

    public RevenueRecognitionGateway(Connection db) {
        this.db = db;
    }

    public ResultSet findRecognitionsFor(long contractID, MfDate asOf) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(findRecognitionsStatement);
        stmt.setLong(1, contractID);
        stmt.setDate(2, asOf.toSqlDate());
        ResultSet result = stmt.executeQuery();
        return result;
    }

    public ResultSet findContract(long contratID) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(findContractStatement);
        stmt.setLong(1, contratID);
        ResultSet result = stmt.executeQuery();
        return result;
    }

    public void insertRecognition(long contractID, Money amount, MfDate asOf) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(insertRecognitionStatement);
        stmt.setLong(1, contractID);
        stmt.setBigDecimal(2, amount.amount());
        stmt.setDate(3, asOf.toSqlDate());
        stmt.executeUpdate();
    }
}
