import java.util.ArrayList;
public class Main {

    public static void main(String[] args){
        String inputLocation = "C:\\Users\\adeol\\OneDrive\\spyCandles.csv";
        input runModel = new input(inputLocation);
        ArrayList<X> modelInput = runModel.returnModelInput();

       // System.out.println("Size of model Input in main class: "+ modelInput.size());
        model testModel = new model(modelInput, 0.15,10);

        
    }
    
}
