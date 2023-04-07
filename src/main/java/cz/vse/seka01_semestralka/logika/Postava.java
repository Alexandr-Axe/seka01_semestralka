package cz.vse.seka01_semestralka.logika;
/**
 *  Třída pro předpis postavy.
 */

public class Postava {

    /**
     *  Vlastnost postavy - Jméno
     */

    private String jmeno;

    /**
     *  Konstruktor postavy
     * @param jmeno jméno postavy
     */
    public Postava(String jmeno)
    {
        this.jmeno = jmeno;
    }
    /**
     *  Metoda na vrácení jména postavy
     * @return vrací jméno postavy
     */
    public String getJmeno()
    {
        return jmeno;
    }
    @Override
    public String toString(){
        return jmeno;
    }
}
