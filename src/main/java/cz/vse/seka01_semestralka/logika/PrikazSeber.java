package cz.vse.seka01_semestralka.logika;
/**
 *  Zde je struktura příkazu seber
 *
 */
public class PrikazSeber implements IPrikaz{
    /**
     Nastavení toho jak se bude příkaz volat
     */
    public static final String JMENO = "seber";
    /**
     * herní plán
     */

    private HerniPlan plan;
    /**
     *konstruktor
     */


    public PrikazSeber(HerniPlan plan)
    {
        this.plan = plan;
    }

    /**
     * Příkaz "seber". Zkouší se sebrat věc z místnosti a uložit do
     * batohu. Pokud věc v místnosti je a je přenositelná, uloží se
     * do batohu.
     *
     * @param parametry příkaz, jako druhý parametr obsahuje jméno
     *               věci, která se má sebrat.
     */
    public String provedPrikaz(String... parametry) {

        if (parametry.length == 0) {
            // pokud chybí druhé slovo, tak ....
            return "Co mám sebrat? Musíš zadat jméno věci";
        }

        String nazevVeci = parametry[0];
        Prostor aktualniMistnost = plan.getAktualniProstor();

        if (aktualniMistnost.obsahujePolozku(nazevVeci)) {
            Polozka pozadovanaVec =
                    aktualniMistnost.vyberPolozku(nazevVeci);
            if (pozadovanaVec == null) {
                return nazevVeci + " se nedá přenášet";
            } else {

                boolean povedloSeVlozit = plan.getBrasna().vlozDoBrasny(pozadovanaVec);
                if(povedloSeVlozit){
                    return nazevVeci + " jsi vzal z místnosti a "
                            + "uložil do brašny ";
                }

                aktualniMistnost.vlozPolozku(pozadovanaVec);
                return nazevVeci + " nepovedlo se vložit, brašna je aktuálně plná";

            }
        } else {
            return nazevVeci + " není v místnosti";
        }
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev()
    {
        return JMENO;
    }
}
