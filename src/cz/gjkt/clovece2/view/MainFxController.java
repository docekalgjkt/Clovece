package cz.gjkt.clovece2.view;

import cz.gjkt.clovece2.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainFxController implements Initializable {

    private final int POCET_HRACU = 4;
    private final int POCET_FIGUREK = 4;
    private final int POCET_MEZIPOLI = 12;
    private final int[][] SMERY = {{0,0},{1,0},{1,0},{1,0},{1,0},{1,0},
                                   {0,-1},{0,-1},{0,-1},{0,-1},{0,-1},
                                   {1,0},{1,0},
                                   {0,1},{0,1},{0,1},{0,1},{0,1},
                                   {1,0},{1,0},{1,0},{1,0},{1,0},
                                   {0,1},{0,1},
                                   {-1,0},{-1,0},{-1,0},{-1,0},{-1,0},
                                   {0,1},{0,1},{0,1},{0,1},{0,1},
                                   {-1,0},{-1,0},
                                   {0,-1},{0,-1},{0,-1},{0,-1},{0,-1},
                                   {-1,0},{-1,0},{-1,0},{-1,0},{-1,0},
                                   {0,-1}};
    private final int VELIKOST_POLE = 30;
    private final Color[] BARVY_FIGUREK = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
    private final int[][] SOURADNICE_S_DOMECKU = {{0,0},{330,0},{330,330},{0,330}};
    private final int[][] SOURADNICE_C_DOMECKU = {{30,180},{180,30},{330,180},{180,330}};
    private final int[][] SMERY_C_DOMECKU = {{1,0},{0,1},{-1,0},{0,-1}};

    private static FigurkaView vybranaFigurka;

    @FXML
    Canvas platno;
    @FXML
    AnchorPane plocha;
    @FXML
    Label kostka;
    @FXML
    Label hracText;

    private HraciPlocha hraciPlocha = new HraciPlocha(POCET_HRACU,POCET_FIGUREK,POCET_MEZIPOLI);
    private ArrayList<int[]> souradnicePoli = new ArrayList<>();
    private Map<BarvaFigurky,StartovniDomecekView> startovniDomeckyView = new HashMap<>();
    private Map<BarvaFigurky,CilovyDomecekView> ciloveDomeckyView = new HashMap<>();
    private HraciKostka hraciKostka = new HraciKostka();
    private int hod;
    private BarvaFigurky aktualniHrac;

    public static void setVybranaFigurka(FigurkaView figurka){
        vybranaFigurka = figurka;
    }

    public static FigurkaView getVybranaFigurka(){return vybranaFigurka;}

    public void kresli(){
        GraphicsContext gc = platno.getGraphicsContext2D();
        gc.setLineWidth(2);
        int x = 0;
        int y = 150;
        int index = 0;

        for(int[] smer : SMERY){
            gc.setStroke(Color.BLACK);
            x += smer[0]*VELIKOST_POLE;
            y += smer[1]*VELIKOST_POLE;
            if (index % POCET_MEZIPOLI == 0) {
                gc.setStroke(BARVY_FIGUREK[(index)/POCET_MEZIPOLI]);
            }
            gc.strokeOval(x+2,y+2,VELIKOST_POLE-4,VELIKOST_POLE-4);
            int[] souradnice = new int[2];
            souradnice[0] = x;
            souradnice[1] = y;
            souradnicePoli.add(index++, souradnice);
        }
    }
    public void kresliPlochu() {
        kresli();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kresliPlochu();

        //vytvoření kreslených startovních domečků
        int i = 0;
        for(BarvaFigurky bf : hraciPlocha.getBarvyFigurek()){
            StartovniDomecekView dw = new StartovniDomecekView(SOURADNICE_S_DOMECKU[i][0],SOURADNICE_S_DOMECKU[i][1],VELIKOST_POLE,POCET_FIGUREK,BARVY_FIGUREK[i],hraciPlocha.getStartovniDomecek(bf));
            i++;
            startovniDomeckyView.put(bf,dw);
            dw.kresli(platno.getGraphicsContext2D());
            for (FigurkaView f : dw.getFigurky()){
                plocha.getChildren().add(f.getFigurkaView());
            }
        }


        //vytvoření kreslených cílových domečků
        i = 0;
        for(BarvaFigurky bf : hraciPlocha.getBarvyFigurek()){
            CilovyDomecekView dw = new CilovyDomecekView(SOURADNICE_C_DOMECKU[i][0],SOURADNICE_C_DOMECKU[i][1],VELIKOST_POLE,SMERY_C_DOMECKU[i],POCET_FIGUREK,BARVY_FIGUREK[i]);
            ciloveDomeckyView.put(bf,dw);
            dw.kresli(platno.getGraphicsContext2D());
            i++;
        }

        //nastavení aktivního hráče
        aktualniHrac = hraciPlocha.getBarvyFigurek().get(0);
    }

    public void hraj(){

        if ((vybranaFigurka != null)&&(hod > 0)) {
            if ((startovniDomeckyView.get(aktualniHrac).getFigurky().contains(vybranaFigurka)) && (hod == 6)){
                startovniDomeckyView.get(aktualniHrac).nasad(vybranaFigurka);
                hraciPlocha.nasadFigurku(vybranaFigurka.getFigurka());
                vybranaFigurka.setX(souradnicePoli.get(aktualniHrac.getStart())[0]+VELIKOST_POLE/2);
                vybranaFigurka.setY(souradnicePoli.get(aktualniHrac.getStart())[1]+VELIKOST_POLE/2);
                vybranaFigurka.setVybrana(false);
                vybranaFigurka = null;
            }
            else {
                hraciPlocha.posunFigurku(vybranaFigurka.getFigurka(),hod);
                vybranaFigurka.setX(souradnicePoli.get(hraciPlocha.getIndexFigurky(vybranaFigurka.getFigurka()))[0]+VELIKOST_POLE/2);
                vybranaFigurka.setY(souradnicePoli.get(hraciPlocha.getIndexFigurky(vybranaFigurka.getFigurka()))[1]+VELIKOST_POLE/2);
                vybranaFigurka.setVybrana(false);
                vybranaFigurka = null;
            }
        }
        aktualniHrac = hraciPlocha.getDalsiHrac(aktualniHrac);
    }

    public void hodKostkou(){
        hod = hraciKostka.hod();
        kostka.setText(hod+"");
    }

    private void tiskSouradnic(){
        for (int[] s : souradnicePoli){
            System.out.println(""+s[0]+","+s[1]);
        }
    }
}

