package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, ses cartes en main, son score, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends Pane {

    private final IJeu jeu;

    private final BorderPane vueDuJeu;

    private HBox plateauIHM;
    private VuePlateau plateau;

    private HBox joueurCourantIHM;
    private VueJoueurCourant joueurCourant;

    private HBox autresJoueursIHM;
    private VueAutresJoueurs autresJoueurs;

    public VueDuJeu(IJeu jeu)
    {
        this.jeu = jeu;

        vueDuJeu = new BorderPane();

        //TOP PLATEAU
        initialisePlateau();


        // BOTTOM joueurCourant
        initialiseJoueurCourant();

        //LEFT
        initialiseAutresJoueurs();



        getChildren().add(vueDuJeu);
    }

    private void initialisePlateau()
    {
        plateauIHM = new HBox();
        plateau = new VuePlateau();
        plateauIHM.getChildren().add(plateau);
        vueDuJeu.setLeft(plateauIHM);
    }

    private void initialiseJoueurCourant()
    {
        joueurCourantIHM = new HBox();
        joueurCourant = new VueJoueurCourant();
        joueurCourantIHM.getChildren().add(joueurCourant);
        vueDuJeu.setBottom(joueurCourantIHM);
    }

    private void initialiseAutresJoueurs()
    {
        autresJoueursIHM = new HBox();
        autresJoueurs = new VueAutresJoueurs();
        autresJoueursIHM.getChildren().add(autresJoueurs);
        vueDuJeu.setRight(autresJoueurs);
    }


    public void creerBindings()
    {

        plateauIHM.prefWidthProperty().bind(getScene().widthProperty().multiply(0.6));
        plateauIHM.prefHeightProperty().bind(getScene().heightProperty().multiply(0.6));

        joueurCourantIHM.prefHeightProperty().bind(getScene().heightProperty().multiply(0.5));
        joueurCourantIHM.prefWidthProperty().bind(getScene().widthProperty().multiply(0.5));

        autresJoueursIHM.prefWidthProperty().bind(getScene().widthProperty());
        autresJoueursIHM.prefHeightProperty().bind(getScene().heightProperty());

        vueDuJeu.prefHeightProperty().bind(getScene().heightProperty());
        vueDuJeu.prefWidthProperty().bind(getScene().widthProperty());

        plateau.creerBindings();
        joueurCourant.creerBindings();
        autresJoueurs.creerBindings();

    }

    public IJeu getJeu() {
        return jeu;
    }

}
