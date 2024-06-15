package fr.umontpellier.iut.trainsJavaFX.vues;

import fr.umontpellier.iut.trainsJavaFX.ICarte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * Cette classe représente la vue d'une carte.
 * <p>
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarte extends StackPane {

    private final ICarte carte;
    private final ImageView imageView;

    public VueCarte(Carte carte) {
        this.carte = carte;

        imageView = new ImageView(getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(105);

        getChildren().add(imageView);
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

}
