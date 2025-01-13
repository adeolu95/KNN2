import java.util.ArrayList;
public class Main {

    public static void main(String[] args){
        String inputLocation = "C:\\Users\\adeol\\OneDrive\\Documents\\abbv 1w.csv";
        int numberOfDays=6; //used to regularize number of days
        int idealN =0;

        double percData=0.60; // used to regularize the amount of data used for model "memory"
        double idealPerc =0;

        int neighbours=1 ; // used to regularize the amount of neighbours considered for predicts
        int idealNeighbours =0;
        // With these 3 regularization points, we select for the best performer on the test set and apply that to live trading predictions.
        // Training Epochs
        int epoch = 2000;

        //boundaries for the regularization parameters
        int maxDays = 25; 
        int minDays =3;

        double minPercData = 0.05;
        double maxPercData = 0.60;

        int minNeigbours = 1;
        int maxNeighbours = 5;
        ArrayList<double[]> predictionData = new ArrayList<double[]>();

       

        model predictionModel=null;

        double maxProfitability = 0;
        

        for(int i=0; i<epoch; i++){
                double random = Math.random();
                double random1 = Math.random();
                double random2 = Math.random();

                System.out.println("Counter: "+i);


                

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
                    

                   
                    predictionModel=testModel;
                }

                numberOfDays = (int) (random*maxDays);
                numberOfDays = Math.max(minDays, numberOfDays);

                //percData = random1*maxPercData;
                //percData = Math.max(minPercData, percData);

                neighbours = (int) (random2* maxNeighbours);
                neighbours = Math.max(minNeigbours, neighbours);

                if ((epoch-i)==1){
                    predictionData = runModel.getPredictionData(idealN);
                    X normX = new X(idealN,predictionData);
                    predictionData = normX.normalizedData();
                    for(int j =0 ;  j<predictionData.size(); j++ ){

                        double[] curr = predictionData.get(j);
                        System.out.println("Normalized data [Prediction]: "+ curr[0]+ ", "+ curr[1]);
                    }
                    System.out.println("triggered");

                }

               


        }
        

        System.out.println("At the end of the Optimization process; MAX PROFIT: "+maxProfitability+"\nData Points used to achieve this; Number of days: "+idealN+
        "\nPercentage of Data: "+idealPerc+"\nNumber of neighbours: "+idealNeighbours);
        predictionModel.testSize();
        predictionModel.print();
        
        

        double predic = predictionModel.makePrediction(predictionData);
        System.out.println("\nModel Prediction for next day price change: "+ predic);
       
        


        
    }
    
}
