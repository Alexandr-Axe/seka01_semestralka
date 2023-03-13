package cz.vse.seka01_semestralka.main;

import cz.vse.seka01_semestralka.logika.Hra;
import cz.vse.seka01_semestralka.logika.IHra;
import cz.vse.seka01_semestralka.logika.Prostor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class HomeController {

    @FXML
    private ListView panelVychodu;
    @FXML
    private Button tlacitkoPoslat;
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;

    private IHra hra = new Hra();
    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();
    @FXML
    private void initialize(){
        vystup.appendText(hra.vratUvitani() + "\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
    }
    @FXML
    private void aktualizujSeznamVychodu()
    {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    @FXML
    private void poslatVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n\n");
        vstup.clear();
        if (hra.konecHry()){
            vystup.appendText(hra.vratEpilog());
            vstup.setDisable(true);
            tlacitkoPoslat.setDisable(true);
        }
    }

    public void ukoncitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opravdu chceš hru ukončit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }

    }
}