package itisgame;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DialogueManager {

    Group gp;
    Dialogue[] dialogues;


    public DialogueManager(Group gp) {
        this.gp = gp;
        dialogues = new Dialogue[10];
        getDialogueImage();
    }


    public void getDialogueImage() {
        dialogues[0] = new Dialogue();
        dialogues[0].image = new Image("images/dialogues/dialogoBidella.png", 96, 48, false, false);
        dialogues[1] = new Dialogue();
        dialogues[1].image = new Image("images/dialogues/dialogoBregola.png", 96, 48, false, false);
        dialogues[2] = new Dialogue();
        dialogues[2].image = new Image("images/dialogues/dialogoBadolato.png", 96, 48, false, false);
        dialogues[3] = new Dialogue();
        dialogues[3].image = new Image("images/dialogues/dialogoMezzadrelli.png", 96, 48, false, false);
        dialogues[4] = new Dialogue();
        dialogues[4].image = new Image("images/dialogues/dialogoMarega.png", 96, 48, false, false);
        dialogues[5] = new Dialogue();
        dialogues[5].image = new Image("images/dialogues/dialogoMike.png", 96, 48, false, false);
        dialogues[6] = new Dialogue();
        dialogues[6].image = new Image("images/dialogues/dialogoDaniele.png", 96, 48, false, false);
    }

    public void draw(String mappa) {

        Canvas canvaPage = new Canvas(ItisGame.W, ItisGame.H);
        GraphicsContext dialogo = canvaPage.getGraphicsContext2D();

        switch (mappa) {
            case "cEntrataL": {
                dialogo.drawImage(dialogues[0].image, coordinataItem(1), coordinataItem(5), 96, 48);
            }
            break;
            case "aula33": {
                dialogo.drawImage(dialogues[3].image, coordinataItem(3), coordinataItem(7), 96, 48);
            }
            break;
            case "aulaChimica3" : {
                dialogo.drawImage(dialogues[1].image, coordinataItem(7), coordinataItem(1), 96, 48);
            }
            break;
            case "aula13" : {
                dialogo.drawImage(dialogues[2].image, coordinataItem(11), coordinataItem(2), 96, 48);
            }
            break;
            case "aulaChimica5" : {
                dialogo.drawImage(dialogues[4].image, coordinataItem(5), coordinataItem(1), 96, 48);
                dialogo.drawImage(dialogues[5].image, coordinataItem(3), coordinataItem(9), 96, 48);
            }
            break;
            case "bar" : {
                dialogo.drawImage(dialogues[6].image, coordinataItem(6), coordinataItem(3), 96, 48);
            }
            break;
        }

        gp.getChildren().add(canvaPage);


    }



    public int coordinataItem(int c) {
        return c*48;
    }

}
