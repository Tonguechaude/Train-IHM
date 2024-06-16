package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.IJoueur;
import fr.umontpellier.iut.trainsJavaFX.TrainsIHM;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;



public class VueResultats extends Pane {

    private TrainsIHM ihm;
    @FXML
    private StackPane resultats;

    @FXML
    private BorderPane fondTitre;

    @FXML
    private BorderPane fondGagnant;

    @FXML
    private Label gagnant;

    public VueResultats(TrainsIHM ihm) {
        this.ihm = ihm;
        resultats = new StackPane();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Resultats.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setStyle("-fx-background-color:   #16a085 ; ");
        setStyle("-fx-background-color:   #16a085 ; ");


        gagnant.setVisible(false);

        getChildren().add(resultats);
    }

    @FXML
    public void afficherGagnant() {

        resultats.getChildren().remove(fondTitre);

        setnomGagnant();

        gagnant.setVisible(true);
        playImpactAnimation(gagnant);

    }

    private void setnomGagnant() {
        String nomJGagnant = ihm.getJeu().getJoueurs().get(0).getNom();
        int scoreGagnant = ihm.getJeu().getJoueurs().get(0).getScoreTotal();

        for(IJoueur j : ihm.getJeu().getJoueurs()) {
            if(scoreGagnant < j.getScoreTotal()) {
                nomJGagnant = j.getNom();
                scoreGagnant = j.getScoreTotal();
            }
        }
        nomJGagnant = nomJGagnant + "!!! Avec un score de " + scoreGagnant + "!!!";
        gagnant.setText(nomJGagnant);
    }


    private void playImpactAnimation(Label label) {
        // Créer une transition de mise à l'échelle (scale)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), label);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);

        // Créer une transition de fondu (fade)
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(200), label);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);

        // Créer une transition de mise à l'échelle inverse (pour l'effet de rebond)
        ScaleTransition scaleBackTransition = new ScaleTransition(Duration.millis(100), label);
        scaleBackTransition.setFromX(1.5);
        scaleBackTransition.setFromY(1.5);
        scaleBackTransition.setToX(1.0);
        scaleBackTransition.setToY(1.0);

        // Combiner les transitions dans une séquence
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(fadeInTransition, scaleTransition, scaleBackTransition);
        sequentialTransition.play();
    }



}
