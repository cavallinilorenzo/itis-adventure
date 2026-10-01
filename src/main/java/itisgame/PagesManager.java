package itisgame;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PagesManager {

    Group gp;
    public boolean[] pagineTrovate = {false,false,false,false,false,false,false,false,false,false};
    Pages pagine;
    public int nPagineTrovate;

    public PagesManager(Group gp) {
        this.gp = gp;
        nPagineTrovate = 0;
        pagine = new Pages();
        pagine.image = new Image("images/items/page.png", 48, 48, false, false);
        pagine.pagesHitBox = new Rectangle(-1000, -1000, 40, 40);
        pagine.pagesHitBox.setFill(Color.YELLOW);
        gp.getChildren().add(pagine.pagesHitBox);
    }

    public void draw(String mappa) {

        Canvas canvaPage = new Canvas(ItisGame.W, ItisGame.H);
        GraphicsContext itemPage = canvaPage.getGraphicsContext2D();

        switch (mappa) {
            case "aula33" : {
                if (!pagineTrovate[0]) {
                    drawItem(itemPage, 12, 3);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "bar" : {
                if (!pagineTrovate[1]) {
                    drawItem(itemPage, 3, 4);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "aulaCad1" : {
                if (!pagineTrovate[2]) {
                    drawItem(itemPage, 13, 3);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "aulaBanchiPiccoli" : {
                if (!pagineTrovate[3]) {
                    drawItem(itemPage, 4, 4);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "aulaCorridoioFromCadToPari" : {
                if (!pagineTrovate[4]) {
                    drawItem(itemPage, 7, 2);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "aulaSotto" : {
                if (!pagineTrovate[5]) {
                    drawItem(itemPage, 9, 10);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "aulaChimica4" : {
                if (!pagineTrovate[6]) {
                    drawItem(itemPage, 11, 6);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "palestraDoppie1" : {
                if (!pagineTrovate[7]) {
                    drawItem(itemPage, 8, 6);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "aulaTele1" : {
                if (!pagineTrovate[8]) {
                    drawItem(itemPage, 12, 8);
                } else {
                    removeHitBox();
                }
            }
            break;
            case "corridoioChimica2" : {
                if (!pagineTrovate[9]) {
                    drawItem(itemPage, 6, 8);
                } else {
                    removeHitBox();
                }
            }
            break;
            default : {
                pagine.pagesHitBox.setX(-1000);
                pagine.pagesHitBox.setY(-1000);
            }
            break;
        }

        gp.getChildren().add(canvaPage);

    }

    public void drawItem(GraphicsContext itemPage, int x, int y) {
        itemPage.drawImage(new Image("images/items/page.png"), coordinataItem(x), coordinataItem(y), 48, 48);
        pagine.pagesHitBox.setX(coordinataHitBox(x));
        pagine.pagesHitBox.setY(coordinataHitBox(y));
    }

    public int coordinataItem(int c) {
        return c*48;
    }

    public int coordinataHitBox(int c) {
        return coordinataItem(c) + 8;
    }


    public void removeHitBox() {
        pagine.pagesHitBox.setX(-1000);
        pagine.pagesHitBox.setY(-1000);
    }


}
