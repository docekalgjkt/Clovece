package cz.gjkt.clovece2.model;

public class MocFigurekException extends Exception {

    @Override
    public String getMessage(){
        return "Startovní domeček je plný, nelze vložit další figurku.";
    }
}
