package com.sail.mobile.deeplearning.update.rating.classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.sail.awsomebasupdates.model.DailyReviewsStatus;
import com.sail.mobile.analysis.adevolution.util.DateUtil;
import com.sail.mobile.deeplearning.update.rating.classification.Loader.AdsInputDataLoader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateRatingInformation;
import com.sail.mobile.deeplearning.update.rating.classification.model.UpdateTable;

/**
 * Filtering some data to make sure we have sufficient number of data to train and test sequence of versions
 * @author ahsan
 *
 */

public class RatingAnalyzer {
	AnalyzePositiveReviewsOnlyII positiveReviewObject;
	HashMap<String, DailyReviewsStatus> appDailyRating = new HashMap<String, DailyReviewsStatus>();
	HashMap<String,ArrayList<UpdateTable>> appUpdates;
	
	
	public Map<String,UpdateRatingInformation>  generateUpdateRating(){
		
		
		Map<String,UpdateRatingInformation> updateRatingInfo = new HashMap<String,UpdateRatingInformation>();
		
		int numberOfCorruptedUPdates = 0;
		int missingApp = 0;
		
		
		for(String appName : appUpdates.keySet()){
			
		
			if(!positiveReviewObject.appsInformation.containsKey(appName)){
				missingApp ++;
				continue;
			}
			String appId =  Integer.toString(positiveReviewObject.appsInformation.get(appName).getAPP_ID());
			ArrayList<UpdateTable> updates = appUpdates.get(appName);
			
			for(int i = 0 ; i < updates.size(); i ++){
				

				
				UpdateTable update = updates.get(i);
				DateTime dateBeforeReleasingPresentUpdate = null;
				DateTime dateBeforeReleasingNextUpdate = null;
				
				// Last update
				if( i == updates.size() - 1){
					dateBeforeReleasingPresentUpdate = DateUtil.formatterWithHyphen.parseDateTime(update.getRELEASE_DATE()).minusDays(1);
					DateTime presentUpdateDate = DateUtil.formatterWithHyphen.parseDateTime(update.getRELEASE_DATE()).minusDays(1);				
					dateBeforeReleasingNextUpdate = Constants.EPERIMENT_END_TIME;
				}else{
					UpdateTable nextUpdate = updates.get(i+1);
					 dateBeforeReleasingPresentUpdate = DateUtil.formatterWithHyphen.parseDateTime(update.getRELEASE_DATE()).minusDays(1);	
					 dateBeforeReleasingNextUpdate = DateUtil.formatterWithHyphen.parseDateTime(nextUpdate.getRELEASE_DATE()).minusDays(1);	
				}
				
				//System.out.println("Update: " + updates.get(i).getRELEASE_DATE()+" " +  updates.get(i+1).getRELEASE_DATE() +" " + updates.get(i).getVERSION_CODE() +" "+updates.get(i+1).getVERSION_CODE()+" "+ updates.size());
				
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
					numberOfCorruptedUPdates++;
					//System.out.println("Problem deploying before update");
					continue;
				}
				
				if(updateLifeTimeInDays < 0){
					//System.err.println(dateBeforeReleasingPresentUpdate.toString()+" " + dateBeforeReleasingNextUpdate.toString());
					continue;
				}
				
				//System.out.println(dateBeforeReleasingPresentUpdateString+" "+ dateBeforeReleasingNextUpdateString);
				
				UpdateRatingInformation updateInformation = null;
				
				double oneStar = ratingBeforeDeployingNextUpdate.getOneStar() - ratingBeforeDeployingUpdate.getOneStar();
				double twoStar = ratingBeforeDeployingNextUpdate.getTwoStar() - ratingBeforeDeployingUpdate.getTwoStar();
				double threeStar = ratingBeforeDeployingNextUpdate.getThreeStar() - ratingBeforeDeployingUpdate.getThreeStar();
				double fourStar = ratingBeforeDeployingNextUpdate.getFourStar() - ratingBeforeDeployingUpdate.getFourStar();
				double fiveStar = ratingBeforeDeployingNextUpdate.getFiveStar() - ratingBeforeDeployingUpdate.getFiveStar();
				double total = oneStar + twoStar + threeStar + fourStar + fiveStar;
				
				double rating = (oneStar + (2 * twoStar) + (3 * threeStar) + (4 * fourStar) + (5 * fiveStar)) / total;
				
				if (oneStar < 0 || twoStar < 0 || threeStar < 0 || fourStar < 0 || fiveStar < 0 || total == 0)
				{
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
				updateInformation.setPackageName(update.getPACKAGE_NAME());
				updateInformation.setVersionCode(update.getVERSION_CODE());
				updateInformation.setTotalStar(total);
				updateRatingInfo.put(appName+Constants.COMMA+update.getVERSION_CODE(), updateInformation);
				//System.out.println("Success");
			}
		}
		System.out.println("Missing Application ["+missingApp+"]");
		System.out.println("Number of Corrupted Updates: ["+numberOfCorruptedUPdates+']');		
		return updateRatingInfo;
	}
	
	
	public RatingAnalyzer(){
		positiveReviewObject = new AnalyzePositiveReviewsOnlyII();
		positiveReviewObject.generateDailyRating();
		appDailyRating = positiveReviewObject.appDailyRating;
		appUpdates = AdsInputDataLoader.readUpdateData(Constants.APP_UPDATE_TABLE_PATH_RATING);
		
	}
	
	public static void main(String arg[]){
		RatingAnalyzer ob = new RatingAnalyzer();
		Map<String,UpdateRatingInformation> updateRatingInfo = ob.generateUpdateRating();
		System.out.println("Total update Rating ["+updateRatingInfo.size()+"]");
		
	}

}
