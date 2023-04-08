package cz.vse.seka01_semestralka.logika;

/**
 * Zde se nachazi struktura prikazu pouzij, ktery zaroven ukoncuje hru.
 */
public class PrikazPouzij implements IPrikaz{
    /**
     Nastavení toho jak se bude příkaz volat
     */
    public static final String JMENO = "pouzij";
    /**
     * herní plán
     */
    private HerniPlan plan;
    /**
     * hra
     */
    private Hra hra;
    private Boolean mrtvyVlk = false;

    /**
     *
     * předpis příkazu
     * @param plan herní plán
     * @param hra hra
     */
    public PrikazPouzij(HerniPlan plan, Hra hra){
        this.plan = plan;
        this.hra = hra;
    }
    @Override
    public String provedPrikaz(String... parametry) {
        if(parametry.length < 2){
            return "Musíš zadat co cheš použít a na co.";
        }

        String nazevPouziteho = parametry[0];
        String nazevUziteho = parametry[1];

        switch (nazevPouziteho){
            case "nuz":
                if (plan.getAktualniProstor().obsahujePostavu(nazevUziteho)
                        && nazevUziteho.equalsIgnoreCase("vlk"))
                {
                    plan.getAktualniProstor().vyberPostavu(nazevUziteho);
                    mrtvyVlk = true;
                    return "Pobodal jsi " + nazevUziteho;
                }
                   break;
            case "proteza":
                  if (plan.getAktualniProstor().obsahujePostavu(nazevUziteho)
                  && nazevUziteho.equalsIgnoreCase("karkulka"))
                  {
                      plan.getBrasna().vyndejZBrasny("proteza");
                      hra.setKonecHry(true);
                      if (mrtvyVlk)
                      {
                          return "Babička je nadšená, že zkoušíš módní trend, který se líbí zrovna jí. \n"
                                  +"Hodila jsi svou minulost za hlavu a žiješ poklidně s babičkou.";
                      }
                      else
                      {
                          hra.getHerniPlan().getAktualniProstor().vlozPostavu(new Postava("vlk"));
                          return "Při zkoušení babiččiny protézy někdo zaťukal na dveře. Rozhořčení jste\n"
                                  + "je otevřeli bez použití kukátka a ihned se setkali s vlkovým hněvem,\n"
                                  + "který zapříčinil smrt Karkulky i babičky.";
                      }
                  }
                break;
            case "maliny":
                if (plan.getBrasna().obsahujePolozku(nazevPouziteho)
                        && nazevUziteho.equalsIgnoreCase("karkulka"))
                {
                    plan.getBrasna().vyndejZBrasny("maliny");
                    hra.setKonecHry(true);
                    return "Maliny ti udělaly radost a dodaly energii.\n" +
                            "Cestou ti začíná být ale divně, pohled se ti mlží a už tolik energie nemáš.\n" +
                            "Je normální, že se ti po styku s malinami zastaví dech?";
                }
                break;
            case "babovka":
                if (plan.getAktualniProstor().obsahujePostavu("babicka")){
                    plan.getBrasna().vyndejZBrasny(nazevPouziteho);
                    hra.setKonecHry(true);
                    return "Babička si pochutnala na bábovce. Dokonce jí chutnala tak moc, že vám nenechala ani kousek.\n" +
                            "Bylo by to špatné, kdyby členové organizovaného zločinu bábovku neotrávili a tím se babičky zbavili.\n"
                            + "Žijeme to ve zvláštním světě, že?";
                }
            break;
            }
        return "To teď nemůžeš!";
    }

    @Override
    public String getNazev()
    {
        return JMENO;
    }

}