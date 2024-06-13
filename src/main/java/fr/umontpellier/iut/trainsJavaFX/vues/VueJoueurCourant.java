package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * <p>
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends Pane {

    @FXML
    private Label nomJoueurCourant;
    @FXML
    private Label nbArgent;
    @FXML
    private Label pointVictoire;
    @FXML
    private Label nbRails;
    @FXML
    private Label nbCartePioche;
    @FXML
    private HBox cartesMain;
    @FXML
    private HBox cartesEnJeu;
    @FXML
    private HBox cartesRecu;

    public VueJoueurCourant() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateau.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void creerBindings() {
        // Bindings
        GestionJeu.getJeu().joueurCourantProperty().addListener(
                (source, oldJoueur, newJoueur) -> {

                    if (source != null) {

                        nomJoueurCourant.setText(newJoueur.getNom());

                        nbArgent.setText(String.valueOf(newJoueur.getArgent()));

                        pointVictoire.setText(String.valueOf(newJoueur.getScoreTotal()));

                        nbRails.setText(String.valueOf(newJoueur.getNbJetonsRails()));

                        nbCartePioche.setText(String.valueOf(newJoueur.getPioche().size()));

                        //Actualisation joueur courant
                        newJoueur.argentProperty().addListener(
                                (src, oldValue, newValue) -> {
                                    nbArgent.setText(String.valueOf(newValue));
                                }
                        );

                        newJoueur.scoreProperty().addListener(
                                (src, oldValue, newValue) -> {
                                    pointVictoire.setText(String.valueOf(newValue));
                                }
                        );

                        newJoueur.nbJetonsRailsProperty().addListener(
                                (src, oldValue, newValue) -> {
                                    nbRails.setText(String.valueOf(newValue));
                                }
                        );

                        newJoueur.piocheProperty().addListener(
                                (src, oldValue, newValue) -> {
                                    nbCartePioche.setText(String.valueOf(newValue));
                                }
                        );

                        //Cartes

                        newJoueur.mainProperty().addListener(
                                (src, oldValue, newValue) -> {
                                    updateCartes(newJoueur.getJeu().getJoueurCourant());
                                }
                        );

                        newJoueur.cartesRecuesProperty().addListener(
                                (src, oldValue, newValue) -> {
                                    updateCartes(newJoueur.getJeu().getJoueurCourant());
                                }
                        );

                        newJoueur.cartesEnJeuProperty().addListener(
                                (src, oldValue, newValue) -> {
                                    updateCartes(newJoueur.getJeu().getJoueurCourant());
                                }
                        );

                        updateCartes(newJoueur.getJeu().getJoueurCourant());
                    }

                }
        );
    }

    private void updateCartes(Joueur joueur) {
        if (joueur != null) {
            updateCartesHBox(cartesMain, joueur.getMain());
            updateCartesHBox(cartesEnJeu, joueur.getCartesEnJeu());
            updateCartesHBox(cartesRecu, joueur.getCartesRecues());
        } else {
            cartesMain.getChildren().clear();
            cartesEnJeu.getChildren().clear();
            cartesRecu.getChildren().clear();
        }
    }

    private void updateCartesHBox(HBox div, List<Carte> cartes) {
        div.getChildren().clear();
        for (Carte carte : cartes) {
            Label carteLabel = new Label(carte.toString());
            div.getChildren().add(carteLabel);
        }
    }
}
