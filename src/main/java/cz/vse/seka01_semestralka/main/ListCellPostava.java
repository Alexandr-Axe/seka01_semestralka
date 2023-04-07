package cz.vse.seka01_semestralka.main;

import cz.vse.seka01_semestralka.logika.Polozka;
import cz.vse.seka01_semestralka.logika.Postava;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

public class ListCellPostava extends ListCell<Postava> {
    @Override
    protected void updateItem(Postava postava, boolean empty) {
        super.updateItem(postava, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            setText(postava.getJmeno());
            String cesta = getClass().getResource("postavy/"+postava.getJmeno()+".png").toExternalForm();
            ImageView iw = new ImageView(cesta);
            setGraphic(iw);
            iw.setFitWidth(50);
            iw.setPreserveRatio(true);
        }
    }
}
