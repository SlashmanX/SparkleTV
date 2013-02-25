package com.teamsparkle.sparkletv.helpers;

import org.pojava.datetime.DateTime;

import com.omertron.tvrageapi.model.EpisodeList;

public class Show {
	private int id;
	private String name;
	private String airDay;
	private String airTime;
	private boolean isRunning;
	private String genre;
	private int runTime;
	private String summary;
	private int numSeasons;
	
	
	public Show() {
		super();
	}


	public Show(int id, String name, String airDay, String airTime,
			boolean isRunning, String genre, int runTime, String summary,
			int numSeasons) {
		super();
		this.id = id;
		this.name = name;
		this.airDay = airDay;
		this.airTime = airTime;
		this.isRunning = isRunning;
		this.genre = genre;
		this.runTime = runTime;
		this.summary = summary;
		this.numSeasons = numSeasons;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAirDay() {
		return airDay;
	}


	public void setAirDay(String airDay) {
		this.airDay = airDay;
	}


	public String getAirTime() {
		return airTime;
	}


	public void setAirTime(String airTime) {
		this.airTime = airTime;
	}


	public boolean isRunning() {
		return isRunning;
	}


	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public int getRunTime() {
		return runTime;
	}


	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public int getNumSeasons() {
		return numSeasons;
	}


	public void setNumSeasons(int numSeasons) {
		this.numSeasons = numSeasons;
	}
	
	
	
}
