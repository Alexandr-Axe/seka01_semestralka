package cz.vse.seka01_semestralka.logika;

import cz.vse.seka01_semestralka.main.Pozorovatel;
import cz.vse.seka01_semestralka.main.PredmetPozorovani;
import cz.vse.seka01_semestralka.main.ZmenaHry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  Třída Hra - třída představující logiku adventury.
 * 
 *  Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan, která inicializuje mistnosti hry
 *  a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 *  Vypisuje uvítací a ukončovací text hry.
 *  Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();
    private String epilog = "Děkuji, že jste si zahráli upravenou verzi Karcoolky!\n";

    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));

        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPoloz(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazBrasna(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazPouzij(herniPlan, this));
        platnePrikazy.vlozPrikaz(new PrikazProstor(herniPlan));

        for (ZmenaHry zmenaHry : ZmenaHry.values())
        {
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return "Vítejte!\n" +
               "Toto je příběh o Červené Karkulce, babičce a vlkovi.\n" +
               "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n" +
               "\n" +
               herniPlan.getAktualniProstor().dlouhyPopis();
    }
    
    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog()
    {
        return epilog;
    }
    
    /** 
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry()
     {
        return konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek)
     {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
           	parametry[i]= slova[i+1];  	
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
    
     /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní Prikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry)
    {
        this.konecHry = konecHry;
        upozorniPozorovatele(ZmenaHry.KONEC_HRY);
    }
    public void setEpilog(String epilog) {
        this.epilog = epilog;
    }
    
     /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     *  @return     odkaz na herní plán
     */
     public HerniPlan getHerniPlan()
     {
        return herniPlan;
     }

    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel)
    {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }
    private void upozorniPozorovatele(ZmenaHry zmenaHry)
    {
        for (Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry))
        {
            pozorovatel.aktualizuj();
        }
    }
}

