package com.sail.mobile.deeplearning.update.rating.classification.Loader;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.csvreader.CsvReader;
import com.sail.mobile.deeplearning.update.rating.classification.common.Constants;
import com.sail.mobile.deeplearning.update.rating.classification.model.AppTable;

public class MyCsvReader {

	public static Map<String,AppTable> readAppData(){
		
		Map<String,AppTable> appTableRecords = new HashMap<String,AppTable>();
		
		try{
			
			CsvReader record = new CsvReader(Constants.APP_CSV_PATH);
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
	
	// Read APP_UPDATE Table Data
	
	public Map<Integer, AppUpdateTable> readAppUpdateData() {
		Map<Integer, AppUpdateTable> appUupdateTableRecords = new HashMap<Integer, AppUpdateTable>();

		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime dateRangeLeft = formatter.parseDateTime("2016-04-20 00:00:00");
		DateTime dateRangeRight = formatter.parseDateTime("2017-04-20 00:00:00");
		
		try {

			CsvReader record = new CsvReader(Constants.APP_UPDATE_TABLE_PATH);
			record.readHeaders();

			while (record.readRecord()) {
				AppUpdateTable data = new AppUpdateTable();
				
				data.setAPP_UPDATE_ID(Integer.parseInt(record.get("APP_UPDATE_ID")));
				data.setAPP_ID(Integer.parseInt(record.get("APP_ID")));
				data.setPACKAGE_NAME(record.get("PACKAGE_NAME"));
				data.setVERSION_CODE(record.get("VERSION_CODE"));
				data.setVERSION_STRING(record.get("VERSION_STRING"));
				data.setGOOGLE_PLAY_STORE_RELEASE_TIME(record.get("GOOGLE_PLAY_STORE_RELEASE_TIME"));
				data.setRELEASE_TIME(record.get("RELEASE_TIME"));
				data.setCLOSURE_TIME(record.get("CLOSURE_TIME"));
				data.setUPDATE_LIFETIME_DAYS(record.get("UPDATE_LIFETIME_DAYS"));
				data.setUPDATE_LIFETIME_HOURS(record.get("UPDATE_LIFETIME_HOURS"));
				data.setUPDATE_LIFETIME_MINUTES(record.get("UPDATE_LIFETIME_MINUTES"));
				data.setAPK_FILE_NAME(record.get("APK_FILE_NAME"));
				data.setAPK_FILE_SIZE(record.get("APK_FILE_SIZE"));
				data.setRELEASE_NOTES(record.get("RELEASE_NOTES"));
				data.setIS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR(record.get("IS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR"));
				data.setDIFFERENCE_IN_RELEASE_NOTES(record.get("DIFFERENCE_IN_RELEASE_NOTES"));
				data.setNUMBER_OF_DOWNLOADS(record.get("NUMBER_OF_DOWNLOADS"));
				data.setNUMBER_OF_GOOGLE_PLUS(record.get("NUMBER_OF_GOOGLE_PLUS"));
				data.setAGGREGATED_RATING(record.get("AGGREGATED_RATING"));
				data.setNUMBER_OF_ONE_STAR(record.get("NUMBER_OF_ONE_STAR"));
				data.setNUMBER_OF_TWO_STARS(record.get("NUMBER_OF_TWO_STARS"));
				data.setNUMBER_OF_THREE_STARS(record.get("NUMBER_OF_THREE_STARS"));
				data.setNUMBER_OF_FOUR_STARS(record.get("NUMBER_OF_FOUR_STARS"));
				data.setNUMBER_OF_FIVE_STARS(record.get("NUMBER_OF_FIVE_STARS"));
				data.setTOTAL_NUMBER_OF_RATES(record.get("TOTAL_NUMBER_OF_RATES"));
				data.setRETURNED_TOTAL_NUMBER_OF_RATES(record.get("RETURNED_TOTAL_NUMBER_OF_RATES"));
				data.setCOMMENT_COUNT(record.get("COMMENT_COUNT"));
				data.setMINMUM_SDK_VERSION(record.get("MINMUM_SDK_VERSION"));
				data.setTARGET_SDK_VERSION(record.get("TARGET_SDK_VERSION"));
				data.setOFFER_MICRO(record.get("OFFER_MICRO"));
				data.setCURRENCY_CODE(record.get("CURRENCY_CODE"));
				data.setPRICE_STRING(record.get("PRICE_STRING"));
				data.setPRICE(record.get("PRICE"));
				data.setOFFERS_IN_APP_PURCHASES(record.get("OFFERS_IN_APP_PURCHASES"));
				data.setIN_APP_PRODUCTS(record.get("IN_APP_PRODUCTS"));
				data.setMIN_IN_APP_PRODUCT_VALUE(record.get("MIN_IN_APP_PRODUCT_VALUE"));
				data.setMAX_IN_APP_PRODUCT_VALUE(record.get("MAX_IN_APP_PRODUCT_VALUE"));
				data.setIS_FREE(record.get("IS_FREE"));
				data.setAVAILABILITY_RESTRICTION_ANDROID_ID(record.get("AVAILABILITY_RESTRICTION_ANDROID_ID"));
				data.setAVAILABILITY_RESTRICTION_CHANNEL_ID(record.get("AVAILABILITY_RESTRICTION_CHANNEL_ID"));
				data.setDEVICE_RESTRICTION(record.get("DEVICE_RESTRICTION"));
				data.setOFFER_TYPE(record.get("OFFER_TYPE"));
				data.setUPDATE_CREATION_TIME(record.get("UPDATE_CREATION_TIME"));
				
				
				DateTime dateUpdateCreation2 = formatter.parseDateTime(data.getRELEASE_TIME());
				DateTime dateUpdateCreation = formatter.parseDateTime(data.getUPDATE_CREATION_TIME());
				
				if (dateUpdateCreation.isBefore(dateRangeLeft) || dateUpdateCreation.isAfter(dateRangeRight)){
					continue;
				}else{
					if(dateUpdateCreation2.isAfter(dateRangeLeft)){
						continue;
					}else{
						appUupdateTableRecords.put(data.getAPP_UPDATE_ID(),data);
					}
				}
					
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return appUupdateTableRecords;
	}
	
	
}
