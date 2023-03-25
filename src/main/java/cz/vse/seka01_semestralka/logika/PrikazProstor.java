package cz.vse.seka01_semestralka.logika;
/**
 *  Zde je struktura příkazu prostor
 *
 */

public class PrikazProstor implements IPrikaz{
    /**
     Nastavení toho jak se bude příkaz volat
     */
    private static final String JMENO = "prostor";
    /**
     Herní plán
     */
    private HerniPlan plan;
    /**
     Předpis příkazu
     */
    public PrikazProstor(HerniPlan plan)
    {
        this.plan = plan;
    }

    /**
     * proveď příkaz
     * @param parametry počet parametrů závisí na daném příkazu
     * @return
     */
    public String provedPrikaz(String... parametry)
    {
        return plan.getAktualniProstor().dlouhyPopis();
    }
    @Override
    public String getNazev()
    {
        return JMENO;
    }

}