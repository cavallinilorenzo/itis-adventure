package itisgame;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    Group gp;
    Tile[] tile;
    int[][] mapTileNum;
    String selectedMap;
    int counterRett=0;
    ArrayList<Rectangle> a;
    PagesManager pm;
    DialogueManager dm;

    public TileManager(Group gp) {
        this.gp = gp;
        tile = new Tile[70];
        mapTileNum = new int[16][12];
        getTileImage();
        a = new ArrayList<>();
        selectedMap = "entrata";
        pm = new PagesManager(gp);
        dm = new DialogueManager(gp);
        this.loadMap();
        this.draw(selectedMap);
    }

    public void loadMap() {
        if(Objects.equals(selectedMap, "entrata") && pm.nPagineTrovate==10) {
            selectedMap="entrataFine";
        }
        BufferedReader br;
        try {
            InputStream is = getClass().getResourceAsStream("/maps/" + selectedMap + ".txt");
            br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col<16 && row<12) {
                String line = br.readLine();
                while(col<16) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row]=num;
                    col++;
                }
                if(col==16) {
                    col=0;
                    row++;
                }
            }
            System.out.println("Mappa caricata: " + selectedMap);
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getTileImage() {
        tile[0] = new Tile();
        tile[0].image = new Image("images/tiles/floor.png", 48, 48, false, false);
        tile[1] = new Tile();
        tile[1].image = new Image("images/tiles/wall.jpg", 48, 48, false, false);
        tile[2] = new Tile();
        tile[2].image = new Image("images/tiles/black.jpg", 48, 48, false, false);
        tile[3] = new Tile();
        tile[3].image = new Image("images/tiles/stairs_hor1.png", 48, 48, false, false);
        tile[4] = new Tile();
        tile[4].image = new Image("images/tiles/floor_with_block.png", 48, 48, false, false);
        tile[5] = new Tile();
        tile[5].image = new Image("images/tiles/door.png", 48, 48, false, false);
        tile[6] = new Tile();
        tile[6].image = new Image("images/tiles/floor2.png", 48, 48, false, false);
        tile[7] = new Tile();
        tile[7].image = new Image("images/tiles/table.png", 48, 48, false, false);
        tile[8] = new Tile();
        tile[8].image = new Image("images/tiles/door_hor.png", 48, 48, false, false);
        tile[9] = new Tile();
        tile[9].image = new Image("images/tiles/grass.png", 48, 48, false, false);
        tile[10] = new Tile();
        tile[10].image = new Image("images/tiles/glassDoor_hor.jpg", 48, 48, false, false);
        tile[11] = new Tile();
        tile[11].image = new Image("images/tiles/glassDoor.jpg", 48, 48, false, false);
        tile[12] = new Tile();
        tile[12].image = new Image("images/tiles/stairs1.png", 48, 48, false, false);
        tile[13] = new Tile();
        tile[13].image = new Image("images/tiles/floor_with_block_vert.png", 48, 48, false, false);
        tile[14] = new Tile();
        tile[14].image = new Image("images/tiles/stairs_hor2.png", 48, 48, false, false);
        tile[15] = new Tile();
        tile[15].image = new Image("images/tiles/stairs2.png", 48, 48, false, false);
        tile[16] = new Tile();
        tile[16].image = new Image("images/tiles/floor_with_locker1.png", 48, 48, false, false);
        tile[17] = new Tile();
        tile[17].image = new Image("images/tiles/floor_with_locker_hor1.png", 48, 48, false, false);
        tile[18] = new Tile();
        tile[18].image = new Image("images/tiles/floor_with_locker2.png", 48, 48, false, false);
        tile[19] = new Tile();
        tile[19].image = new Image("images/tiles/floor_with_locker_hor2.png", 48, 48, false, false);
        tile[20] = new Tile();
        tile[20].image = new Image("images/tiles/floor_with_totem1.png", 48, 48, false, false);
        tile[21] = new Tile();
        tile[21].image = new Image("images/tiles/floor_with_totem2.png", 48, 48, false, false);
        tile[22] = new Tile();
        tile[22].image = new Image("images/tiles/floor_with_totem_hor1.png", 48, 48, false, false);
        tile[23] = new Tile();
        tile[23].image = new Image("images/tiles/floor_with_totem_hor2.png", 48, 48, false, false);
        tile[24] = new Tile();
        tile[24].image = new Image("images/tiles/floor_with_locker_mike.png", 48, 48, false, false);
        tile[25] = new Tile();
        tile[25].image = new Image("images/tiles/wall_with_lim1.png", 48, 48, false, false);
        tile[26] = new Tile();
        tile[26].image = new Image("images/tiles/wall_with_lim2.png", 48, 48, false, false);
        tile[27] = new Tile();
        tile[27].image = new Image("images/tiles/wall_with_lim_hor1.png", 48, 48, false, false);
        tile[28] = new Tile();
        tile[28].image = new Image("images/tiles/wall_with_lim_hor2.png", 48, 48, false, false);
        tile[29] = new Tile();
        tile[29].image = new Image("images/tiles/parquet.png", 48, 48, false, false);
        tile[30] = new Tile();
        tile[30].image = new Image("images/tiles/lato1.png", 48, 48, false, false);
        tile[31] = new Tile();
        tile[31].image = new Image("images/tiles/lato2.png", 48, 48, false, false);
        tile[32] = new Tile();
        tile[32].image = new Image("images/tiles/corner1.png", 48, 48, false, false);
        tile[33] = new Tile();
        tile[33].image = new Image("images/tiles/corner2.png", 48, 48, false, false);
        tile[34] = new Tile();
        tile[34].image = new Image("images/tiles/corner3.png", 48, 48, false, false);
        tile[35] = new Tile();
        tile[35].image = new Image("images/tiles/corner4.png", 48, 48, false, false);
        tile[36] = new Tile();
        tile[36].image = new Image("images/tiles/barTable.png", 48, 48, false, false);
        tile[37] = new Tile();
        tile[37].image = new Image("images/characters/daniele.png", 48, 48, false, false);
        tile[38] = new Tile();
        tile[38].image = new Image("images/tiles/fakeWall.jpg", 48, 48, false, false);
        tile[39] = new Tile();
        tile[39].image = new Image("images/characters/mezzadrelli.png", 48, 48, false, false);
        tile[40] = new Tile();
        tile[40].image = new Image("images/characters/puviani.png", 48, 48, false, false);
        tile[41] = new Tile();
        tile[41].image = new Image("images/characters/badolato.png", 48, 48, false, false);
        tile[42] = new Tile();
        tile[42].image = new Image("images/characters/marega.png", 48, 48, false, false);
        tile[43] = new Tile();
        tile[43].image = new Image("images/tiles/parquet_basket.png", 48, 48, false, false);
        tile[44] = new Tile();
        tile[44].image = new Image("images/tiles/parquet_calcio.png", 48, 48, false, false);
        tile[45] = new Tile();
        tile[45].image = new Image("images/tiles/parquet_tennis.png", 48, 48, false, false);
        tile[46] = new Tile();
        tile[46].image = new Image("images/tiles/parquet_pallavolo.png", 48, 48, false, false);
        tile[47] = new Tile();
        tile[47].image = new Image("images/tiles/wallFilm1.png", 48, 48, false, false);
        tile[48] = new Tile();
        tile[48].image = new Image("images/tiles/wallFilm2.png", 48, 48, false, false);
        tile[49] = new Tile();
        tile[49].image = new Image("images/tiles/wallFilm3.png", 48, 48, false, false);
        tile[50] = new Tile();
        tile[50].image = new Image("images/characters/mike.png", 48, 48, false, false);
        tile[51] = new Tile();
        tile[51].image = new Image("images/characters/bregola.png", 48, 48, false, false);
        tile[52] = new Tile();
        tile[52].image = new Image("images/characters/bidella.png", 48, 48, false, false);


    }

    public void draw(String mappa) {

        for (int i=a.size()-1; i>=0; i--) {
            gp.getChildren().remove(a.get(i));
            a.remove(i);
        }

        counterRett = 0;

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        Canvas canvas = new Canvas(ItisGame.W, ItisGame.H);
        GraphicsContext block = canvas.getGraphicsContext2D();
        while(col<16 && row<12) {
            int tileNum = mapTileNum[col][row];
            if (tileNum!=0 && tileNum!=3 && tileNum!=5 && tileNum!=8 && tileNum!=6 && tileNum!=9 && tileNum!=10 && tileNum!=11 && tileNum!=12 && tileNum!=14 && tileNum!=15 && tileNum!=29 && tileNum!=30 && tileNum!=31 && tileNum!=32 && tileNum!=33 && tileNum!=34 && tileNum!=35 && tileNum!=37 && tileNum!=38 && tileNum!=43 && tileNum!=44 && tileNum!=45 && tileNum!=46) {
                a.add(new Rectangle(x, y, 48, 48));
                a.get(counterRett).setFill(Color.YELLOW);
                gp.getChildren().add(a.get(counterRett));
                counterRett++;
            }
            block.drawImage(tile[tileNum].image, x, y, 48, 48);
            col++;
            x+=48;

            if (col==16) {
                col = 0;
                x = 0;
                row++;
                y+=48;
            }
        }

        gp.getChildren().add(canvas);

        pm.draw(mappa);


    }

}
