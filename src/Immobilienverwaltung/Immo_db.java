package Immobilienverwaltung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        String query = "INSERT INTO immobilien (immobiliennummer,wohnlage_id,eigentuemer_id,strasse, plz, ort) VALUES (null,";
        query = query + "'"+(wohnlage_id)+"'," + "'"+(eigentuemer_id)+"'," 
                + "'"+strasse+"'," + "'"+plz+"'," + "'"+ort+"');";
        openDB();
        insertDB(query);
        closeDB();        
    }
    /**
     * Eine neue Wohnung in die Datenbank speichern
     * @param wohnungs_id String Kennzeichnung der Wohnung
     * @param immobiliennummer Integer Immobiliennummer
     * @param mieterID Integer ID des Mieters
     * @param qm Double Angabe der Quadratmeteranzahl
     * @param zimmeranzahl Integer Anzahl der Zimmer
     * @param kuechen_id Integer ID der Küchenausstattung
     * @param heizungs_id Integer ID der Heizungsausstattung
     * @param bad_id Integer ID der Badausstattung
     * @param zusatzausstattung String zusätzliche Ausstattung
     * @param kaltmiete Double Kaltmiete
     * @param nebenkosten Double Nebenkosten
     */
    public void saveNewWohnung(String wohnungs_id, int immobiliennummer, 
            int mieterID, double qm, int zimmeranzahl, int kuechen_id,
            int heizungs_id, int bad_id, String zusatzausstattung,
            double kaltmiete, double nebenkosten)
    {
        String query ="INSERT INTO wohnungen VALUES ('";
        query = query + wohnungs_id+"',";
        query = query + "'"+immobiliennummer+"',";
        if (mieterID == 0)
        {
            query = query + "null,";
        }
        else
        {
            query = query +"'"+mieterID+"',";
        }
        query = query + "'"+qm+"',";
        query = query + "'"+zimmeranzahl+"',";
        query = query + "'"+kuechen_id+"',";
        query = query + "'"+heizungs_id+"',";
        query = query + "'"+bad_id+"',";
        query = query + "'"+zusatzausstattung+"',";
        query = query + "'"+kaltmiete+"',";
        query = query + "'"+nebenkosten+"');";
        openDB();
        insertDB(query);
        closeDB();         
    }
    /**
     * Auslesen der Anschrift einer Immobilie
     * @param immoNummer Integer Immobiliennummer
     * @return anschrift String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getImmoAnschrift(int immoNummer)
    {
        String anschrift = "";
        String query = "SELECT CONCAT (Strasse,' ',plz,' ', ort) AS anschrift FROM immobilien where immobiliennummer = "+immoNummer;
        openDB();
        resultSet = selectDB(query); 
        try
        {
            while (resultSet.next())
            {
                anschrift = resultSet.getString("anschrift");
            }
        }
        catch(SQLException se)
        {
        }     
        closeDB();     
        return anschrift;
    }
    /**
     * Hinzufügen eines neuen Ausstattungsmerkmales
     * @param ausstattungsbez String Neues Ausstattungsbezeichnung
     * @param tabelle String Tabelle der neuen Ausstattungsbezeichnung
     */
    public void setAusstattung(String ausstattungsbez, String tabelle)
    {
        String query = "INSERT INTO "+tabelle+" VALUES (null, '"
                + ausstattungsbez + "');";
        openDB();
        insertDB(query);
        closeDB();       
    }
    /**
     * Ermitteln der eingetragenen Wohnungen einer Immobilie
     * Für jede Wohnung wird ein Wohnungsobjekt erstellt und in 
     * einer ArrayList abgelegt.
     * @param aktuelleImmobilie Integer ID der Immobilie
     * @return wohnungen ArrayList mit Wohnungsobjekten
     */
    public ArrayList getWohnungsUebersicht(int aktuelleImmobilie)
    {
        String wohnungsid, name, kuechenbezeichnung, heizungbezeichnung,
                badbezeichnung, zusatzausstattung;
        double qm,zimmeranzahl, kaltmiete, nebenkosten;
        ArrayList wohnungen = new ArrayList();
        String query = "SELECT wohnungs_id, Concat(nachname,' ',vorname) "
                + "as name, qm, zimmeranzahl, kuechenbezeichnung, "
                + "heizungbezeichnung,badbezeichnung, zusatzausstattung,"
                + " kaltmiete, nebenkosten "
                + "FROM wohnungen LEFT JOIN mieter ON wohnungen.mieter_ID "
                + "= mieter.mieter_ID "
                + "LEFT JOIN bad ON bad.bad_id = wohnungen.bad_id "
                + "LEFT JOIN kuechen on kuechen.kuechen_id "
                + "= wohnungen.kuechen_id "
                + "LEFT JOIN heizung on heizung.heizungs_id = "
                + "wohnungen.heizungs_id  "
                + "WHERE immobiliennummer = "+aktuelleImmobilie;
        openDB();
        resultSet = selectDB(query); 
        try
        {
            while (resultSet.next())
            {
                wohnungsid = resultSet.getString("wohnungs_id");
                name = resultSet.getString("name");
                qm = resultSet.getDouble("qm");
                zimmeranzahl = resultSet.getDouble("zimmeranzahl");
                kuechenbezeichnung = resultSet.getString("kuechenbezeichnung");
                heizungbezeichnung = resultSet.getString("heizungbezeichnung");
                badbezeichnung = resultSet.getString("badbezeichnung");
                zusatzausstattung = resultSet.getString("zusatzausstattung");
                kaltmiete = resultSet.getDouble("kaltmiete");
                nebenkosten = resultSet.getDouble("nebenkosten");
                Immo_wohnungen temp = new Immo_wohnungen(wohnungsid, name, qm,
                        zimmeranzahl, kuechenbezeichnung, heizungbezeichnung,
                        badbezeichnung, zusatzausstattung, kaltmiete, nebenkosten);
                wohnungen.add(temp);
            }
        }
        catch(SQLException se)
        {
        }     
        closeDB();        
        return wohnungen;
    }
    /**
     * Abspeichern eines neuen Mieters in die Datenbank
     * @param titel Integer Index der JComboBox Titel
     * @param anrede Integer Index der JComboBox Anrede
     * @param nachname String Nachname des Mieters
     * @param vorname String Vorname des Mieters
     * @param strasse String Strasse des Mieters
     * @param plz String Postleitzahl des Mieters
     * @param ort String Ort des Mieters
     * @param geburtstag String Geburtsdatum des Mieters
     * @param mieter_seid String Seid wann Mieter
     * @param telefon String Telefon des Mieters
     * @param eMail String eMail des Mieters
     */
    public void saveNewMieter(int titel, int anrede, String nachname,
            String vorname, String strasse, String plz, String ort,
            String geburtstag, String mieter_seid, String telefon,
            String eMail)
    {
        String query = "INSERT INTO mieter (mieter_ID, titel, anrede,"
                + "nachname, vorname, strasse, plz, ort, geburtstag,"
                + "mieter_seid, telefon, email) VALUES (null, ";
        if (titel==0)
        {
            query = query + "null,";
        }
        else
        {
            query = query +"'"+(titel)+"',";
        }
        query = query + "'"+anrede+"'," + "'"+nachname+"'," 
                + "'"+vorname+"'," + "'"+strasse+"'," + "'"+plz+"'," 
                + "'"+ort+"'," + "'"+geburtstag+"','"+mieter_seid+"'," 
                + "'"+telefon+"'," + "'"+eMail+"');";   
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
    public String getNewMieterNummer()
    {
        String mieterNr = "";
        String query = "SELECT Count(*) AS anzahl FROM mieter";
        openDB();
        resultSet = selectDB(query); 
        try
        {
            while (resultSet.next())
            {
                mieterNr = resultSet.getString("anzahl");
            }
        }
        catch(SQLException se)
        {
        }     
        closeDB();
        return mieterNr;        
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
        query = "SELECT CONCAT (nachname,' ',vorname) AS bez FROM eigentuemer";
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
     * Vorhandene Mieter auslesen für Auswahlfeld
     * @return mieter String[] mit vorhanden Mieter mit Vor- und Nachnamen
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String[] getMieter()
    {
        int anzahl = 0;
        String query = "SELECT Count(*) FROM mieter";
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
        String[] mieter = new String[(anzahl)+2];
        query = "SELECT CONCAT (nachname,' ',vorname) AS bez FROM mieter";
        mieter[0] = "Bitte wählen...";
        resultSet = selectDB(query); 
        try
        {
            int zaehler = 1;
            while (resultSet.next())
            {
                mieter[zaehler] = resultSet.getString("bez");
                zaehler++;
            }
            mieter[zaehler] = "Neue Auswahl hinzufügen";
        }
        catch(SQLException se)
        {
        }     
        closeDB();
        return mieter;        
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
