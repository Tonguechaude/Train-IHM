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




                       /* newJoueur.mainProperty().addListener(
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

                        updateCartes(newJoueur.getJeu().getJoueurCourant());*/

                        cartesMain.getChildren().clear();

                        for (Carte c : GestionJeu.getJeu().joueurCourantProperty().get().mainProperty())
                        {
                            Button b = new Button(c.getNom());
                            cartesMain.getChildren().add(b);
                            EventHandler<? super MouseEvent> actionCarteMain = (mouseEvent -> newJoueur.uneCarteDeLaMainAEteChoisie(c.getNom()));
                            b.setOnMouseClicked(actionCarteMain);
                        }

                        cartesEnJeu.getChildren().clear();

                        for (Carte c : GestionJeu.getJeu().joueurCourantProperty().get().cartesEnJeuProperty())
                        {
                            Button b = new Button(c.getNom());
                            cartesMain.getChildren().add(b);
                            EventHandler<? super MouseEvent> actionCarteRecu = (mouseEvent -> newJoueur.uneCarteEnJeuAEteChoisie(c.getNom()));
                            b.setOnMouseClicked(actionCarteRecu);
                        }

                        cartesRecu.getChildren().clear();

                    }
                }
        );
    }

    /*private void updateCartes(Joueur joueur) {
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
        for (Carte carte : cartes)
        {
            Button b = new Button(carte.getNom());
            div.getChildren().add(b);
            b.setOnMouseClicked(actionCarte);

        }
    }*/

    private void initialiseCarte (HBox cartesMain, HBox cartesEnJeu, HBox cartesRecu)
    {

    }
}
