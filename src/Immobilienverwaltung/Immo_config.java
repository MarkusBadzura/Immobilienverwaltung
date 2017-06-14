package Immobilienverwaltung;

import errorlog.Errorlog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

/**
 * PSA-Projekt Immobilienverwaltung
 * Config-Leser
 * @author Markus Badzura
 * @version 1.0.001
 */
public class Immo_config 
{
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Config-Variablen und Objekte                              //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private String HOSTNAME;
    private String PORT;
    private String USER;
    private String PASSWORD;
    private String DBNAME;
    private BufferedReader br;
    private String speicherort = "config.txt";
    private String inhalt = "";
    private Errorlog el = new Errorlog("m","");  
    
    /**
     * Konstruktor Configreader
     * @author Markus Badzura
     * @since 1.0.001
     */
    Immo_config()
    {
        readConfig();
    }
    
    /**
     * Config-Datei einlesen und Variablen setzen
     * @author Markus Badzura
     * @since 1.0.001
     */
    public void readConfig()
    {
        try
        {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(speicherort), StandardCharsets.UTF_8));
            while ((inhalt=br.readLine())!= null)    
            {
                if (!"".equals(inhalt))
                {
                    try
                    {
                        if("hostname:".equals(inhalt.substring(0,9).toLowerCase())) HOSTNAME = (inhalt.substring(10).trim());
                        if("port:".equals(inhalt.substring(0,5).toLowerCase())) PORT = (inhalt.substring(6).trim());
                        if("username:".equals(inhalt.substring(0,9).toLowerCase())) USER = (inhalt.substring(10).trim());
                        if("password:".equals(inhalt.substring(0,9).toLowerCase())) PASSWORD = (inhalt.substring(10).trim());
                        if("dbname:".equals(inhalt.substring(0,7).toLowerCase())) DBNAME = (inhalt.substring(8).trim());
                    }
                    catch(StringIndexOutOfBoundsException | NullPointerException e)
                    {
                        el.schreibe(e.toString(), "immo_config");
                    }                  
                }
            }                
        }
        catch(IOException e)
        {
            el.schreibe(e.toString(), "immo_config");
            JOptionPane.showMessageDialog(null, "Die Datei config.txt kann im "
                    + "Verzeichnis dieser Anwendung nicht gefunden werden.\n\n"
                    + "Bitte kopieren Sie die Datei config.txt in das "
                    + "Verzeichnis, in welchem sich auch diese Anwendung "
                    + "befindet, und starten Sie die Anwendung neu.",
            "Programm beenden", JOptionPane.OK_OPTION);
            System.exit(0);            
        }
    } 
    /**
     * Übergabe Hostname
     * @return HOSTNAME String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getHostname()
    {
        return HOSTNAME;
    }
    /**
     * Übergabe Portnummer
     * @return PORT String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getPort()
    {
        return PORT;
    }
    /**
     * Übergabe Datenbank-Username
     * @return USER String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getUser()
    {
        return USER;
    }
    /**
     * Übergabe Datenbank-Passwort
     * @return PASSWORD String
     * @author Markus BAdzura
     * @since 1.0.001
     */
    public String getPassword()
    {
        return PASSWORD;
    }
    /**
     * Übergabe Datenbankname
     * @return DBNAME
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getDBName()
    {
        return DBNAME;
    }
}
