package cz.vse.seka01_semestralka.main;

/**
 * Interface observable
 */
public interface PredmetPozorovani
{
    /**
     * @param zmenaHry výčtový typ
     * @param pozorovatel observer
     * registruje změnu
     */
    void registruj(ZmenaHry zmenaHry, Pozorovatel pozorovatel);
}
