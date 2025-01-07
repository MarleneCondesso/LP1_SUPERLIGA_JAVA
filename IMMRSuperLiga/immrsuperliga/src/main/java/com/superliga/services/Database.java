/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mstevz
 */
public class Database {
            
    private String driver;
    private String connectionString;
    private String url;
    private String server;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    
    private static Database singleton;
    
    private static Connection connection;
    
    
    public static Database getInstance(){
        if(singleton == null){
            singleton = fromProperties();
        }
        
        return singleton;
    }
    
    private static Database fromProperties(){
            
        Database db = null;
        Properties databaseProperties = new Properties();

        try {
            
            FileReader fr = new FileReader(new File("src/main/java/resources/database.properties"));
        
            databaseProperties.load(fr);   
            
            db = new Database( databaseProperties.getProperty("db.driver.name"),
                               databaseProperties.getProperty("db.server"),
                               Integer.parseInt(databaseProperties.getProperty("db.server.port")),
                               databaseProperties.getProperty("db.name"),
                               databaseProperties.getProperty("db.user"), 
                               databaseProperties.getProperty("db.password"));
            
            fr.close();
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return db;
    }

    private Database(String driver, String server, int port, String database, String username, String password) {
        setDriver(driver);
        setServer(server);
        setPort(port);
        setDatabaseName(database);
        setUsername(username);
        setPassword(password);
        setUrl();
    }

    private Database() { }
    
    public Connection connect(){
        try {
            //Class.forName(driverFqdn).newInstance();
            connection = DriverManager.getConnection(getUrl(), username, password);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return connection;
    }
    
    public void disconnect(){
        try{
            connection.close();
        }
        catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public String getConnectionString() {
        return connectionString;
    }
    
    public String getUrl(){
        return url;
    }
    
    public String getDriver() {
        return driver;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
    
    private String getUsername() {
        return username;
    }

    private String getPassword() {
        return password;
    }
    
    private void setDriver(String driver) {
        this.driver = driver;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setServer(String server) {
        this.server = server;
    }

    private void setPort(int port) {
        this.port = port;
    }

    private void setUrl() {
        this.url = "jdbc:" 
                   + getDriver() 
                   + "://" 
                   + getServer() + ':' + getPort() + ';'
                   + "databaseName=" + getDatabaseName();
    }

    private void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
    
    
}
