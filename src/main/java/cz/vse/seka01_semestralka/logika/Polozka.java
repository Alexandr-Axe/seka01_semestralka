package cz.vse.seka01_semestralka.logika;

/**
 * Zakládá třídu položka, která určuje parametry každé položky
 */
public class Polozka {
    /**
     * jméno položky
     */
    private String jmeno;
    /**
     * jedná se o osobu?
     */
    private Boolean osoba;
    /**
     * lze zabít?
     */
    private Boolean lzeZabit;
    /**
     * lze přenést?
     */
    private boolean lzePrenest;
    /**
     * Vytvoření věci se zadaným názvem
     *
     * @param jmeno        Jméno věci, jednoznačný identifikátor,
     *                     pokud možno jedno slovo
     * @param lzePrenest Parametr určuje, zda je věc
     *                     přenositelná hráčem

     */

    public Polozka(String jmeno, boolean lzePrenest){
        this.jmeno = jmeno;
                this.lzePrenest = lzePrenest;
    }
    /**
     * Vrací jméno položky.
     *
     * @return jméno položky
     */

    public String getJmeno(){return jmeno;}












    /**
     * Vrací informaci o tom, zda je položka přenositelná ve hře.
     *
     * @return true, pokud je věc přenositelná, jinak false
     */
    public boolean muzuPrenest()
    {
        return lzePrenest;
    }
    @Override
    public String toString(){
        return jmeno;
    }
}
