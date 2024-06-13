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
    private HBox plateauIHM;
    private VuePlateau plateau;
    private VueJoueurCourant joueurCourant;

    public VueDuJeu(IJeu jeu) {
        this.jeu = jeu;

        //BorderPane root = new BorderPane();

        //TOP PLATEAU
        plateauIHM = new HBox();
        plateauIHM.setAlignment(Pos.CENTER);
        plateau = new VuePlateau();
        plateauIHM.getChildren().add(plateau);
        //root.getChildren().add(plateauIHM);
        //root.setTop(plateauIHM);


        // BOTTOM joueurCourant
        HBox joueurCourantIHM = new HBox();
        joueurCourant = new VueJoueurCourant();
        joueurCourantIHM.getChildren().add(joueurCourant);
        //root.getChildren().add(joueurCourantIHM);
        //root.setBottom(joueurCourantIHM);



        //getChildren().add(root);
        getChildren().addAll(plateauIHM, joueurCourantIHM);
    }

    public void creerBindings() {
        plateauIHM.prefWidthProperty().bind(getScene().widthProperty().multiply(0.6));
        plateauIHM.prefHeightProperty().bind(getScene().heightProperty().multiply(0.6));

        //plateau.prefWidthProperty().bind(getScene().widthProperty());
        //plateau.prefHeightProperty().bind(getScene().heightProperty());
        plateau.creerBindings();
        joueurCourant.creerBindings();

    }

    public IJeu getJeu() {
        return jeu;
    }

    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent -> System.out.println("Passer a été demandé"));

}
