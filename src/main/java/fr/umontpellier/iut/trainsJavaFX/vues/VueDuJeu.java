package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.GestionJeu;
import fr.umontpellier.iut.trainsJavaFX.IJeu;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

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

    private HBox reserveIHM;
    private FlowPane reserve;

    private HBox bottom;
    private HBox joueurCourantIHM;
    private VueJoueurCourant joueurCourant;

    private HBox autresJoueursIHM;
    private VueAutresJoueurs autresJoueurs;

    public VueDuJeu(IJeu jeu)
    {
        this.jeu = jeu;

        vueDuJeu = new BorderPane();

        //LEFT PLATEAU
        initialisePlateau();


        // BOTTOM joueurCourant
        initialiseBottom();

        //RIGHT reserve
        initialiseReserve();

        getChildren().add(vueDuJeu);

        setStyle(
                "-fx-background-color:#efece4"
        );
    }

    private void initialisePlateau()
    {
        plateauIHM = new HBox();
        plateau = new VuePlateau();
        plateauIHM.getChildren().add(plateau);
        vueDuJeu.setLeft(plateauIHM);
        HBox.setMargin(plateau, new Insets(10,0,0,120));
    }

    public void initialiseBottom()
    {
        initialiseJoueurCourant();
        initialiseAutresJoueurs();
        bottom = new HBox(joueurCourantIHM, autresJoueursIHM);
        bottom.setAlignment(Pos.CENTER);
        vueDuJeu.setBottom(bottom);
    }

    private void initialiseJoueurCourant()
    {
        joueurCourantIHM = new HBox();
        joueurCourant = new VueJoueurCourant();
        joueurCourantIHM.getChildren().add(joueurCourant);
    }

    private void initialiseAutresJoueurs()
    {
        autresJoueursIHM = new HBox();
        autresJoueurs = new VueAutresJoueurs();
        autresJoueursIHM.getChildren().add(autresJoueurs);
        //HBox.setMargin(autresJoueursIHM, new Insets(50));
    }

    private void initialiseReserve()
    {
        reserve = new FlowPane();
        reserveIHM = new HBox(reserve);

        for(String nomCarte : jeu.getTaillesPilesReserveProperties().keySet()) {
            VueCarte vc = new VueCarte(jeu.getReserve().getCarte(nomCarte), jeu.getTaillesPilesReserveProperties().get(nomCarte));
            reserve.getChildren().add(vc);
            EventHandler<MouseEvent> actionCarteReserve = (mouseEvent -> GestionJeu.getJeu().uneCarteDeLaReserveEstAchetee(nomCarte));
            vc.setCarteChoisieListener(actionCarteReserve);
            jeu.getTaillesPilesReserveProperties().get(nomCarte).addListener(
                    (observable, oldValue, newValue) -> {
                        vc.getLabel().setValue(String.valueOf(newValue));
                    }
            );
        }
        reserve.setAlignment(Pos.CENTER);
        reserve.setHgap(6);
        reserve.setVgap(6);
        reserve.setPadding(new Insets(10));
        reserve.setStyle(
                "-fx-background-color:  #aeb6bf ;" +
        "-fx-background-radius: 25px;" +
                "-fx-border-color: #283747;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 25px;"
        );
        HBox.setMargin(reserve, new Insets(10, 50,10,0));
        vueDuJeu.setRight(reserveIHM);
    }

    public void creerBindings()
    {
        plateauIHM.prefWidthProperty().bind(getScene().widthProperty().multiply(0.5));
        plateauIHM.prefHeightProperty().bind(getScene().heightProperty().multiply(0.5));

        ((HBox) bottom.getChildren().get(0)).prefWidthProperty().bind(getScene().widthProperty().multiply(0.60));
        ((HBox) bottom.getChildren().get(1)).prefWidthProperty().bind(((HBox) bottom.getChildren().get(0)).prefWidthProperty().multiply(0.4));


        vueDuJeu.minHeightProperty().bind(getScene().heightProperty());
        vueDuJeu.minWidthProperty().bind(getScene().widthProperty());

        joueurCourantIHM.setPadding(new Insets(0,10,10,10));
        autresJoueursIHM.setPadding(new Insets(0,10,10,10));

        plateau.creerBindings();
        joueurCourant.creerBindings();
        autresJoueurs.creerBindings();

    }

    public IJeu getJeu() {
        return jeu;
    }

}
