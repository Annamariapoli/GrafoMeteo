package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	
	private Model model = new Model();
	
	public void setModel(Model model){
		this.model = model;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtTemperatura;

    @FXML
    private Button btnCalcola;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcola(ActionEvent event) {                                     //calc le temp pox entro 2 gg a torino
    	try {
    		    int temperatura = Integer.parseInt(txtTemperatura.getText());
    		    model.buildGraph();
    	    	if(!model.trovaTemperaturaMediaTraNodi(temperatura)){                 //controllo inutile
    	    		txtResult.appendText("La temperatura media non esiste \n ");
    	    		return ;
    	    	}
    	    	
    	    	// se è tra i nodi , vado a vedere i 2 livelli
    	    	
    	    	List<Integer> viciniPrimi = model.getPrimiVicini(temperatura);
    	    	txtResult.appendText("Tra un giorno le temperature possibili saranno : " +viciniPrimi.toString() +" \n");
    	    	List<Integer> viciniSecondi = model.getSecondiVicini(temperatura);
    	    	txtResult.appendText("Tra due giorni le temperature possibili saranno : " +viciniSecondi.toString()+" \n");
    	    	
    	} catch(Exception e) {
    			txtResult.appendText("Inserisci un numero valido! \n ");            //se non è un int
    			return;
    		}
    
    }

    @FXML
    void initialize() {
        assert txtTemperatura != null : "fx:id=\"txtTemperatura\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";
    }
}
