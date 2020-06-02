package com.sail.awsomebasupdates.model;

public class AppMetaData {

	//APP_ID	APP_UPDATE_ID	PACKAGE_NAME	VERSION_CODE	RELEASE_TIME	Aggregated_Rating	NUMBER_OF_DOWNLOADS	NUMBER_OF_ONE_STAR	NUMBER_OF_TWO_STARS	NUMBER_OF_THREE_STARS	
	// NUMBER_OF_FOUR_STARS	NUMBER_OF_FIVE_STARS	RELEASE_NOTE	RELEASE_NOTE_DIFFERENCE

	public String packageName;
	public String versionCode;
	public String releaseDate;
	public String aggregatedRating;
	public String numberOfDownloads;
	public String numberOfOneStars;
	public String numberOfTwoStars;
	public String numberOfThreeStars;
	public String numberOfFourStars;
	public String numberOfFiveStars;
	public String releaseNotes;
	public String releaseNoteDifference;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getAggregatedRating() {
		return aggregatedRating;
	}
	public void setAggregatedRating(String aggregatedRating) {
		this.aggregatedRating = aggregatedRating;
	}
	public String getNumberOfDownloads() {
		return numberOfDownloads;
	}
	public void setNumberOfDownloads(String numberOfDownloads) {
		this.numberOfDownloads = numberOfDownloads;
	}
	public String getNumberOfOneStars() {
		return numberOfOneStars;
	}
	public void setNumberOfOneStars(String numberOfOneStars) {
		this.numberOfOneStars = numberOfOneStars;
	}
	public String getNumberOfTwoStars() {
		return numberOfTwoStars;
	}
	public void setNumberOfTwoStars(String numberOfTwoStars) {
		this.numberOfTwoStars = numberOfTwoStars;
	}
	public String getNumberOfThreeStars() {
		return numberOfThreeStars;
	}
	public void setNumberOfThreeStars(String numberOfThreeStars) {
		this.numberOfThreeStars = numberOfThreeStars;
	}
	public String getNumberOfFourStars() {
		return numberOfFourStars;
	}
	public void setNumberOfFourStars(String numberOfFourStars) {
		this.numberOfFourStars = numberOfFourStars;
	}
	public String getNumberOfFiveStars() {
		return numberOfFiveStars;
	}
	public void setNumberOfFiveStars(String numberOfFiveStars) {
		this.numberOfFiveStars = numberOfFiveStars;
	}
	public String getReleaseNotes() {
		return releaseNotes;
	}
	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}
	public String getReleaseNoteDifference() {
		return releaseNoteDifference;
	}
	public void setReleaseNoteDifference(String releaseNoteDifference) {
		this.releaseNoteDifference = releaseNoteDifference;
	}
}
