package cz.vse.seka01_semestralka.main;

import cz.vse.seka01_semestralka.logika.Prostor;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Třída, ve které se nastavují obrázky východů
 */
public class ListCellProstor extends ListCell<Prostor> {
    @Override
    protected void updateItem(Prostor prostor, boolean empty) {
        super.updateItem(prostor, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            setText(prostor.getNazev());
            String cesta = getClass().getResource("prostory/"+prostor.getNazev()+".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            setGraphic(iw);
            iw.setFitWidth(50);
            iw.setPreserveRatio(true);
        }
    }
}