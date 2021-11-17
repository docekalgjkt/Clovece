package cz.gjkt.clovece2.view;

import cz.gjkt.clovece2.model.Figurka;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class FigurkaView {

    private int VELIKOST_FIGURKY = 10;

    private FigurkaView tatoFigurka;
    private Figurka figurka;
    private Circle figurkaView;
    private Paint barva;
    private int x;
    private int y;

    public FigurkaView(Figurka figurka, int x, int y, Paint barva){
        this.figurka = figurka;
        this.barva = barva;
        this.x = x;
        this.y = y;
        figurkaView = new Circle(x,y,VELIKOST_FIGURKY,barva);
        tatoFigurka = this;
        setHandler();
    }

    public Circle getFigurkaView(){return figurkaView;}
    public Figurka getFigurka(){return figurka;}
    public void setX(int x){this.x = x; figurkaView.setCenterX(x);}
    public void setY(int y){this.y = y; figurkaView.setCenterY(y);}

    public void setVybrana(boolean vybrana){
        if (vybrana) {
            figurkaView.setStroke(Color.BLACK);
        }
        else {
            figurkaView.setStroke(null);
        }
    }

    private void setHandler(){
        figurkaView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FigurkaView f = MainFxController.getVybranaFigurka();
                if (f!=null) MainFxController.getVybranaFigurka().setVybrana(false);
                setVybrana(true);
                MainFxController.setVybranaFigurka(tatoFigurka);
            }
        });
    }
}
