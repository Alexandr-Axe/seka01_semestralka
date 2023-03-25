package cz.vse.seka01_semestralka.logika;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Třída, která zajišťuje běh brašny - inventáře
 */
public class Brasna {
    /**
     * udává kapacitu brašny
     */
    private int kapacitaBrasny;

    /**
     * vytváří se obsah brašny
     */

    private Set<Polozka> obsahBrasny;
    /**
     *  zde omezujeme kapacitu brašny
     *  @param  kapacitaBrasny udává jeho velikost
     *
     */
    public Brasna(int kapacitaBrasny){
        this.kapacitaBrasny = kapacitaBrasny;
        obsahBrasny = new HashSet<>();
        /**
         * zde vkládáme položky do brašny
         *  @param  neco udává co chceme vložit
         *  @return udává, zda se uspělo vložit věc nebo ne
         */
    }

    /**
     * metoda, vkládající položku do brašny
     * @param neco
     * @return
     */
    public boolean vlozDoBrasny(Polozka neco){
        if (this.obsahBrasny.size() < kapacitaBrasny){
             this.obsahBrasny.add(neco);
             return true;
        }
        return false;

    }
    /**
     * zde vyndaváme položky z brašny
     *
     * @param jmeno udává co chceme vzít
     */
    public Polozka vyndejZBrasny(String jmeno){
        for (Polozka neco: obsahBrasny){
            if (neco.getJmeno().equals(jmeno)){

                this.obsahBrasny.remove(neco);
                return neco;
            }
        }
        return null;
    }
    /**
     *  zde kontrolujeme, zda daná položka je v brašně
     *  @param jmeno udává co hledáme
     *  @return udává, zda se daná položka v brašně našla
     */
    public boolean obsahujePolozku(String jmeno){
        for (Polozka neco: obsahBrasny){
            if (neco.getJmeno().equals(jmeno)){
                return true;
            }
        }
        return false;
    }
    /**
     *  zde vypisujeme seznam položek
     *
     *  @return udává seznam položek v jednom Stringu
     */
    public String seznamPolozek()
    {
        StringBuilder seznam = new StringBuilder();
        for (Polozka neco: obsahBrasny){
            seznam.append(neco.getJmeno()).append(" ");
        }
        return seznam.toString();
    }
    public Collection<Polozka> getSeznamPolozek() {
        return obsahBrasny;
    }
}
