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
    private JButton jb_immoNeu,jb_sneSave;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Tab Neue Wohnung                                          //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////    
    private JPanel jp_wohnungen;
    private JLabel jl_whgImmoNummer, jl_whgImmoStrasse, jl_whgImmoPlz,
            jl_whgImmoOrt, jl_whgId, jl_whgMieter, jl_whgQm, jl_whgZimmer,
            jl_whgKuechenid, jl_whgHeizungid, jl_whgBadid, jl_whgZusatz,
            jl_whgKaltmiete, jl_whgNebenkosten;
    private JComboBox jcb_whgMieter, jcb_whgKueche, jcb_whgHeizung, jcb_whgBad;
    private JTextPane jtp_whgUebersicht;
    private JTextField jtf_whgId, jtf_whgQm, jtf_whgZimmer, jtf_whgZusatz, 
            jtf_whgKaltmiete, jtf_whgNebenkosten;
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Dialogfenster Programminformationen                       //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////     
    JDialog showAbout;
    JLabel jl_saTitel, jl_saAnlass, jl_saAuthor;  
    ///////////////////////////////////////////////////////////////////////////
    //                                                                       //
    // Deklaration Dialogfenster Neuer Eigentümer                            //
    //                                                                       //
    ///////////////////////////////////////////////////////////////////////////  
    JDialog showNewEigentuemer;
    JLabel jl_sneEigentuemerId, jl_sneTitel, jl_sneAnrede, jl_sneNachname,
            jl_sneVorname, jl_sneStrasse, jl_snePlz, jl_sneOrt, jl_sneGeburtstag,
            jl_sneTelefon, jl_sneEmail;
    JButton jb_sneClear;
    JTextField jtf_sneEigentuemerId, jtf_sneNachname, jtf_sneVorname, jtf_sneStrasse,
            jtf_snePlz, jtf_sneOrt, jtf_sneGeburtstag, jtf_sneTelefon, jtf_sneEmail;    
    JComboBox jcb_sneTitel, jcb_sneAnrede;
    LocalDate gebtag;
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
     * Panel für Tab Neue Wohnung
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setTabNewWohnung()
    {
        jp_wohnungen = new JPanel();
        jp_wohnungen.setSize(this.getSize());        
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
     * Fensteransicht für das Anlegen einer neuen Immobilie
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void setImmoNew()
    {
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
                gebtag = LocalDate.of(jahr,monat,tag);
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
                gebTag = gebtag.toString();
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
        if (jcb_immoEigentuemer.getSelectedIndex()== 0);
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
     * Dialogfenster zur Eingabe eines neuen Eigentümers
     * @author Markus Badzura
     * @since 1.0.001
     */
    private void showNewEigentuemer()
    {
        showNewEigentuemer = new JDialog();
        showNewEigentuemer.setTitle("Programminformationen");
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
            notImplemented();
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
            notImplemented();
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
        if (e.getSource() == jcb_immoEigentuemer)
        {
            if("Neue Auswahl hinzufügen".equals(jcb_immoEigentuemer.getSelectedItem().toString()))
            {
                jcb_immoEigentuemer.setSelectedIndex(0);
                
                showNewEigentuemer();
            }
        }
    }
}