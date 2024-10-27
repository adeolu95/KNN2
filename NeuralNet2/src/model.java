import java.util.ArrayList;

public class model {

    private int k; //number of nearest neighbours used in the prediction; MAJOR REGULARIZATION POINT. 
    private double percent; // % of data used for memory storage; MAJOR REGULARIZATION POINT 
    private double instanceProfitability;

    //Has to have an upper limit because some data has to be used for test.

    ArrayList<X> historicalData;// store previous information for distance comparison.


    model(ArrayList<X> input, double perc, int neighboursConsidered){

        if(input.size()>0){
            //Testing the amount of days, n involved
            historicalData = new ArrayList<X>();
            percent = perc;
            k= neighboursConsidered;

            int storage  = (int) (percent*input.size());
            ArrayList<X> memoryData = new ArrayList<>(input.subList(0,storage));

            
                   
            historicalData=copyArray(memoryData);

            
           
            int testStart = storage+10;
            int size = input.size();
            double counter = 0;
            double profitable =0;

            for (int j=testStart; j<size&&j<testStart+1000; j++ ){
                counter++;
                X curr = input.get(j);
                double actualPriceChange = curr.getPriceChange();

                ArrayList<double[]> modelInput = curr.normalizedData();
                double modelPrediction = makePrediction(modelInput);

                //System.out.println("\nActual Price Change: "+ actualPriceChange);
              //  System.out.println("Model Predicted Price Change: "+ modelPrediction);
                if(isProfitable(modelPrediction, actualPriceChange))profitable++;
            }

            double profitability = (profitable/counter)*100; 
            instanceProfitability= profitability;
            
            //System.out.println("\nModel profitable: "+profitability);


       
        

        }

        else{

            System.out.println("Check Model class; Data Input is empty");
        }



    }

    public double modelProfit(){
        return instanceProfitability;
    }
    

    private boolean isProfitable(double prediction, double actualPriceChange){
        boolean result = false;

        if (prediction<0 && actualPriceChange<0 && actualPriceChange<prediction)result =true;
        else if (prediction>=0 && actualPriceChange>=0 && actualPriceChange>prediction)result = true;

        return result;
    }

    private double makePrediction(ArrayList<double[]> input){
        int counter = 0; 
        double [] posValues = new double [k]; // number of values used in the prediction

        ArrayList<X> dataCopy =copyArray(historicalData);

        //System.out.println("Size of dataCopy Array: "+historicalData.size());
        double distance = 100000000;
        X bestX = null;
        while (counter< posValues.length){

            int indexofBest = 0; // to trim an already selected answer from the search
                for (int i=0; i<dataCopy.size(); i++){
                    X curr = dataCopy.get(i);
                    ArrayList<double[]> normData = curr.normalizedData();

                    double currDist = distance(normData, input);
                    if (currDist<distance){
                        distance = currDist;
                        bestX = curr;
                        indexofBest =i;
                    }
                }

                dataCopy.remove(indexofBest);


                posValues[counter] = bestX.getPriceChange();
                counter++;


            }

            double sum=0;
            for(int j=0; j<posValues.length; j++){

                sum+=posValues[j];
            }
            double result = sum/posValues.length;
                


        return result;
    }

    private ArrayList<X> copyArray(ArrayList<X> input){
        ArrayList<X> result = new ArrayList<X>();
        for (int i=0; i< input.size(); i++){
            X curr = input.get(i);
            result.add(curr);
        }
        return result;
    }

    


    private double distance(ArrayList<double[]> data1, ArrayList<double[]> data2){
        double result = 0;
        double sum =0;
        if(data1.size()==data2.size()){
            for(int i=0; i< data1.size(); i++){
                double dist1 = Math.pow(data1.get(i)[0]- data2.get(i)[0],2);
                double dist2 = Math.pow(data1.get(i)[1]- data2.get(i)[1],2);

                sum+= dist1+dist2;
            }

            result =Math.sqrt(sum);

            

        }

        else {
            System.out.println("Check distance method in model class; input arrayLists are not the same size");
        }



        return result;
    }





    


    
}
