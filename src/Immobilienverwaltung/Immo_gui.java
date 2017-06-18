package Immobilienverwaltung;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import hilfefenster.Hilfe;
import errorlog.Errorlog;
import java.awt.Color;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.html.HTMLEditorKit;

/**
 * PSA-Projekt Immobilienverwaltung
 * Startfenster
 * @author Markus Badzura
 * @version 1.0.001
 */
public class Immo_gui extends JFrame implements ActionListener, KeyListener, ItemListener
{
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration ImageIcon - Objekt und Bildschirmgröße                    //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////
    // Pfadangabe zum Image
    private static final URL URLICON = Immo_gui.class.getResource("Icon.gif");
    // Erstellen ImageIcon-Objekt
    private static final ImageIcon ICON = new ImageIcon(URLICON);
    private static final Dimension SCREENSIZE = java.awt.Toolkit.getDefaultToolkit().getScreenSize ();
    Immo_wohnungen tempIw;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Einbindung Templates Hilfefenster,Logdatei, Config-Reader             //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private Hilfe h = new Hilfe();
    private Errorlog el = new Errorlog("m","");
    private Immo_config config = new Immo_config();
    private Immo_db database;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklarationen MenuBar                                                 //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private JMenuBar jmb;
    private JMenu jm_allgemein, jm_datenbank, jm_immobilien, jm_mietzahlungen, jm_mieter,
            jm_wohnung, jm_kostenkonto, jm_hilfe;
    private JMenuItem jmi_allg_readLog, jmi_allgDb_bad, jmi_allgDb_kueche, jmi_allgDb_heizung,
            jmi_allgDb_sicherung, jmi_allg_beenden, jmi_immo_neu, jmi_immo_chEigentuemer,
            jmi_immo_kostenuebersicht, jmi_immo_mieteruebersicht, jmi_immoMz_aktuell,
            jmi_immoMz_Jahr, jmi_immoMz_zeitraum, jmi_immo_delete, jmi_immo_uebersicht,
            jmi_mieter_neu, jmi_mieter_end, jmi_mieter_change, jmi_whg_neu,
            jmi_whg_uebersicht, jmi_whg_changeAM, jmi_kkt_change, jmi_kkt_bka,
            jmi_hilfe_hilfe, jmi_hilfe_about;
    private Border bo_menu;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Ansicht Neue Immobilie                                    //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////       
    private JTabbedPane jtp_immobilie;
    private boolean immobilie;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Tab Neue Immobilie                                        //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private JPanel jp_immobilie;   
    private JTextField jtf_immoNr, jtf_immoStr, jtf_immoPlz, jtf_immoOrt;
    private JComboBox jcb_immoWohnlage, jcb_immoEigentuemer;
    private JLabel jl_immoNr, jl_immoStr, jl_immoPlz, jl_immoOrt, jl_immoUeber,
            jl_immoWohnlage, jl_immoEigentuemer;
    private JButton jb_immoNeu;
    private int aktuelleImmo;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Tab Neue Wohnung                                          //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private JPanel jp_wohnungen;
    private JLabel jl_whgImmoNummer, jl_whgImmoAnschrift, jl_whgId, jl_whgMieter, 
            jl_whgQm, jl_whgZimmer,jl_whgKuechenid, jl_whgHeizungid, jl_whgBadid, 
            jl_whgZusatz, jl_whgKaltmiete, jl_whgNebenkosten;
    private JComboBox jcb_whgMieter, jcb_whgKueche, jcb_whgHeizung, jcb_whgBad;
    private JTextPane jtp_whgUebersicht;
    private HTMLEditorKit eKit = new HTMLEditorKit();    
    private JTextField jtf_whgId, jtf_whgQm, jtf_whgZimmer, jtf_whgZusatz, 
            jtf_whgKaltmiete, jtf_whgNebenkosten;
    private JButton jb_whgSave, jb_whgClear;
    private ArrayList al_wohnungen;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Dialogfenster Programminformationen                       //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////     
    private JDialog showAbout;
    private JLabel jl_saTitel, jl_saAnlass, jl_saAuthor;  
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Dialogfenster Neuer Eigentümer                            //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////  
    private JDialog showNewEigentuemer;
    private JLabel jl_sneEigentuemerId, jl_sneTitel, jl_sneAnrede, jl_sneNachname,
            jl_sneVorname, jl_sneStrasse, jl_snePlz, jl_sneOrt, jl_sneGeburtstag,
            jl_sneTelefon, jl_sneEmail;
    private JButton jb_sneClear,jb_sneSave;
    private JTextField jtf_sneEigentuemerId, jtf_sneNachname, jtf_sneVorname, jtf_sneStrasse,
            jtf_snePlz, jtf_sneOrt, jtf_sneGeburtstag, jtf_sneTelefon, jtf_sneEmail;    
    private JComboBox jcb_sneTitel, jcb_sneAnrede;
    private LocalDate ld_sneGebtag;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Dialogfenster Neuer Mieter                                //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////  
    private JDialog showNewMieter;
    private JLabel jl_snmMieterId, jl_snmTitel, jl_snmAnrede, jl_snmNachname,
            jl_snmVorname, jl_snmStrasse, jl_snmPlz, jl_snmOrt, jl_snmGeburtstag,
            jl_snmTelefon, jl_snmEmail, jl_snmMieterSeid;
    private JButton jb_snmClear, jb_snmSave;
    private JTextField jtf_snmMieterId, jtf_snmNachname, jtf_snmVorname, jtf_snmStrasse,
            jtf_snmPlz, jtf_snmOrt, jtf_snmGeburtstag, jtf_snmTelefon, jtf_snmEmail,
            jtf_snmMieterSeid;    
    private JComboBox jcb_snmTitel, jcb_snmAnrede;
    private LocalDate ld_snmGebtag, ld_snmMieterSeid;  
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Dialogfenster Neue Ausstattung                            //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////     
    private JDialog showNewAusstattung;
    private JLabel jl_snaAusstattung;
    private JTextField jtf_snaAusstattung;
    private JButton jb_snaClear;
    private JButton jb_snaSave;
    //////////////////////////////////////////////////////////////////
    //                                                              //
    // Deklaration Kostenkonto                                      //
    //                                                              //
    ////////////////////////////////////////////////////////////////// 
    private JPanel jp_kkto;
    private JComboBox jcb_kktoImmo, jcb_kktoVorgang, jcb_kktoWohnung,
            jcb_kktoMieter;
    private JLabel jl_kktoImmobilie, jl_kktoDatum, jl_kktoGeschVorgang,
            jl_kktoVorgang, jl_kktoBetrag, jl_kktoWohnung, jl_kktoMieter,
            jl_kktoKaltmiete, jl_kktoNebenkosten, jl_kktoBetragKalt,
            jl_kktoBetragNeben, jl_kktoMiete, jl_kktoBetragMiete;
    private JTextField jtf_kktoDatum, jtf_kktoGeschVorgang, 
            jtf_kktoBetrag;
    private JButton jb_kktoClear, jb_kktoSave, jb_kktoOk;
    private boolean kostenkonto;
    /**
     * Setzen des Anwendungsfensters
     * @author Markus Badzura
     * @since 1.0.001
     */
    public void Immo_gui()
    {
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) 
        {
            el.schreibe(e.toString(), "Immo_gui");
        }
        this.setTitle("Immobilienverwaltung v1.0");
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });          
        this.setSize(SCREENSIZE);
        this.setIconImage(ICON.getImage());
        this.setMinimumSize(SCREENSIZE);
        config.readConfig();
        database = new Immo_db(config.getHostname(), config.getUser(), config.getPort(),
                config.getPassword(), config.getDBName());
        setMenuBar();
        setVisible(true);        
    }
     /**
     * Abfragedialog beim Beenden des Programmes, inclusive des Schließens
     * über ALT + F4 und dem Schließbutton über die Titelleiste.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void exit()
    {
        int result = JOptionPane.showConfirmDialog(null, "Möchten Sie wirklich beenden?",
                "Programm beenden", JOptionPane.YES_NO_OPTION);
        switch (result)
        {
            case JOptionPane.YES_OPTION:
                System.exit(0);
        }
    } 
     /**
     * Abfragedialog beim Beenden des Programmes, inclusive des Schließens
     * über ALT + F4 und dem Schließbutton über die Titelleiste.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void exitDialog(JDialog name)
    {
        int result = JOptionPane.showConfirmDialog(null, "Möchten Sie die Eingabe wirklich ohne Speichern beenden?",
                "Dialog beenden", JOptionPane.YES_NO_OPTION);
        switch (result)
        {
            case JOptionPane.YES_OPTION:
                name.dispose();
        }
    }        
    /**
     * Setzen der Menüleiste
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setMenuBar()
    {
        jmb = new JMenuBar();
        bo_menu = new LineBorder(Color.BLACK);
        jmb.setBorder(bo_menu);
        jm_allgemein = new JMenu("Allgemein");
        jm_allgemein.setToolTipText("LogDatei, Datenbank und Beenden");
        jm_allgemein.setMnemonic('A');
        jm_datenbank = new JMenu("Datenbank");
        jm_datenbank.setMnemonic('D');
        jmi_allgDb_bad = new JMenuItem("Bad",'B');
        jmi_allgDb_bad.addActionListener(this);
        jmi_allgDb_heizung = new JMenuItem("Heizung",'H');
        jmi_allgDb_heizung.addActionListener(this);
        jmi_allgDb_kueche = new JMenuItem("Küche",'K');
        jmi_allgDb_kueche.addActionListener(this);
        jmi_allgDb_sicherung = new JMenuItem("sichern",'s');
        jmi_allgDb_sicherung.addActionListener(this);
        jmi_allg_beenden = new JMenuItem("Beenden",'B');
        jmi_allg_beenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
        jmi_allg_beenden.addActionListener(this);
        jmi_allg_readLog = new JMenuItem("Logdatei lesen",'L');
        jmi_allg_readLog.addActionListener(this);
        jm_datenbank.add(jmi_allgDb_bad);
        jm_datenbank.add(jmi_allgDb_kueche);
        jm_datenbank.add(jmi_allgDb_heizung);
        jm_datenbank.add(jmi_allgDb_sicherung);
        jm_allgemein.add(jmi_allg_readLog);
        jm_allgemein.add(jm_datenbank);
        jm_allgemein.add(jmi_allg_beenden);
        jm_hilfe = new JMenu("?");
        jmi_hilfe_about = new JMenuItem("Programminformation",'P');
        jmi_hilfe_about.addActionListener(this);
        jmi_hilfe_hilfe = new JMenuItem("Hilfe",'H');
        jmi_hilfe_hilfe.addActionListener(this);
        jmi_hilfe_hilfe.setAccelerator(KeyStroke.getKeyStroke("F1"));
        jm_hilfe.add(jmi_hilfe_hilfe);
        jm_hilfe.add(jmi_hilfe_about);
        jm_immobilien = new JMenu("Immobilien");
        jm_immobilien.setToolTipText("Immobilienverwaltung");
        jm_immobilien.setMnemonic('I');
        jm_mietzahlungen = new JMenu("Mietzahlungen");
        jm_mietzahlungen.setMnemonic('M'); 
        jmi_immoMz_aktuell = new JMenuItem("Aktueller Monat",'A');
        jmi_immoMz_aktuell.addActionListener(this);
        jmi_immoMz_aktuell.setAccelerator(KeyStroke.getKeyStroke("F6"));
        jmi_immoMz_Jahr = new JMenuItem("Laufendes Jahr",'L');
        jmi_immoMz_Jahr.addActionListener(this);
        jmi_immoMz_Jahr.setAccelerator(KeyStroke.getKeyStroke("F7"));
        jmi_immoMz_zeitraum = new JMenuItem("Spezifischer Zeitraum",'S');
        jmi_immoMz_zeitraum.addActionListener(this);
        jmi_immoMz_zeitraum.setAccelerator(KeyStroke.getKeyStroke("F8"));
        jm_mietzahlungen.add(jmi_immoMz_aktuell);
        jm_mietzahlungen.add(jmi_immoMz_Jahr);
        jm_mietzahlungen.add(jmi_immoMz_zeitraum);
        jmi_immo_chEigentuemer = new JMenuItem("Eigentümerwechsel",'E');
        jmi_immo_chEigentuemer.addActionListener(this);
        jmi_immo_delete = new JMenuItem("Immobilie löschen",'l');
        jmi_immo_delete.addActionListener(this);
        jmi_immo_kostenuebersicht = new JMenuItem("Kostenübersicht anzeigen",'K');
        jmi_immo_kostenuebersicht.addActionListener(this);
        jmi_immo_kostenuebersicht.setAccelerator(KeyStroke.getKeyStroke("F2"));
        jmi_immo_mieteruebersicht = new JMenuItem("Mieterübersicht anzeigen",'M');
        jmi_immo_mieteruebersicht.addActionListener(this);
        jmi_immo_mieteruebersicht.setAccelerator(KeyStroke.getKeyStroke("F3"));
        jmi_immo_uebersicht = new JMenuItem("Immobilienübersicht",'I');
        jmi_immo_uebersicht.addActionListener(this);
        jmi_immo_uebersicht.setAccelerator(KeyStroke.getKeyStroke("F4"));
        jmi_immo_neu = new JMenuItem("Neue Immobilie",'N');
        jmi_immo_neu.addActionListener(this);
        jmi_immo_neu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        jm_immobilien.add(jmi_immo_neu);
        jm_immobilien.add(jmi_immo_chEigentuemer);
        jm_immobilien.add(jmi_immo_kostenuebersicht);
        jm_immobilien.add(jmi_immo_mieteruebersicht);
        jm_immobilien.add(jm_mietzahlungen);
        jm_immobilien.add(jmi_immo_delete);
        jm_immobilien.add(jmi_immo_uebersicht);
        jm_kostenkonto = new JMenu("Kostenkonto");
        jm_kostenkonto.setToolTipText("Eingaben, Ausgaben, Betriebskostenabrechnung");
        jm_kostenkonto.setMnemonic('K');
        jmi_kkt_bka = new JMenuItem("Betriebskostenabrechnung",'B');
        jmi_kkt_bka.addActionListener(this);
        jmi_kkt_change = new JMenuItem("Kontobewegungen",'K');
        jmi_kkt_change.addActionListener(this);
        jmi_kkt_change.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, KeyEvent.CTRL_DOWN_MASK));
        jm_kostenkonto.add(jmi_kkt_change);
        jm_kostenkonto.add(jmi_kkt_bka);
        jm_mieter = new JMenu("Mieter");
        jm_mieter.setToolTipText("Mietverhältnisse, Mieter");
        jm_mieter.setMnemonic('M');
        jmi_mieter_change = new JMenuItem("Änderungen Mieter",'M');
        jmi_mieter_change.addActionListener(this);
        jmi_mieter_end = new JMenuItem("Mitverhältnis beendet",'b');
        jmi_mieter_end.addActionListener(this);
        jmi_mieter_neu = new JMenuItem("Neues Mietverhältnis",'N');
        jmi_mieter_neu.addActionListener(this);
        jm_mieter.add(jmi_mieter_neu);
        jm_mieter.add(jmi_mieter_end);
        jm_mieter.add(jmi_mieter_change);
        jm_wohnung = new JMenu("Wohnung");
        jm_wohnung.setToolTipText("Wohnungsverwaltung");
        jm_wohnung.setMnemonic('W');
        jmi_whg_changeAM = new JMenuItem("Änderung Ausstattungsmerkmal",'A');
        jmi_whg_changeAM.addActionListener(this);
        jmi_whg_neu = new JMenuItem("Neue Wohnung",'N');
        jmi_whg_neu.addActionListener(this);
        jmi_whg_uebersicht = new JMenuItem("Übersicht",'b');
        jmi_whg_uebersicht.addActionListener(this);
        jmi_whg_uebersicht.setAccelerator(KeyStroke.getKeyStroke("F5"));
        jm_wohnung.add(jmi_whg_neu);
        jm_wohnung.add(jmi_whg_uebersicht);
        jm_wohnung.add(jmi_whg_changeAM);
        jmb.add(jm_allgemein);
        jmb.add(jm_immobilien);
        jmb.add(jm_mieter);
        jmb.add(jm_wohnung);
        jmb.add(jm_kostenkonto);
        jmb.add(jm_hilfe);
        this.setJMenuBar(jmb);
    }
    /**
     * Öffnen der Hilfefunktion
     * Grundlage Template MBTemplate - package hilfefenster
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void showHelp()
    {
        h.setHilfe("Immobilienverwaltung",URLICON,"xml/help.xml");        
    }
    /**
     * Befüllen des Auswahlfeldes Eigentümer
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillEigentuemer()
    {
        String[] eigentuemer = database.getEigentuemer();
        for (int i = 0; i<eigentuemer.length;i++)
        {
            jcb_immoEigentuemer.addItem(eigentuemer[i]);
        }
        jcb_immoEigentuemer.addItemListener(this);
    }
    /**
     * Befüllen des Auswahlfeldes Wohnlage
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillWohnlage()
    {
        String[] wohnlage = database.getBezeichnungen("wohnlage");
        for (int i = 0; i<wohnlage.length;i++)
        {
            jcb_immoWohnlage.addItem(wohnlage[i]);
        }
        jcb_immoWohnlage.addItemListener(this);
    }
    /**
     * Befüllen des Auswahlfeldes Küche
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillKueche()
    {
        String[] kueche = database.getBezeichnungen("kuechen");
        for (int i = 0; i<kueche.length;i++)
        {
            jcb_whgKueche.addItem(kueche[i]);
        }
        jcb_whgKueche.addItemListener(this);
    }
    /**
     * Befüllen des Auswahlfeldes Heizung
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillHeizung()
    {
        String[] heizung = database.getBezeichnungen("heizung");
        for (int i = 0; i<heizung.length;i++)
        {
            jcb_whgHeizung.addItem(heizung[i]);
        }
        jcb_whgHeizung.addItemListener(this);        
    }
    /**
     * Befüllen des Auswahlfeldes Bad
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillBad()
    {
        String[] bad = database.getBezeichnungen("bad");
        for (int i = 0; i<bad.length;i++)
        {
            jcb_whgBad.addItem(bad[i]);
        }
        jcb_whgBad.addItemListener(this);
    }
    /**
     * Befüllen des Auswahlfeldes Mieter
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillMieter()
    {
        String[] mieter = database.getMieter();
        for (int i = 0; i<mieter.length;i++)
        {
            jcb_whgMieter.addItem(mieter[i]);
        }
        jcb_whgMieter.addItemListener(this);
    }
    /**
     * Befüllen des Auswahlfeldes Mieter, wenn im Eingabeformular
     * Kostenkonte Gesamtmiete ausgewählt wurde und die Wohnung
     * ausgewählt ist.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillKktoMieter()
    {
      notImplemented();
    }
    /**
     * Befüllen der Felder Kaltmiete, Nebenkosten und Gesamtmiete
     * im Eingabeformular Kostenkonto, wenn Vorgang Gesamtmiete ist
     * und die Wohnung ausgewählt wurde.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillKaltmieteNebenkosten()
    {
      notImplemented();
    }
    /**
     * Befüllen des Auswahlfeldes Vorgang im Eingabeformular Kosten-
     * konto
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillVorgang()
    {
      notImplemented();
    }
    /**
     * Befüllen des Auswahlfeldes Wohnung im Eingabeformular Kosten-
     * konto, wenn Vorgang Gesamtmiete
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillKktoWohnung()
    {
      notImplemented();
    }
    /**
     * Befüllen des Auswahlfeldes Immobilie im Eingabeformular
     * Kostenkonto
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillImmobilie()
    {
      String[] kkto_immobilie = database.getImmobilien();
      for (int i = 0; i<kkto_immobilie.length;i++)
      {
        jcb_kktoImmo.addItem(kkto_immobilie[i]);
      }
    }
    /**
     * Erstinitialisierung Übersicht aktuelle Immobilie bei Neuer Wohnung
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void fillWhgUebersicht()
    {
        al_wohnungen = database.getWohnungsUebersicht(aktuelleImmo);
        Immo_wohnungen iwTemp;
        String whgUebersicht = 
                "<html>"
                + "<table>"
                    + "<thead>"
                        + "<tr>"
                            + "<th>Wohnungsnummer</th>"
                            + "<th>Mieter</th>"
                            + "<th>qm</th>"
                            + "<th>Zimmer</th>"
                            + "<th>Kaltmiete</th>"
                            + "<th>Nebenkosten</th>"
                        + "</tr>"
                    + "<tbody>";
        for(int i = 0; i<al_wohnungen.size();i++)
        {
            iwTemp = (Immo_wohnungen) al_wohnungen.get(i);
            whgUebersicht = whgUebersicht +
                    "<tr><td>"+iwTemp.getWohnungsid()+"</td>"
                    + "<td>"+iwTemp.getMieter()+"</td>"
                    + "<td>"+iwTemp.getQm()+"</td>"
                    + "<td>"+iwTemp.getZimmer() +"</td>"
                    + "<td>"+iwTemp.getKaltmiete()+"</td>"
                    + "<td>"+iwTemp.getNebenkosten()+"</td></tr>";
        }
        whgUebersicht = whgUebersicht + "</tbody></table></html>";
        jtp_whgUebersicht.setText(whgUebersicht);
    }
    /**
     * Setzen der neuen Immobiliennummer in das Textfeld. Anzahl der Immo-
     * bilien wird über Datenbankabfrage ermittelt und dieser Wert wird um
     * 1 inkrementiert.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setImmobiliennummer()
    {
        int newImmoNummer = Integer.parseInt(database.getNewImmobiliennummer())+1;
        aktuelleImmo = newImmoNummer;
        jtf_immoNr.setText(String.valueOf(newImmoNummer));
    }
    /**
     * Setzen der neuen Eigentümer-Id in das Textfeld. Die Anzahl der Eigentümer
     * wird in einer Datenbankabfrage ermittelt und um 1 inkrementiert.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setNewEigentuemerId()
    {
        int newEigentuemerNummer = Integer.parseInt(database.getNewEigentuemerNummer())+1;
        jtf_sneEigentuemerId.setText(String.valueOf(newEigentuemerNummer));
    }
    /**
     * Setzen der neuen Mieter-ID in das Textfeld. Die Anzahl der
     * Mieter wird in einer Datenbankabfrage ermittelt und um 1
     * inkrementiert.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setNewMieterId()
    {
        int newMieterNummer = Integer.parseInt(database
        .getNewMieterNummer())+1;
        jtf_snmMieterId.setText(String.valueOf(newMieterNummer));
    }
    /**
     * Befüllen des Auswahlfeldes Titel
     * @param feld JComboBox welche mit dem Datenbankinhalt aus der Tabelle titel befüllt wird
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setTitel(JComboBox feld)
    {
        String[] inhalt = database.getBezeichnungen("titel");
        for (int i = 0;i<inhalt.length;i++)
        {
            feld.addItem(inhalt[i]);
        }
    }
    /**
     * Befüllen des Auswahlfeldes Anrede
     * @param feld JComboBox welche mit dem Datenbankinhalt aus der Tabelle anrede befüllt wird
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setAnrede(JComboBox feld)
    {
        String[] inhalt = database.getBezeichnungen("anrede");
        for (int i = 0;i<inhalt.length; i++)
        {
            feld.addItem(inhalt[i]);
        }
    }
    /**
     * Formularansicht für das Kostenkonto
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setKostenkonto()
    {
      removePanel();
      kostenkonto = true;
      int x1 = 10;
      int x2 = 220;
      int x3 = 430;
      int x4 = 640;
      int breite = 200;
      int hoehe = 25;      
      jp_kkto = new JPanel();
      jp_kkto.setSize(this.getSize());
      jp_kkto.setLayout(null);
      jl_kktoImmobilie = new JLabel("Immobilie: ",JLabel.RIGHT);
      jl_kktoImmobilie.setFont(jl_kktoImmobilie.getFont()
              .deriveFont(14f));
      jl_kktoImmobilie.setBounds(x1,10,breite,hoehe);
      jl_kktoDatum = new JLabel("Datum: ",JLabel.RIGHT);
      jl_kktoDatum.setFont(jl_kktoDatum.getFont().deriveFont(14f));
      jl_kktoDatum.setBounds(x1,40,breite,hoehe);
      jl_kktoGeschVorgang = new JLabel("Vorgangsbezeichnung: ",JLabel
              .RIGHT);
      jl_kktoGeschVorgang.setFont(jl_kktoGeschVorgang.getFont()
              .deriveFont(14f));
      jl_kktoGeschVorgang.setBounds(x1,70,breite,hoehe);
      jl_kktoVorgang = new JLabel("Konto: ",JLabel.RIGHT);
      jl_kktoVorgang.setFont(jl_kktoVorgang.getFont().deriveFont(14f));
      jl_kktoVorgang.setBounds(x1,100,breite,hoehe);
      jl_kktoBetrag = new JLabel("Betrag: ",JLabel.RIGHT);
      jl_kktoBetrag.setFont(jl_kktoBetrag.getFont().deriveFont(14f));
      jl_kktoBetrag.setBounds(x1,130,breite,hoehe);
      jb_kktoClear = new JButton("Zurücksetzen");
      jb_kktoClear.addActionListener(this);
      jb_kktoClear.setBounds(x1,200,breite,hoehe);
      jcb_kktoImmo = new JComboBox();
      jcb_kktoImmo.setFont(jcb_kktoImmo.getFont().deriveFont(14f));
      jcb_kktoImmo.setBounds(x2,10,breite,hoehe);
      fillImmobilie();
      jtf_kktoDatum = new JTextField();
      jtf_kktoDatum.setFont(jtf_kktoDatum.getFont().deriveFont(14f));
      jtf_kktoDatum.setBounds(x2,40,breite,hoehe);
      jtf_kktoGeschVorgang = new JTextField();
      jtf_kktoGeschVorgang.setFont(jtf_kktoGeschVorgang.getFont()
              .deriveFont(14f));
      jtf_kktoGeschVorgang.setBounds(x2,70,breite,hoehe);
      jcb_kktoVorgang = new JComboBox();
      jcb_kktoVorgang.setFont(jcb_kktoVorgang.getFont().deriveFont(14f));
      jcb_kktoVorgang.setBounds(x2,100,breite,hoehe);
      fillVorgang();
      jtf_kktoBetrag = new JTextField();
      jtf_kktoBetrag.setFont(jtf_kktoBetrag.getFont().deriveFont(14));
      jtf_kktoBetrag.setBounds(x2,130,breite,hoehe);
      jb_kktoSave = new JButton("Speichern");
      jb_kktoSave.addActionListener(this);
      jb_kktoSave.setBounds(x2,200,breite,hoehe);
      jl_kktoWohnung = new JLabel("Wohnung: ",JLabel.RIGHT);
      jl_kktoWohnung.setFont(jl_kktoWohnung.getFont().deriveFont(14f));
      jl_kktoWohnung.setBounds(x3,10,breite,hoehe);
      jl_kktoWohnung.setVisible(false);
      jcb_kktoWohnung = new JComboBox();
      jcb_kktoWohnung.setFont(jcb_kktoWohnung.getFont()
              .deriveFont(14f));
      jcb_kktoWohnung.setBounds(x4,10,breite,hoehe);
      fillKktoWohnung();
      jcb_kktoWohnung.setVisible(false);
      jl_kktoMieter = new JLabel("Mieter: ",JLabel.RIGHT);
      jl_kktoMieter.setFont(jl_kktoMieter.getFont().deriveFont(14f));
      jl_kktoMieter.setBounds(x3,40,breite,hoehe);
      jl_kktoMieter.setVisible(false);
      jcb_kktoMieter = new JComboBox();
      jcb_kktoMieter.setFont(jcb_kktoMieter.getFont().deriveFont(14f));
      jcb_kktoMieter.setBounds(x4,40,breite,hoehe);
      jcb_kktoMieter.setVisible(false);
      fillKktoMieter();
      jl_kktoKaltmiete = new JLabel("Kaltmiete: ",JLabel.RIGHT);
      jl_kktoKaltmiete.setFont(jl_kktoKaltmiete.getFont()
              .deriveFont(14f));
      jl_kktoKaltmiete.setBounds(x3,70,breite,hoehe);
      jl_kktoKaltmiete.setVisible(false);
      jl_kktoBetragKalt = new JLabel("");
      jl_kktoBetragKalt.setFont(jl_kktoBetragKalt.getFont()
              .deriveFont(14f));
      jl_kktoBetragKalt.setBounds(x4,70,breite,hoehe);
      jl_kktoBetragKalt.setVisible(false);
      jl_kktoNebenkosten = new JLabel("Nebenkosten: ",JLabel.RIGHT);
      jl_kktoNebenkosten.setFont(jl_kktoNebenkosten.getFont()
              .deriveFont(14f));
      jl_kktoNebenkosten.setBounds(x3,100,breite,hoehe);
      jl_kktoNebenkosten.setVisible(false);
      jl_kktoBetragNeben = new JLabel("");
      jl_kktoBetragNeben.setFont(jl_kktoBetragNeben.getFont()
              .deriveFont(14f));
      jl_kktoBetragNeben.setBounds(x4,100,breite,hoehe);
      jl_kktoBetragNeben.setVisible(false);
      jb_kktoOk = new JButton("Übernehmen");
      jb_kktoOk.addActionListener(this);
      jb_kktoOk.setBounds(x4,200,breite,hoehe);
      jb_kktoOk.setVisible(false);
      jl_kktoMiete = new JLabel("Gesamtmiete: ",JLabel.RIGHT);
      jl_kktoMiete.setFont(jl_kktoMiete.getFont().deriveFont(14f));
      jl_kktoMiete.setBounds(x3,130,breite,hoehe);
      jl_kktoMiete.setVisible(false);
      jl_kktoBetragMiete = new JLabel("");
      jl_kktoBetragMiete.setFont(jl_kktoBetragMiete.getFont()
              .deriveFont(14f));
      jl_kktoBetragMiete.setBounds(x4,130,breite,hoehe);
      jl_kktoBetragMiete.setVisible(false);
      jp_kkto.add(jl_kktoBetragMiete);
      jp_kkto.add(jl_kktoMiete);
      jp_kkto.add(jb_kktoOk);
      jp_kkto.add(jl_kktoBetragNeben);
      jp_kkto.add(jl_kktoNebenkosten);
      jp_kkto.add(jl_kktoBetragKalt);
      jp_kkto.add(jl_kktoKaltmiete);
      jp_kkto.add(jcb_kktoMieter);
      jp_kkto.add(jl_kktoMieter);
      jp_kkto.add(jcb_kktoWohnung);
      jp_kkto.add(jl_kktoWohnung);
      jp_kkto.add(jb_kktoSave);
      jp_kkto.add(jtf_kktoBetrag);
      jp_kkto.add(jcb_kktoVorgang);
      jp_kkto.add(jtf_kktoGeschVorgang);
      jp_kkto.add(jtf_kktoDatum);
      jp_kkto.add(jcb_kktoImmo);
      jp_kkto.add(jb_kktoClear);
      jp_kkto.add(jl_kktoBetrag);
      jp_kkto.add(jl_kktoVorgang);
      jp_kkto.add(jl_kktoGeschVorgang);
      jp_kkto.add(jl_kktoDatum);
      jp_kkto.add(jl_kktoImmobilie);
      this.add(jp_kkto);
      this.repaint();
//      this.setVisible(true);
    }
    /**
     * Anzeige der Wohnungen, wenn als Auswahlpunkt bei Vorgang
     * Gesamtmiete im Kostenkonto ausgewählt wurde
     * @param show boolean true für anzeigen, false für ausblenden
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setShowMiete(boolean show)
    {
      jl_kktoBetragMiete.setVisible(show);
      jl_kktoMiete.setVisible(show);
      jb_kktoOk.setVisible(show);
      jl_kktoBetragNeben.setVisible(show);
      jl_kktoNebenkosten.setVisible(show);
      jl_kktoBetragKalt.setVisible(show);
      jl_kktoKaltmiete.setVisible(show);
      jcb_kktoMieter.setVisible(show);
      jl_kktoMieter.setVisible(show);
      jcb_kktoWohnung.setVisible(show);
      jl_kktoWohnung.setVisible(show);
      if (show)
      {
        fillKaltmieteNebenkosten();
      }
    }
    /**
     * Panel für Tab Neue Wohnung
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setTabNewWohnung()
    {
        int x1 = 10;
        int x2 = SCREENSIZE.width/4;
        int x3 = SCREENSIZE.width/2;
        int breite = x2-20;
        int hoehe = 25;
        jp_wohnungen = new JPanel();
        jp_wohnungen.setSize(this.getSize());   
        jp_wohnungen.setLayout(null);
        jl_whgImmoNummer = new JLabel("Immobiliennummer: "+Integer.toString(aktuelleImmo));
        jl_whgImmoNummer.setFont(jl_whgImmoNummer.getFont().deriveFont(14f));
        jl_whgImmoNummer.setBounds(x1,10,breite,hoehe);
        jl_whgImmoAnschrift = new JLabel(database.getImmoAnschrift(aktuelleImmo));
        jl_whgImmoAnschrift.setFont(jl_whgImmoAnschrift.getFont().deriveFont(14f));
        jl_whgImmoAnschrift.setBounds(x2,10,breite,hoehe);
        jl_whgId = new JLabel("Wohnungskennzeichnung: ",JLabel.RIGHT);
        jl_whgId.setFont(jl_whgId.getFont().deriveFont(14f));
        jl_whgId.setBounds(x1,50,breite,hoehe);
        jl_whgMieter = new JLabel("Mieter: ",JLabel.RIGHT);
        jl_whgMieter.setFont(jl_whgMieter.getFont().deriveFont(14f));
        jl_whgMieter.setBounds(x1,80,breite,hoehe);
        jl_whgQm = new JLabel("Quadratmeter: ",JLabel.RIGHT);
        jl_whgQm.setFont(jl_whgQm.getFont().deriveFont(14f));
        jl_whgQm.setBounds(x1,110,breite,hoehe);
        jl_whgZimmer = new JLabel("Zimmer: ",JLabel.RIGHT);
        jl_whgZimmer.setFont(jl_whgZimmer.getFont().deriveFont(14f));
        jl_whgZimmer.setBounds(x1,140,breite,hoehe);
        jl_whgKuechenid = new JLabel("Küche: ",JLabel.RIGHT);
        jl_whgKuechenid.setFont(jl_whgKuechenid.getFont().deriveFont(14f));
        jl_whgKuechenid.setBounds(x1,170,breite,hoehe);
        jl_whgHeizungid = new JLabel("Heizung: ",JLabel.RIGHT);
        jl_whgHeizungid.setFont(jl_whgHeizungid.getFont().deriveFont(14f));
        jl_whgHeizungid.setBounds(x1,200,breite,hoehe);
        jl_whgBadid = new JLabel("Bad: ",JLabel.RIGHT);
        jl_whgBadid.setFont(jl_whgBadid.getFont().deriveFont(14f));
        jl_whgBadid.setBounds(x1,230,breite,hoehe);
        jl_whgZusatz = new JLabel("Zusatzausstattung: ",JLabel.RIGHT);        
        jl_whgZusatz.setFont(jl_whgZusatz.getFont().deriveFont(14f));
        jl_whgZusatz.setBounds(x1,260,breite,hoehe);
        jl_whgKaltmiete = new JLabel("Kaltmiete: ",JLabel.RIGHT);
        jl_whgKaltmiete.setFont(jl_whgKaltmiete.getFont().deriveFont(14f));
        jl_whgKaltmiete.setBounds(x1,290,breite,hoehe);
        jl_whgNebenkosten = new JLabel("Nebenkosten: ",JLabel.RIGHT);
        jl_whgNebenkosten.setFont(jl_whgNebenkosten.getFont().deriveFont(14f));
        jl_whgNebenkosten.setBounds(x1,320,breite,hoehe);
        jb_whgSave = new JButton("Speichern");
        jb_whgSave.setFont(jb_whgSave.getFont().deriveFont(14f));
        jb_whgSave.addActionListener(this);
        jb_whgSave.setBounds(x1,370,breite,hoehe);
        jtf_whgId = new JTextField();
        jtf_whgId.setFont(jtf_whgId.getFont().deriveFont(14f));
        jtf_whgId.setBounds(x2,50,breite,hoehe);
        jcb_whgMieter = new JComboBox();
        fillMieter();
        jcb_whgMieter.setFont(jcb_whgMieter.getFont().deriveFont(14f));
        jcb_whgMieter.setBounds(x2,80,breite,hoehe);
        jtf_whgQm = new JTextField();
        jtf_whgQm.setFont(jtf_whgQm.getFont().deriveFont(14f));
        jtf_whgQm.setBounds(x2,110,breite,hoehe);
        jtf_whgZimmer = new JTextField();
        jtf_whgZimmer.setFont(jtf_whgZimmer.getFont().deriveFont(14f));
        jtf_whgZimmer.setBounds(x2,140,breite,hoehe);
        jcb_whgKueche = new JComboBox();
        fillKueche();
        jcb_whgKueche.setFont(jcb_whgKueche.getFont().deriveFont(14f));
        jcb_whgKueche.setBounds(x2,170,breite,hoehe);
        jcb_whgHeizung = new JComboBox();
        fillHeizung();
        jcb_whgHeizung.setFont(jcb_whgHeizung.getFont().deriveFont(14f));
        jcb_whgHeizung.setBounds(x2,200,breite,hoehe);
        jcb_whgBad = new JComboBox();
        fillBad();
        jcb_whgBad.setFont(jcb_whgBad.getFont().deriveFont(14f));
        jcb_whgBad.setBounds(x2,230,breite,hoehe);
        jtf_whgZusatz = new JTextField();
        jtf_whgZusatz.setFont(jtf_whgZusatz.getFont().deriveFont(14f));
        jtf_whgZusatz.setBounds(x2,260,breite,hoehe);
        jtf_whgKaltmiete = new JTextField();
        jtf_whgKaltmiete.setFont(jtf_whgKaltmiete.getFont().deriveFont(14f));
        jtf_whgKaltmiete.setBounds(x2,290,breite,hoehe);
        jtf_whgNebenkosten = new JTextField();
        jtf_whgNebenkosten.setFont(jtf_whgNebenkosten.getFont().deriveFont(14f));
        jtf_whgNebenkosten.setBounds(x2,320,breite,hoehe);
        jb_whgClear = new JButton ("zurücksetzen");
        jb_whgClear.setFont(jb_whgClear.getFont().deriveFont(14f));
        jb_whgClear.addActionListener(this);
        jb_whgClear.setBounds(x2,370,breite,hoehe);
        jtp_whgUebersicht = new JTextPane();
        jtp_whgUebersicht.setEditorKit(eKit);
        jtp_whgUebersicht.setBounds(x3+20,10,x3-40,SCREENSIZE.height);
        fillWhgUebersicht();
        jp_wohnungen.add(jtp_whgUebersicht);
        jp_wohnungen.add(jb_whgClear);
        jp_wohnungen.add(jtf_whgNebenkosten);
        jp_wohnungen.add(jtf_whgKaltmiete);
        jp_wohnungen.add(jtf_whgZusatz);
        jp_wohnungen.add(jcb_whgBad);
        jp_wohnungen.add(jcb_whgHeizung);
        jp_wohnungen.add(jcb_whgKueche);
        jp_wohnungen.add(jtf_whgZimmer);
        jp_wohnungen.add(jtf_whgQm);
        jp_wohnungen.add(jcb_whgMieter);
        jp_wohnungen.add(jtf_whgId);
        jp_wohnungen.add(jb_whgSave);
        jp_wohnungen.add(jl_whgNebenkosten);
        jp_wohnungen.add(jl_whgKaltmiete);
        jp_wohnungen.add(jl_whgZusatz);
        jp_wohnungen.add(jl_whgBadid);
        jp_wohnungen.add(jl_whgHeizungid);
        jp_wohnungen.add(jl_whgKuechenid);
        jp_wohnungen.add(jl_whgZimmer);
        jp_wohnungen.add(jl_whgQm);
        jp_wohnungen.add(jl_whgMieter);
        jp_wohnungen.add(jl_whgId);
        jp_wohnungen.add(jl_whgImmoAnschrift);
        jp_wohnungen.add(jl_whgImmoNummer);  
    }
    /**
     * Panel für Tab Neue Immobilie
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setTabNewImmobilie()
    {
        int jlb = SCREENSIZE.width/3;
        int jlh = 25;
        int spx = jlb+20;
        jp_immobilie = new JPanel();
        jp_immobilie.setSize(this.getSize());
        jp_immobilie.setLayout(null);
        jl_immoUeber = new JLabel("Neue Immobilie anlegen",JLabel.CENTER);
        jl_immoUeber.setBounds(10,10,SCREENSIZE.width,jlh);
        jl_immoUeber.setFont(jl_immoUeber.getFont().deriveFont(18f));
        jl_immoUeber.setFont(jl_immoUeber.getFont().deriveFont(Font.BOLD));
        jl_immoNr = new JLabel("Immobiliennummer: ",JLabel.RIGHT);
        jl_immoNr.setFont(jl_immoNr.getFont().deriveFont(14f));
        jl_immoNr.setBounds(10,50,jlb,jlh);
        jl_immoStr = new JLabel("Straße: ",JLabel.RIGHT);
        jl_immoStr.setFont(jl_immoNr.getFont().deriveFont(14f));
        jl_immoStr.setBounds(10,90,jlb,jlh);
        jl_immoPlz = new JLabel("Postleitzahl: ",JLabel.RIGHT);
        jl_immoPlz.setFont(jl_immoPlz.getFont().deriveFont(14f));
        jl_immoPlz.setBounds(10,130,jlb,jlh);
        jl_immoOrt = new JLabel("Ort: ",JLabel.RIGHT);
        jl_immoOrt.setFont(jl_immoOrt.getFont().deriveFont(14f));
        jl_immoOrt.setBounds(10,170,jlb,jlh);
        jl_immoWohnlage = new JLabel("Wohnlage: ",JLabel.RIGHT);
        jl_immoWohnlage.setFont(jl_immoWohnlage.getFont().deriveFont(14f));
        jl_immoWohnlage.setBounds(10,210,jlb,jlh);
        jl_immoEigentuemer = new JLabel ("Eigentümer: ",JLabel.RIGHT);
        jl_immoEigentuemer.setFont(jl_immoEigentuemer.getFont().deriveFont(14f));
        jl_immoEigentuemer.setBounds(10,270,jlb,jlh);
        jtf_immoNr = new JTextField();
        jtf_immoNr.setFont(jtf_immoNr.getFont().deriveFont(14f));
        jtf_immoNr.setBounds(spx,50,jlb,jlh);
        jtf_immoNr.setEditable(false);
        setImmobiliennummer();
        jtf_immoStr = new JTextField();
        jtf_immoStr.setFont(jtf_immoStr.getFont().deriveFont(14f));
        jtf_immoStr.setBounds(spx,90,jlb,jlh);
        jtf_immoPlz = new JTextField();
        jtf_immoPlz.setFont(jtf_immoPlz.getFont().deriveFont(14f));
        jtf_immoPlz.setBounds(spx,130,jlb,jlh);
        jtf_immoOrt = new JTextField();
        jtf_immoOrt.setFont(jtf_immoOrt.getFont().deriveFont(14f));
        jtf_immoOrt.setBounds(spx,170,jlb,jlh);
        jcb_immoWohnlage = new JComboBox();
        jcb_immoWohnlage.setFont(jcb_immoWohnlage.getFont().deriveFont(14f));
        jcb_immoWohnlage.setBounds(spx,210,jlb,jlh);
        jcb_immoWohnlage.addItemListener(this);
        fillWohnlage();
        jcb_immoEigentuemer = new JComboBox();
        jcb_immoEigentuemer.setFont(jcb_immoEigentuemer.getFont().deriveFont(14f));
        jcb_immoEigentuemer.setBounds(spx,270,jlb,jlh);    
        fillEigentuemer();   
        jb_immoNeu = new JButton("Immobilie anlegen");
        jb_immoNeu.setBounds(spx,350,jlb,jlh);
        jb_immoNeu.addActionListener(this);
        jp_immobilie.add(jl_immoUeber);
        jp_immobilie.add(jl_immoNr);
        jp_immobilie.add(jl_immoStr);
        jp_immobilie.add(jl_immoPlz);
        jp_immobilie.add(jl_immoOrt);
        jp_immobilie.add(jl_immoWohnlage);
        jp_immobilie.add(jl_immoEigentuemer);
        jp_immobilie.add(jtf_immoNr);
        jp_immobilie.add(jtf_immoStr);
        jp_immobilie.add(jtf_immoPlz);
        jp_immobilie.add(jtf_immoOrt);
        jp_immobilie.add(jcb_immoWohnlage);
        jp_immobilie.add(jcb_immoEigentuemer);
        jp_immobilie.add(jb_immoNeu);        
    }
    /**
     * logische Überprüfung der eingegebenen Werte auf Datenbankkon-
     * sistenz, Richtigkeit und Vollständigkeit der Eintragungen.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void pruefeKostenkonto()
    {
      notImplemented();
    }
    /**
     * logische Überprüfung der eingegebenen Werte auf Datenbankkon-
     * sitzenz, Richtigkeit und Vollständigkeit der Eintragungen.
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void pruefeMieter()
    {
        String fehler = "";
        String snmNachname = jtf_snmNachname.getText().trim();
        String snmVorname = jtf_snmVorname.getText().trim();
        String snmStrasse = jtf_snmStrasse.getText().trim();
        String snmPlz = jtf_snmPlz.getText().trim();
        String snmOrt = jtf_snmOrt.getText().trim();
        String snmGeburtstag = jtf_snmGeburtstag.getText().trim();
        String snmTelefon = jtf_snmTelefon.getText().trim();
        String snmEmail = jtf_snmEmail.getText().trim();
        String snmMieterSeid = jtf_snmMieterSeid.getText().trim();
        int snmTitel = jcb_snmTitel.getSelectedIndex();
        int snmAnrede = jcb_snmAnrede.getSelectedIndex();
        if (snmAnrede == 0)
        {
            fehler = fehler + "Sie müssen eine Anrede auswählen";
            jcb_snmAnrede.requestFocus();
        }        
        if ("".equals(snmNachname.trim())) 
        {
            fehler = fehler + "Sie haben keinen Nachnamen eingetragen.\n";
            jtf_snmNachname.requestFocus();
        }
        if ("".equals(snmVorname))
        {
            fehler = fehler + "Sie haben keinen Vornamen eingetragen.\n";
            jtf_snmVorname.requestFocus();
        }
        if ("".equals(snmStrasse))
        {
            fehler = fehler + "Sie haben die Strasse nicht eingetragen.\n";
            jtf_snmStrasse.requestFocus();
        }
        if ("".equals(snmPlz))
        {
            fehler = fehler + "Sie haben keine Postleitzahl eingetragen.\n";
            jtf_snmPlz.requestFocus();
        }
        else
        {
            if (snmPlz.length() != 5)
            {
                fehler = fehler + "Die Postleitzahl muss 5-stellig sein.\n";
                jtf_snmPlz.requestFocus();
            }
            else
            {
                if (!snmPlz.matches("[0-9]{5}"))
                {
                    fehler = fehler + "Die Postleitzahl besteht "
                            + "nur aus Ziffern.\n";
                    jtf_snmPlz.requestFocus();
                }
            }
        }
        if ("".equals(snmOrt))
        {
            fehler = fehler + "Sie haben keinen Ort eingegeben.\n";
            jtf_snmOrt.requestFocus();
        }
        if (!"".equals(snmGeburtstag))
        {
            try
            {
                int jahr = Integer.parseInt(snmGeburtstag.substring(6));
                int monat = Integer.parseInt(snmGeburtstag.substring(3,5));
                int tag = Integer.parseInt(snmGeburtstag.substring(0,2));
                ld_snmGebtag = LocalDate.of(jahr,monat,tag);
            }
            catch(NumberFormatException e)
            {
                fehler = fehler + "Geben Sie das Geburtsdatum im "
                        + "Format tt.mm.jjjj ein.\n";
                jtf_snmGeburtstag.requestFocus();
            }
        }
        if (!"".equals(snmEmail))
        {
            if(!snmEmail.matches("[\\w|.|-]+@\\w[\\w|-]*\\.[a-z]{2,4}"))
            {
                fehler = fehler + "Geben Sie eine gültige eMail-Adresse ein.\n";
                jtf_snmEmail.requestFocus();
            }
        }
        if ("".equals(snmTelefon))
        {
            fehler = fehler + "Sie haben keine Telefonnummer eingegeben.\n";
            jtf_snmTelefon.requestFocus();
        }
        else
        {
            if(snmTelefon.length()>20)
            {
                fehler = fehler + "Die Telefonnummer ist zu lang.\n";
                jtf_snmTelefon.requestFocus();
            }
            else
            {
                if (!snmTelefon.matches("[0-9/. \\-]+"))
                {
                    fehler = fehler + "Die Telefonnummer darf nur "
                            + "aus Ziffern, . / und - bestehen.\n";
                    jtf_snmTelefon.requestFocus();
                }
            }
        }
        if (!"".equals(snmMieterSeid))
        {
            try
            {
                int jahr = Integer.parseInt(snmMieterSeid.substring(6));
                int monat = Integer.parseInt(snmMieterSeid.substring(3,5));
                int tag = Integer.parseInt(snmMieterSeid.substring(0,2));
                ld_snmMieterSeid = LocalDate.of(jahr,monat,tag);
            }
            catch(NumberFormatException e)
            {
                fehler = fehler + "Geben Sie das Datum, seid wann das"
                        + "Miterverhältnis besteht, im Format tt.mm.jjjj ein.\n";
                jtf_snmMieterSeid.requestFocus();
            }
        } 
        else
        {
            fehler = fehler + "Geben Sie an, seid wann der Mieter Mieter"
                    + "ist.";
                    jtf_snmMieterSeid.requestFocus();
        }
        if (!"".equals(fehler))
        {
            showNewMieter.setModal(false);
            JOptionPane.showMessageDialog(null, fehler,
                "Fehlerhafte Eingaben", JOptionPane.OK_OPTION);  
            showNewMieter.setModal(true);
        }
        else
        {
            String gebTag,mieterSeid;
            try
            {
                gebTag = ld_sneGebtag.toString();
            }
            catch(NullPointerException e)
            {
                gebTag = "0000-00-00";
            }
            try
            {
                mieterSeid = ld_snmMieterSeid.toString();
            }
            catch(NullPointerException e)
            {
                mieterSeid = "0000-00-00";
            }                      
            database.saveNewMieter(snmTitel, snmAnrede, snmNachname,
                    snmVorname, snmStrasse, snmPlz, snmOrt, gebTag,
                    mieterSeid, snmTelefon, snmEmail);
            showNewMieter.dispose();
            jcb_whgMieter.removeItemListener(this);
            jcb_whgMieter.removeAllItems();
            fillMieter();      
        }        
    }
    /** 
     * logische Überprüfung des eingegebenen Wertes auf Datenbankkon-
     * sitzens, Richtigkeit und Vollständigkeit der Eintragung
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void pruefeAusstattung()
    {
      if ("".equals(jtf_snaAusstattung.getText().trim()))
      {
        showNewAusstattung.setModal(false);
        JOptionPane.showMessageDialog(null, "Sie haben kein"
                + "Ausstattungsmerkmal eingetragen.",
                "Fehlende Eingaben", JOptionPane.OK_OPTION);  
        showNewAusstattung.setModal(true); 
        jtf_snaAusstattung.requestFocus();
      }
      else
      {
        String table = jb_snaSave.getText().trim().toLowerCase().
                        substring(22);
        database.setAusstattung(jtf_snaAusstattung.getText().trim()
                ,table);
        showNewAusstattung.dispose();
        if ("kuechen".equals(table))
        {
          jcb_whgKueche.removeItemListener(this);
          jcb_whgKueche.removeAllItems();          
          fillKueche();
        }
        else
        {
          if ("heizung".equals(table))
          {
            jcb_whgHeizung.removeItemListener(this);
            jcb_whgHeizung.removeAllItems();            
            fillHeizung();
          }
          else
          {
            jcb_whgBad.removeItemListener(this);
            jcb_whgBad.removeAllItems();            
            fillBad();
          }
        }
      }
    }
    /**
     * logische Überprüfung der eingegebenen Werte auf Datenbankkon-
     * sitenz, Richtigkeit und Vollständigkeit der Eintragungen
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void pruefeWohnung()
    {
        String fehler = "";
        double prWhg_qm = 0; 
        double prWhg_kaltmiete = 0;
        double prWhg_nebenkosten = 0;
        int prWhg_zimmeranzahl = 0;
        if ("".equals(jtf_whgId.getText().trim()))
        {
            fehler = fehler + "Wohnungskennzeichnung wurde nicht ein"
                    + "getragen.\n";
            jtf_whgId.requestFocus();
        }
        if ("".equals(jtf_whgQm.getText().trim()))
        {
            fehler = fehler + "Quadratmeteranzahl der Wohnung nicht "
                    + "eingetragen\n";
            jtf_whgQm.requestFocus();
        }
        else
        {
            if (!jtf_whgQm.getText().trim().matches("[1-9][0-9]{1,2}[/.][0-9]{0,1}"))
            {
                fehler = fehler + "Eingabeformat für Quadratmeter ist "
                        + "0xx.x. Beispiel: 123.7.\n";
                jtf_whgQm.requestFocus();
            }
            else
            {
                prWhg_qm = Double.parseDouble(jtf_whgQm.getText().trim());
            }
        }
        if ("".equals(jtf_whgZimmer.getText().trim()))
        {
            fehler = fehler + "Sie haben keine Zimmeranzahl eingegeben.\n";
            jtf_whgZimmer.requestFocus();
        }
        else
        {
            try
            {
                prWhg_zimmeranzahl = Integer.parseInt(jtf_whgZimmer.getText().
                        trim());
            }
            catch(NumberFormatException e)
            {
                fehler = fehler + "Die Zimmeranzahl kann nur aus Ziffern"
                        + "bestehen.\n";
                jtf_whgZimmer.requestFocus();
            }
        }
        if (jcb_whgKueche.getSelectedIndex() == 0)
        {
            fehler = fehler + "Sie müssen die Küchenausstatung aus"
                    + "wählen.\n";
            jcb_whgKueche.requestFocus();
        }
        if (jcb_whgHeizung.getSelectedIndex() == 0)
        {
            fehler = fehler + "Sie müssen Heizungsangaben auswählen.\n";
            jcb_whgHeizung.requestFocus();
        }
        if (jcb_whgBad.getSelectedIndex() == 0)
        {
            fehler = fehler + "Sie müssen Angaben über das Bad auswählen.\n";
            jcb_whgBad.requestFocus();
        }
        if ("".equals(jtf_whgKaltmiete.getText().trim()))
        {
            fehler = fehler + "Sie müssen die Kaltmiete eingeben.\n";
            jtf_whgKaltmiete.requestFocus();
        }
        else
        {
            if (!jtf_whgKaltmiete.getText().trim()
                    .matches("[1-9][0-9]{1,3}[/.][0-9]{2}"))
            {
                fehler = fehler + "Die Kaltmiete kann höchstens 9999.99"
                        + "betragen und muss 2 Stellen vor und 2 Stellen"
                        + "nach dem Dezimalzeichen (.) haben.\n";
                jtf_whgKaltmiete.requestFocus();
            }
            else
            {
                prWhg_kaltmiete = Double.parseDouble(jtf_whgKaltmiete.
                        getText().trim());
            }
        }
        if ("".equals(jtf_whgNebenkosten.getText().trim()))
        {
            fehler = fehler + "Sie müssen die Nebenkosten eingeben.\n";
            jtf_whgNebenkosten.requestFocus();
        }
        else
        {
            if (!jtf_whgNebenkosten.getText().trim()
                    .matches("[1-9][0-9]{1,3}[/.][0-9]{2}"))
            {
                fehler = fehler + "Die Nebenkosten können höchstens 9999.99"
                        + "betragen und müssen 2 Stellen vor und 2 Stellen"
                        + "nach dem Dezimalzeichen (.) haben.\n";
                jtf_whgNebenkosten.requestFocus();
            }
            else
            {
                prWhg_nebenkosten = Double.parseDouble(jtf_whgNebenkosten.
                        getText().trim());
            }
        }  
        if("".equals(fehler))
        {
            database.saveNewWohnung(jtf_whgId.getText().trim(),
                    aktuelleImmo, jcb_whgMieter.getSelectedIndex(), 
                    prWhg_qm, prWhg_zimmeranzahl, jcb_whgKueche
                    .getSelectedIndex(), jcb_whgHeizung.getSelectedIndex(), 
                    jcb_whgBad.getSelectedIndex(), jtf_whgZusatz.getText()
                    .trim(), prWhg_kaltmiete, prWhg_nebenkosten);
            fillWhgUebersicht();
            jp_wohnungen.repaint();
            resetWohnung();
        }
        else
        {
            JOptionPane.showMessageDialog(null, fehler,
                "Fehlerhafte Eingaben", JOptionPane.OK_OPTION);  
        }
    }
    /**
     * Zurücksetzen der Eingaben im Tab Wohnungen
     * JComboBoxen auf Index 0 setzen und Textfelder leeren
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void resetWohnung()
    {
        jcb_whgMieter.setSelectedIndex(0);
        jcb_whgKueche.setSelectedIndex(0);
        jcb_whgHeizung.setSelectedIndex(0);
        jcb_whgBad.setSelectedIndex(0);
        jtf_whgId.setText("");
        jtf_whgQm.setText("");
        jtf_whgZimmer.setText("");
        jtf_whgZusatz.setText("");
        jtf_whgKaltmiete.setText("");
        jtf_whgNebenkosten.setText("");
        jtf_whgId.requestFocus();
    }
    private void removePanel()
    {
      if(immobilie)
      {
        this.remove(jtp_immobilie);
        immobilie = false;
        this.repaint();
      }
      if (kostenkonto)
      {
        this.remove(jp_kkto);
        kostenkonto = false;
        this.repaint();
      }
    }
    /**
     * Übernahme der in der Datenbank gespeicherten Kaltmiete und
     * Nebenkosten im Forumular Kostenkonto, wenn Vorgang Gesamtmiete
     * ist und Wohnung ausgewählt wurde.
     */
    private void setMiete()
    {
      notImplemented();
    }
    /**
     * Fensteransicht für das Anlegen einer neuen Immobilie
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setImmoNew()
    {
      removePanel();
      immobilie = true;
      jtp_immobilie = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
      jtp_immobilie.setSize(this.getSize());
      setTabNewImmobilie();
      setTabNewWohnung();       
      jtp_immobilie.add("Neue Immobilie",jp_immobilie);
      jtp_immobilie.add("Wohnungen der Immobilie",jp_wohnungen);  
      jtp_immobilie.setEnabledAt(1, false);
      this.remove(this.getContentPane());
      this.add(jtp_immobilie);
      this.setVisible(true);
    }
    /**
     * Prüfung, ob alle Pflichtfelder ausgefüllt sind und ob die Konventionen
     * eingehalten wurden. Wenn alles korrekt eingegeben ist, wird ein neuer
     * Eigentümer in die Datenbank gespeichert.
     * @author Markus Badzura
     * @since 1.0.001
     */
    @SuppressWarnings("UnusedAssignment")
    private void pruefeNewEigentuemer()
    {
        String fehler = "";
        String sneNachname = jtf_sneNachname.getText().trim();
        String sneVorname = jtf_sneVorname.getText().trim();
        String sneStrasse = jtf_sneStrasse.getText().trim();
        String snePlz = jtf_snePlz.getText().trim();
        String sneOrt = jtf_sneOrt.getText().trim();
        String sneGeburtstag = jtf_sneGeburtstag.getText().trim();
        String sneTelefon = jtf_sneTelefon.getText().trim();
        String sneEmail = jtf_sneEmail.getText().trim();
        int sneTitel = jcb_sneTitel.getSelectedIndex();
        int sneAnrede = jcb_sneAnrede.getSelectedIndex();
        if (sneAnrede == 0)
        {
            fehler = fehler + "Sie müssen eine Anrede auswählen";
            jcb_sneAnrede.requestFocus();
        }        
        if ("".equals(sneNachname.trim())) 
        {
            fehler = fehler + "Sie haben keinen Nachnamen eingetragen.\n";
            jtf_sneNachname.requestFocus();
        }
        if ("".equals(sneVorname))
        {
            fehler = fehler + "Sie haben keinen Vornamen eingetragen.\n";
            jtf_sneVorname.requestFocus();
        }
        if ("".equals(sneStrasse))
        {
            fehler = fehler + "Sie haben die Strasse nicht eingetragen.\n";
            jtf_sneStrasse.requestFocus();
        }
        if ("".equals(snePlz))
        {
            fehler = fehler + "Sie haben keine Postleitzahl eingetragen.\n";
            jtf_snePlz.requestFocus();
        }
        else
        {
            if (snePlz.length() != 5)
            {
                fehler = fehler + "Die Postleitzahl muss 5-stellig sein.\n";
                jtf_snePlz.requestFocus();
            }
            else
            {
                if (!snePlz.matches("[0-9]{5}"))
                {
                    fehler = fehler + "Die Postleitzahl besteht nur aus Ziffern.\n";
                    jtf_snePlz.requestFocus();
                }
            }
        }
        if ("".equals(sneOrt))
        {
            fehler = fehler + "Sie haben keinen Ort eingegeben.\n";
            jtf_sneOrt.requestFocus();
        }
        if (!"".equals(sneGeburtstag))
        {
            try
            {
                int jahr = Integer.parseInt(sneGeburtstag.substring(6));
                int monat = Integer.parseInt(sneGeburtstag.substring(3,5));
                int tag = Integer.parseInt(sneGeburtstag.substring(0,2));
                ld_sneGebtag = LocalDate.of(jahr,monat,tag);
            }
            catch(NumberFormatException e)
            {
                fehler = fehler + "Geben Sie das Geburtsdatum im Format tt.mm.jjjj ein.\n";
                jtf_sneGeburtstag.requestFocus();
            }
        }
        if (!"".equals(sneEmail))
        {
            if(!sneEmail.matches("[\\w|.|-]+@\\w[\\w|-]*\\.[a-z]{2,4}"))
            {
                fehler = fehler + "Geben Sie eine gültige eMail-Adresse ein.\n";
                jtf_sneEmail.requestFocus();
            }
        }
        if ("".equals(sneTelefon))
        {
            fehler = fehler + "Sie haben keine Telefonnummer eingegeben.\n";
            jtf_sneTelefon.requestFocus();
        }
        else
        {
            if(sneTelefon.length()>20)
            {
                fehler = fehler + "Die Telefonnummer ist zu lang.\n";
                jtf_sneTelefon.requestFocus();
            }
            else
            {
                if (!sneTelefon.matches("[0-9/. \\-]+"))
                {
                    fehler = fehler + "Die Telefonnummer darf nur aus Ziffern, . / und - bestehen.\n";
                    jtf_sneTelefon.requestFocus();
                }
            }
        }
        if (!"".equals(fehler))
        {
            showNewEigentuemer.setModal(false);
            JOptionPane.showMessageDialog(null, fehler,
                "Fehlende Eingaben", JOptionPane.OK_OPTION);  
            showNewEigentuemer.setModal(true);
        }
        else
        {
            String gebTag;
            try
            {
                gebTag = ld_sneGebtag.toString();
            }
            catch(NullPointerException e)
            {
                gebTag = "0000-00-00";
            }
            database.saveNewEigentuemer(sneTitel, sneAnrede, sneNachname, sneVorname, sneStrasse, snePlz, sneOrt, gebTag, sneTelefon, sneEmail);
            showNewEigentuemer.dispose();
            jcb_immoEigentuemer.removeItemListener(this);
            jcb_immoEigentuemer.removeAllItems();
            fillEigentuemer();      
        }
    }
    /**
     * Prüfung ob die Daten bei Neueingabe Immobilie logisch vorhanden sind
     * Eingabefelder auf Inhalt prüfen, ComboBox für erstmaligen Eintrag prüfen
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void pruefeNewImmobilie()
    {
        String fehler = "";
        if ("".equals(jtf_immoStr.getText().trim()))
        {
            fehler = fehler + "Straße der Immobilie ist nicht eingetragen.\n";
            jtf_immoStr.requestFocus();
        }
        if ("".equals(jtf_immoPlz.getText().trim()))
        {
            fehler = fehler + "Sie haben keine Postleitzahl eingetragen.\n";
            jtf_immoPlz.requestFocus();
        }
        else
        {
            if (jtf_immoPlz.getText().trim().length() != 5)
            {
                fehler = fehler + "Die Postleitzahl muss 5-stellig sein.\n";
                jtf_immoPlz.requestFocus();
            }
            else
            {
                if (!jtf_immoPlz.getText().trim().matches("[0-9]{5}"))
                {
                    fehler = fehler + "Die Postleitzahl besteht nur aus Ziffern.\n";
                    jtf_immoPlz.requestFocus();
                }
            }
        }
        if ("".equals(jtf_immoOrt.getText().trim()))
        {
            fehler = fehler + "Ort ist nicht eingetragen.\n";
            jtf_immoOrt.requestFocus();
        }
        if (jcb_immoEigentuemer.getSelectedIndex()== 0)
        {
            fehler = fehler + "Eigentümer wurde noch nicht hinzugefügt.";
        }
        if ("".equals(fehler))
        {
            database.saveNewImmobilie(jcb_immoWohnlage.getSelectedIndex(), 
                    jcb_immoEigentuemer.getSelectedIndex(), jtf_immoStr.getText().trim(),
                    jtf_immoPlz.getText().trim(), jtf_immoOrt.getText().trim());
            jtp_immobilie.setEnabledAt(1, true);
            jtp_immobilie.setSelectedIndex(1);
            jtp_immobilie.setEnabledAt(0, false);
            sleeper();
            jl_whgImmoAnschrift.setText(database.getImmoAnschrift(aktuelleImmo));
        }
        else
        {
            JOptionPane.showMessageDialog(null, fehler,
                "Fehlende Eingaben", JOptionPane.OK_OPTION);            
        }
    }
    /**
     * Dialogfenster Programminformationen
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void showAbout()
    {
        showAbout = new JDialog();
        showAbout.setTitle("Programminformationen");
        showAbout.setSize(300,200);
        showAbout.setLocation(SCREENSIZE.width/2-150,SCREENSIZE.height/2-100);
        showAbout.setIconImage(ICON.getImage());
        showAbout.setLayout(null);
        showAbout.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        showAbout.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                showAbout.dispose();
            }
        });              
        jl_saTitel = new JLabel("Immobilienverwaltung v1.0");
        jl_saAnlass = new JLabel("PSA-Projekt Konzept mit Prototyp");
        jl_saAuthor = new JLabel("Erstellt durch: Markus Badzura");
        jl_saTitel.setBounds(5,5,this.getWidth()-20,25);
        jl_saAnlass.setBounds(5,45,this.getWidth()-20,25);
        jl_saAuthor.setBounds(5,85,this.getWidth()-20,25);
        showAbout.add(jl_saTitel);
        showAbout.add(jl_saAnlass);
        showAbout.add(jl_saAuthor);        
        showAbout.setVisible(true);        
    }
    /**
     * Dialogfenster zur Eingabe eines neuen Ausstattungmerkmales
     * @param art String mit kuechen, heizung oder bad
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void showNewAusstattung(String art)
    {
        showNewAusstattung = new JDialog();
        showNewAusstattung.setTitle("Hinzufügen einer Zussatzausstattung Bereich "+art.toUpperCase());
        showNewAusstattung.setSize(500,200);
        showNewAusstattung.setLocation(SCREENSIZE.width/2-150,SCREENSIZE.height/2-100);
        showNewAusstattung.setIconImage(ICON.getImage());
        showNewAusstattung.setLayout(null);
        showNewAusstattung.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        showNewAusstattung.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                showNewAusstattung.dispose();
            }
        });              
        jl_snaAusstattung = new JLabel("Neue Ausstattung: ",JLabel.RIGHT);
        jl_snaAusstattung.setFont(jl_snaAusstattung.getFont().deriveFont(14f));
        jl_snaAusstattung.setBounds(10,10,200,25);
        jtf_snaAusstattung = new JTextField();
        jtf_snaAusstattung.setFont(jtf_snaAusstattung.getFont().deriveFont(14f));
        jtf_snaAusstattung.setBounds(220,10,200,25);
        jb_snaClear = new JButton ("Abbrechen");
        jb_snaClear.addActionListener(this);
        jb_snaClear.setBounds(10,50,200,25);
        jb_snaSave = new JButton ("Speichern Ausstattung "+art.toUpperCase());
        jb_snaSave.addActionListener(this);
        jb_snaSave.setBounds(220,50,200,25);
        showNewAusstattung.add(jl_snaAusstattung);
        showNewAusstattung.add(jtf_snaAusstattung);
        showNewAusstattung.add(jb_snaClear);
        showNewAusstattung.add(jb_snaSave);
        showNewAusstattung.setModal(true);
        showNewAusstattung.setVisible(true);       
    }
    /**
     * Dialogfenster zur Eingabe eines neuen Mieters
     * author Markus Badzura
     * @since 1.0.001
     */
    private void showNewMieter()
    {
        jcb_whgMieter.setSelectedIndex(0);
        showNewMieter = new JDialog();
        showNewMieter.setTitle("Programminformationen");
        showNewMieter.setSize(400,500);    
        showNewMieter.setLocation(SCREENSIZE.width/2-150,SCREENSIZE.height/2-100);
        showNewMieter.setIconImage(ICON.getImage());
        showNewMieter.setLayout(null);
        showNewMieter.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        showNewMieter.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exitDialog(showNewMieter);
            }
        });    
        int breite = 175;
        int hoehe = 25;
        int posx = breite+10;
        jl_snmMieterId = new JLabel("Mieternummer: ",JLabel.RIGHT);
        jl_snmMieterId.setFont(jl_snmMieterId.getFont().deriveFont(14f));
        jl_snmMieterId.setBounds(10,10,breite,hoehe);
        jl_snmTitel = new JLabel("Titel: ",JLabel.RIGHT);
        jl_snmTitel.setFont(jl_snmTitel.getFont().deriveFont(14f));
        jl_snmTitel.setBounds(10,40,breite,hoehe);
        jl_snmAnrede = new JLabel("Anrede: ",JLabel.RIGHT);
        jl_snmAnrede.setFont(jl_snmAnrede.getFont().deriveFont(14f));
        jl_snmAnrede.setBounds(10,70,breite,hoehe);
        jl_snmNachname = new JLabel("Nachname: ",JLabel.RIGHT);
        jl_snmNachname.setFont(jl_snmNachname.getFont().deriveFont(14f));
        jl_snmNachname.setBounds(10,100,breite,hoehe);
        jl_snmVorname = new JLabel("Vorname: ",JLabel.RIGHT);
        jl_snmVorname.setFont(jl_snmVorname.getFont().deriveFont(14f));
        jl_snmVorname.setBounds(10,130,breite,hoehe);
        jl_snmStrasse = new JLabel("Straße: ",JLabel.RIGHT);
        jl_snmStrasse.setFont(jl_snmStrasse.getFont().deriveFont(14f));
        jl_snmStrasse.setBounds(10,160,breite,hoehe);
        jl_snmPlz = new JLabel("PLZ: ",JLabel.RIGHT);
        jl_snmPlz.setFont(jl_snmPlz.getFont().deriveFont(14f));
        jl_snmPlz.setBounds(10,190,breite,hoehe);
        jl_snmOrt = new JLabel("Ort: ",JLabel.RIGHT);
        jl_snmOrt.setFont(jl_snmOrt.getFont().deriveFont(14f));
        jl_snmOrt.setBounds(10,220,breite,hoehe);
        jl_snmGeburtstag = new JLabel("Geburtstag: ",JLabel.RIGHT);
        jl_snmGeburtstag.setFont(jl_snmGeburtstag.getFont().deriveFont(14f));
        jl_snmGeburtstag.setBounds(10,250,breite,hoehe);
        jl_snmTelefon = new JLabel("Telefon: ",JLabel.RIGHT);
        jl_snmTelefon.setFont(jl_snmTelefon.getFont().deriveFont(14f));
        jl_snmTelefon.setBounds(10,280,breite,hoehe);
        jl_snmEmail = new JLabel("E-Mail-Adresse: ",JLabel.RIGHT);
        jl_snmEmail.setFont(jl_snmEmail.getFont().deriveFont(14f));
        jl_snmEmail.setBounds(10,310,breite,hoehe);
        jl_snmMieterSeid = new JLabel("Mieter seid: ",JLabel.RIGHT);
        jl_snmMieterSeid.setFont(jl_snmMieterSeid.getFont().deriveFont(14f));
        jl_snmMieterSeid.setBounds(10,340,breite,hoehe);
        jb_snmClear = new JButton("abbrechen");
        jb_snmClear.setBounds(10,400,breite,hoehe);
        jb_snmClear.addActionListener(this);
        jtf_snmMieterId = new JTextField();
        jtf_snmMieterId.setFont(jtf_snmMieterId.getFont().deriveFont(14f));
        jtf_snmMieterId.setEditable(false);
        jtf_snmMieterId.setBounds(posx,10,breite,hoehe);
        setNewMieterId();
        jcb_snmTitel = new JComboBox();
        jcb_snmTitel.setFont(jcb_snmTitel.getFont().deriveFont(14f));
        jcb_snmTitel.setBounds(posx,40,breite,hoehe);
        setTitel(jcb_snmTitel);
        jcb_snmAnrede = new JComboBox();
        jcb_snmAnrede.setFont(jcb_snmAnrede.getFont().deriveFont(14f));
        jcb_snmAnrede.setBounds(posx,70,breite,hoehe);
        setAnrede(jcb_snmAnrede);
        jtf_snmNachname = new JTextField();
        jtf_snmNachname.setFont(jtf_snmNachname.getFont().deriveFont(14f));
        jtf_snmNachname.setBounds(posx,100,breite,hoehe);
        jtf_snmVorname = new JTextField();
        jtf_snmVorname.setFont(jtf_snmVorname.getFont().deriveFont(14f));
        jtf_snmVorname.setBounds(posx,130,breite,hoehe);    
        jtf_snmStrasse = new JTextField();
        jtf_snmStrasse.setFont(jtf_snmStrasse.getFont().deriveFont(14f));
        jtf_snmStrasse.setBounds(posx,160,breite,hoehe);
        jtf_snmStrasse.setText(jtf_immoStr.getText().trim());
        jtf_snmPlz = new JTextField();
        jtf_snmPlz.setFont(jtf_snmPlz.getFont().deriveFont(14f));
        jtf_snmPlz.setBounds(posx,190,breite,hoehe);
        jtf_snmPlz.setText(jtf_immoPlz.getText().trim());
        jtf_snmOrt = new JTextField();
        jtf_snmOrt.setFont(jtf_snmOrt.getFont().deriveFont(14f));
        jtf_snmOrt.setBounds(posx,220,breite,hoehe);
        jtf_snmOrt.setText(jtf_immoOrt.getText().trim());
        jtf_snmGeburtstag = new JTextField();
        jtf_snmGeburtstag.setFont(jtf_snmGeburtstag.getFont().deriveFont(14f));
        jtf_snmGeburtstag.setBounds(posx,250,breite,hoehe);
        jtf_snmGeburtstag.setToolTipText("Format: tt.mm.jjjj");
        jtf_snmTelefon = new JTextField();
        jtf_snmTelefon.setFont(jtf_snmTelefon.getFont().deriveFont(14f));
        jtf_snmTelefon.setBounds(posx,280,breite,hoehe);
        jtf_snmEmail = new JTextField();
        jtf_snmEmail.setFont(jtf_snmEmail.getFont().deriveFont(14f));
        jtf_snmEmail.setBounds(posx,310,breite,hoehe);
        jtf_snmMieterSeid = new JTextField();
        jtf_snmMieterSeid.setFont(jtf_snmMieterSeid.getFont().deriveFont(14f));
        jtf_snmMieterSeid.setBounds(posx,340,breite,hoehe);
        jtf_snmMieterSeid.setToolTipText("Format: tt.mm.jjjj");
        jb_snmSave = new JButton("Speichern");
        jb_snmSave.setBounds(posx,400,breite,hoehe);
        jb_snmSave.addActionListener(this);
        showNewMieter.add(jl_snmTitel);
        showNewMieter.add(jl_snmAnrede);
        showNewMieter.add(jl_snmNachname);
        showNewMieter.add(jl_snmVorname);
        showNewMieter.add(jl_snmStrasse);
        showNewMieter.add(jl_snmPlz);
        showNewMieter.add(jl_snmOrt);
        showNewMieter.add(jl_snmGeburtstag);
        showNewMieter.add(jl_snmTelefon);
        showNewMieter.add(jl_snmEmail);
        showNewMieter.add(jl_snmMieterSeid);
        showNewMieter.add(jb_snmClear);
        showNewMieter.add(jtf_snmMieterId);
        showNewMieter.add(jcb_snmTitel);
        showNewMieter.add(jcb_snmAnrede);
        showNewMieter.add(jtf_snmNachname);
        showNewMieter.add(jtf_snmVorname);
        showNewMieter.add(jtf_snmStrasse);  
        showNewMieter.add(jtf_snmPlz);
        showNewMieter.add(jtf_snmOrt);
        showNewMieter.add(jtf_snmGeburtstag);
        showNewMieter.add(jtf_snmTelefon);
        showNewMieter.add(jtf_snmEmail);
        showNewMieter.add(jtf_snmMieterSeid);
        showNewMieter.add(jl_snmMieterId);        
        showNewMieter.add(jb_snmSave);  
        showNewMieter.setModal(true);
        showNewMieter.setVisible(true);        
    }
    /**
     * Dialogfenster zur Eingabe eines neuen Eigentümers
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void showNewEigentuemer()
    {
        showNewEigentuemer = new JDialog();
        showNewEigentuemer.setTitle("Neuen Eigentümer anlegen");
        showNewEigentuemer.setSize(400,450);    
        showNewEigentuemer.setLocation(SCREENSIZE.width/2-150,SCREENSIZE.height/2-100);
        showNewEigentuemer.setIconImage(ICON.getImage());
        showNewEigentuemer.setLayout(null);
        showNewEigentuemer.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        showNewEigentuemer.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exitDialog(showNewEigentuemer);
            }
        });    
        int breite = 175;
        int hoehe = 25;
        int posx = breite+10;
        jl_sneEigentuemerId = new JLabel("Eigentümernummer: ",JLabel.RIGHT);
        jl_sneEigentuemerId.setFont(jl_sneEigentuemerId.getFont().deriveFont(14f));
        jl_sneEigentuemerId.setBounds(10,10,breite,hoehe);
        jl_sneTitel = new JLabel("Titel: ",JLabel.RIGHT);
        jl_sneTitel.setFont(jl_sneTitel.getFont().deriveFont(14f));
        jl_sneTitel.setBounds(10,40,breite,hoehe);
        jl_sneAnrede = new JLabel("Anrede: ",JLabel.RIGHT);
        jl_sneAnrede.setFont(jl_sneAnrede.getFont().deriveFont(14f));
        jl_sneAnrede.setBounds(10,70,breite,hoehe);
        jl_sneNachname = new JLabel("Nachname: ",JLabel.RIGHT);
        jl_sneNachname.setFont(jl_sneNachname.getFont().deriveFont(14f));
        jl_sneNachname.setBounds(10,100,breite,hoehe);
        jl_sneVorname = new JLabel("Vorname: ",JLabel.RIGHT);
        jl_sneVorname.setFont(jl_sneVorname.getFont().deriveFont(14f));
        jl_sneVorname.setBounds(10,130,breite,hoehe);
        jl_sneStrasse = new JLabel("Straße: ",JLabel.RIGHT);
        jl_sneStrasse.setFont(jl_sneStrasse.getFont().deriveFont(14f));
        jl_sneStrasse.setBounds(10,160,breite,hoehe);
        jl_snePlz = new JLabel("PLZ: ",JLabel.RIGHT);
        jl_snePlz.setFont(jl_snePlz.getFont().deriveFont(14f));
        jl_snePlz.setBounds(10,190,breite,hoehe);
        jl_sneOrt = new JLabel("Ort: ",JLabel.RIGHT);
        jl_sneOrt.setFont(jl_sneOrt.getFont().deriveFont(14f));
        jl_sneOrt.setBounds(10,220,breite,hoehe);
        jl_sneGeburtstag = new JLabel("Geburtstag: ",JLabel.RIGHT);
        jl_sneGeburtstag.setFont(jl_sneGeburtstag.getFont().deriveFont(14f));
        jl_sneGeburtstag.setBounds(10,250,breite,hoehe);
        jl_sneTelefon = new JLabel("Telefon: ",JLabel.RIGHT);
        jl_sneTelefon.setFont(jl_sneTelefon.getFont().deriveFont(14f));
        jl_sneTelefon.setBounds(10,280,breite,hoehe);
        jl_sneEmail = new JLabel("E-Mail-Adresse: ",JLabel.RIGHT);
        jl_sneEmail.setFont(jl_sneEmail.getFont().deriveFont(14f));
        jl_sneEmail.setBounds(10,310,breite,hoehe);
        jb_sneClear = new JButton("abbrechen");
        jb_sneClear.setBounds(10,350,breite,hoehe);
        jb_sneClear.addActionListener(this);
        jtf_sneEigentuemerId = new JTextField();
        jtf_sneEigentuemerId.setFont(jtf_sneEigentuemerId.getFont().deriveFont(14f));
        jtf_sneEigentuemerId.setEditable(false);
        jtf_sneEigentuemerId.setBounds(posx,10,breite,hoehe);
        setNewEigentuemerId();
        jcb_sneTitel = new JComboBox();
        jcb_sneTitel.setFont(jcb_sneTitel.getFont().deriveFont(14f));
        jcb_sneTitel.setBounds(posx,40,breite,hoehe);
        setTitel(jcb_sneTitel);
        jcb_sneAnrede = new JComboBox();
        jcb_sneAnrede.setFont(jcb_sneAnrede.getFont().deriveFont(14f));
        jcb_sneAnrede.setBounds(posx,70,breite,hoehe);
        setAnrede(jcb_sneAnrede);
        jtf_sneNachname = new JTextField();
        jtf_sneNachname.setFont(jtf_sneNachname.getFont().deriveFont(14f));
        jtf_sneNachname.setBounds(posx,100,breite,hoehe);
        jtf_sneVorname = new JTextField();
        jtf_sneVorname.setFont(jtf_sneVorname.getFont().deriveFont(14f));
        jtf_sneVorname.setBounds(posx,130,breite,hoehe);    
        jtf_sneStrasse = new JTextField();
        jtf_sneStrasse.setFont(jtf_sneStrasse.getFont().deriveFont(14f));
        jtf_sneStrasse.setBounds(posx,160,breite,hoehe);
        jtf_snePlz = new JTextField();
        jtf_snePlz.setFont(jtf_snePlz.getFont().deriveFont(14f));
        jtf_snePlz.setBounds(posx,190,breite,hoehe);
        jtf_sneOrt = new JTextField();
        jtf_sneOrt.setFont(jtf_sneOrt.getFont().deriveFont(14f));
        jtf_sneOrt.setBounds(posx,220,breite,hoehe);
        jtf_sneGeburtstag = new JTextField();
        jtf_sneGeburtstag.setFont(jtf_sneGeburtstag.getFont().deriveFont(14f));
        jtf_sneGeburtstag.setBounds(posx,250,breite,hoehe);
        jtf_sneGeburtstag.setToolTipText("Format: tt.mm.jjjj");
        jtf_sneTelefon = new JTextField();
        jtf_sneTelefon.setFont(jtf_sneTelefon.getFont().deriveFont(14f));
        jtf_sneTelefon.setBounds(posx,280,breite,hoehe);
        jtf_sneEmail = new JTextField();
        jtf_sneEmail.setFont(jtf_sneEmail.getFont().deriveFont(14f));
        jtf_sneEmail.setBounds(posx,310,breite,hoehe);
        jb_sneSave = new JButton("Speichern");
        jb_sneSave.setBounds(posx,350,breite,hoehe);
        jb_sneSave.addActionListener(this);
        showNewEigentuemer.add(jl_sneTitel);
        showNewEigentuemer.add(jl_sneAnrede);
        showNewEigentuemer.add(jl_sneNachname);
        showNewEigentuemer.add(jl_sneVorname);
        showNewEigentuemer.add(jl_sneStrasse);
        showNewEigentuemer.add(jl_snePlz);
        showNewEigentuemer.add(jl_sneOrt);
        showNewEigentuemer.add(jl_sneGeburtstag);
        showNewEigentuemer.add(jl_sneTelefon);
        showNewEigentuemer.add(jl_sneEmail);
        showNewEigentuemer.add(jb_sneClear);
        showNewEigentuemer.add(jtf_sneEigentuemerId);
        showNewEigentuemer.add(jcb_sneTitel);
        showNewEigentuemer.add(jcb_sneAnrede);
        showNewEigentuemer.add(jtf_sneNachname);
        showNewEigentuemer.add(jtf_sneVorname);
        showNewEigentuemer.add(jtf_sneStrasse);  
        showNewEigentuemer.add(jtf_snePlz);
        showNewEigentuemer.add(jtf_sneOrt);
        showNewEigentuemer.add(jtf_sneGeburtstag);
        showNewEigentuemer.add(jtf_sneTelefon);
        showNewEigentuemer.add(jtf_sneEmail);
        showNewEigentuemer.add(jl_sneEigentuemerId);        
        showNewEigentuemer.add(jb_sneSave);  
        showNewEigentuemer.setModal(true);
        showNewEigentuemer.setVisible(true);
    }
    /**
     * Thread kurzfristig pausieren, um Folgeaktion vollständig durch-
     * führen zu können
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void sleeper()
    {
      try
      {
        Thread.sleep( 150 );
      }
      catch(InterruptedException ie){}      
    }
    /**
     * Action-Listener
     * @param e auslösendes Objekt
     * @author Markus Badzura
     * @since 1.0.001
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        // Menüpunkt ALLGEMEIN-LOGDATEI LESEN
        if(e.getSource() == jmi_allg_readLog)
        {
            notImplemented();
        }
        // Menüpunkt ALLGEMEIN - DATENBANK - AUSSTATTUNGSMERKMAL BAD
        if (e.getSource() == jmi_allgDb_bad)
        {
            notImplemented();
        }
        // Menüpunkt ALLGEMEIN - DATENBANK - AUSSTATUNGSMERKMAL KÜCHE
        if (e.getSource() == jmi_allgDb_kueche)
        {
            notImplemented();
        }
        // Menüpunkt ALLGEMEIN - DATENBANK - AUSSTATTUNGSMERKMAL HEIZUNG
        if (e.getSource() == jmi_allgDb_heizung)
        {
            notImplemented();
        }
        // Menüpunkt ALLGEMEIN - DATENBANK - DATENBANK SICHERN
        if (e.getSource() == jmi_allgDb_sicherung)
        {
            notImplemented();
        }
        // Menüpunkt ALLGEMEIN - BEENDEN
        if (e.getSource() == jmi_allg_beenden)
        {
            exit();
        }
        // Menüpunkt IMMOBILIEN - NEU ANLEGEN
        if (e.getSource() == jmi_immo_neu)
        {
            setImmoNew();
        }
        // Menüpunkt IMMOBILIEN - EIGENTÜMERWECHSEL
        if (e.getSource() == jmi_immo_chEigentuemer)
        {
            notImplemented();
        }
        // Menüpunkt IMMOBILIEN - KOSTENÜBERSICHT ANZEIGEN
        if (e.getSource() == jmi_immo_kostenuebersicht)
        {
            notImplemented();
        }
        // Menüpunkt IMMOBILIEN - MIETERÜBERSICHT ANZEIGEN
        if (e.getSource() == jmi_immo_mieteruebersicht)
        {
            notImplemented();
        }
        // Menüpunkt IMMOBILIEN - UEBERSICHT MIETZAHLUNG - AKTUELLES MONAT
        if (e.getSource() == jmi_immoMz_aktuell)
        {
            notImplemented();
        }
        // Menüpunkt IMMOBILIEN - ÜBERSICHT MIETZAHLUNG - JAHR
        if (e.getSource() == jmi_immoMz_Jahr)
        {
            notImplemented();
        }
        // Menüpunkt IMMOBLIEN - ÜBERSICHT MIETZAHLUNG - SPEZIFISCHER ZEITRAUM
        if (e.getSource() == jmi_immoMz_zeitraum)
        {
            notImplemented();
        }
        // Menüpunkt IMMOBILIEN - IMMOBILIE LÖSCHEN
        if (e.getSource() == jmi_immo_delete)
        {
            notImplemented();
        }
        // Menüpunkt IMMOBILIEN - IMMOBILIENÜBERSICHT GESAMT
        if (e.getSource() == jmi_immo_uebersicht)
        {
            notImplemented();
        }
        // Menüpunkt MIETER - NEUES MIETVERHÄLTNIS
        if (e.getSource() == jmi_mieter_neu)
        {
            notImplemented();
        }
        // Menüpunkt MIETER - MIETVERHÄLTNIS BEENDET
        if (e.getSource() == jmi_mieter_end)
        {
            notImplemented();
        }
        // Menüpunkt MIETER - ÄNDERUNGEN
        if (e.getSource() == jmi_mieter_change)
        {
            notImplemented();
        }
        // Menüpunkt WOHNUNG - NEUE WOHNUNG
        if (e.getSource() == jmi_whg_neu)
        {
            notImplemented();
        }
        // Menüpunkt WOHNUNG - ÜBERSICHT
        if (e.getSource() == jmi_whg_uebersicht)
        {
            notImplemented();
        }
        // Menüpunkt WOHNUNG - AUSSTATTUNGSMERKMAL
        if (e.getSource() == jmi_whg_changeAM)
        {
            notImplemented();
        }
        // Menüpunkt KOSTENKONTO - KONTOBEWEGUNGEN
        if (e.getSource() == jmi_kkt_change)
        {
            setKostenkonto();
        }
        // Menüpunkt KOSTENKONTO - BETRIEBSKOSTENABRECHNUNG
        if (e.getSource() == jmi_kkt_bka)
        {
            notImplemented();
        }
        // Menüpunkt HILFE - HILFE
        if (e.getSource() == jmi_hilfe_hilfe)
        {
            showHelp();
        }
        // Menüpunkt HILFE - PRGOGRAMMINFORMATION
        if (e.getSource() == jmi_hilfe_about)
        {
            showAbout();
        }
        // Button Neue Immobilie anlegen
        if (e.getSource() == jb_immoNeu)
        {
            pruefeNewImmobilie();
        }
        // Button showNewEigentümer Abbrechen
        if (e.getSource() == jb_sneClear)
        {
            showNewEigentuemer.dispose();
        }
        // Button showNewEigentümer Speichern
        if (e.getSource() == jb_sneSave)
        {
            pruefeNewEigentuemer();
        }
        // Button Neue Wohnung speichern
        if (e.getSource() == jb_whgSave)
        {
            pruefeWohnung();
        }
        // Button Neue Wohnung Eingabe zurücksetzen
        if (e.getSource() == jb_whgClear)
        {
            resetWohnung();
        }
        // Button Neuen Mieter speichern
        if (e.getSource() == jb_snmSave)
        {
            pruefeMieter();
            jcb_whgMieter.setSelectedIndex(0);
        }
        // Button Neuen Mieter abbrechen
        if (e.getSource() == jb_snmClear)
        {
          showNewMieter.dispose();
          jcb_whgMieter.setSelectedIndex(0);
        }
        // Button Neues Ausstattungsmerkmal abbrechen
        if (e.getSource() == jb_snaClear)
        {
          showNewAusstattung.dispose(); 
        }
        // Button Neues Ausstattungsmerkmal speichern
        if (e.getSource() == jb_snaSave)
        {
          pruefeAusstattung();
        }
        // Button Kostenkonto Eingabe zurücksetzen
        if (e.getSource() == jb_kktoClear)
        {
          jcb_kktoImmo.setSelectedIndex(0);
          jtf_kktoDatum.setText("");
          jtf_kktoGeschVorgang.setText("");
          jcb_kktoVorgang.setSelectedIndex(0);
          jtf_kktoBetrag.setText("");
          jcb_kktoImmo.requestFocus();
        }
        // Button Kostenkonte speichern
        if (e.getSource() == jb_kktoSave)
        {
          pruefeKostenkonto();
        }
        // Button Übernehmen der Miete
        if (e.getSource() == jb_kktoOk)
        {
          setMiete();
        }
    }
    /**
     * KeyListener Aktion für keyTyped
     * keine Aktionen
     * @param e Auslösende Tasten
     * @author Markus Badzura
     * @since 1.0.001
     */
    @Override
    public void keyTyped(KeyEvent e) {}
    /**
     * KeyListener Aktion für keyPressed
     * @param e Auslösende Tasten
     * @author Markus Badzura
     * @since 1.0.001
     */
    @Override
    public void keyPressed(KeyEvent e) 
    {
        // Tastenkombination STRG + K (Kontobewegung Kostenkonto)
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_K)
        {
            setKostenkonto();
        }
        // Tastenkombination STRG + N (Neue Immobilie anlegen)
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N)
        {
            setImmoNew();
        }
        // Funktionstaste F1 (Hilfe)
        if (e.getKeyCode() == KeyEvent.VK_F1)
        {
            showHelp();
        }        
        // Funktionstaste F2 (Kostenübersicht anzeigen)
        if (e.getKeyCode() == KeyEvent.VK_F2)
        {
            notImplemented();
        }
        // Funktionstaste F3 (Miterübersicht anzeigen)
        if (e.getKeyCode() == KeyEvent.VK_F3)
        {
            notImplemented();
        }
        // Funktionstaste F4 (Immobilienübersicht gesamt)
        if (e.getKeyCode() == KeyEvent.VK_F4)
        {
            notImplemented();
        }
        // Funktionstaste F5 (Wohnungsübersicht)
        if (e.getKeyCode() == KeyEvent.VK_F5)
        {
            notImplemented();
        }        
        // Funktionstaste F6 (Übersicht Mietzahlung aktueller Monat)
        if (e.getKeyCode() == KeyEvent.VK_F6)
        {
            notImplemented();
        }
        // Funktionstaste F17 (Übersicht Mietzahlung Laufendes Jahr)
        if (e.getKeyCode() == KeyEvent.VK_F7)
        {
            notImplemented();
        }
        // Funktionstaste F8 (Übersicht Mietzahlung Spezifischer Zeitraum)
        if (e.getKeyCode() == KeyEvent.VK_F8)
        {
            notImplemented();
        }
    }
    /**
     * KeyListener Aktion für keyReleased
     * Keine Aktionen
     * @param e Auslösende Tasten
     * @author Markus Badzura
     * @since 1.0.001
     */
    @Override
    public void keyReleased(KeyEvent e) {}
    /**
     * Programmmitteilung, dass Bestandteil noch nicht implementiert ist
     * @author Markus Badzura
     * @since 1.0.001
     */
    public void notImplemented()
    {
        JOptionPane.showMessageDialog(null, "Diese Funktion muss noch implementiert werden.",
                "Programmmitteilung", JOptionPane.CANCEL_OPTION);
        
    }

    @Override
    public void itemStateChanged(ItemEvent e) 
    {
        // Auswahl Eigentümer Neue Immobilie eingeben
        if (e.getSource() == jcb_immoEigentuemer)
        {
            if("Neue Auswahl hinzufügen".equals(jcb_immoEigentuemer
                    .getSelectedItem().toString()))
            {
                jcb_immoEigentuemer.setSelectedIndex(0); 
                sleeper();
                showNewEigentuemer();
            }
        }
        // Auswahl Mieter neue Wohnung eingeben
        if (e.getSource() == jcb_whgMieter)
        {
            if("Neue Auswahl hinzufügen".equals(jcb_whgMieter
                    .getSelectedItem().toString()))
            {
                jcb_whgMieter.setSelectedIndex(0);
                sleeper();
                showNewMieter();
            }
        }
        // Auswahl Küchenausstattungsmerkmal neue Wohnung eingeben
        if (e.getSource() == jcb_whgKueche)
        {
            if("Neue Auswahl hinzufügen".equals(jcb_whgKueche
                    .getSelectedItem().toString()))
            {
                jcb_whgKueche.setSelectedIndex(0);
                sleeper();
                showNewAusstattung("kuechen");
            }
        }
        // Auswahl Heizungsausstattungsmerkmal neue Wohnung eingeben
        if (e.getSource() == jcb_whgHeizung)
        {
            if("Neue Auswahl hinzufügen".equals(jcb_whgHeizung
                    .getSelectedItem().toString()))
            {
              jcb_whgHeizung.setSelectedIndex(0);
              sleeper();
              showNewAusstattung("heizung");
            }
        }
        // Auswahl Badausstattungsmerkmal neue Wohnung eingeben
        if (e.getSource() == jcb_whgBad)
        {
            if("Neue Auswahl hinzufügen".equals(jcb_whgBad
                    .getSelectedItem().toString()))
            {
              jcb_whgBad.setSelectedIndex(0);
              sleeper();
              showNewAusstattung("bad");
            }
        }
    }
}