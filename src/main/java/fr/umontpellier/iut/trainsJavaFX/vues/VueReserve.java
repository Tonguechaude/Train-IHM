package fr.umontpellier.iut.trainsJavaFX.vues;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.Carte;
import fr.umontpellier.iut.trainsJavaFX.mecanique.cartes.ListeDeCartes;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class VueReserve extends Pane {
    private final FlowPane VueReserve;

    public VueReserve(ListeDeCartes reserve) {
        VueReserve = new FlowPane();
        for(Carte carte : reserve) {
            VueCarte vueC = new VueCarte(carte);
            VueReserve.getChildren().add(vueC);
            Circle circle = new Circle(10);
            circle.setCenterX(vueC.getLayoutX());
            circle.setCenterY(vueC.getLayoutY());
            VueReserve.getChildren().add(circle);
        }
    }

    private class VueCarteReserve extends VueCarte {
        private IntegerProperty nbRestants;
        private Circle circle;
        private Label basDroite;
        public VueCarteReserve(Carte carte, SimpleIntegerProperty nbRestants) {
            super(carte);
            this.nbRestants = nbRestants;
            this.circle = new Circle(10);
            Label label = new Label(carte.toString());
        }

        private void createBindings() {
            ;

        }
    }





}
