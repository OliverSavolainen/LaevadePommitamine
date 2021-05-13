package oop;
import javafx.scene.Node;
import javafx.scene.control.Label;


public class Silt {
    private Label silt;

    public void muudaSõnumit(String sõnum){
        silt.setText(sõnum);
    }

    public Silt (Label silt){
        this.silt = silt;
    }

    public Label getSilt(){
        return silt;
    }

}
