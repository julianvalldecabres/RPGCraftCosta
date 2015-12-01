/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftcosta.jailrules.rpgcraftcosta.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jail
 */
public class ConnectionDB implements Runnable {

    private Connection conn;
    private String url;
    private String port;
    private String username;
    private String password;
    private String database;

    /**
     *
     */
    public void connect() {
        try {

            conn = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + database, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    public void createTables() {
        //
    }
}
