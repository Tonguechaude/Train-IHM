package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.Joueur;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    private Pane joueurCourantBox;


    @FXML
    private Label instructions;

    @FXML
    private Button passer;

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
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/JoueurCourant.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getChildren().add(joueurCourantBox);
    }

    public void creerBindings() {
        System.out.println("Création des bindings pour le joueur courant...");
        // Bindings
        GestionJeu.getJeu().joueurCourantProperty().addListener(
                (source, oldJoueur, newJoueur) -> {

                    if (newJoueur != null) {
                        System.out.println("Changement de joueur courant : " + newJoueur.getNom());


                        instructions.textProperty().bind(GestionJeu.getJeu().instructionProperty());


                        nomJoueurCourant.setText(newJoueur.getNom());
                        nbArgent.setText(String.valueOf(newJoueur.getArgent()));
                        pointVictoire.setText(String.valueOf(newJoueur.getScoreTotal()));
                        nbRails.setText(String.valueOf(newJoueur.getNbJetonsRails()));
                        nbCartePioche.setText(String.valueOf(newJoueur.getPioche().size()));

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

                        cartesMain.getChildren().clear();

                        for (Carte c : GestionJeu.getJeu().joueurCourantProperty().get().mainProperty()) {
                            VueCarte vc = new VueCarte(c);
                            cartesMain.getChildren().add(vc);
                            EventHandler<? super MouseEvent> actionCarteMain = (mouseEvent -> newJoueur.uneCarteDeLaMainAEteChoisie(c.getNom()));
                            vc.setOnMouseClicked(actionCarteMain);
                        }

                        cartesEnJeu.getChildren().clear();

                        for (Carte c : GestionJeu.getJeu().joueurCourantProperty().get().cartesEnJeuProperty()) {
                            VueCarte vc = new VueCarte(c);
                            cartesEnJeu.getChildren().add(vc);
                            EventHandler<? super MouseEvent> actionCarteRecu = (mouseEvent -> newJoueur.uneCarteEnJeuAEteChoisie(c.getNom()));
                            vc.setOnMouseClicked(actionCarteRecu);
                        }

                        cartesRecu.getChildren().clear();

                        for (Carte c : GestionJeu.getJeu().joueurCourantProperty().get().cartesRecuesProperty()) {
                            VueCarte vc = new VueCarte(c);
                            cartesRecu.getChildren().add(vc);
                        }
                    }
                }
        );

      /*  GestionJeu.getJeu().joueurCourantProperty().get().mainProperty().addListener(
                (source, oldJoueur, newJoueur) -> {


                }

        );*/

        passer.setOnMouseClicked(actionPasserParDefaut);

    }
    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> GestionJeu.getJeu().passerAEteChoisi());


}
