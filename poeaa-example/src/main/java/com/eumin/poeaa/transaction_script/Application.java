package com.eumin.poeaa.transaction_script;

import com.eumin.poeaa.transaction_script.gateway.RevenueRecognitionGateway;
import com.eumin.poeaa.transaction_script.service.RecognitionService;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(final String[] args) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

            RevenueRecognitionGateway revenueRecognitionGateway = new RevenueRecognitionGateway(connection);
            RecognitionService recognitionService = new RecognitionService(revenueRecognitionGateway);

            Undertow server = Undertow.builder()
                    .addHttpListener(8080, "localhost")
                    .setHandler(new HttpHandler() {
                        @Override
                        public void handleRequest(final HttpServerExchange exchange) throws Exception {
                            recognitionService.calculateRevenueRecognitions(1);
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                            exchange.getResponseSender().send("Hello World");
                        }
                    }).build();
            server.start();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.err.println(e);
            }
        }
    }
}
