package cz.vse.seka01_semestralka.logika;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class Prostor {

    private String nazev;
    private String popis;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private List<Polozka> seznamPolozek;
    private List<Postava> seznamPostav;

    /**
     * Vytvoreni prostoru se zadanym popisem, napr. "kuchyn", "hala", "travnik
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     */
    public Prostor(String nazev, String popis) {
        this.nazev = nazev;
        this.popis = popis;
        vychody = new HashSet<>();
        seznamPolozek = new ArrayList<Polozka>();
        seznamPostav = new ArrayList<Postava>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi)
    {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
      @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }
      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Nacházíš se v " + popis + "\n"
                + "Položky: " + seznamPolozek() + "\n"
                + "Postava: " + seznamPostav() + "\n"
                + popisVychodu();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody()
    {
        return Collections.unmodifiableCollection(vychody);
    }

    /**
     * @param neco položka
     * @return vloží položku
     */
    public void vlozPolozku(Polozka neco) {
        seznamPolozek.add(neco);
    }

    /**
     * @param jmenoPolozky jméno položky, které nás zajímá
     * @return vrátí boolean, zda obsahuje položku, nebo ne
     */
    public boolean obsahujePolozku(String jmenoPolozky) {
        for (Polozka neco : seznamPolozek) {
            if (neco.getJmeno().equals(jmenoPolozky)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param jmenoPolozky jméno položky
     * @return vybere položku ze seznamu položek
     */
    public Polozka vyberPolozku(String jmenoPolozky)
    {
        Polozka vybranaPolozka = null;
        for (Polozka neco : seznamPolozek) {
            if (neco.getJmeno().equals(jmenoPolozky))
            {
                vybranaPolozka = neco;
            }
        }
        if (vybranaPolozka != null)
        {
            if (vybranaPolozka.muzuPrenest())
            {
                seznamPolozek.remove(vybranaPolozka);
            }
            else
            {
                vybranaPolozka = null;
            }
        }
        return vybranaPolozka;
    }
    private String seznamPolozek(){
        String seznam = "";
        for (Polozka polozka : seznamPolozek){
            seznam = seznam + polozka.getJmeno()+" ";
        }
        return seznam;
    }

    /**
     * @param nekdo postava, kterou vložíme do seznamu
     * @return vloží postavu do seznamu
     */
    public void vlozPostavu(Postava nekdo)
    {
        seznamPostav.add(nekdo);
    }
    private String seznamPostav()
    {
        String seznam = "";
        for (Postava nekdo : seznamPostav)
        {
            seznam = seznam + nekdo.getJmeno() + " ";
        }
        return seznam;
    }

    /**
     * @param jmenoPostavy jméno postavy
     * @return vrátí boolean, zda seznam postav obsahuje postavu
     */
    public boolean obsahujePostavu(String jmenoPostavy)
    {
        for (Postava nekdo : seznamPostav) {
            if (nekdo.getJmeno().equals(jmenoPostavy))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param jmenoPostavy jméno postavy
     * @return vybere jméno postavy ze seznamu
     */
    public Postava vyberPostavu(String jmenoPostavy)
    {
        Postava vybranaPostava = null;
        for (Postava postava : seznamPostav)
        {
            if (postava.getJmeno().equals(jmenoPostavy))
            {
                vybranaPostava = postava;
            }
        }
        if (vybranaPostava != null)
        {
            seznamPostav.remove(vybranaPostava);
        }
        return vybranaPostava;
    }
    @Override
    public String toString()
    {
        return getNazev();
    }

    /**
     * @return získá seznam položek
     */
    public Collection<Polozka> getSeznamPolozek()
    {
        return seznamPolozek;
    }

    /**
     * @return získá seznam postav
     */
    public Collection<Postava> getSeznamPostav()
    {
        return seznamPostav;
    }
}
