import java.util.ArrayList;
public class Main {

    public static void main(String[] args){
        String inputLocation = "C:\\Users\\adeol\\OneDrive\\spyCandles.csv";
        int numberOfDays=10; //used to regularize number of days
        int idealN =0;

        double percData=0.20; // used to regularize the amount of data used for model "memory"
        double idealPerc =0;

        int neighbours=10; // used to regularize the amount of neighbours considered for predicts
        int idealNeighbours =0;
        // With these 3 regularization points, we select for the best performer on the test set and apply that to live trading predictions.
        // Training Epochs
        int epoch = 100;

        //boundaries for the regularization parameters
        int maxDays = 25;
        int minDays =4;

        double minPercData = 0.05;
        double maxPercData = 0.70;

        int minNeigbours = 1;
        int maxNeighbours = 30;

        double maxProfitability = 0;
        

        for(int i=0; i<epoch; i++){
                double random = Math.random();
                double random1 = Math.random();
                double random2 = Math.random();

                
                input runModel = new input(inputLocation,numberOfDays);
                ArrayList<X> modelInput = runModel.returnModelInput();

            // System.out.println("Size of model Input in main class: "+ modelInput.size());
                model testModel = new model(modelInput,percData,neighbours);

                double modelOutput = testModel.modelProfit();

                if (modelOutput>maxProfitability){
                    maxProfitability = modelOutput;
                    idealN=numberOfDays;
                    idealPerc= percData;
                    idealNeighbours = neighbours;
                }

                numberOfDays = (int) (random*maxDays);
                numberOfDays = Math.max(minDays, numberOfDays);

                percData = random1*maxPercData;
                percData = Math.max(minPercData, percData);

                neighbours = (int) (random2* maxNeighbours);
                neighbours = Math.max(minNeigbours, neighbours);

               


        }
        

        System.out.println("At the end of the Optimization process; MAX PROFIT: "+maxProfitability+"\nData Points used to achieve this; Number of days: "+idealN+
        "\nPercentage of Data: "+idealPerc+"\nNumber of neighbours: "+idealNeighbours);
    }
    
}
