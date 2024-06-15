package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.mecanique.plateau.Plateau;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le nombre et les noms des joueurs de la partie.
 * <p>
 * Sa présentation graphique peut automatiquement être actualisée chaque fois que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private final ObservableList<String> nomsJoueurs;
    private Plateau plateauChoisi = Plateau.OSAKA;

    /**
     * comboBox pour récupérer le nombre de joueurs de la partie
     */
    @FXML
    private ComboBox<Integer> nbJoueurs;
    /**
     * gridpane renseignant le nom des joueurs
     */
    @FXML
    private GridPane choixJoueurs;

    /**
     * attributs gérant :
     * radio Button pour gérer le choix du plateau
     */
    private ToggleGroup choixPlateau;
    @FXML
    private RadioButton choixPlateauTokyo;
    @FXML
    private RadioButton choixPlateauOsaka;

    /**
     * pane de la fenêtre
     */
    @FXML
     private AnchorPane Ancre;


    public VueChoixJoueurs() {
        nomsJoueurs = FXCollections.observableArrayList();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ChoixJoueurs.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setScene(new Scene(Ancre));
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);
    }

    @FXML
    private void jouerClique() {
        setListeDesNomsDeJoueurs();
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs() ; i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            }
            else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return nbJoueurs.getValue();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return ((TextField)choixJoueurs.getChildren().get(playerNumber + 4)).getText();
    }

    public Plateau getPlateau() {
        return plateauChoisi;
    }

    public void createBindings() {
        ChangeListener<Number> nbJoueursChangeListener = (observable, oldValue, newValue) -> {
            for(int i = 1 ; i < 9 ; i++) {
                choixJoueurs.getChildren().get(i).setVisible(false);
            }
            for(int i = 1 ; i <= newValue.intValue() ; i++) {
                choixJoueurs.getChildren().get(i).setVisible(true);
                choixJoueurs.getChildren().get(i+4).setVisible(true);
            }
        };
        ChangeListener<Toggle> choixPlateauChangeListener = (observable, oldValue, newValue) -> {
            plateauChoisi = plateauChoisi.getNomPlateau(newValue.toString());
        };

        choixPlateau.selectedToggleProperty().addListener(choixPlateauChangeListener);
        nbJoueurs.getSelectionModel().selectedItemProperty().addListener(nbJoueursChangeListener);
    }

    public void initialize() {
        choixPlateau = new ToggleGroup();
        choixPlateauOsaka.setToggleGroup(choixPlateau);
        choixPlateauTokyo.setToggleGroup(choixPlateau);
        choixPlateauOsaka.setSelected(true);

        ObservableList<Integer> obList = FXCollections.observableArrayList();
        for(int i = 2; i <= 4; i++) {
            obList.add(i);
        }
        nbJoueurs.setItems(obList);

        createBindings();

    }
}
