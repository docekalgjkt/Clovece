package cz.gjkt.clovece2.view;

import cz.gjkt.clovece2.model.StartovniDomecek;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class StartovniDomecekView {

    private int[] souradnice = new int[2];
    private Paint barva;
    private int velikostPole;
    private int pocetFigurek;
    private ArrayList<FigurkaView> figurky = new ArrayList<>();
    private StartovniDomecek startovniDomecek;

    public StartovniDomecekView(int x, int y, int velikostPole, int pocetFigurek, Paint barva, StartovniDomecek startovniDomecek){
        souradnice[0] = x;
        souradnice[1] = y;
        this.barva = barva;
        this.velikostPole = velikostPole;
        this.pocetFigurek = pocetFigurek;
        this.startovniDomecek = startovniDomecek;
    }

    public ArrayList<FigurkaView> getFigurky(){return figurky;}
    public FigurkaView getFigurka(){return figurky.get(figurky.size()-1);}
    public StartovniDomecek getStartovniDomecek(){return startovniDomecek;}

    public void kresli(GraphicsContext gc){
        int x = souradnice[0];
        int y = souradnice[1];

        gc.setStroke(barva);
        gc.setLineWidth(2);
        int pocet = 0;
        while(pocet<pocetFigurek){
            figurky.add(new FigurkaView(startovniDomecek.getFigurka(pocet),x+velikostPole/2,y+velikostPole/2,barva));
            gc.strokeOval(x+2,y+2,velikostPole-4,velikostPole-4);
            pocet++;
            if(pocet<pocetFigurek){
                gc.strokeOval(x+velikostPole+2,y+2,velikostPole-4,velikostPole-4);
                figurky.add(new FigurkaView(startovniDomecek.getFigurka(pocet),x+velikostPole+velikostPole/2,y+velikostPole/2,barva));
                pocet++;
            }
            y += velikostPole;
        }
    }

    public FigurkaView nasad(FigurkaView vybranaFigurka) {
        figurky.remove(vybranaFigurka);
        return vybranaFigurka;
    }
}
