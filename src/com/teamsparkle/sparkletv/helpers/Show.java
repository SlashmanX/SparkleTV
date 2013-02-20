package com.teamsparkle.sparkletv.helpers;

import org.pojava.datetime.DateTime;

public class Show {
	private int id;
	private String name;
	private DateTime lastEpisodeDate;
	private DateTime nextEpisodeDate;
	private String lastEpisodeName;
	private String nextEpisodeName;
	private int currentSeason;
	private int currentEpisode;
	
	public Show(int id, String name, DateTime lastEpisodeDate,
			DateTime nextEpisodeDate, String lastEpisodeName,
			String nextEpisodeName, int currentSeason, int currentEpisode) {
		super();
		this.id = id;
		this.name = name;
		this.lastEpisodeDate = lastEpisodeDate;
		this.nextEpisodeDate = nextEpisodeDate;
		this.lastEpisodeName = lastEpisodeName;
		this.nextEpisodeName = nextEpisodeName;
		this.currentSeason = currentSeason;
		this.currentEpisode = currentEpisode;
	}

	public Show() {
		super();
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

	public DateTime getLastEpisodeDate() {
		return lastEpisodeDate;
	}

	public void setLastEpisodeDate(DateTime lastEpisodeDate) {
		this.lastEpisodeDate = lastEpisodeDate;
	}

	public DateTime getNextEpisodeDate() {
		return nextEpisodeDate;
	}

	public void setNextEpisodeDate(DateTime nextEpisodeDate) {
		this.nextEpisodeDate = nextEpisodeDate;
	}

	public String getLastEpisodeName() {
		return lastEpisodeName;
	}

	public void setLastEpisodeName(String lastEpisodeName) {
		this.lastEpisodeName = lastEpisodeName;
	}

	public String getNextEpisodeName() {
		return nextEpisodeName;
	}

	public void setNextEpisodeName(String nextEpisodeName) {
		this.nextEpisodeName = nextEpisodeName;
	}

	public int getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(int currentSeason) {
		this.currentSeason = currentSeason;
	}

	public int getCurrentEpisode() {
		return currentEpisode;
	}

	public void setCurrentEpisode(int currentEpisode) {
		this.currentEpisode = currentEpisode;
	}
	
	
}
