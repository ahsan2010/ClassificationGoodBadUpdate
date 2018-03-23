package com.sail.mobile.deeplearning.update.rating.classification.Loader;

import com.sail.mobile.deeplearning.update.rating.classification.util.Preprocessing;

public class AppUpdateTable {

	public String ADDED_NOTES;
	public int APP_UPDATE_ID;
	public int APP_ID;
	public String PACKAGE_NAME;
	public int VERSION_CODE;
	public String VERSION_STRING;
	public String GOOGLE_PLAY_STORE_RELEASE_TIME;
	public String RELEASE_TIME;
	public String CLOSURE_TIME;
	public int UPDATE_LIFETIME_DAYS;
	public int UPDATE_LIFETIME_HOURS;
	public int UPDATE_LIFETIME_MINUTES;
	public String APK_FILE_NAME;
	public String APK_FILE_SIZE;
	public String RELEASE_NOTES;
	public String IS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR;
	public String DIFFERENCE_IN_RELEASE_NOTES;
	public String NUMBER_OF_DOWNLOADS;
	public String NUMBER_OF_GOOGLE_PLUS;
	public double AGGREGATED_RATING;
	public int NUMBER_OF_ONE_STAR;
	public int NUMBER_OF_TWO_STARS;
	public int NUMBER_OF_THREE_STARS;
	public int NUMBER_OF_FOUR_STARS;
	public int NUMBER_OF_FIVE_STARS;
	public int TOTAL_NUMBER_OF_RATES;
	public int RETURNED_TOTAL_NUMBER_OF_RATES;
	public int COMMENT_COUNT;
	public int MINMUM_SDK_VERSION;
	public int TARGET_SDK_VERSION;
	public int OFFER_MICRO;
	public String CURRENCY_CODE;
	public String PRICE_STRING;
	public double PRICE;
	public String OFFERS_IN_APP_PURCHASES;
	public String IN_APP_PRODUCTS;
	public double MIN_IN_APP_PRODUCT_VALUE;
	public double MAX_IN_APP_PRODUCT_VALUE;
	public String IS_FREE;
	public String AVAILABILITY_RESTRICTION_ANDROID_ID;
	public int AVAILABILITY_RESTRICTION_CHANNEL_ID;
	public int DEVICE_RESTRICTION;
	public int OFFER_TYPE;
	public String UPDATE_CREATION_TIME;
	
	
	
	public String getADDED_NOTES() {
		return ADDED_NOTES;
	}
	public void setADDED_NOTES(String aDDED_NOTES) {
		ADDED_NOTES = aDDED_NOTES;
	}
	public int getAPP_UPDATE_ID() {
		return APP_UPDATE_ID;
	}
	public void setAPP_UPDATE_ID(int aPP_UPDATE_ID) {
		APP_UPDATE_ID = aPP_UPDATE_ID;
	}
	public int getAPP_ID() {
		return APP_ID;
	}
	public void setAPP_ID(int aPP_ID) {
		APP_ID = aPP_ID;
	}
	public String getPACKAGE_NAME() {
		return PACKAGE_NAME;
	}
	public void setPACKAGE_NAME(String pACKAGE_NAME) {
		PACKAGE_NAME = pACKAGE_NAME;
	}
	public int getVERSION_CODE() {
		return VERSION_CODE;
	}
	public void setVERSION_CODE(String vERSION_CODE) {
		if(vERSION_CODE == null){			
			VERSION_CODE = -1;			
		}
		else if (vERSION_CODE.length() <= 0){
			VERSION_CODE = -1;
		}
			
		else VERSION_CODE = Integer.parseInt(vERSION_CODE);	
	}
	public String getVERSION_STRING() {
		return VERSION_STRING;
	}
	public void setVERSION_STRING(String vERSION_STRING) {
		VERSION_STRING = vERSION_STRING;
	}
	public String getGOOGLE_PLAY_STORE_RELEASE_TIME() {
		return GOOGLE_PLAY_STORE_RELEASE_TIME;
	}
	public void setGOOGLE_PLAY_STORE_RELEASE_TIME(String gOOGLE_PLAY_STORE_RELEASE_TIME) {
		GOOGLE_PLAY_STORE_RELEASE_TIME = gOOGLE_PLAY_STORE_RELEASE_TIME;
	}
	public String getRELEASE_TIME() {
		return RELEASE_TIME;
	}
	public void setRELEASE_TIME(String rELEASE_TIME) {
		RELEASE_TIME = rELEASE_TIME;
	}
	public String getCLOSURE_TIME() {
		return CLOSURE_TIME;
	}
	public void setCLOSURE_TIME(String cLOSURE_TIME) {
		CLOSURE_TIME = cLOSURE_TIME;
	}
	public int getUPDATE_LIFETIME_DAYS() {
		return UPDATE_LIFETIME_DAYS;
	}
	public void setUPDATE_LIFETIME_DAYS(String uPDATE_LIFETIME_DAYS) {
		if(uPDATE_LIFETIME_DAYS == null){			
			UPDATE_LIFETIME_DAYS = -1;			
		}
		else if (uPDATE_LIFETIME_DAYS.length() <= 0 || uPDATE_LIFETIME_DAYS.equals("NULL")){
			UPDATE_LIFETIME_DAYS = -1;
		}
			
		else UPDATE_LIFETIME_DAYS = Integer.parseInt(uPDATE_LIFETIME_DAYS);	
	}
	public int getUPDATE_LIFETIME_HOURS() {
		return UPDATE_LIFETIME_HOURS;
	}
	public void setUPDATE_LIFETIME_HOURS(String uPDATE_LIFETIME_HOURS) {
		if(uPDATE_LIFETIME_HOURS == null){			
			UPDATE_LIFETIME_HOURS = -1;			
		}
		else if (uPDATE_LIFETIME_HOURS.length() <= 0 || uPDATE_LIFETIME_HOURS.equals("NULL")){
			UPDATE_LIFETIME_HOURS = -1;
		}
			
		else UPDATE_LIFETIME_HOURS = Integer.parseInt(uPDATE_LIFETIME_HOURS);
	}
	public int getUPDATE_LIFETIME_MINUTES() {
		return UPDATE_LIFETIME_MINUTES;
	}
	public void setUPDATE_LIFETIME_MINUTES(String uPDATE_LIFETIME_MINUTES) {
		if(uPDATE_LIFETIME_MINUTES == null){			
			UPDATE_LIFETIME_MINUTES = -1;			
		}
		else if (uPDATE_LIFETIME_MINUTES.length() <= 0 || uPDATE_LIFETIME_MINUTES.equals("NULL")){
			UPDATE_LIFETIME_MINUTES = -1;
		}
			
		else UPDATE_LIFETIME_MINUTES = Integer.parseInt(uPDATE_LIFETIME_MINUTES);
	}
	public String getAPK_FILE_NAME() {
		return APK_FILE_NAME;
	}
	public void setAPK_FILE_NAME(String aPK_FILE_NAME) {
		APK_FILE_NAME = aPK_FILE_NAME;
	}
	public String getAPK_FILE_SIZE() {
		return APK_FILE_SIZE;
	}
	public void setAPK_FILE_SIZE(String aPK_FILE_SIZE) {
		APK_FILE_SIZE = aPK_FILE_SIZE;
	}
	public String getRELEASE_NOTES() {
		return RELEASE_NOTES;
	}
	public void setRELEASE_NOTES(String rELEASE_NOTES) {
		String st = rELEASE_NOTES.replace("^~^", " ");
		RELEASE_NOTES = Preprocessing.htmlRemove(st);
	}
	public String getIS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR() {
		return IS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR;
	}
	public void setIS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR(String iS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR) {
		IS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR = iS_RELEASE_NOTES_HAS_NON_PRINTABLE_CHAR;
	}
	public String getDIFFERENCE_IN_RELEASE_NOTES() {
		return DIFFERENCE_IN_RELEASE_NOTES;
	}
	public void setDIFFERENCE_IN_RELEASE_NOTES(String dIFFERENCE_IN_RELEASE_NOTES) {
		DIFFERENCE_IN_RELEASE_NOTES = dIFFERENCE_IN_RELEASE_NOTES;
	}
	public String getNUMBER_OF_DOWNLOADS() {
		return NUMBER_OF_DOWNLOADS;
	}
	public void setNUMBER_OF_DOWNLOADS(String nUMBER_OF_DOWNLOADS) {
		NUMBER_OF_DOWNLOADS = nUMBER_OF_DOWNLOADS;
	}
	public String getNUMBER_OF_GOOGLE_PLUS() {
		return NUMBER_OF_GOOGLE_PLUS;
	}
	public void setNUMBER_OF_GOOGLE_PLUS(String nUMBER_OF_GOOGLE_PLUS) {
		NUMBER_OF_GOOGLE_PLUS = nUMBER_OF_GOOGLE_PLUS;
	}
	public double getAGGREGATED_RATING() {
		return AGGREGATED_RATING;
	}
	public void setAGGREGATED_RATING(String aGGREGATED_RATING) {
		if(aGGREGATED_RATING == null){			
			AGGREGATED_RATING = -1;			
		}
		else if (aGGREGATED_RATING.length() <= 0){
			AGGREGATED_RATING = -1;
		}		
		else AGGREGATED_RATING = Double.parseDouble(aGGREGATED_RATING);
	}
	public int getNUMBER_OF_ONE_STAR() {
		return NUMBER_OF_ONE_STAR;
	}
	public void setNUMBER_OF_ONE_STAR(String nUMBER_OF_ONE_STAR) {
		if(nUMBER_OF_ONE_STAR == null){			
			NUMBER_OF_ONE_STAR = -1;			
		}
		else if (nUMBER_OF_ONE_STAR.length() <= 0){
			NUMBER_OF_ONE_STAR = -1;
		}
			
		else NUMBER_OF_ONE_STAR = Integer.parseInt(nUMBER_OF_ONE_STAR);
	}
	public int getNUMBER_OF_TWO_STARS() {
		return NUMBER_OF_TWO_STARS;
	}
	public void setNUMBER_OF_TWO_STARS(String nUMBER_OF_TWO_STARS) {
		if(nUMBER_OF_TWO_STARS == null){			
			NUMBER_OF_TWO_STARS = -1;			
		}
		else if (nUMBER_OF_TWO_STARS.length() <= 0){
			NUMBER_OF_TWO_STARS = -1;
		}
			
		else NUMBER_OF_TWO_STARS = Integer.parseInt(nUMBER_OF_TWO_STARS);
	}
	public int getNUMBER_OF_THREE_STARS() {
		return NUMBER_OF_THREE_STARS;
	}
	public void setNUMBER_OF_THREE_STARS(String nUMBER_OF_THREE_STARS) {
		if(nUMBER_OF_THREE_STARS == null){			
			NUMBER_OF_THREE_STARS = -1;			
		}
		else if (nUMBER_OF_THREE_STARS.length() <= 0){
			NUMBER_OF_THREE_STARS = -1;
		}
			
		else NUMBER_OF_THREE_STARS = Integer.parseInt(nUMBER_OF_THREE_STARS);
	}
	public int getNUMBER_OF_FOUR_STARS() {
		return NUMBER_OF_FOUR_STARS;
	}
	public void setNUMBER_OF_FOUR_STARS(String nUMBER_OF_FOUR_STARS) {
		if(nUMBER_OF_FOUR_STARS == null){			
			NUMBER_OF_FOUR_STARS = -1;			
		}
		else if (nUMBER_OF_FOUR_STARS.length() <= 0){
			NUMBER_OF_FOUR_STARS = -1;
		}
			
		else NUMBER_OF_FOUR_STARS = Integer.parseInt(nUMBER_OF_FOUR_STARS);
	}
	public int getNUMBER_OF_FIVE_STARS() {
		return NUMBER_OF_FIVE_STARS;
	}
	public void setNUMBER_OF_FIVE_STARS(String nUMBER_OF_FIVE_STARS) {
		if(nUMBER_OF_FIVE_STARS == null){			
			NUMBER_OF_FIVE_STARS = -1;			
		}
		else if (nUMBER_OF_FIVE_STARS.length() <= 0){
			NUMBER_OF_FIVE_STARS = -1;
		}
			
		else NUMBER_OF_FIVE_STARS = Integer.parseInt(nUMBER_OF_FIVE_STARS);
	}
	public int getTOTAL_NUMBER_OF_RATES() {
		return TOTAL_NUMBER_OF_RATES;
	}
	public void setTOTAL_NUMBER_OF_RATES(String tOTAL_NUMBER_OF_RATES) {
		if(tOTAL_NUMBER_OF_RATES == null){			
			TOTAL_NUMBER_OF_RATES = -1;			
		}
		else if (tOTAL_NUMBER_OF_RATES.length() <= 0){
			TOTAL_NUMBER_OF_RATES = -1;
		}
			
		else TOTAL_NUMBER_OF_RATES = Integer.parseInt(tOTAL_NUMBER_OF_RATES);
	}
	public int getRETURNED_TOTAL_NUMBER_OF_RATES() {
		return RETURNED_TOTAL_NUMBER_OF_RATES;
	}
	public void setRETURNED_TOTAL_NUMBER_OF_RATES(String rETURNED_TOTAL_NUMBER_OF_RATES) {
		if(rETURNED_TOTAL_NUMBER_OF_RATES == null){			
			RETURNED_TOTAL_NUMBER_OF_RATES = -1;			
		}
		else if (rETURNED_TOTAL_NUMBER_OF_RATES.length() <= 0){
			RETURNED_TOTAL_NUMBER_OF_RATES = -1;
		}
			
		else RETURNED_TOTAL_NUMBER_OF_RATES = Integer.parseInt(rETURNED_TOTAL_NUMBER_OF_RATES);	}
	public int getCOMMENT_COUNT() {
		return COMMENT_COUNT;
	}
	public void setCOMMENT_COUNT(String cOMMENT_COUNT) {
		if(cOMMENT_COUNT == null){			
			COMMENT_COUNT = -1;			
		}
		else if (cOMMENT_COUNT.length() <= 0){
			COMMENT_COUNT = -1;
		}
			
		else COMMENT_COUNT = Integer.parseInt(cOMMENT_COUNT);	
	}
	public int getMINMUM_SDK_VERSION() {
		return MINMUM_SDK_VERSION;
	}
	public void setMINMUM_SDK_VERSION(String mINMUM_SDK_VERSION) {
		if(mINMUM_SDK_VERSION == null){			
			MINMUM_SDK_VERSION = -1;			
		}
		else if (mINMUM_SDK_VERSION.length() <= 0 || mINMUM_SDK_VERSION.equals("NULL")){
			MINMUM_SDK_VERSION = -1;
		}
			
		else MINMUM_SDK_VERSION = Integer.parseInt(mINMUM_SDK_VERSION);
	}
	public int getTARGET_SDK_VERSION() {
		return TARGET_SDK_VERSION;
	}
	public void setTARGET_SDK_VERSION(String tARGET_SDK_VERSION) {
		if(tARGET_SDK_VERSION == null){			
			TARGET_SDK_VERSION = -1;			
		}
		else if (tARGET_SDK_VERSION.length() <= 0 || tARGET_SDK_VERSION.equals("NULL")){
			TARGET_SDK_VERSION = -1;
		}
			
		else TARGET_SDK_VERSION = Integer.parseInt(tARGET_SDK_VERSION);
	}
	public int getOFFER_MICRO() {
		return OFFER_MICRO;
	}
	public void setOFFER_MICRO(String oFFER_MICRO) {
		if(oFFER_MICRO == null){			
			OFFER_MICRO = -1;			
		}
		else if (oFFER_MICRO.length() <= 0){
			OFFER_MICRO = -1;
		}
			
		else OFFER_MICRO = Integer.parseInt(oFFER_MICRO);
	}
	public String getCURRENCY_CODE() {
		return CURRENCY_CODE;
	}
	public void setCURRENCY_CODE(String cURRENCY_CODE) {
		CURRENCY_CODE = cURRENCY_CODE;
	}
	public String getPRICE_STRING() {
		return PRICE_STRING;
	}
	public void setPRICE_STRING(String pRICE_STRING) {
		PRICE_STRING = pRICE_STRING;
	}
	public double getPRICE() {
		return PRICE;
	}
	public void setPRICE(String pRICE) {
		if(pRICE == null){			
			PRICE = -1;			
		}
		else if (pRICE.length() <= 0){
			PRICE = -1;
		}
			
		else PRICE = Double.parseDouble(pRICE);
	}
	public String getOFFERS_IN_APP_PURCHASES() {
		return OFFERS_IN_APP_PURCHASES;
	}
	public void setOFFERS_IN_APP_PURCHASES(String oFFERS_IN_APP_PURCHASES) {
		OFFERS_IN_APP_PURCHASES = oFFERS_IN_APP_PURCHASES;
	}
	public String getIN_APP_PRODUCTS() {
		return IN_APP_PRODUCTS;
	}
	public void setIN_APP_PRODUCTS(String iN_APP_PRODUCTS) {
		IN_APP_PRODUCTS = iN_APP_PRODUCTS;
	}
	public double getMIN_IN_APP_PRODUCT_VALUE() {
		return MIN_IN_APP_PRODUCT_VALUE;
	}
	public void setMIN_IN_APP_PRODUCT_VALUE(String mIN_IN_APP_PRODUCT_VALUE) {
		if(mIN_IN_APP_PRODUCT_VALUE == null){			
			MIN_IN_APP_PRODUCT_VALUE = -1;			
		}
		else if (mIN_IN_APP_PRODUCT_VALUE.length() <= 0){
			MIN_IN_APP_PRODUCT_VALUE = -1;
		}
			
		else MIN_IN_APP_PRODUCT_VALUE = Double.parseDouble(mIN_IN_APP_PRODUCT_VALUE);
	}
	public double getMAX_IN_APP_PRODUCT_VALUE() {
		return MAX_IN_APP_PRODUCT_VALUE;
	}
	public void setMAX_IN_APP_PRODUCT_VALUE(String mAX_IN_APP_PRODUCT_VALUE) {
		if(mAX_IN_APP_PRODUCT_VALUE == null){			
			MAX_IN_APP_PRODUCT_VALUE = -1;			
		}
		else if (mAX_IN_APP_PRODUCT_VALUE.length() <= 0){
			MAX_IN_APP_PRODUCT_VALUE = -1;
		}
			
		else MAX_IN_APP_PRODUCT_VALUE = Double.parseDouble(mAX_IN_APP_PRODUCT_VALUE);
	}
	public String getIS_FREE() {
		return IS_FREE;
	}
	public void setIS_FREE(String iS_FREE) {
		IS_FREE = iS_FREE;
	}
	public String getAVAILABILITY_RESTRICTION_ANDROID_ID() {
		return AVAILABILITY_RESTRICTION_ANDROID_ID;
	}
	public void setAVAILABILITY_RESTRICTION_ANDROID_ID(String aVAILABILITY_RESTRICTION_ANDROID_ID) {
		AVAILABILITY_RESTRICTION_ANDROID_ID = aVAILABILITY_RESTRICTION_ANDROID_ID;
	}
	public int getAVAILABILITY_RESTRICTION_CHANNEL_ID() {
		return AVAILABILITY_RESTRICTION_CHANNEL_ID;
	}
	public void setAVAILABILITY_RESTRICTION_CHANNEL_ID(String aVAILABILITY_RESTRICTION_CHANNEL_ID) {
		if(aVAILABILITY_RESTRICTION_CHANNEL_ID == null){			
			AVAILABILITY_RESTRICTION_CHANNEL_ID = -1;			
		}
		else if (aVAILABILITY_RESTRICTION_CHANNEL_ID.length() <= 0 || aVAILABILITY_RESTRICTION_CHANNEL_ID.equals("NULL")){
			AVAILABILITY_RESTRICTION_CHANNEL_ID = -1;
		}
			
		else AVAILABILITY_RESTRICTION_CHANNEL_ID = Integer.parseInt(aVAILABILITY_RESTRICTION_CHANNEL_ID);
	}
	public int getDEVICE_RESTRICTION() {
		return DEVICE_RESTRICTION;
	}
	public void setDEVICE_RESTRICTION(String dEVICE_RESTRICTION) {
		if(dEVICE_RESTRICTION == null){			
			DEVICE_RESTRICTION = -1;			
		}
		else if (dEVICE_RESTRICTION.length() <= 0 || dEVICE_RESTRICTION.equals("NULL")){
			DEVICE_RESTRICTION = -1;
		}
			
		else DEVICE_RESTRICTION = Integer.parseInt(dEVICE_RESTRICTION);
	}
	public int getOFFER_TYPE() {
		return OFFER_TYPE;
	}
	public void setOFFER_TYPE(String oFFER_TYPE) {
		if(oFFER_TYPE == null){			
			OFFER_TYPE = -1;			
		}
		else if (oFFER_TYPE.length() <= 0){
			OFFER_TYPE = -1;
		}
			
		else OFFER_TYPE = Integer.parseInt(oFFER_TYPE);
	}
	public String getUPDATE_CREATION_TIME() {
		return UPDATE_CREATION_TIME;
	}
	public void setUPDATE_CREATION_TIME(String uPDATE_CREATION_TIME) {
		UPDATE_CREATION_TIME = uPDATE_CREATION_TIME;
	}
	
	
	
	
	
}
