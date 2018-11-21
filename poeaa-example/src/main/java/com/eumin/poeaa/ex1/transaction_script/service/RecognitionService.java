package com.eumin.poeaa.ex1.transaction_script.service;

import com.eumin.poeaa.ex1.transaction_script.gateway.table_data_gateway.RevenueRecognitionGateway;
import com.eumin.poeaa.ex1.transaction_script.util.MfDate;
import com.eumin.poeaa.ex1.transaction_script.util.Money;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecognitionService {
    private RevenueRecognitionGateway db;

    public RecognitionService(RevenueRecognitionGateway db) {
        this.db = db;
    }

    public Money recognizedRevenue(long contractNumber, MfDate asOf) {
        Money result = Money.dollars(0);
        try {
            ResultSet rs = db.findRecognitionsFor(contractNumber, asOf);
            while (rs.next()) {
                result = result.add(Money.dollars(rs.getBigDecimal("amount")));
            }
            return result;
        } catch (SQLException e) {
        }
        return null;
    }

    public void calculateRevenueRecognitions(long contractNumber) {
        try {
            ResultSet contracts = db.findContract(contractNumber);
            contracts.next();
            Money totalRevenue = Money.dollars(contracts.getBigDecimal("revenue"));
            MfDate recognitionDate = new MfDate(contracts.getDate("dateSigned"));
            String type = contracts.getString("type");
            if (type.equals("S")) {
                Money[] alloction = totalRevenue.allocate(3);
                db.insertRecognition(contractNumber, alloction[0], recognitionDate);
                db.insertRecognition(contractNumber, alloction[1], recognitionDate.addDays(60));
                db.insertRecognition(contractNumber, alloction[2], recognitionDate.addDays(90));
            } else if (type.equals("W")) {
                db.insertRecognition(contractNumber, totalRevenue, recognitionDate);
            } else if (type.equals("D")) {
                Money[] allocation = totalRevenue.allocate(3);
                db.insertRecognition(contractNumber, allocation[0], recognitionDate);
                db.insertRecognition(contractNumber, allocation[1], recognitionDate.addDays(30));
                db.insertRecognition(contractNumber, allocation[2], recognitionDate.addDays(60));
            }
        } catch (SQLException e) {
        }
    }
}
