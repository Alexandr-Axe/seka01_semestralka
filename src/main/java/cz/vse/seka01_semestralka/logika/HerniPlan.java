package cz.vse.seka01_semestralka.logika;


import cz.vse.seka01_semestralka.main.Pozorovatel;
import cz.vse.seka01_semestralka.main.PredmetPozorovani;
import cz.vse.seka01_semestralka.main.ZmenaHry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 */
public class HerniPlan implements PredmetPozorovani {
    
    private Prostor aktualniProstor;
    private Map<ZmenaHry, Set<Pozorovatel>> seznamPozorovatelu = new HashMap<>();
    private Prostor vyherniProstor;
    private Brasna brasna;

    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan() {
        brasna = new Brasna(4);
        zalozProstoryHry();
        for (ZmenaHry zmenaHry : ZmenaHry.values())
        {
            seznamPozorovatelu.put(zmenaHry, new HashSet<>());
        }
    }
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví domeček.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
        Prostor domecek = new Prostor("domeček","domeček, ve kterém bydlí Karkulka");
        Prostor chaloupka = new Prostor("chaloupka", "chaloupka, ve které bydlí babička Karkulky");
        Prostor jeskyne = new Prostor("jeskyně","stará plesnivá jeskyně");
        Prostor les = new Prostor("les","les s jahodami, malinami a pramenem vody");
        Prostor hlubokyLes = new Prostor("hluboký_les","temný les, ve kterém lze potkat vlka");

        Postava karkulka = new Postava("karkulka");
        Postava vlk = new Postava("vlk");
        Postava babicka = new Postava("babicka");

        domecek.vlozPostavu(karkulka);
        chaloupka.vlozPostavu(babicka);
        hlubokyLes.vlozPostavu(vlk);

        chaloupka.vlozPostavu(karkulka);
        hlubokyLes.vlozPostavu(karkulka);
        jeskyne.vlozPostavu(karkulka);
        les.vlozPostavu(karkulka);


        // přiřazují se průchody mezi prostory (sousedící prostory)
        domecek.setVychod(les);
        les.setVychod(domecek);
        les.setVychod(hlubokyLes);
        hlubokyLes.setVychod(les);
        hlubokyLes.setVychod(jeskyne);
        hlubokyLes.setVychod(chaloupka);
        jeskyne.setVychod(hlubokyLes);
        chaloupka.setVychod(hlubokyLes);

        Polozka babovka = new Polozka("babovka", true);
        Polozka maliny = new Polozka("maliny", true);
        Polozka nuz = new Polozka("nuz", true);
        Polozka proteza = new Polozka("proteza", true);

        domecek.vlozPolozku(babovka);
        domecek.vlozPolozku(nuz);
        chaloupka.vlozPolozku(proteza);
        les.vlozPolozku(maliny);
                
        aktualniProstor = domecek;  // hra začíná v domečku       
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor()
    {
        return aktualniProstor;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor)
    {
       aktualniProstor = prostor;
       upozorniPozorovatele(ZmenaHry.ZMENA_MISTNOSTI);
    }

    @Override
    public void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel)
    {
        seznamPozorovatelu.get(zmenaHry).add(pozorovatel);
    }
    private void upozorniPozorovatele(ZmenaHry zmenaHry) {
        for (Pozorovatel pozorovatel : seznamPozorovatelu.get(zmenaHry))
        {
            pozorovatel.aktualizuj();
        }
    }

    /**
     * @return získá výherní prostor
     */
    public Prostor getVyherniProstor()
    {
        return vyherniProstor;
    }

    /**
     * @return získá brašnu
     */
    public Brasna getBrasna()
    {
        return brasna;
    }
}
