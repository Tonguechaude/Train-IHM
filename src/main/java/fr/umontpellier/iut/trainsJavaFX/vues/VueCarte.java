package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.geometry.Insets;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.awt.*;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarte extends StackPane {

    private final ICarte carte;
    private final ImageView imageView;
    private final Circle cercle;
    private Label basDroite;

    public VueCarte(Carte carte) {
        this.carte = carte;

        imageView = new ImageView(getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(125);

        //EFFETS AFFICHAGE
        addTransition();
        //addPopup();

        getChildren().addAll(imageView);

        cercle = new Circle(10);
        basDroite = new Label();


    }

    public VueCarte(Carte carte, IntegerProperty nbRestants) {
        this(carte);

        cercle.setFill(Paint.valueOf("white"));
        cercle.setStroke(Paint.valueOf("black"));

        getChildren().addAll(cercle, basDroite);

        setAlignment(cercle, Pos.BOTTOM_RIGHT);
        //setMargin(cercle, new Insets(0,10,10,0));

        setAlignment(basDroite, Pos.BOTTOM_RIGHT);
        setMargin(basDroite, new Insets(0,3,3,0));
        basDroite.setTextFill(Paint.valueOf("black"));
        basDroite.setText(String.valueOf(nbRestants.get()));
    }

    private Image getImage() {
        return new Image("images/cartes/" + refractorNom(carte.getNom()) + ".jpg");
    }

    public void setCarteChoisieListener(EventHandler<MouseEvent> quandCarteEstChoisie) {
        setOnMouseClicked(quandCarteEstChoisie);
    }
    public String refractorNom(String nom) {
        return nom.replaceAll(" ", "_")
                .replaceAll("[ôÔ]", "o")
                .replaceAll("[éÉ]", "e")
                .toLowerCase();
    }

    public StringProperty getLabel() {
        return basDroite.textProperty();
    }

    public void addTransition() {
        ImageView largeImageView = new ImageView(getImage());
        largeImageView.setPreserveRatio(true);
        largeImageView.setFitHeight(350);

        Popup popup = new Popup();
        popup.setAutoHide(true);
        popup.getContent().add(largeImageView);

        //{Transition
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), largeImageView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), largeImageView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        imageView.setOnMouseEntered(event -> {
            largeImageView.setOpacity(0);
            popup.show(imageView, event.getScreenX(), event.getScreenY());
            fadeIn.playFromStart();
        });

        imageView.setOnMouseExited(event -> {
            fadeOut.playFromStart();
            fadeOut.setOnFinished(e -> popup.hide());
        });
        //FIN Transition}
    }

    public void addPopup() {
        ImageView largeImageView = new ImageView(getImage());
        largeImageView.setPreserveRatio(true);
        largeImageView.setFitHeight(350);

        Popup popup = new Popup();
        popup.setAutoHide(true);
        popup.getContent().add(largeImageView);

        //{ POPUP
        imageView.setOnMouseEntered(event -> {
            popup.show(imageView, event.getScreenX(), event.getScreenY());
        });

        imageView.setOnMouseExited(event -> {
            popup.hide();
        });
        //FIN POPUP}
    }

}
