package Immobilienverwaltung;

/**
 * PSA-Projekt Immobilienverwaltung
 * Objektklasse Wohnungen
 * @author Markus Badzura
 * @version 1.0.001
 */
public class Immo_wohnungen 
{
    private String wohnungsid;
    private String mieter;
    private double qm;
    private double zimmer;
    private String kueche;
    private String heizung;
    private String bad;
    private String zusatz;
    private double kaltmiete;
    private double nebenkosten;
    /**
     * Standardkonstruktur zur Einzelobjektabfrage
     * @author Markus Badzura
     * @since 1.0.001
     */
    Immo_wohnungen(){}
    /**
     * Konstrukur für das Erstellen eines Wohnungsobjektes
     * @param wohnungsid String Wohnungskennung
     * @param mieter String Nachname und Vorname des Mieters
     * @param qm Double Quadratmeteranzahl der Wohnung
     * @param zimmer Double Zimmeranzahl der Wohnung
     * @param kueche String Küchenbezeichnung der Wohnung
     * @param heizung String Heizungsbezeichnung der Wohnung
     * @param bad String Badbezeichnung der Wohnung
     * @param zusatz String Zusatzausstattung der Wohnung
     * @param kaltmiete Double Kaltmiete der Wohnung
     * @param nebenkosten Double Nebenkosten der Wohnung
     * @author Markus Badzura
     * @since 1.0.001
     */
    Immo_wohnungen(String wohnungsid, String mieter, double qm, double zimmer, 
            String kueche, String heizung, String bad, String zusatz, 
            double kaltmiete, double nebenkosten)
    {
        this.wohnungsid = wohnungsid;
        this.mieter = mieter;
        this.qm = qm;
        this.zimmer = zimmer;
        this.kueche = kueche;
        this.heizung = heizung;
        this.bad = bad;
        this.zusatz = zusatz;
        this.kaltmiete = kaltmiete;
        this.nebenkosten = nebenkosten;
    }
    /**
     * Übergabe der Wohnungskennzeichnung
     * @return wohnungsid String 
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getWohnungsid() 
    {
        return wohnungsid;
    }
    /**
     * Übergabe Nach- und Vorname des Mieters
     * @return mieter String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getMieter() 
    {
        return mieter;
    }
    /**
     * Übergabe der Quadramtmeterzahl
     * @return qm Double
     * @author Markus Badzura
     * @since 1.0.001
     */
    public double getQm() 
    {
        return qm;
    }
    /**
     * Übergabe der Zimmeranzahl
     * @return zimmer Double
     * @author Markus Badzura
     * @since 1.0.001
     */
    public double getZimmer() 
    {
        return zimmer;
    }
    /**
     * Übergabe der Küchenbezeichnung
     * @return kueche String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getKueche() 
    {
        return kueche;
    }
    /**
     * Übergabe der Heizungsbezeichnung
     * @return heizung String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getHeizung() 
    {
        return heizung;
    }
    /**
     * Übergabe der Badbezeichnung
     * @return bad String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getBad() 
    {
        return bad;
    }
    /** 
     * Übergabe der Zusatzausstattung
     * @return zusatz String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public String getZusatz() 
    {
        return zusatz;
    }
    /**
     * Übergabe der Kaltmiete
     * @return kaltmiete Double
     * @author Markus Badzura
     * @since 1.0.001
     */
    public double getKaltmiete() 
    {
        return kaltmiete;
    }
    /**
     * Übergabe der Nebenkosten
     * @return nebenkosten String
     * @author Markus Badzura
     * @since 1.0.001
     */
    public double getNebenkosten() 
    {
        return nebenkosten;
    }   
}
