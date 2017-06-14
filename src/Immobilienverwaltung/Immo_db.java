/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Immobilienverwaltung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * PSA-Projekt Immobilienverwaltung
 * Datenbankklasse
 * @author Markus Badzura
 * @since 1.0.001
 */
public class Immo_db 
{
    private String hostname;
    private String user;
    private String port;
    private String password;
    private String dbname;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;    
    /**
     * Konstruktor für Datenbankobjekt
     * @param hostname String Hostname
     * @param user String Username an der MySQL-Datenbank
     * @param port String Port der MySQL-Datenbank
     * @param password String Datenbankpasswort user
     * @param dbname String Datenbankname
     * @author Markus Badzura
     * @since 1.0.001
     */
    Immo_db(String hostname, String user, String port, String password, String dbname)
    {
        this.hostname = hostname;
        this.user = user;
        this.port = port;
        this.password = password;
        this.dbname = dbname;
    }
    /**
     * Vorhandene Immobilienanzahl ermitteln
     * @return immoNr String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getNewImmobiliennummer()
    {
        String immoNr = "";
        String query = "SELECT Count(*) FROM immobilien";
        openDB();
        ResultSet tempRs = selectDB(query); 
        try
        {
            while (tempRs.next())
            {
                immoNr = tempRs.getString("Count(*)");
            }
        }
        catch(SQLException se)
        {
        }     
        closeDB();
        return immoNr;
    }
    public String[] getEigentuemer()
    {
        int anzahl = 0;
        String query = "SELECT Count(*) FROM eigentümer";
        openDB();
        ResultSet tempRs = selectDB(query); 
        try
        {
            while (tempRs.next())
            {
                anzahl = tempRs.getInt("Count(*)");
            }
        }
        catch(SQLException se)
        {
        }     
        String[] eigentuemer = new String[(anzahl)+1];
        query = "SELECT Nachname AS bez FROM eigentümer";
        tempRs = selectDB(query); 
        try
        {
            int zaehler = 0;
            while (tempRs.next())
            {
                eigentuemer[zaehler] = tempRs.getString("bez");
                zaehler++;
            }
            eigentuemer[zaehler] = "Neue Auswahl hinzufügen";
        }
        catch(SQLException se)
        {
        }     
        closeDB();
        return eigentuemer;
    }    
    /**
     * Auslesen der bezeichnungen
     * @return bezeichnungen String[]
     * @param tabelle String mit Tabellenname (wohnlage, heizung, küchen, bad)
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String[] getBezeichnungen(String tabelle)
    {
        int anzahl = 0;
        String query = "SELECT Count(*) FROM "+tabelle;
        openDB();
        ResultSet tempRs = selectDB(query); 
        try
        {
            while (tempRs.next())
            {
                anzahl = tempRs.getInt("Count(*)");
            }
        }
        catch(SQLException se)
        {
        }     
        String[] bezeichnungen = new String[(anzahl)+1];
        query = "SELECT "+tabelle+"bezeichnung AS bez FROM "+tabelle;
        tempRs = selectDB(query); 
        try
        {
            int zaehler = 0;
            while (tempRs.next())
            {
                bezeichnungen[zaehler] = tempRs.getString("bez");
                zaehler++;
            }
            bezeichnungen[zaehler] = "Neue Auswahl hinzufügen";
        }
        catch(SQLException se)
        {
        }     
        closeDB();
        return bezeichnungen;
    }
    /**
     * Öffnen der Datenbankverbindung
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void openDB()
    {
        try
        {
            String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname; 
	    connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException se)
        {
            JOptionPane.showMessageDialog(null,"Datenbankverbindung kann nicht hergestellt werden",
                    "Datenbankfehler",JOptionPane.OK_OPTION);
        }
    }  
    /**
     * Datenbankverbindung schließen
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void closeDB()
    {
        try
        {
            statement.close();
            resultSet.close();
            connection.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Datenbankverbindung konnte nicht beendet werden",
                    "Datenbankfehler",JOptionPane.OK_OPTION);            
        }
    }    
    /**
     * Select-Anweisungen ausführen
     * @param query Select-Anweisung
     * @author Markus Badzura
     * @since 1.0.001
     */
    private ResultSet selectDB(String query)
    {
        try
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Datenbankanfrage kann nicht verarbeitet werden.",
                    "Datenbankfehler",JOptionPane.OK_OPTION);            
        }
        return resultSet;
    }    
}
