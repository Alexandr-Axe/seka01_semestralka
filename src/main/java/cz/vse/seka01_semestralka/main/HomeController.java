package cz.vse.seka01_semestralka.main;

import cz.vse.seka01_semestralka.logika.Hra;
import cz.vse.seka01_semestralka.logika.IHra;
import cz.vse.seka01_semestralka.logika.PrikazJdi;
import cz.vse.seka01_semestralka.logika.Prostor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HomeController {

    @FXML
    private ListView<Prostor> panelVychodu;
    @FXML
    private Button tlacitkoPoslat;
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;

    private IHra hra = new Hra();
    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();
    @FXML
    public ImageView hrac;
    private Map<String, Point2D> souradniceProstoru = new HashMap<>();

    @FXML
    private void initialize(){
        vystup.appendText(hra.vratUvitani() + "\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelVychodu.setItems(seznamVychodu);
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
            aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
        });
        hra.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());
        aktualizujSeznamVychodu();
        vlozSouradnice();
    }
    private void aktualizujPolohuHrace()
    {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
    }
    private void vlozSouradnice()
    {
        souradniceProstoru.put("domeček", new Point2D(14, 14));
        souradniceProstoru.put("les", new Point2D(100, 14));
        souradniceProstoru.put("hluboký_les", new Point2D(186, 14));
        souradniceProstoru.put("jeskyně", new Point2D(271, 14));
        souradniceProstoru.put("chaloupka", new Point2D(190, 100));
    }
    @FXML
    private void aktualizujSeznamVychodu()
    {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }
    public void aktualizujKonecHry()
    {
        if (hra.konecHry()){
            vystup.appendText(hra.vratEpilog());
        }

        vstup.setDisable(hra.konecHry());
        tlacitkoPoslat.setDisable(hra.konecHry());
        panelVychodu.setDisable(hra.konecHry());
    }

    @FXML
    private void poslatVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    private void zpracujPrikaz(String prikaz) {
        vystup.appendText("> " + prikaz + "\n");
        String vysledek = hra.zpracujPrikaz(prikaz);
        vystup.appendText(vysledek + "\n\n");
    }

    public void ukoncitHru(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opravdu chceš hru ukončit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }

    }

    @FXML
    private void klikPanelVychodu(MouseEvent mouseEvent)
    {
        Prostor cil = panelVychodu.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = PrikazJdi.NAZEV + " " + cil;
        zpracujPrikaz(prikaz);
    }
}