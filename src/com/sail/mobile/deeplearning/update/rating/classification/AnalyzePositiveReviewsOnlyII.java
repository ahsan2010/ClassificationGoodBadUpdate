package com.sail.mobile.deeplearning.update.rating.classification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.csvreader.CsvReader;
import com.sail.awsomebasupdates.model.AppInfo;
import com.sail.awsomebasupdates.model.DailyReviewsStatus;
import com.sail.awsomebasupdates.model.InvestigationPositivity;
import com.sail.awsomebasupdates.model.UpdatePositiveSignalInfo;
import com.sail.mobile.analyzer.googleplay.commmon.DateUtil;
import com.sail.mobile.analyzer.googleplay.commmon.TextUtil;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AppTable;

public class AnalyzePositiveReviewsOnlyII
{

	public static final String WORKING_DIRECTORY = Constants.ROOT + "/Update_Rating_Code_Safwat/";
	public static final String ALL_UPDATES_FILE = WORKING_DIRECTORY+"update_2016_app_daily_rating.csv";
	public static final String APP_DAILY_RATING_FILE = WORKING_DIRECTORY+"Apps_Daily_Rating.csv";
	public static final String UPDATE_POSITIVITY_FILE = WORKING_DIRECTORY+"Updates_Positivity.csv";
	public static final String UPDATE_DISCONTINUITY_FILE = WORKING_DIRECTORY+"Apps_Discontinuity.csv";
	public static final String UPDATE_INVESTIGATION_FILE = WORKING_DIRECTORY+"Updates_Investigate_Positivity.csv";

	public static String APP_CSV_PATH = WORKING_DIRECTORY + "app_top_2016.csv";
	public static final String APK_FTP_ERROR = "APK_FTP_ERROR";
	static DecimalFormat formatter = new DecimalFormat("#,###");

	/** Key is APP_ID,Date and value is the rating infor at this date.	*/
	public static HashMap<String, DailyReviewsStatus> appDailyRating = new HashMap<String, DailyReviewsStatus>();

	static HashMap<Long, AppInfo> appDailyPositivity = new HashMap<Long, AppInfo>();
	
	static ArrayList<UpdatePositiveSignalInfo> updatesPositivity = new ArrayList<UpdatePositiveSignalInfo>();
	static ArrayList<InvestigationPositivity> updatesInvestigationData = new ArrayList<InvestigationPositivity>();
	
	static HashMap<Long, Integer> totalDiscontinouty = new HashMap<Long, Integer>();
	static HashMap<Long, Integer> maxDiscontinouty = new HashMap<Long, Integer>();
	public static  Map<String,AppTable> appsInformation;
	
	
	
public static Map<String,AppTable> readAppData(){
		
		Map<String,AppTable> appTableRecords = new HashMap<String,AppTable>();
		
		try{
			
			CsvReader record = new CsvReader(APP_CSV_PATH);
			record.readHeaders();
			
			while (record.readRecord()){
				AppTable data = new AppTable();
				
				data.setAPP_ID(Integer.parseInt(record.get("APP_ID")));
				data.setPACKAGE_NAME(record.get("PACKAGE_NAME"));
				data.setAPP_TITLE(record.get("APP_TITLE"));
				data.setIS_APP_TITLE_HAS_NON_PRINTABLE_CHAR(record.get("IS_APP_TITLE_HAS_NON_PRINTABLE_CHAR"));
				data.setAPP_DESCRIPTION(record.get("APP_DESCRIPTION"));
				data.setIS_APP_DESCRIPTION_HAS_NON_PRINTABLE_CHAR(record.get("IS_APP_DESCRIPTION_HAS_NON_PRINTABLE_CHAR"));
				data.setAPP_CATEGORY(record.get("APP_CATEGORY"));
				data.setAPP_SUB_CATEGORY(record.get("APP_SUB_CATEGORY"));
				data.setORGANIZATION_ID(record.get("ORGANIZATION_ID"));
				data.setCONTENT_RATING(record.get("CONTENT_RATING"));
				data.setCONTENT_RATING_TEXT(record.get("CONTENT_RATING_TEXT"));
				data.setIS_TOP_DEVELOPER(record.get("IS_TOP_DEVELOPER"));
				data.setIS_WEARABLE_APP(record.get("IS_WEARABLE_APP"));
				data.setAPP_TYPE(record.get("APP_TYPE"));
				data.setAVAILABILITY_RESTRICTION(record.get("setAVAILABILITY_RESTRICTION"));
				data.setMAIN_PAGE_URL(record.get("MAIN_PAGE_URL"));
				data.setDETAILS_URL(record.get("DETAILS_URL"));
				data.setREVIEWS_URL(record.get("REVIEWS_URL"));
				data.setPURCHASE_DETAILS_URL(record.get("PURCHASE_DETAILS_URL"));
				data.setDEVELOPER_EMAIL(record.get("DEVELOPER_EMAIL"));
				data.setDEVELOPER_WEBSITE(record.get("DEVELOPER_WEBSITE"));
				data.setIS_2013_TOP_10K_APPS(record.get("IS_2013_TOP_10K_APPS"));
				data.setIS_2015_TOP_500_APPS(record.get("IS_2015_TOP_500_APPS"));
				data.setIS_FDROID_APP(record.get("IS_FDROID_APP"));
				data.setIS_2016_TOP_2500_APPS(record.get("IS_2016_TOP_2500_APPS"));
				data.setIS_2016_FAMILY_APPS(record.get("IS_2016_FAMILY_APPS"));
				data.setIS_2016_TOP_2500_NON_FREE_APPS(record.get("IS_2016_TOP_2500_NON_FREE_APPS"));
				data.setIS_2016_FAMILY_NON_FREE_APPS(record.get("IS_2016_FAMILY_NON_FREE_APPS"));
				
				appTableRecords.put(data.getPACKAGE_NAME(),data);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return appTableRecords;
		
	}
	/**
	 * 	Step 1: Load app daily rating for both start and end dates.
		The app start date is the fist date after the crawler start date.
		The app end date is the last date before the cralwer end date. 
	 * @throws Exception
	 */
	public static void readAppRatingInfo() throws Exception
	{
		System.out.println("Step1: Load app daily rating for both start and end dates.");
		String currentRecord;
		BufferedReader appsFileReader = new BufferedReader(new FileReader(APP_DAILY_RATING_FILE));
		appsFileReader.readLine(); // Skip header
		long oldAppID = 0;
		Set<String> analyzedApps = new HashSet<String>();
		
		while ((currentRecord = appsFileReader.readLine()) != null)
		{
			analyzedApps.add(currentRecord.split(",")[1]);
			long currentAppID = TextUtil.getLongColumn(currentRecord, 0);
			AppInfo appInfo;
			if(currentAppID != oldAppID)
			{
				// First time to see the app
				appInfo = new AppInfo();
				appDailyPositivity.put(currentAppID, appInfo);
			}
			else
			{
				appInfo = appDailyPositivity.get(currentAppID);
			}
			Date currentDate = DateUtil.readShortDate(currentRecord, 8);
			
			// Find the first crawling date for the app
			if(appInfo.getStartRating()!=null)
			{
				if(appInfo.getStartRating().getDate().after(currentDate))
				{
					DailyReviewsStatus dailyStatus = getDailyStatus(currentRecord);
					appInfo.setStartRating(dailyStatus);
				}
			}
			else
			{
				DailyReviewsStatus dailyStatus = getDailyStatus(currentRecord);
				appInfo.setStartRating(dailyStatus);
			}
			
			if(appInfo.getEndRating()!=null)
			{
				if(appInfo.getEndRating().getDate().before(currentDate))
				{
					DailyReviewsStatus dailyStatus = getDailyStatus(currentRecord);
					appInfo.setEndRating(dailyStatus);
				}
			}
			else
			{
				DailyReviewsStatus dailyStatus = getDailyStatus(currentRecord);
				appInfo.setEndRating(dailyStatus);
			}
			oldAppID = currentAppID;
			
		}
		appsFileReader.close();
		
		System.out.println("App Rating Info analyze ["+analyzedApps.size()+"]");
		System.out.println("Loaded apps rating information for [" + appDailyPositivity.size() + "] apps.");
		System.out.println("=============================================================");

	}

	public static String generateAppDailyKey(long appID, Date currentDate)
	{
		String monthString ="";
		String dateString = "";
		if(currentDate.getMonth()+1<10){
			monthString+="0";
		}
		if(currentDate.getDate() < 10){
			dateString+="0";
		}
		monthString +=currentDate.getMonth()+1;
		dateString  += currentDate.getDate();
		String appDailyKey = String.valueOf(appID) + Constants.COMMA_SEPARATOR + ((currentDate.getYear()+1900)+"-"+(monthString)+"-"+dateString);
		//String appDailyKey = String.valueOf(appID) + Constants.COMMA_SEPARATOR + currentDate;
		return appDailyKey;
	}
	
	public static void readAppDailyRating() throws Exception
	{
		System.out.println("Step2: Loading apps daily rating during the overall study period (1.5 year).");
		String currentRecord, oldRecord;
		BufferedReader appsFileReader = new BufferedReader(new FileReader(APP_DAILY_RATING_FILE));
		appsFileReader.readLine(); // Skip header
		Set<String> analyzedApps = new HashSet<String>();
		// Load data for old day
		oldRecord = appsFileReader.readLine();
		long oldAppID = TextUtil.getLongColumn(oldRecord, 0);
		DailyReviewsStatus oldDailyReviewsInfo = getDailyStatus(oldRecord);
		String appDailyKey = generateAppDailyKey(oldAppID, oldDailyReviewsInfo.getDate());
		appDailyRating.put(appDailyKey, oldDailyReviewsInfo);
		
		while ((currentRecord = appsFileReader.readLine()) != null)
		{
			long currentAppID = TextUtil.getLongColumn(currentRecord, 0);
			oldAppID = TextUtil.getLongColumn(oldRecord, 0);
			oldDailyReviewsInfo = getDailyStatus(oldRecord);
			
			if(oldAppID==currentAppID)
			{
				DailyReviewsStatus currentDailyReviewsInfo = getDailyStatus(currentRecord);
				int crawlingDaysDiff = DateUtil.calculateDatesDifference(oldDailyReviewsInfo.getDate(), currentDailyReviewsInfo.getDate());
				// If the crawler has a contious data so the crawlingDaysDiff will be one day otherwise we need to de a linear interpolation to get the app daily rating for the missing dates.
				if(crawlingDaysDiff==1)
				{
					appDailyKey = generateAppDailyKey(currentAppID, currentDailyReviewsInfo.getDate());
					appDailyRating.put(appDailyKey, currentDailyReviewsInfo);
				}
				else
				{
					int currentDaysDiff = crawlingDaysDiff-1;
					if(!maxDiscontinouty.containsKey(currentAppID) || (maxDiscontinouty.get(currentAppID)<currentDaysDiff))
					{
						maxDiscontinouty.put(currentAppID, currentDaysDiff);
					}
					
					if(!totalDiscontinouty.containsKey(currentAppID))
					{
						totalDiscontinouty.put(currentAppID, currentDaysDiff);
					}
					else
					{
						totalDiscontinouty.put(currentAppID, totalDiscontinouty.get(currentAppID) + currentDaysDiff);
					}
					
					for(int interval = 1; interval<=crawlingDaysDiff; interval++)
					{
						// Claculate App rating change rate
						DailyReviewsStatus currentDailyReviewsStatus = predictDailyReviewsStatus(oldDailyReviewsInfo, currentDailyReviewsInfo, interval);
						appDailyKey = generateAppDailyKey(currentAppID, currentDailyReviewsStatus.getDate());
						appDailyRating.put(appDailyKey, currentDailyReviewsStatus);
					}
					
				}
			}
			analyzedApps.add(currentRecord.split(",")[1]);
			oldRecord = currentRecord;
		}
		appsFileReader.close();
		System.out.println("App Daily Rating analyze ["+analyzedApps.size()+"]");
		System.out.println("Loaded apps daily rating information for [" + appDailyRating.size() + "] apps and days.");
		System.out.println("=============================================================");

	}
	
	/**
	 * Run simple linear interpolation to predict the rating in any time interval from start rating date to end dating date
	 * Including both start and end rating dates.
	 * @param startRatingStatus
	 * @param endRatingStatus
	 * @param interval
	 * @return
	 */
	public static DailyReviewsStatus predictDailyReviewsStatus(DailyReviewsStatus startRatingStatus, DailyReviewsStatus endRatingStatus, int interval)
	{
		/**
		 * Testing Data:
		 * Case start rating 	77,28,56,93,468,2016-04-22
		 * 		end rating		78,28,56,94,472,2016-04-27
		 * Dating diff			1,0,0,1,4,5
		 * 
		 * Results: Predicted Rating
		 *  
		 * date=Sat Apr 23 00:00:00 EDT 2016 ,One-star=77.0 ,two-stars=28.0 ,three-stars=56.0 ,four-stars=93.0 ,five-stars=469.0		==> Interval 1 Day
		 * date=Sun Apr 24 00:00:00 EDT 2016 ,One-star=77.0 ,two-stars=28.0 ,three-stars=56.0 ,four-stars=93.0 ,five-stars=470.0		==> Interval 2 Days
		 * date=Mon Apr 25 00:00:00 EDT 2016 ,One-star=78.0 ,two-stars=28.0 ,three-stars=56.0 ,four-stars=94.0 ,five-stars=470.0		==> Interval 3 Days
		 * date=Tue Apr 26 00:00:00 EDT 2016 ,One-star=78.0 ,two-stars=28.0 ,three-stars=56.0 ,four-stars=94.0 ,five-stars=471.0		==> Interval 4 Days
		 * date=Wed Apr 27 00:00:00 EDT 2016 ,One-star=78.0 ,two-stars=28.0 ,three-stars=56.0 ,four-stars=94.0 ,five-stars=472.0		==> Interval 5 Days
		 * 
		 */
		// Calculate Delta rate
		double delta1StarRate = endRatingStatus.getOneStar() - startRatingStatus.getOneStar();
		double delta2StarRate = endRatingStatus.getTwoStar() - startRatingStatus.getTwoStar();
		double delta3StarRate = endRatingStatus.getThreeStar() - startRatingStatus.getThreeStar();
		double delta4StarRate = endRatingStatus.getFourStar() - startRatingStatus.getFourStar();
		double delta5StarRate = endRatingStatus.getFiveStar() - startRatingStatus.getFiveStar();
		
		// Calculate Delta time
		double deltaTime = DateUtil.calculateDatesDifference(startRatingStatus.getDate(), endRatingStatus.getDate());
		
		// Based on Dy and Dt we caluclate the daily change rate and the expected rate aver certain interval
		double expected1Star = startRatingStatus.getOneStar() + ((delta1StarRate/deltaTime) * (interval));
		double expected2Star = startRatingStatus.getTwoStar() + ((delta2StarRate/deltaTime) * (interval));
		double expected3Star = startRatingStatus.getThreeStar() + ((delta3StarRate/deltaTime) * (interval));
		double expected4Star = startRatingStatus.getFourStar() + ((delta4StarRate/deltaTime) * (interval));
		double expected5Star = startRatingStatus.getFiveStar() + ((delta5StarRate/deltaTime) * (interval));
		
		// Costruct the new dailt review status
		DailyReviewsStatus dailyReviewsStatus = new DailyReviewsStatus();
		Date predictedDate = DateUtil.shiftDate(startRatingStatus.getDate(), interval);
		dailyReviewsStatus.setDate(predictedDate);
		dailyReviewsStatus.setOneStar(Math.round(expected1Star));
		dailyReviewsStatus.setTwoStar(Math.round(expected2Star));
		dailyReviewsStatus.setThreeStar(Math.round(expected3Star));
		dailyReviewsStatus.setFourStar(Math.round(expected4Star));
		dailyReviewsStatus.setFiveStar(Math.round(expected5Star));
		return dailyReviewsStatus;
	}
	
	/**
	 * claculate app daily positivity
	 */
	public static void calculateAppDailyPositivity(AppInfo appInfo, DailyReviewsStatus currentDailyReview)
	{
		double oneStar = currentDailyReview.getOneStar() - appInfo.getStartRating().getOneStar();
		double twoStar = currentDailyReview.getTwoStar() - appInfo.getStartRating().getTwoStar();
		double threeStar = currentDailyReview.getThreeStar() - appInfo.getStartRating().getThreeStar();
		double fourStar = currentDailyReview.getFourStar() - appInfo.getStartRating().getFourStar();
		double fiveStar = currentDailyReview.getFiveStar() - appInfo.getStartRating().getFiveStar();
		double total = oneStar + twoStar + threeStar + fourStar + fiveStar;
		if (fiveStar < 0 || fourStar < 0)
		{
			appInfo.setCorruptedData(true);
		}
		double totalPositiveSignal = (UpdatePositiveSignalInfo.FIVE_STAR_WEIGHT * fiveStar) + (UpdatePositiveSignalInfo.FOUR_STARS_WEIGHT * fourStar);
		double appLifetime = DateUtil.calculateDatesDifference(appInfo.getStartRating().getDate(), currentDailyReview.getDate());
		if(appLifetime<=0)
		{
			appInfo.setCorruptedData(true);
		}
		if(totalPositiveSignal==0)
		{
			appInfo.setCorruptedData(true); // This case makes either infenity or 0/0
		}
		double dailyPositiveSignal = totalPositiveSignal / appLifetime;
		appInfo.setDailyPositiveSignal(dailyPositiveSignal);
		appInfo.setPositivePercentage(totalPositiveSignal / total);
		appInfo.setLifeTime((int) appLifetime);
		appInfo.setTotalPositiveSignal((long)totalPositiveSignal);
		appInfo.setTotalReviews((long)total);
	}
	
	
	public static void loadUpdatesData() throws Exception
	{
		HashSet<Long> consideredApps = new HashSet<Long>();
		System.out.println("Step3: Loading updates daily positivity.");
		String currentRecord, oldRecord, nextRecord;
		ArrayList<String> allUpdates = new ArrayList<String>();
		BufferedReader revisionsFileName = new BufferedReader(new FileReader(ALL_UPDATES_FILE));
		revisionsFileName.readLine(); // Skip header

		// Step 1: Read all updates
		while ((currentRecord = revisionsFileName.readLine()) != null)
		{
				allUpdates.add(currentRecord);
		}
		revisionsFileName.close();
		
		
		// Step 2: Calculate app rating for each update.
		oldRecord = allUpdates.get(0);
		int numberOfCorruptedUPdates = 0, noPreceedingUpdates = 1, updatesForCorruptedApps = 0, 
				updatesWithPositiveLifetime = 0, updatesWithZeroLifetime = 0, updateOutOfBoundaries =0;
		
		for (int index =1; index < allUpdates.size(); index++)
		{
			currentRecord = allUpdates.get(index);
			if(index<(allUpdates.size()-1))
			{
				nextRecord = allUpdates.get(index+1);
			}
			else
			{
				nextRecord = null;
			}
			
			long oldAppID = Long.valueOf(oldRecord.split(",")[0]);
			long currentAppID = Long.valueOf(currentRecord.split(",")[0]);
			if (oldAppID == currentAppID)
			{
				// Two release for the same app.
				UpdatePositiveSignalInfo updateRatingInfo = new UpdatePositiveSignalInfo();
				InvestigationPositivity investigationData = new InvestigationPositivity();
				
				/* Determine the update start date and end date.
				 * There are different conventions but lets consider the update rating is the rating form the release date-1 minus the rating at the next realease date -1
				 * I.e., the rating before deploying the next update - the rating before deploying the current update 
				 */
				Date currentReleaseDate = DateUtil.readFullDate(currentRecord, 5); 
				Date dateBeforeReleasingCurrentUpdate = DateUtil.shiftDate(currentReleaseDate, -1);
				
				Date updateEndDate;
				if(nextRecord!= null && Long.valueOf(nextRecord.split(",")[0])==currentAppID)
				{
					// Lifetime = next release time = current release time 
					Date nextUpdateReleaseDate = DateUtil.readFullDate(nextRecord, 5); 
					updateEndDate = DateUtil.shiftDate(nextUpdateReleaseDate, -1);
				}
				else
				{
					// this is the latest release, so release lifetime = latest app lifetime - release date
					updateEndDate = appDailyPositivity.get(currentAppID).getEndRating().getDate();
					updateRatingInfo.setIsAlife(1);
				}
				
				updateRatingInfo.setStartDate(currentReleaseDate); 		// Starts from this date at 0:0  			==> in where condition say where date(review) >= start date 
				updateRatingInfo.setEndDate(updateEndDate);					// Ends at this date at 23:59:59		==> in where condition say where date(review) <= end date
				
				double updateLifetime = DateUtil.calculateDatesDifference(dateBeforeReleasingCurrentUpdate, updateEndDate);
				DailyReviewsStatus ratingBeforeDeployingUpdate = appDailyRating.get(generateAppDailyKey(currentAppID, dateBeforeReleasingCurrentUpdate));
				DailyReviewsStatus ratingBeforeDeployingNextUpdate = appDailyRating.get(generateAppDailyKey(currentAppID, updateEndDate));
				
				// Check positive updtae lifetimes.
				if(updateLifetime<0)
				{
					updatesWithPositiveLifetime++;
					oldRecord = currentRecord;
					continue;
				}
				else if(updateLifetime==0)
				{
					updatesWithZeroLifetime++;
					oldRecord = currentRecord;
					continue;
				}
				
				// A) Get app daily rating
				
				AppInfo originalAppInfo = appDailyPositivity.get(currentAppID);
				// App daily rating is teh app rating in the period form crawling the app till this update is released.
				// So the rating is the rating immediatly before deploying the update (old record) and the date is the update release date. 
				
				if(ratingBeforeDeployingUpdate == null || ratingBeforeDeployingNextUpdate == null)
				{
					updateOutOfBoundaries++;
					oldRecord = currentRecord;
					continue;
				}
				AppInfo appInfo  = originalAppInfo.clone();
				calculateAppDailyPositivity(appInfo, ratingBeforeDeployingUpdate);
				if(appInfo.isCorruptedData())
				{
					updatesForCorruptedApps++;
					oldRecord = currentRecord;
					continue;
				}
				
				// B) Get rating before update and after update and devide on update lifetime
				
				double oneStar = ratingBeforeDeployingNextUpdate.getOneStar() - ratingBeforeDeployingUpdate.getOneStar();
				double twoStar = ratingBeforeDeployingNextUpdate.getTwoStar() - ratingBeforeDeployingUpdate.getTwoStar();
				double threeStar = ratingBeforeDeployingNextUpdate.getThreeStar() - ratingBeforeDeployingUpdate.getThreeStar();
				double fourStar = ratingBeforeDeployingNextUpdate.getFourStar() - ratingBeforeDeployingUpdate.getFourStar();
				double fiveStar = ratingBeforeDeployingNextUpdate.getFiveStar() - ratingBeforeDeployingUpdate.getFiveStar();
				double total = oneStar + twoStar + threeStar + fourStar + fiveStar;
				
				double rating = (oneStar + (2 * twoStar) + (3 * threeStar) + (4 * fourStar) + (5 * fiveStar)) / total;
				double appRatingBeforeDeployment = Double.valueOf(oldRecord.split(",")[9]);
				if (oneStar < 0 || twoStar < 0 || threeStar < 0 || fourStar < 0 || fiveStar < 0)
				{
					numberOfCorruptedUPdates++;
					oldRecord = currentRecord;
					continue;
				}
			
				updateRatingInfo.setOneStar(oneStar);
				updateRatingInfo.setTwoStar(twoStar);
				updateRatingInfo.setThreeStar(threeStar);
				updateRatingInfo.setFourStar(fourStar);
				updateRatingInfo.setFiveStar(fiveStar);
				updateRatingInfo.setAggregatedRating(rating);
				updateRatingInfo.setAppRatingBeforeDeployment(appRatingBeforeDeployment);
				updateRatingInfo.setAppID(currentAppID);
				updateRatingInfo.setUpdateID(Long.valueOf(currentRecord.split(",")[1]));

				// Add positive reviews data
				double totalPositiveSignal = (UpdatePositiveSignalInfo.FIVE_STAR_WEIGHT * fiveStar) + (UpdatePositiveSignalInfo.FOUR_STARS_WEIGHT * fourStar);
				updateRatingInfo.setTotalPositiveSignal((int)totalPositiveSignal);
				updateRatingInfo.setLifeTime((int)updateLifetime);
				
				double dailyPositiveSignal = totalPositiveSignal/updateLifetime;
				updateRatingInfo.setDailyPositiveSignal(dailyPositiveSignal);
				
				// Finally link app updates with its app
				updateRatingInfo.setAppInfo(appInfo);
				double updatePositivePercentage = totalPositiveSignal/total;
				updateRatingInfo.setUpdatePositivity(updatePositivePercentage /  appInfo.getPositivePercentage());
					
				// Read the previously deployed APK.
				String previousDeployedApk = TextUtil.getColumn(oldRecord, 7);
				if(APK_FTP_ERROR.equals(previousDeployedApk))
				{
					// The file is downlaoded but not FTP so lets generate the APK file automatically.
					previousDeployedApk = TextUtil.getColumn(oldRecord, 2) + "-" + TextUtil.getColumn(oldRecord, 3) + "-" +   
					DateUtil.convertDate2String(DateUtil.readFullDate(oldRecord, 5), Constants.SHORT_DATE_FORMAT).replaceAll(Constants.DASH_SEPARATOR, Constants.UNDERSCORE_SEPARATOR)  + ".apk";
				}
				updateRatingInfo.setPreviousDeployedApk(previousDeployedApk);
				
				// Read the currently deployed APK
				String currentlyDeployedApk  = TextUtil.getColumn(currentRecord, 7);
				if(APK_FTP_ERROR.equals(currentlyDeployedApk))
				{
					// The file is downlaoded but not FTP so lets generate the APK file automatically.
					// The file is downlaoded but not FTP so lets generate the APK file automatically.
					currentlyDeployedApk = TextUtil.getColumn(currentRecord, 2) + "-" + TextUtil.getColumn(currentRecord, 3) + "-" +   
					DateUtil.convertDate2String(DateUtil.readFullDate(currentRecord, 5), Constants.SHORT_DATE_FORMAT).replaceAll(Constants.DASH_SEPARATOR, Constants.UNDERSCORE_SEPARATOR)  + ".apk";
				}
				updateRatingInfo.setDeployedApk(currentlyDeployedApk);
				
				// Set all needed data for investigation.
				
				investigationData.setAppID(updateRatingInfo.getAppID());
				investigationData.setUpdateID(updateRatingInfo.getUpdateID());
				
				investigationData.setCurrentUpdatePositiveReviews(totalPositiveSignal);
				investigationData.setCurrentUpdateTotalReviews(total);
				investigationData.setCurrentUpdatePositiveReviewsPercentage(totalPositiveSignal / total);
				investigationData.setUpdateLifeTime(updateLifetime);
				investigationData.setCurrentUpdateDailyPositiveReviews(totalPositiveSignal/updateLifetime);
				
				
				investigationData.setPreviousPositiveReviews(appInfo.getTotalPositiveSignal());
				investigationData.setPreviousTotalReviews(appInfo.getTotalReviews());
				investigationData.setPreviousPositiveReviewsPercentage((appInfo.getTotalPositiveSignal() * 1.0) / appInfo.getTotalReviews());
				investigationData.setAppLifeTime(appInfo.getLifeTime());
				investigationData.setPreviousDailyPositiveReviews((appInfo.getTotalPositiveSignal() * 1.0) / (appInfo.getLifeTime() * 1.0 ));
				
				investigationData.measurePositivityRatio();
				
				investigationData.measurePositivityPercentageRatio();
				
				
				
				consideredApps.add(currentAppID);
				updatesPositivity.add(updateRatingInfo);
				updatesInvestigationData.add(investigationData);
			}
			else
			{
				noPreceedingUpdates++;
			}
			oldRecord = currentRecord;
		}
		System.out.println("System analyzed [" + formatter.format(allUpdates.size()) + "] updates and results as follows:");
		System.out.println("1) Updates with No preceedig update = [" + formatter.format(noPreceedingUpdates) + "] updates \n"
				+ "2) Corrupted updates =  [" + formatter.format(numberOfCorruptedUPdates) + "] updates " + " \n"
				+ "3) Updates for corrupted apps =  [" + formatter.format(updatesForCorruptedApps) + "] updates (still considered as corrupted update) " + " \n"
				+ "4) Updates with out of boundry dates reviews = [" + formatter.format(updateOutOfBoundaries) + "] \n"
				+ "5) Updates with Positive lifetime = [" + formatter.format(updatesWithPositiveLifetime) + "] \n"
				+ "6) Updates with zero lifetime = [" + formatter.format(updatesWithZeroLifetime) + "] \n"
				+ "7) Considered updates = [" + formatter.format(updatesPositivity.size()) + "] updates for [" + formatter.format(consideredApps.size()) + "] apps. \n"
				+ "For sake of accuracy, the total valid + error updates [" + 
				formatter.format(noPreceedingUpdates + numberOfCorruptedUPdates + updatesForCorruptedApps + updateOutOfBoundaries +  
						updatesWithPositiveLifetime + updatesWithZeroLifetime + 
						updatesPositivity.size()) + "] and total examined updates [" + formatter.format(allUpdates.size()) + "].");

		System.out.println("=============================================================");

	}
	
	public static void investigateSingleApp(){
		String investigatedAppName = "9828";
		for(String appKey : appDailyRating.keySet()){
			String appId = appKey.split(",")[0].trim();
			String date = appKey.split(",")[1].trim();
			
			

			if(investigatedAppName.equals(appId)){
				DailyReviewsStatus status = appDailyRating.get(appKey);
				System.out.println(appId+","+status.getOneStar()+"," + status.getTwoStar()+"," + status.getThreeStar()+"," + status.getFourStar()+","+status.getFiveStar()+","+date);
			}
		}
	}
	
	public void generateDailyRating(){
		try{
			appsInformation = readAppData();
			readAppRatingInfo();
			readAppDailyRating();
			//investigateSingleApp();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		
		//Load App Data
		appsInformation = readAppData();
		/* Step 1: Load app daily rating for both start and end dates.
		 * The app start date is the fist date after the crawler start date.
		 * The app end date is the last date before the cralwer end date. 
		 */
		readAppRatingInfo();
		
		// Step 2: Load apps daily ratings.
		readAppDailyRating();
		
		//investigate single app
		investigateSingleApp();
		// Step 3 : load updates data and calculate app ngativity just before deploying the new update
		//loadUpdatesData();
		
		// Step 4 : Store rating info for each update
		//storeUpdatesPositivity();
		
		// Store investigation
		//storeUpdatesInvestigation();
		
		// Step 5: Store discontinuity info.
		//storeDiscontinuityInfo();
		
	}

	private static void storeUpdatesPositivity() throws Exception
	{
		System.out.println("Step4: Stores updates positivity.");
		PrintWriter writer = new PrintWriter(UPDATE_POSITIVITY_FILE, "UTF-8");

		writer.println(UpdatePositiveSignalInfo.HEADER); 
		for(UpdatePositiveSignalInfo updatePositiveSignalInfo : updatesPositivity)
		{
			writer.println(updatePositiveSignalInfo.printInfo()); 
		}
		writer.close();
		System.out.println("Stored results successfully in the location [" + UPDATE_POSITIVITY_FILE + "].");
		System.out.println("=============================================================");
	}

	
	private static void storeUpdatesInvestigation() throws Exception
	{
		System.out.println("Step5: Stores updates positivity Investigation.");
		PrintWriter writerInvestigate = new PrintWriter(UPDATE_INVESTIGATION_FILE, "UTF-8");

		writerInvestigate.println(InvestigationPositivity.HEADER); 
		
		for(InvestigationPositivity investigation : updatesInvestigationData)
		{
			writerInvestigate.println(investigation.toString());
		}
		writerInvestigate.close();
		
		System.out.println("Stored Investigation results successfully in the location [" + UPDATE_INVESTIGATION_FILE + "].");
		System.out.println("=============================================================");
	}
	private static void storeDiscontinuityInfo() throws Exception
	{
		System.out.println("Step5: Stores apps Discontinuity Info.");
		PrintWriter writer = new PrintWriter(UPDATE_DISCONTINUITY_FILE, "UTF-8");

		writer.println("APP_ID,Max_Discontinuity,Total_Discontinuity");
		
		for(Long appID : maxDiscontinouty.keySet())
		{
			writer.println(appID + Constants.COMMA_SEPARATOR + maxDiscontinouty.get(appID) + Constants.COMMA_SEPARATOR + totalDiscontinouty.get(appID));
		}
		
		writer.close();
		System.out.println("Stored Discontinuity in the location [" + UPDATE_DISCONTINUITY_FILE + "].");
		System.out.println("=============================================================");
		
	}
	
	private static DailyReviewsStatus getDailyStatus(String currentRecord) throws Exception
	{
		DailyReviewsStatus dailyReviewsStatus = new DailyReviewsStatus();
		dailyReviewsStatus.setOneStar(TextUtil.getDoubleColumn(currentRecord, 3));
		dailyReviewsStatus.setTwoStar(TextUtil.getDoubleColumn(currentRecord, 4));
		dailyReviewsStatus.setThreeStar(TextUtil.getDoubleColumn(currentRecord, 5));
		dailyReviewsStatus.setFourStar(TextUtil.getDoubleColumn(currentRecord, 6));
		dailyReviewsStatus.setFiveStar(TextUtil.getDoubleColumn(currentRecord, 7));
		dailyReviewsStatus.setDate(DateUtil.readShortDate(currentRecord, 8));

		return dailyReviewsStatus;
	}

	
	
}
