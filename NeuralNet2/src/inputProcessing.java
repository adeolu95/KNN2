import java.util.ArrayList;

public class inputProcessing {//smoothen and normalize data for kNN model 
    private int n; //number of days considered for each price change n+1
    private ArrayList<X> xInput;
    

    inputProcessing(ArrayList<double[]> input){

        xInput = new ArrayList<X>();
        this.n = 10; // will vary later to guage best performer; THIS IS A MAJOR REGULIZER FOR THE MODEL

        
        for(int i =0; i+(n+1)< input.size(); i++){
            double[] target = input.get(i+n);
            double targetPriceChange = target[3]-target[0];

            ArrayList<double[]> xValues = new ArrayList<> (input.subList(i,i+n));
            X modelInput = new X(n,targetPriceChange,xValues);

            xInput.add(modelInput);


            


            
        }

        for(int j=0; j<10; j++){
            X curr = xInput.get(j);
            curr.print();
        }

    }
}
