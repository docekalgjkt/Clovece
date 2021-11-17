package cz.gjkt.clovece2.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class CilovyDomecekView {

        private int[] souradnice = new int[2];
        private Paint barva;
        private int velikostPole;
        private int pocetFigurek;
        private int[] smer;

        public CilovyDomecekView(int x, int y, int velikostPole, int[] smer, int pocetFigurek, Paint barva){
            souradnice[0] = x;
            souradnice[1] = y;
            this.barva = barva;
            this.velikostPole = velikostPole;
            this.pocetFigurek = pocetFigurek;
            this.smer = smer;
        }

        public void kresli(GraphicsContext gc){
            int x = souradnice[0];
            int y = souradnice[1];

            gc.setStroke(barva);
            gc.setLineWidth(2);
            int pocet = 0;
            while(pocet<pocetFigurek){
                gc.strokeOval(x+2,y+2,velikostPole-4,velikostPole-4);
                x += velikostPole*smer[0];
                y += velikostPole*smer[1];
                pocet++;
            }
        }
}
