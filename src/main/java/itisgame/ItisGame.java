package itisgame;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ItisGame extends Application{
    public static final double W = 768, H = 576;
    private static final String PLAYER_UP_1 = "images/player/boy_up_1.png";
    private static final String PLAYER_UP_2 = "images/player/boy_up_2.png";
    private static final String PLAYER_DOWN_1 = "images/player/boy_down_1.png";
    private static final String PLAYER_DOWN_2 = "images/player/boy_down_2.png";
    private static final String PLAYER_LEFT_1 = "images/player/boy_left_1.png";
    private static final String PLAYER_LEFT_2 = "images/player/boy_left_2.png";
    private static final String PLAYER_RIGHT_1 = "images/player/boy_right_1.png";
    private static final String PLAYER_RIGHT_2 = "images/player/boy_right_2.png";
    private ImageView player;
    public int which_sprite;
    public int sprite_counter;
    boolean running, goNorth, goSouth, goEast, goWest;
    public Label counterPagineTrovate;
    public Label labelVittoria;
    public Label intro;
    TileManager tileM;
    Group map;
    Rectangle player_hitbox;
    boolean vittoria = false;
    boolean inizio = false;
    public Media media;
    MediaPlayer soundtrack;
    public Media media2;
    MediaPlayer soundtrack2;
    public Media media3;
    MediaPlayer soundtrack3;

    @Override
    public void start(Stage stage) {

        /*      SETUP MUSICA        */
        /*media = new Media(getClass().getResource("/sounds/soundtrack.mp3").toExternalForm());
        soundtrack = new MediaPlayer(media);
        soundtrack.setVolume(0.3);
        soundtrack.setCycleCount(1);
        soundtrack.setOnEndOfMedia(() -> {
            soundtrack.seek(Duration.ZERO);
            soundtrack.play();
        });
        media2 = new Media(getClass().getResource("/sounds/getPage.mp3").toExternalForm());
        soundtrack2 = new MediaPlayer(media2);
        soundtrack2.setVolume(0.7);
        media3 = new Media(getClass().getResource("/sounds/win.mp3").toExternalForm());
        soundtrack3 = new MediaPlayer(media3);
        soundtrack3.setVolume(0.7);*/
        //soundtrack.setCycleCount(MediaPlayer.INDEFINITE);
        //-------------------------------------------------------------

        
        /*      FONTS       */
        Font customFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 40);
        Font fontIntro = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 20);
        //-------------------------------------------------------------


        /*      LABEL INTRO      */
        intro = new Label("Trova le 10 pagine di quaderno\nper uscire dall'Itis E.Fermi");
        intro.setTextFill(Color.WHITE);
        intro.setFont(fontIntro);
        intro.setLayoutX(90);
        intro.setLayoutY(H/2);
        Rectangle sfondoIntro = new Rectangle(0, 0, W, H);
        //-------------------------------------------------------------


        /*      SETUP INTRO      */
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> {
                    inizio=true;
                    sfondoIntro.setVisible(false);
                    intro.setVisible(false);
                })
        );
        timeline.setCycleCount(1);
        //-------------------------------------------------------------


        /*      LABEL VITTORIA      */
        labelVittoria = new Label("HAI VINTO!");
        labelVittoria.setTextFill(Color.WHITE);
        labelVittoria.setFont(customFont);
        labelVittoria.setLayoutX(200);
        labelVittoria.setLayoutY(H/2);
        //-------------------------------------------------------------


        /*      CREAZIONE PLAYER        */
        player = new ImageView(new Image(PLAYER_DOWN_1, 48, 48, false, false));
        player_hitbox = new Rectangle(30, 30);
        player_hitbox.setFill(Color.TRANSPARENT);
        which_sprite = 1;
        sprite_counter = 0;
        //-------------------------------------------------------------


        /*      CREAZIONE MAPPA         */
        map = new Group();
        tileM = new TileManager(map);
        map.getChildren().add(player);
        map.getChildren().add(player_hitbox);
        //-------------------------------------------------------------


        /*      LABEL COUNTER PAGINE TROVATE      */
        counterPagineTrovate = new Label(tileM.pm.nPagineTrovate + "/10");
        counterPagineTrovate.setTextFill(Color.WHITE);
        counterPagineTrovate.setFont(customFont);
        counterPagineTrovate.setLayoutX(20);
        counterPagineTrovate.setLayoutY(20);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(javafx.scene.paint.Color.GRAY);
        dropShadow.setRadius(5.0);
        counterPagineTrovate.setEffect(dropShadow);
        map.getChildren().add(counterPagineTrovate);
        //-------------------------------------------------------------

        /*      LANCIO INTRO        */
        map.getChildren().add(sfondoIntro);
        map.getChildren().add(intro);
        timeline.play();
        //-------------------------------------------------------------
        
        //soundtrack.setAutoPlay(true);
        
        
        moveHeroTo(W / 2, H / 2);
        Scene scene = new Scene(map, W, H, Color.BLACK);


        /*      GESTIONE DIREZIONI      */
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP: goNorth = true;
                break;
                case DOWN: goSouth = true;
                break;
                case LEFT: goWest = true;
                break;
                case RIGHT:goEast = true;
                break;
                case SHIFT: running = true;
                break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP: goNorth = false;
                break;
                case DOWN: goSouth = false;
                break;
                case LEFT: goWest = false;
                break;
                case RIGHT:goEast = false;
                break;
                case SHIFT: running = false;
                break;
            }
        });
        //-------------------------------------------------------------

        /*      LANCIO SCENA        */
        stage.setScene(scene);
        stage.setTitle("Itis Game");
        stage.setResizable(false);
        stage.show();
        //-------------------------------------------------------------


        /*      GESTIONE ANIMAZIONI PLAYER      */
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;
                if (goNorth) {
                    dy -= 3;
                    sprite_counter++;
                    if (sprite_counter > 15) {
                        if (which_sprite == 1) which_sprite = 2;
                        else if (which_sprite == 2) which_sprite = 1;
                        sprite_counter = 0;
                    }
                    if (which_sprite == 1) {
                        player.setImage(new Image(PLAYER_UP_1, 48, 48, false, false));
                    } else {
                        player.setImage(new Image(PLAYER_UP_2, 48, 48, false, false));
                    }
                }
                if (goSouth) {
                    dy += 3;
                    sprite_counter++;
                    if (sprite_counter > 15) {
                        if (which_sprite == 1) which_sprite = 2;
                        else if (which_sprite == 2) which_sprite = 1;
                        sprite_counter = 0;
                    }
                    if (which_sprite == 1) {
                        player.setImage(new Image(PLAYER_DOWN_1, 48, 48, false, false));
                    } else {
                        player.setImage(new Image(PLAYER_DOWN_2, 48, 48, false, false));
                    }
                }
                if (goEast) {
                    dx += 3;
                    sprite_counter++;
                    if (sprite_counter > 15) {
                        if (which_sprite == 1) which_sprite = 2;
                        else if (which_sprite == 2) which_sprite = 1;
                        sprite_counter = 0;
                    }
                    if (which_sprite == 1) {
                        player.setImage(new Image(PLAYER_RIGHT_1, 48, 48, false, false));
                    } else {
                        player.setImage(new Image(PLAYER_RIGHT_2, 48, 48, false, false));
                    }
                }
                if (goWest) {
                    dx -= 3;
                    sprite_counter++;
                    if (sprite_counter > 15) {
                        if (which_sprite == 1) which_sprite = 2;
                        else if (which_sprite == 2) which_sprite = 1;
                        sprite_counter = 0;
                    }
                    if (which_sprite == 1) {
                        player.setImage(new Image(PLAYER_LEFT_1, 48, 48, false, false));
                    } else {
                        player.setImage(new Image(PLAYER_LEFT_2, 48, 48, false, false));
                    }
                }
                if (running) {
                    dx *= 2;
                    dy *= 2;
                }
                if (!vittoria && inizio)
                    moveHeroBy(dx, dy);
            }
        };
        timer.start();
        //-------------------------------------------------------------
    }


    /*      METODO PER RICHIAMARE SPOSTAMENTO PLAYER        */
    private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = player.getBoundsInLocal().getWidth()  / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;

        double x = cx + player.getLayoutX() + dx;
        double y = cy + player.getLayoutY() + dy;

        moveHeroTo(x, y);
    }


    /*      METODO PER SPOSTARE IL PLAYER      */
    private void moveHeroTo(double x, double y) {

        /*      VARIABILI PER MOVIMENTO PLAYER     */
        final double cx = player.getBoundsInLocal().getWidth()  / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;
        player_hitbox.setX(x);
        player_hitbox.setY(y);
        //-------------------------------------------------------------


        /*      GESTORE COLLISIONE PLAYER BLOCCHI     */
        boolean intersecato = false;
        for (int i=0; i<tileM.a.size(); i++) {
            if (checkCollision(player_hitbox, tileM.a.get(i))) {
                intersecato= true;
            }
        }
        if (x-cx>=0 && x+cx<=W && y-cy>=0 && y+cy<=H && !intersecato) {
            player.relocate(x - cx, y - cy);
            player_hitbox.relocate(x-cx+9, y-cy+9);
        }
        //-------------------------------------------------------------


        /*      GESTORE COLLISIONE PLAYER ITEM     */
        if (checkCollision(player_hitbox, tileM.pm.pagine.pagesHitBox)) {
            switch (tileM.selectedMap) {
                case "aula33":
                    tileM.pm.pagineTrovate[0]=true;
                    break;
                case "bar":
                    tileM.pm.pagineTrovate[1]=true;
                    break;
                case "aulaCad1":
                    tileM.pm.pagineTrovate[2]=true;
                    break;
                case "aulaBanchiPiccoli":
                    tileM.pm.pagineTrovate[3]=true;
                    break;
                case "aulaCorridoioFromCadToPari":
                    tileM.pm.pagineTrovate[4]=true;
                    break;
                case "aulaSotto":
                    tileM.pm.pagineTrovate[5]=true;
                    break;
                case "aulaChimica4":
                    tileM.pm.pagineTrovate[6]=true;
                    break;
                case "palestraDoppie1":
                    tileM.pm.pagineTrovate[7]=true;
                    break;
                case "aulaTele1":
                    tileM.pm.pagineTrovate[8]=true;
                    break;
                case "corridoioChimica2":
                    tileM.pm.pagineTrovate[9]=true;
                    break;
            }
            /*soundtrack2.play();
            soundtrack2.seek(Duration.seconds(0));*/
            tileM.pm.nPagineTrovate++;
            redrawMap();
        }
        //-------------------------------------------------------------



        /*      CAMBIO DI STANZA       */
        switch (tileM.selectedMap) {
            case "entrata":
                if(x<=27 && y>=216 && y<=360) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(740-cx, y);
                } else if (y>=553 && x>=260 && x<=460) { 
                    tileM.selectedMap = "cEntrataD";
                    reloadMap();
                    moveHeroTo(x, 21+cx);
                }
                break;    
            case "cEntrataL":
                if(x>=744 && y>=216 && y<=360) {
                    tileM.selectedMap = "entrata";
                    reloadMap();
                    moveHeroTo(27+cx, y);
                } else if(x<=23 && y>=363 && y<=405) {
                    tileM.selectedMap = "scaleDispari1";
                    reloadMap();
                    moveHeroTo(555-cx, y);
                } else if (y<=23 && x>=594 && x<=606) {
                    tileM.selectedMap = "aulaL1";
                    reloadMap();
                    moveHeroTo(552, 465-cy);
                } else if (y<=23 && x>=354 && x<=366) {
                    tileM.selectedMap = "aulaL2";
                    reloadMap();
                    moveHeroTo(552, 465-cy);
                } else if (y>=553 && x>=354 && x<=366) {
                    tileM.selectedMap = "aulaL3";
                    reloadMap();
                    moveHeroTo(552, 111+cy);
                } else if (y>=553 && x>=162 && x<=174) {
                    tileM.selectedMap = "aulaL4";
                    reloadMap();
                    moveHeroTo(552, 111+cy);
                } else if(x<=23 && y>=150 && y<=230) {
                tileM.selectedMap = "corridoioInfo";
                reloadMap();
                moveHeroTo(747-cx, y);
            }
                break;
            case "aulaL1":
                if (y>=462 && x>=546 && x<=558) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(600, 21+cy);
                }
                break;
            case "aulaL2":
                if (y>=462 && x>=546 && x<=558) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(366, 21+cy);
                }
                break;
            case "aulaL3":
                if (y<=111 && x>=546 && x<=558) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(366, 555-cy);
                }
                break;
            case "aulaL4":
                if (y<=111 && x>=546 && x<=558) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(168, 555-cy);
                }
                break;
            case "cEntrataD":
                if (y<=23 && x>=260 && x<=460) {
                    tileM.selectedMap = "entrata";
                    reloadMap();
                    moveHeroTo(x, 555-cx);
                } else if (y>=553 && x>=258 && x<=462) {
                    tileM.selectedMap = "curvaBar1";
                    reloadMap();
                    moveHeroTo(x, 21+cx);
                } else if (x>=510 && y>=306 && y<=510) {
                    tileM.selectedMap = "stanzaXAulePari";
                    reloadMap();
                    moveHeroTo(21+cx, y-237);
                } else if (x>=510 && y>=60 && y<=80) {
                    tileM.selectedMap = "aulaBanchiPiccoli";
                    reloadMap();
                    moveHeroTo(21+cx, 115);
                } else if (x>=510 && y>=200 && y<=230) {
                    tileM.selectedMap = "aulaBanchiPiccoli";
                    reloadMap();
                    moveHeroTo(21+cx, 460);
                }
                break;
            case "aulaBanchiPiccoli":
                if (x<=23 && y>=110 && y<=130) {
                    tileM.selectedMap = "cEntrataD";
                    reloadMap();
                    moveHeroTo(510-cx, 70);
                } else if (x<=23 && y>=440 && y<=460) {
                    tileM.selectedMap = "cEntrataD";
                    reloadMap();
                    moveHeroTo(510-cx, 215);
                }
                break;
            case "stanzaXAulePari":
                if (x<=23 && y>=66 && y<=270) {
                    tileM.selectedMap = "cEntrataD";
                    reloadMap();
                    moveHeroTo(513-cx, y+237);
                } else if (x>=744 && y>=354 && y<=510) {
                    tileM.selectedMap = "angoloXLaboratori";
                    reloadMap();
                    moveHeroTo(21+cx, y-144);
                } else if (y<=23 && x>=162 && x<=174) {
                    tileM.selectedMap = "giardinoCentrale2";
                    reloadMap();
                    moveHeroTo(120, 553-cy);
                }
                break;
            case "scaleDispari1":
                if (x>=553 && y>=363 && y<=408) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                } else if (x>=553 && y>=171 && y<=213) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                }
                break;
            case "scaleDispari2":
                if (x>=553 && y>=363 && y<=408) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                } else if (x>=553 && y>=171 && y<=213) {
                    tileM.selectedMap = "2oPianoDispari";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                } else if (y>=462 && x>=300 && x<=320) {
                    tileM.selectedMap = "aulaTele1";
                    reloadMap();
                    moveHeroTo(597, 111+cy);
                } else if (y<=111 && x>=300 && x<=320) {
                    tileM.selectedMap = "aulaTele2";
                    reloadMap();
                    moveHeroTo(597, 450-cy);
                }
                break;
            case "aulaTele1":
                if (y<=111 && x>=594 && x<=606) {
                    tileM.selectedMap = "scaleDispari2";
                    reloadMap();
                    moveHeroTo(310, 462-cy);
                }
                break;
            case "aulaTele2":
                if (y>=463 && x>=594 && x<=606) {
                    tileM.selectedMap = "scaleDispari2";
                    reloadMap();
                    moveHeroTo(310, 111+cy);
                }
                break;
            case "1oPianoDispari":
                if (x<=23 && y>=171 && y<=213) {
                    tileM.selectedMap = "scaleDispari1";
                    reloadMap();
                    moveHeroTo(555-cx, y);
                } else if (y<=23 && x>=110 && x<=130) {
                    tileM.selectedMap = "aula13";
                    reloadMap();
                    moveHeroTo(552, 465-cx);
                } else if (y<=23 && x>=350 && x<=370) {
                    tileM.selectedMap = "aula15";
                    reloadMap();
                    moveHeroTo(552, 465-cx);
                } else if (y<=23 && x>=640 && x<=660) {
                    tileM.selectedMap = "aula17";
                    reloadMap();
                    moveHeroTo(552, 465-cx);
                } else if (y>=553 && x>=640 && x<=660) {
                    tileM.selectedMap = "aula19";
                    reloadMap();
                    moveHeroTo(552, 111+cy);
                } else if (y>=553 && x>=350 && x<=370) {
                    tileM.selectedMap = "aula21";
                    reloadMap();
                    moveHeroTo(552, 111+cy);
                } else if (y>=553 && x>=110 && x<=130) {
                    tileM.selectedMap = "aula23";
                    reloadMap();
                    moveHeroTo(552, 111+cy);
                } else if (x<=23 && y>=354 && y<=414) {
                    tileM.selectedMap = "scaleDispari2";
                    reloadMap();
                    moveHeroTo(555-cx, y);
                }
                break;
            case "2oPianoDispari":
                if (x<=23 && y>=171 && y<=213) {
                    tileM.selectedMap = "scaleDispari2";
                    reloadMap();
                    moveHeroTo(555-cx, y);
                } else if (y<=23 && x>=110 && x<=130) {
                    tileM.selectedMap = "aula25";
                    reloadMap();
                    moveHeroTo(552, 465-cx);
                } else if (y<=23 && x>=350 && x<=370) {
                    tileM.selectedMap = "aula27";
                    reloadMap();
                    moveHeroTo(552, 465-cx);
                } else if (y<=23 && x>=640 && x<=660) {
                    tileM.selectedMap = "aula29";
                    reloadMap();
                    moveHeroTo(552, 465-cx);
                } else if (y>=553 && x>=640 && x<=660) {
                    tileM.selectedMap = "aula31";
                    reloadMap();
                    moveHeroTo(552, 111+cy);
                } else if (y>=553 && x>=350 && x<=370) {
                    tileM.selectedMap = "aula33";
                    reloadMap();
                    moveHeroTo(216, 111+cy);
                } else if (y>=553 && x>=110 && x<=130) {
                    tileM.selectedMap = "aula35";
                    reloadMap();
                    moveHeroTo(552, 111+cy);
                }
                break;
            case "curvaBar1":
                if (y<=23 && x>=258 && x<=459) {
                    tileM.selectedMap = "cEntrataD";
                    reloadMap();
                    moveHeroTo(x, 555-cx);
                } else if (x<=23 && y>=306 && y<=510) {
                    tileM.selectedMap = "bar";
                    reloadMap();
                    moveHeroTo(747-cx, y);
                }
                break;
            case "bar":
                if (x>=740 && y>=306 && y<=510) {
                    tileM.selectedMap = "curvaBar1";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                } else if (x<=23 && y>=306 && y<=510) {
                    tileM.selectedMap = "curvaBar2";
                    reloadMap();
                    moveHeroTo(747-cx, y);
                }
                break;
            case "curvaBar2":
                if (x>=740 && y>=306 && y<=510) {
                    tileM.selectedMap = "bar";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                } else if (y<=23 && x>=258 && x<=459) {
                    tileM.selectedMap = "corridoioXInfo";
                    reloadMap();
                    moveHeroTo(x, 555-cy);
                }
                break;
            case "corridoioXInfo":
                if (y>=553 && x>=258 && x<=459) {
                    tileM.selectedMap = "curvaBar2";
                    reloadMap();
                    moveHeroTo(x, 21+cy);
                } else if (x>=510 && y>=150 && y<=270) {
                    tileM.selectedMap = "giardino3";
                    reloadMap();
                    moveHeroTo(21+cx, 265);
                } else if (y<=23 && x>=258 && x<=459) {
                    tileM.selectedMap = "corridoioInfo";
                    reloadMap();
                    moveHeroTo(x, 555-cy);
                }
                break;
            case "giardino3":
                if (x<=23 && y>=250 && y<=270) {
                    tileM.selectedMap = "corridoioXInfo";
                    reloadMap();
                    moveHeroTo(510-cx, 260);
                }
                break;
            case "corridoioInfo":
                if (x>=747 && y>=150 && y<=230) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                } else if (y>=553 && x>=258 && x<=459) {
                    tileM.selectedMap = "corridoioXInfo";
                    reloadMap();
                    moveHeroTo(x, 21+cy);
                } else if (y<=113 && x>=110 && x<=130) {
                    tileM.selectedMap = "aulaInfo1";
                    reloadMap();
                    moveHeroTo(312, 500-cy);
                } else if (y>=465 && x>=110 && x<=130) {
                    tileM.selectedMap = "aulaInfo2";
                    reloadMap();
                    moveHeroTo(312, 130+cy);
                }
                break;
            case "aulaInfo1":
                if (y>=513 && x>=300 && x<=330) {
                    tileM.selectedMap = "corridoioInfo";
                    reloadMap();
                    moveHeroTo(120, 113+cy);
                }
                break;
            case "aulaInfo2":
                if (y<=135 && x>=300 && x<=330) {
                    tileM.selectedMap = "corridoioInfo";
                    reloadMap();
                    moveHeroTo(120, 465-cy);
                }
                break;
            case "aula13":
                if (y>=465 && x>=546 && x<=558) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(120, 21+cy);
                }
                break;
            case "aula15":
                if (y>=465 && x>=546 && x<=558) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(360, 21+cy);
                }
                break;
            case "aula17":
                if (y>=465 && x>=546 && x<=558) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(650, 21+cy);
                }
                break;
            case "aula19":
                if (y<=111 && x>=546 && x<=558) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(650, 555-cy);
                }
                break;
            case "aula21":
                if (y<=111 && x>=546 && x<=558) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(360, 555-cy);
                }
                break;
            case "aula23":
                if (y<=111 && x>=546 && x<=558) {
                    tileM.selectedMap = "1oPianoDispari";
                    reloadMap();
                    moveHeroTo(120, 555-cy);
                }
                break;
            case "aula25":
                if (y>=465 && x>=546 && x<=558) {
                    tileM.selectedMap = "2oPianoDispari";
                    reloadMap();
                    moveHeroTo(120, 21+cy);
                }
                break;
            case "aula27":
                if (y>=465 && x>=546 && x<=558) {
                    tileM.selectedMap = "2oPianoDispari";
                    reloadMap();
                    moveHeroTo(360, 21+cy);
                }
                break;
            case "aula29":
                if (y>=465 && x>=546 && x<=558) {
                    tileM.selectedMap = "2oPianoDispari";
                    reloadMap();
                    moveHeroTo(650, 21+cy);
                }
                break;
            case "aula31":
                if (y<=111 && x>=546 && x<=558) {
                    tileM.selectedMap = "2oPianoDispari";
                    reloadMap();
                    moveHeroTo(650, 555-cy);
                }
                break;
            case "aula33":
                if (y<=111 && x>=207 && x<=228) {
                    tileM.selectedMap = "2oPianoDispari";
                    reloadMap();
                    moveHeroTo(360, 555-cy);
                }
                break;
            case "aula35":
                if (y<=111 && x>=546 && x<=558) {
                    tileM.selectedMap = "2oPianoDispari";
                    reloadMap();
                    moveHeroTo(120, 555-cy);
                }
                break;
            case "angoloXLaboratori":
                if (x<=23 && y>=210 && y<=366) {
                    tileM.selectedMap = "stanzaXAulePari";
                    reloadMap();
                    moveHeroTo(747-cx, y+144);
                } else if (x>=513 && y>=258 && y<=318) {
                    tileM.selectedMap = "giardinoCentrale1";
                    reloadMap();
                    moveHeroTo(21+cx, y+47);
                } else if (y>=553 && x>=251 && x<=470) {
                    tileM.selectedMap = "corridoioFromCadToPari";
                    reloadMap();
                    moveHeroTo(x, 116+cy);
                }
                break;
            case "giardinoCentrale1":
                if (x==21 && y>=303 && y<=366) {
                    tileM.selectedMap = "angoloXLaboratori";
                    reloadMap();
                    moveHeroTo(504-cx, y-47);
                } else if (x<=23 && y>=66 && y<=126) {
                    tileM.selectedMap = "giardinoCentrale2";
                    reloadMap();
                    moveHeroTo(747-cx, y+390);
                } else if (x>=747 && y>=353 && y<=368) {
                    tileM.selectedMap = "corridoioChimica1";
                    reloadMap();
                    moveHeroTo(21+cx, y);
                } else if (x>=747 && y>=110 && y<=130) {
                    tileM.selectedMap = "aulaSotto";
                    reloadMap();
                    moveHeroTo(21+cx, 260);
                }
                break;
            case "aulaSotto":
                if (x<=23 && y>=250 && y<=280) {
                    tileM.selectedMap = "giardinoCentrale1";
                    reloadMap();
                    moveHeroTo(747-cx, 120);
                }
                break;
            case "corridoioChimica1":
                if (x<=23 && y>=356 && y<=368) {
                    tileM.selectedMap = "giardinoCentrale1";
                    reloadMap();
                    moveHeroTo(747-cx, y);
                } else if (x<=309 && y>=161 && y<=176) {
                    tileM.selectedMap = "aulaChimica1";
                    reloadMap();
                    moveHeroTo(591-cx, 311);
                } else if (x>=609 && y>=114 && y<=126) {
                    tileM.selectedMap = "aulaChimica2";
                    reloadMap();
                    moveHeroTo(114+cx, 311);
                } else if (x>=609 && y>=258 && y<=270) {
                    tileM.selectedMap = "aulaChimica3";
                    reloadMap();
                    moveHeroTo(114+cx, 311);
                } else if (x>=513 && y>=448 && y<=463) {
                    tileM.selectedMap = "aulaChimica4";
                    reloadMap();
                    moveHeroTo(114+cx, 311);
                } else if (y<=23 && x>=445 && x<=465) {
                    tileM.selectedMap = "palestraChimica";
                    reloadMap();
                    moveHeroTo(360, 555-cy);
                } else if (y>=553 && x>=250 && x<=470) {
                    tileM.selectedMap = "corridoioChimica2";
                    reloadMap();
                    moveHeroTo(360, 21+cy);
                }
                break;
            case "palestraChimica":
                if (y>=553 && x>=350 && x<=370) {
                    tileM.selectedMap = "corridoioChimica1";
                    reloadMap();
                    moveHeroTo(450, 21+cy);
                }
            case "aulaChimica1":
                if (x>=609 && y>=305 && y<=320) {
                    tileM.selectedMap = "corridoioChimica1";
                    reloadMap();
                    moveHeroTo(315+cx, 167);
                }
                break;
            case "aulaChimica2":
                if (x<=111 && y>=305 && y<=320) {
                    tileM.selectedMap = "corridoioChimica1";
                    reloadMap();
                    moveHeroTo(609-cx, 120);
                }
                break;
            case "aulaChimica3":
                if (x<=111 && y>=305 && y<=320) {
                    tileM.selectedMap = "corridoioChimica1";
                    reloadMap();
                    moveHeroTo(609-cx, 265);
                }
                break;
            case "aulaChimica4":
                if (x<=111 && y>=305 && y<=320) {
                    tileM.selectedMap = "corridoioChimica1";
                    reloadMap();
                    moveHeroTo(510-cx, 458);
                }
                break;
            case "aulaChimica5":
                if (x>=609 && y>=150 && y<=180) {
                    tileM.selectedMap = "corridoioChimica2";
                    reloadMap();
                    moveHeroTo(207+cx, 310);
                }
                break;
            case "aulaChimica6":
                if (x<=111 && y>=305 && y<=320) {
                    tileM.selectedMap = "corridoioChimica2";
                    reloadMap();
                    moveHeroTo(513-cx, 310);
                }
                break;
            case "corridoioChimica2":
                if (y<=23 && x>=258 && x<=462) {
                    tileM.selectedMap = "corridoioChimica1";
                    reloadMap();
                    moveHeroTo(x, 500);
                } else if (x<=207 && y>=306 && y<=318) {
                    tileM.selectedMap = "aulaChimica5";
                    reloadMap();
                    moveHeroTo(591-cx, 167);
                } else if (x>=513 && y>=304 && y<=319) {
                    tileM.selectedMap = "aulaChimica6";
                    reloadMap();
                    moveHeroTo(114+cx, 311);
                } else if (y>=553 && x>=258 && x<=459) {
                    tileM.selectedMap = "corridoioCad";
                    reloadMap();
                    moveHeroTo(x+200, 215);
                }
                break;
            case "corridoioCad":
                if (y>=460 && x>=497 && x<=512) {
                    tileM.selectedMap = "aulaCad1";
                    reloadMap();
                    moveHeroTo(218, 119+cy);
                } else if (y>=460 && x>=209 && x<=224) {
                    tileM.selectedMap = "aulaCad2";
                    reloadMap();
                    moveHeroTo(650, 119+cy);
                } else if (y<=210 && x>=161 && x<=176) {
                    tileM.selectedMap = "scaleFromCadToPari";
                    reloadMap();
                    moveHeroTo(412, 365-cy);
                } else if (y<=207 && x>=449 && x<=653) {
                    tileM.selectedMap = "corridoioChimica2";
                    reloadMap();
                    moveHeroTo(x-191, 554-cy);
                }
                break;
            case "aulaCad1":
                if (y<=119 && x>=213 && x<=220) {
                    tileM.selectedMap = "corridoioCad";
                    reloadMap();
                    moveHeroTo(500, 467-cy);
                }
                break;
            case "aulaCad2":
                if (y<=119 && x>=645 && x<=658) {
                    tileM.selectedMap = "corridoioCad";
                    reloadMap();
                    moveHeroTo(212, 453-cy);
                }
                break;
            case "scaleFromCadToPari":
                if (y>=362 && x>=403 && x<=415) {
                    tileM.selectedMap = "corridoioCad";
                    reloadMap();
                    moveHeroTo(167, 206+cy);
                } else if (x<=205 && y>=257 && y<=320) {
                    tileM.selectedMap = "corridoioFromCadToPari";
                    reloadMap();
                    moveHeroTo(506-cx, y+96);
                }
                break;
            case "corridoioFromCadToPari":
                if (x>=512 && y>=356 && y<=413) {
                    tileM.selectedMap = "scaleFromCadToPari";
                    reloadMap();
                    moveHeroTo(205+cx, y-96);
                } else if (x>=512 && y>=209 && y<=221) {
                    tileM.selectedMap = "aulaCorridoioFromCadToPari";
                    reloadMap();
                    moveHeroTo(114+cx, 311);
                } else if (y<=116 && x>=331 && x<=407) {
                    tileM.selectedMap = "angoloXLaboratori";
                    reloadMap();
                    moveHeroTo(x, 554-cy);
                }
                break;
            case "aulaCorridoioFromCadToPari":
                if (x<=120 && y>=308 && y<=320) {
                    tileM.selectedMap = "corridoioFromCadToPari";
                    reloadMap();
                    moveHeroTo(512-cx, 215);
                }
                break;
            case "giardinoCentrale2":
                if (x>=745 && y>=455 && y<=510) {
                    tileM.selectedMap = "giardinoCentrale1";
                    reloadMap();
                    moveHeroTo(21+cx, y-390);
                } else if (y>=553 && x>=114 && x<=126) {
                    tileM.selectedMap = "stanzaXAulePari";
                    reloadMap();
                    moveHeroTo(168, 21+cy);
                } else if (x>=655 && y>=112 && y<=127) {
                    tileM.selectedMap = "entrataDoppie";
                    reloadMap();
                    moveHeroTo(170+cx, 409);
                }
                break;
            case "entrataDoppie":
                if (x<=158 && y>=400 && y<=415) {
                    tileM.selectedMap = "giardinoCentrale2";
                    reloadMap();
                    moveHeroTo(655-cx, 120);
                } else if (x>=560 && y>=160 && y<=175) {
                    tileM.selectedMap = "palestraDoppie1";
                    reloadMap();
                    moveHeroTo(21+cx, 304);
                } else if (y<=63 && x>=449 && x<=464) {
                    tileM.selectedMap = "palestraDoppie2";
                    reloadMap();
                    moveHeroTo(164, 555-cy);
                }
                break;
            case "palestraDoppie1":
                if (x<=23 && y>=304 && y<=319) {
                    tileM.selectedMap = "entrataDoppie";
                    reloadMap();
                    moveHeroTo(563-cx, 165);
                }
                break;
            case "palestraDoppie2":
                if (y>=553 && x>=161 && x<=176) {
                    tileM.selectedMap = "entrataDoppie";
                    reloadMap();
                    moveHeroTo(460, 63+cy);
                }
                break;
            case "entrataFine":
                if(x<=27 && y>=216 && y<=360) {
                    tileM.selectedMap = "cEntrataL";
                    reloadMap();
                    moveHeroTo(740-cx, y);
                } else if (y>=553 && x>=260 && x<=460) { 
                    tileM.selectedMap = "cEntrataD";
                    reloadMap();
                    moveHeroTo(x, 21+cx);
                } else if (y<=23 && x>=250 && x<=480) {
                    vittoriaScene();
                }
                break;
        }
        //-------------------------------------------------------------


        /*      STAMPA COORDINATE E MAPPA       */
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("-----");
        System.out.println(tileM.selectedMap);
        //-------------------------------------------------------------

    }


    /*      METODO CONTROLLO COLLISIONI     */
    public static boolean checkCollision(Rectangle shape1, Rectangle shape2) {
        if (shape1.getBoundsInParent().intersects(shape2.getBoundsInParent())) {
            return true;
        }
        return false;
    }
    //-------------------------------------------------------------


    /*      METODO RICARICA MAPPA       */
    public void reloadMap() {
        map.getChildren().clear();
        tileM.loadMap();
        redrawMap();
    }
    //-------------------------------------------------------------



    /*      SCENA VITTORIA       */
    public void vittoriaScene() {
        /*soundtrack.stop();
        soundtrack3.play();*/
        vittoria=true;
        map.getChildren().clear();
        map.getChildren().add(labelVittoria);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    Stage stage = (Stage) map.getScene().getWindow();
                    stage.close();
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
    //-------------------------------------------------------------



    /*      METODO PER RIDISEGNARE MAPPA       */
    public void redrawMap() {
        map.getChildren().remove(player);
        map.getChildren().remove(counterPagineTrovate);
        tileM.draw(tileM.selectedMap);
        counterPagineTrovate.setText(tileM.pm.nPagineTrovate + "/10");
        map.getChildren().add(player);
        map.getChildren().add(counterPagineTrovate);
        if(tileM.selectedMap=="bar") {
            redrawOnTop();
        }
        tileM.dm.draw(tileM.selectedMap);
    }


    public void redrawOnTop() {
        Canvas canvas = new Canvas(ItisGame.W, ItisGame.H);
        GraphicsContext block = canvas.getGraphicsContext2D();
        block.drawImage(tileM.tile[38].image, 12*48, 4*48, 48, 48);
        block.drawImage(tileM.tile[38].image, 11*48, 4*48, 48, 48);
        block.drawImage(tileM.tile[38].image, 10*48, 4*48, 48, 48);
        map.getChildren().add(canvas);
    }


    public static void main(String[] args) {
        launch(args);
    }
}