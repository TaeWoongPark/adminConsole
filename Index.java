package kr.co.drawing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Index {

	public static void main(String[] args) {
		Refer ref = new Refer();
		
		// csv file
		BufferedReader br = null;	
		
		try {
			br = Files.newBufferedReader(Paths.get("C:\\.korbitKRW.csv"));
			String line = null;
			line = br.readLine();
			ref.firstTimestamp(line);
			
			while((line = br.readLine()) != null) {
				ref.set(line);
			}			
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
	        try{
	            if(br != null){
	                br.close();
	                // 출력
	                ref.output();
	                
	            }
	        }catch(IOException e){
	            e.printStackTrace();
	        }
	    }
	}

}
