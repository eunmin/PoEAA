package com.eunmin.poeaa.ex2.service;

import com.eumin.poeaa.ex1.transaction_script.gateway.table_data_gateway.RevenueRecognitionGateway;
import com.eumin.poeaa.ex1.transaction_script.util.MfDate;
import com.eumin.poeaa.ex1.transaction_script.util.Money;
import com.eunmin.poeaa.ex2.gateway.PersonGateway;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonService {
    private PersonGateway db;

    public PersonService(PersonGateway db) {
        this.db = db;
    }
}
