package cz.vse.seka01_semestralka.logika;

/**
 *  Zde je struktura příkazu brasna
 *
 */

public class PrikazBrasna implements IPrikaz{
    /**
     Nastavení toho jak se bude příkaz volat
     */
    private static final String JMENO = "brasna";
    /**
     Herní plán
     */
    private HerniPlan plan;
    /**
     Předpis příkazu
     */
    public PrikazBrasna(HerniPlan plan){
        this.plan = plan;
    }
    @Override
    public String provedPrikaz(String... parametry) {
        if (plan.getBrasna().seznamPolozek().isEmpty()){
            return "Brašna je prázdná!";
        }
        else {
            return plan.getBrasna().seznamPolozek();
        }
    }

    @Override
    public String getNazev() {
        return JMENO;
    }
}
