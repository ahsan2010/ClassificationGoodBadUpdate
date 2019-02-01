package com.sail.mobile.deeplearning.update.rating.classification.feature.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

public class CollectClassMethodMaster {

	HashMap<String, ArrayList<UpdateTable>> appUpdates = AdsInputDataLoader
			.readUpdateData(Constants.APP_UPDATE_TABLE_PATH);
	int numberOfThreads = 20;

	public void startThreadProcess() throws Exception {
		ArrayList<String> selectedApps = new ArrayList<String>();
		selectedApps.addAll(appUpdates.keySet());
		//selectedApps.add("air.nn.mobile.app.main");
		int start = 0;
		int difference = selectedApps.size() / this.numberOfThreads;
		int END_INDEX = selectedApps.size();

		System.out.println("Total Threads [" + this.numberOfThreads + "]");
		System.out.println("Total Number of Jars [" + selectedApps.size() + "]");
		System.out.println("Difference [" + difference + "]");

		ExecutorService executor = Executors.newFixedThreadPool(this.numberOfThreads);
		
		long startTime = System.currentTimeMillis();
		for(int i = 1 ; i <= this.numberOfThreads ; i ++ ){
			
			if( i == this.numberOfThreads){
				Runnable worker = new CollectClassMethodFeature(selectedApps,appUpdates,start, END_INDEX,i);
				executor.execute(worker);	
			}else{
				Runnable worker = new CollectClassMethodFeature(selectedApps,appUpdates, start, start+difference,i);
				System.out.println("*Thread Start Position ["+ start +"]" +" " + " End Position ["+(start+difference)+"]");
				start+= difference;
				executor.execute(worker);
				
			}
		}
		executor.shutdown();
        while (!executor.isTerminated()) {
        }
        long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Time ["+totalTime/(1000*60)+"] minutes");		
        System.out.println("Finished all threads");
	}

	public static void main(String[] args) throws Exception{
		CollectClassMethodMaster ob = new CollectClassMethodMaster();
		ob.numberOfThreads = Integer.parseInt(args[0]);
		ob.startThreadProcess();
		System.out.println("Program finishes successfully");
	}
}
