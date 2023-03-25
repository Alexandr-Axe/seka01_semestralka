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
     *  Vlastnost postavy - Interakce
     *  To co postava řekne když jí potkáte.
     */

    /**
     *  Konstruktor postavy
     */
    public Postava(String jmeno)
    {
        this.jmeno = jmeno;
    }
    /**
     *  Metoda na vrácení jména postavy
     */
    public String getJmeno()
    {
        return jmeno;
    }
}
