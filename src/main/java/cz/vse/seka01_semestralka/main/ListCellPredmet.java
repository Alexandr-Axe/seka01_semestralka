package cz.vse.seka01_semestralka.main;

import cz.vse.seka01_semestralka.logika.Polozka;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Třída, ve které se nastavují obrázky předmětů
 */
public class ListCellPredmet extends ListCell<Polozka> {
    @Override
    protected void updateItem(Polozka polozka, boolean empty) {
        super.updateItem(polozka, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            setText(polozka.getJmeno());
            String cesta = getClass().getResource("predmety/"+polozka.getJmeno()+".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            setGraphic(iw);
            iw.setFitWidth(25);
            iw.setPreserveRatio(true);
        }
    }
}
