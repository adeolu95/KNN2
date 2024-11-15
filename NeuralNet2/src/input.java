import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;

public class input {

    private ArrayList<double[]> inputData;
    
    private ArrayList<X> modelFeed;
    private int days;
    private inputProcessing dataProcessing;

    input(String input, int n){
        inputData = new ArrayList<double[]>();
        days =n;
        
        readfile(input,inputData);

        dataProcessing = new inputProcessing(inputData,days);
        
        modelFeed = dataProcessing.modelInput();
       
       


    }
   

    

    public ArrayList<X> returnModelInput(){
        return modelFeed;
    }

    public ArrayList<double[]> getPredictionData(int number){
        ArrayList<double[]> result = new ArrayList<double[]>();
        int noDays = number;
        for (int i = (inputData.size())-noDays; i<inputData.size(); i++){
            double[] temp = inputData.get(i);
            System.out.println("Targeted data: "+ temp[0]+ ", "+ temp[1]+","+ temp[2]+ ", "+ temp[3]);
            result.add(temp);
        }
        


        return result;
    }


    
    private static void readfile(String loc, ArrayList<double[]> dataList){
        String line ="";
        try{
            BufferedReader br = new BufferedReader(new FileReader(loc));
            while((line=br.readLine())!= null){
                if(line.isEmpty()!=true){
                String[] data = line.split(",");

                //Data Points
                Double open = Double.parseDouble(data[0]);
                Double high = Double.parseDouble(data[1]);
                Double low = Double.parseDouble(data[2]);
                Double close = Double.parseDouble(data[3]);
                
                double[] input = new double[4];
                input[0]=open; input[1]=high; input[2]=low; input[3]=close;
                dataList.add(input);

              
            }
        }
        br.close();
    }

    catch(IOException e){
        e.printStackTrace();
    }
}


  


}
