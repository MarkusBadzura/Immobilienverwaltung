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
     * Neue Immobilie abspeichern.
     * @param wohnlage_id int JComboBox-Index Wohnlage
     * @param eigentuemer_id int JComboBox-Index Eigentümer
     * @param strasse String Strasse der Immobilie
     * @param plz String Postleitzahl der Immobilie
     * @param ort String Ort der Immobilie
     * @author Markus Badzura
     * @since 1.0.001
     */
    public void saveNewImmobilie(int wohnlage_id, int eigentuemer_id, 
            String strasse, String plz, String ort)
    {
        String query = "INSERT INTO immobilien (immboliliennummer,wohnlage_id,eigentuemer_id,strasse, plz, ort) VALUES (null,";
        query = query + "'"+(wohnlage_id)+"'," + "'"+(eigentuemer_id)+"'," 
                + "'"+strasse+"'," + "'"+plz+"'," + "'"+ort+"');";
        System.out.println(query);
        openDB();
        insertDB(query);
        closeDB();        
    }
    /**
     * Neuen Eigentümer abspeichern
     * @param titel int JComboBox-Index Titel
     * @param anrede int JComboBox-Index Anrede
     * @param nachname String Nachname des Eigentümers
     * @param vorname String Vorname des Eigentümers
     * @param strasse String Strasse des Eigentümers
     * @param plz String Postleitzahl des Eigentümers
     * @param ort String Ort des Eigentümers
     * @param gebtag String Geburtstag des Eigentümers
     * @param telefon String Telefonnummer des Eigentümers
     * @param eMail String eMail-Adresse des Eigentümers
     */
    public void saveNewEigentuemer(int titel, int anrede, String nachname, String vorname,
            String strasse, String plz, String ort, String gebtag, String telefon,
            String eMail)
    {
        String query ="INSERT INTO eigentuemer(eigentuemer_id,titel,anrede,nachname,"
                + "vorname,strasse,plz,ort,geburtstag,telefon,eMail) VALUES (null,";
        if (titel==0)
        {
            query = query + "null,";
        }
        else
        {
            query = query +"'"+(titel)+"',";
        }
        query = query + "'"+(anrede)+"'," + "'"+nachname+"'," + "'"+vorname+"',"
                + "'"+strasse+"'," + "'"+plz+"'," + "'"+ort+"'," + "'"+gebtag+"'," 
                + "'"+telefon+"'," + "'"+eMail+"');";
                    System.out.println(query);
            openDB();
            insertDB(query);
            closeDB();    

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
        resultSet = selectDB(query); 
        try
        {
            while (resultSet.next())
            {
                immoNr = resultSet.getString("Count(*)");
            }
        }
        catch(SQLException se)
        {
        }     
        closeDB();
        return immoNr;
    }
    /**
     * Ermitteln Anzahl der Eigentümer in der Datenbank, um beim Speichern
     * eines neuen Eigentümers die Folge-ID anzeigen zu lassen.
     * @return eigentuemerNr String Anzahl der Eigentümer
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getNewEigentuemerNummer()
    {
        String eigentuemerNr = "";
        String query = "SELECT Count(*) AS anzahl FROM eigentuemer";
        openDB();
        resultSet = selectDB(query); 
        try
        {
            while (resultSet.next())
            {
                eigentuemerNr = resultSet.getString("anzahl");
            }
        }
        catch(SQLException se)
        {
        }     
        closeDB();
        return eigentuemerNr;
    }    
    /**
     * Vorhandene Eigentümer auslesen für Auswahlfelder
     * @return eigentuemer String[] mit allen Eigentümern
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String[] getEigentuemer()
    {
        int anzahl = 0;
        String query = "SELECT Count(*) FROM eigentuemer";
        openDB();
        ResultSet resultSet = selectDB(query); 
        try
        {
            while (resultSet.next())
            {
                anzahl = resultSet.getInt("Count(*)");
            }
        }
        catch(SQLException se)
        {
        }     
        String[] eigentuemer = new String[(anzahl)+2];
//        query = "SELECT Nachname AS bez FROM eigentuemer";
        query = "SELECT CONCAT (nachname,' ',vorname) AS bez FROM eigentuemer";
//        SELECT CONCAT(LEFT(firstname, 1), LEFT(lastname, 2))  FROM users;
        eigentuemer[0] = "Bitte wählen...";
        resultSet = selectDB(query); 
        try
        {
            int zaehler = 1;
            while (resultSet.next())
            {
                eigentuemer[zaehler] = resultSet.getString("bez");
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
     * @param tabelle String mit Tabellenname (wohnlage, heizung, küchen, bad, anrede, titel)
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String[] getBezeichnungen(String tabelle)
    {
        int anzahl = 0;
        String query = "SELECT Count(*) FROM "+tabelle;
        openDB();
        resultSet = selectDB(query); 
        try
        {
            while (resultSet.next())
            {
                anzahl = resultSet.getInt("Count(*)");
            }
        }
        catch(SQLException se)
        {
        }     
        String[] bezeichnungen = new String[(anzahl)+2];
        bezeichnungen[0] = "Bitte wählen ...";
        query = "SELECT "+tabelle+"bezeichnung AS bez FROM "+tabelle;
        resultSet = selectDB(query); 
        try
        {
            int zaehler = 1;
            while (resultSet.next())
            {
                bezeichnungen[zaehler] = resultSet.getString("bez");
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
//            JOptionPane.showMessageDialog(null,"Datenbankanfrage kann nicht verarbeitet werden.",
//                    "Datenbankfehler",JOptionPane.OK_OPTION);            
        }
        return resultSet;
    }    
    /**
     * Insert-Anweisung ausführen
     * @param query Insert-Anweisung
     * @author Markus Badzura
     * @version 1.0.001
     */
    private void insertDB(String query)
    {
        try
        {
            statement = connection.createStatement();
            statement.execute(query);
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null,"Speicherung konnte nicht durchgeführt werden.",
                    "Datenbankfehler",JOptionPane.OK_OPTION); 
        }
    }    
}
