package frontend.popUp;

import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class PopUp {
    /**
     * Create constructor that creates a small pop up window
     * @param message the message on the pop up window
     */
    public PopUp(String message){
        Stage window = new Stage();
        Label errorMessage = new Label(message);
        errorMessage.setAlignment(Pos.CENTER);
        errorMessage.setWrapText(true);
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setScene(new Scene(errorMessage, 300, 200));
        window.showAndWait();

    }
}
