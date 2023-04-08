package cz.vse.seka01_semestralka.main;

import cz.vse.seka01_semestralka.logika.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Hlavní třída ovládající javafx
 */
public class HomeController {

    @FXML
    private ListView<Prostor> panelVychodu;
    @FXML
    private ListView<Polozka> panelBrasny;
    @FXML
    private ListView<Polozka> panelPolozek;
    @FXML
    private ListView<Postava> panelPostav;
    @FXML
    private Button tlacitkoPoslat;
    @FXML
    private TextArea vystup;
    @FXML
    private TextField vstup;

    private IHra hra = new Hra();
    private ObservableList<Prostor> seznamVychodu = FXCollections.observableArrayList();
    private ObservableList<Polozka> obsahBrasny = FXCollections.observableArrayList();
    private ObservableList<Polozka> obsahProstoru = FXCollections.observableArrayList();
    private ObservableList<Postava> postavyProstoru = FXCollections.observableArrayList();
    /**
     * obrázek hráče
     */
    @FXML
    public ImageView hrac;
    private Map<String, Point2D> souradniceProstoru = new HashMap<>();

    @FXML
    private void initialize(){
        vystup.appendText(hra.vratUvitani() + "\n\n");
        Platform.runLater(() -> vstup.requestFocus());
        panelBrasny.setItems(obsahBrasny);
        panelPolozek.setItems(obsahProstoru);
        panelVychodu.setItems(seznamVychodu);
        panelPostav.setItems(postavyProstoru);
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
            aktualizujSeznamVychodu();
            aktualizujPolohuHrace();
        });
        hra.registruj(ZmenaHry.KONEC_HRY, () -> aktualizujKonecHry());
        aktualizujSeznamVychodu();
        aktualizujObsahBrasny();
        aktualizujObsahProstoru();
        aktualizujSeznamPostav();
        vlozSouradnice();
        panelVychodu.setCellFactory(param -> new ListCellProstor());
        panelBrasny.setCellFactory(param -> new ListCellPredmet());
        panelPolozek.setCellFactory(param -> new ListCellPredmet());
        panelPostav.setCellFactory(param -> new ListCellPostava());
    }

    private void aktualizujSeznamPostav() {
        postavyProstoru.clear();
        postavyProstoru.addAll(hra.getHerniPlan().getAktualniProstor().getSeznamPostav());
    }

    private void aktualizujObsahProstoru()
    {
        obsahProstoru.clear();
        obsahProstoru.addAll(hra.getHerniPlan().getAktualniProstor().getSeznamPolozek());
    }

    private void aktualizujObsahBrasny()
    {
        obsahBrasny.clear();
        obsahBrasny.addAll(hra.getHerniPlan().getBrasna().getSeznamPolozek());
    }

    private void aktualizujPolohuHrace()
    {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        hrac.setLayoutX(souradniceProstoru.get(prostor).getX());
        hrac.setLayoutY(souradniceProstoru.get(prostor).getY());
    }
    private void vlozSouradnice()
    {
        souradniceProstoru.put("domeček", new Point2D(40, 0));
        souradniceProstoru.put("les", new Point2D(340, 0));
        souradniceProstoru.put("hluboký_les", new Point2D(30, 100));
        souradniceProstoru.put("jeskyně", new Point2D(140, 125));
        souradniceProstoru.put("chaloupka", new Point2D(340, 100));
    }
    @FXML
    private void aktualizujSeznamVychodu()
    {
        seznamVychodu.clear();
        seznamVychodu.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    /**
     * aktualizuje konec hry
     */
    public void aktualizujKonecHry()
    {
        if (hra.konecHry()){
            vystup.appendText(hra.vratEpilog());
        }

        vstup.setDisable(hra.konecHry());
        tlacitkoPoslat.setDisable(hra.konecHry());
        panelVychodu.setDisable(hra.konecHry());
        panelPostav.setDisable(hra.konecHry());
        panelPolozek.setDisable(hra.konecHry());
        panelBrasny.setDisable(hra.konecHry());

    }

    @FXML
    private void poslatVstup(ActionEvent actionEvent) {
        String prikaz = vstup.getText();
        vstup.clear();
        zpracujPrikaz(prikaz);
    }

    private void zpracujPrikaz(String prikaz) {
        String vysledek = "";
        if (prikaz.equalsIgnoreCase("seber maliny"))
        {
            vystup.appendText("> " + prikaz + "\n");
            vysledek = hra.zpracujPrikaz(prikaz);
            vystup.appendText(vysledek + "\n\n");
            prikaz = "pouzij maliny karkulka";
            vystup.appendText("> " + prikaz + "\n");
            vysledek = hra.zpracujPrikaz(prikaz);
            vystup.appendText(vysledek + "\n\n");
        }
        else
        {
            vystup.appendText("> " + prikaz + "\n");
            vysledek = hra.zpracujPrikaz(prikaz);
            vystup.appendText(vysledek + "\n\n");
        }
        aktualizujObsahBrasny();
        aktualizujObsahProstoru();
        aktualizujSeznamPostav();
    }

    /**
     * ukončí hru předběžně
     * @param actionEvent povinný nevyužitý parametr
     */
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
        String prikaz = PrikazJdi.NAZEV + " " + cil.getNazev();
        zpracujPrikaz(prikaz);
    }
    @FXML
    private void napovedaKlik(ActionEvent actionEvent)
    {
        Stage napovedaStage = new Stage();
        WebView wv = new WebView();
        Scene napovedaScena = new Scene(wv);
        napovedaStage.setScene(napovedaScena);
        napovedaStage.show();
        wv.getEngine().load(getClass().getResource("napoveda.html").toExternalForm());
    }
    @FXML
    private void klikPanelPolozky(MouseEvent mouseEvent)
    {
        Polozka cil = panelPolozek.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = PrikazSeber.JMENO + " " + cil.getJmeno();
        zpracujPrikaz(prikaz);
    }
    @FXML
    private void klikPanelBrasna(MouseEvent mouseEvent)
    {
        Polozka cil = panelBrasny.getSelectionModel().getSelectedItem();
        if (cil == null) return;
        String prikaz = PrikazPoloz.JMENO + " " + cil.getJmeno();
        zpracujPrikaz(prikaz);
    }

    @FXML
    private void restartKlik(ActionEvent actionEvent) {
        Alert opakovat = new Alert(Alert.AlertType.CONFIRMATION, "Opravdu chceš restartovat hru?");
        Optional<ButtonType> vysledek = opakovat.showAndWait();
        if(vysledek.isPresent() && vysledek.get() == ButtonType.OK)
        {
            vystup.clear();
            hra = new Hra();
            initialize();
            aktualizujPolohuHrace();
            aktualizujKonecHry();
        }
    }

    /**
     * @param mouseEvent povinný nevyužitý parametr
     * zařizuje, co se stane po kliknutí na položku v panelu postav
     */
    public void klikPanelPostav(MouseEvent mouseEvent) {
        Postava postava = panelPostav.getSelectionModel().getSelectedItem();
        String prikaz = "";
        boolean vysledek = false;
        switch (postava.getJmeno())
        {
            case "vlk":
                vysledek = hra.getHerniPlan().getBrasna().obsahujePolozku("nuz");
                if (vysledek)
                {
                    prikaz = PrikazPouzij.JMENO + " nuz vlk";
                }
                break;
            case "babicka":
                vysledek = hra.getHerniPlan().getBrasna().obsahujePolozku("babovka");
                if (vysledek)
                {
                    prikaz = PrikazPouzij.JMENO + " babovka babicka";
                }
                break;
            case "karkulka":
                vysledek = hra.getHerniPlan().getBrasna().obsahujePolozku("proteza");
                if (vysledek)
                {
                    prikaz = PrikazPouzij.JMENO + " proteza karkulka";
                }
                break;
        }
        if (postava == null) return;
        if (!prikaz.isEmpty()) zpracujPrikaz(prikaz);
    }
}