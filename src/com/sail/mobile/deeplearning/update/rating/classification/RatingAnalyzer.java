package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.sail.awsomebasupdates.model.AppAnalyticsModel;
import com.sail.awsomebasupdates.model.DailyReviewsStatus;
import com.sail.mobile.analysis.adevolution.util.DateUtil;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;
import com.sail.mobile.deeplearning.update.rating.classification.util.FileUtil;

/**
 * Filtering some data to make sure we have sufficient number of data to train and test sequence of versions
 * @author ahsan
 *
 */

public class RatingAnalyzer {
	AnalyzePositiveReviewsOnlyII positiveReviewObject;
	HashMap<String, DailyReviewsStatus> appDailyRating = new HashMap<String, DailyReviewsStatus>();
	Map<String, ArrayList<AppAnalyticsModel>> appUpdatesAnalyticList;
	
	public Map<String,UpdateRatingInformation>  generateUpdateRating(){
		
		Map<String,UpdateRatingInformation> updateRatingInfo = new HashMap<String,UpdateRatingInformation>();
		
		int numberOfCorruptedUPdates = 0;
		int missingApp = 0;
		
		
		for(String packageName : appUpdatesAnalyticList.keySet()){
			if(!positiveReviewObject.appsInformation.containsKey(packageName)){
				missingApp ++;
				continue;
			}
			
			String appId =  Integer.toString(positiveReviewObject.appsInformation.get(packageName).getAPP_ID());
			ArrayList<AppAnalyticsModel> updates = appUpdatesAnalyticList.get(packageName);
			
			for(int i = 0 ; i < updates.size(); i ++){
				
				AppAnalyticsModel update = updates.get(i);
				DateTime dateBeforeReleasingPresentUpdate = null;
				DateTime dateBeforeReleasingNextUpdate = null;
				
				// Last update
				if( i == updates.size() - 1){
					dateBeforeReleasingPresentUpdate = update.getJodaReleaseDate();
					DateTime presentUpdateDate = update.getJodaReleaseDate().minusDays(1);				
					dateBeforeReleasingNextUpdate = Constants.EPERIMENT_END_TIME;
				}else{
					AppAnalyticsModel nextUpdate = updates.get(i+1);
					dateBeforeReleasingPresentUpdate = update.getJodaReleaseDate();	
					dateBeforeReleasingNextUpdate = nextUpdate.getJodaReleaseDate().minusDays(1);	
				}
				
				//System.out.println(dateBeforeReleasingPresentUpdate+" Update: " + updates.get(i).getRELEASE_DATE()+" " +  updates.get(i+1).getRELEASE_DATE() +" " + updates.get(i).getVERSION_CODE() +" "+updates.get(i+1).getVERSION_CODE()+" "+ updates.size());
				
				String dateBeforeReleasingPresentUpdateString = dateBeforeReleasingPresentUpdate.toString().substring(0,dateBeforeReleasingPresentUpdate.toString().indexOf("T")).trim();
				String dateBeforeReleasingNextUpdateString = dateBeforeReleasingNextUpdate.toString().substring(0,dateBeforeReleasingNextUpdate.toString().indexOf("T")).trim();
				
				
				String keyPresent = appId+","+dateBeforeReleasingPresentUpdateString;
				String keyNext = appId + "," + dateBeforeReleasingNextUpdateString;
				
				
				
				
				int updateLifeTimeInDays = Days.daysBetween(dateBeforeReleasingPresentUpdate, dateBeforeReleasingNextUpdate).getDays();
				
				//System.out.println(keyNext +" " + keyPresent);
				
				DailyReviewsStatus ratingBeforeDeployingUpdate = appDailyRating.get(keyPresent);
				DailyReviewsStatus ratingBeforeDeployingNextUpdate = appDailyRating.get(keyNext);
				//String key = id + "," + dateString;
				
				
				if(ratingBeforeDeployingNextUpdate == null){
					numberOfCorruptedUPdates++;
					//System.out.println("Problem deploying next update");
					continue;
				}
				if(ratingBeforeDeployingUpdate == null){
					//System.out.println(packageName +" " + update.getVersionCode() +" " + update.getReleaseDate());
					numberOfCorruptedUPdates++;
					continue;
				}
				
				if(updateLifeTimeInDays < 0){
					continue;
				}
				
				//System.out.println(dateBeforeReleasingPresentUpdateString+" "+ dateBeforeReleasingNextUpdateString);
				UpdateRatingInformation updateInformation = null;
				
				double oneStar = Math.abs(ratingBeforeDeployingNextUpdate.getOneStar() - ratingBeforeDeployingUpdate.getOneStar());
				double twoStar = Math.abs(ratingBeforeDeployingNextUpdate.getTwoStar() - ratingBeforeDeployingUpdate.getTwoStar());
				double threeStar = Math.abs(ratingBeforeDeployingNextUpdate.getThreeStar() - ratingBeforeDeployingUpdate.getThreeStar());
				double fourStar = Math.abs(ratingBeforeDeployingNextUpdate.getFourStar() - ratingBeforeDeployingUpdate.getFourStar());
				double fiveStar = Math.abs(ratingBeforeDeployingNextUpdate.getFiveStar() - ratingBeforeDeployingUpdate.getFiveStar());
				double total = oneStar + twoStar + threeStar + fourStar + fiveStar;
				
				double rating = (oneStar + (2 * twoStar) + (3 * threeStar) + (4 * fourStar) + (5 * fiveStar)) / total;
				
				if (oneStar < 0 || twoStar < 0 || threeStar < 0 || fourStar < 0 || fiveStar < 0)
				{
					System.out.println(oneStar +" " + twoStar +" " + threeStar +" " + fourStar +" " + fiveStar);
					numberOfCorruptedUPdates++;
					continue;
				}
			
				updateInformation = new UpdateRatingInformation();
				updateInformation.setOneStar(oneStar);
				updateInformation.setTwoStar(twoStar);
				updateInformation.setThreeStar(threeStar);
				updateInformation.setFourStar(fourStar);
				updateInformation.setFiveStar(fiveStar);
				updateInformation.setAggregatedRating(rating);
				updateInformation.setStartDate(dateBeforeReleasingPresentUpdate);
				updateInformation.setEndDate(dateBeforeReleasingNextUpdate);
				updateInformation.setUpdateLifeTimeInDays(updateLifeTimeInDays);
				updateInformation.setAppId(appId);
				updateInformation.setPackageName(update.getPackageName());
				updateInformation.setVersionCode(update.getVersionCode());
				updateInformation.setTotalStar(total);
				updateRatingInfo.put(packageName + "-" +update.getVersionCode(), updateInformation);
				
				//System.out.println("Apprating: " + packageName + " Version: " + update.getVersionCode() + " One = "+oneStar +" Two = " + twoStar + " Three = " + threeStar + " Four = " + fourStar + " Five = " + fiveStar + " Aggregated = " + rating);
				//System.out.println("Apprating: " + packageName + " Version: " + update.getVersionCode() + " One = "+update.getUpdateOneStar() +" Two = " + update.getUpdateTwoStar() + " Three = " + update.getUpdateThreeStar() + " Four = " + update.getUpdateFourStar() + " Five = " + update.getUpdateFiveStar() + " Aggregated = " + update.getUpdateAggreatedRating());
				
				
			}
		}
		System.out.println("Total updates [" + updateRatingInfo.size() + "]");
		System.out.println("Missing Application ["+missingApp+"]");
		System.out.println("Number of Corrupted Updates: ["+numberOfCorruptedUPdates+']');		
		return updateRatingInfo;
	}
	
	
	public RatingAnalyzer(Map<String, ArrayList<AppAnalyticsModel>> appUpdatesAnalyticList){
		this.appUpdatesAnalyticList = appUpdatesAnalyticList;
		positiveReviewObject = new AnalyzePositiveReviewsOnlyII();
		positiveReviewObject.generateDailyRating();
		appDailyRating = positiveReviewObject.appDailyRating;
	}

	public RatingAnalyzer(){
		positiveReviewObject = new AnalyzePositiveReviewsOnlyII();
		positiveReviewObject.generateDailyRating();
		appDailyRating = positiveReviewObject.appDailyRating;
	}
	
	public static void main(String arg[]){
		RatingAnalyzer ob = new RatingAnalyzer(FileUtil.readAppAnalyticsInfo(Constants.APP_ANALYTICS_FILE_PATH));
		Map<String,UpdateRatingInformation> updateRatingInfo = ob.generateUpdateRating();
		System.out.println("Total update Rating ["+updateRatingInfo.size()+"]");
	}

}
