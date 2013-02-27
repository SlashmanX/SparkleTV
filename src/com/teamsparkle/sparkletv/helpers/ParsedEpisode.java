package com.teamsparkle.sparkletv.helpers;

public class ParsedEpisode {

	private String showName;
	private String episodeNumber;
	private String seasonNumber;
	private String episodeStart;
	private String episodeEnd;
	private String episodeName;
	
	public ParsedEpisode() {
		super();
	}

	public ParsedEpisode(String showName, String episodeNumber,
			String seasonNumber, String episodeStart, String episodeEnd,
			String episodeName) {
		super();
		this.showName = showName;
		this.episodeNumber = episodeNumber;
		this.seasonNumber = seasonNumber;
		this.episodeStart = episodeStart;
		this.episodeEnd = episodeEnd;
		this.episodeName = episodeName;
	}



	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(String episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(String seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public String getEpisodeStart() {
		return episodeStart;
	}

	public void setEpisodeStart(String episodeStart) {
		this.episodeStart = episodeStart;
	}

	public String getEpisodeEnd() {
		return episodeEnd;
	}

	public void setEpisodeEnd(String episodeEnd) {
		this.episodeEnd = episodeEnd;
	}

	public String getEpisodeName() {
		return episodeName;
	}

	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}
	
	@Override
    public String toString() {
        StringBuilder sbEpisode = new StringBuilder("[ParsedEpisode ");

        sbEpisode.append("[show=").append(showName).append("]");
        sbEpisode.append("[EpisodeNumber=").append(episodeNumber).append("]");
        sbEpisode.append("[SeasonNumber=").append(seasonNumber).append("]");
        sbEpisode.append("[EpisodeStart=").append(episodeStart).append("]");
        sbEpisode.append("[EpisodeEnd=").append(episodeEnd).append("]");
        sbEpisode.append("[EpisodeName=").append(episodeName).append("]");
        sbEpisode.append("]");

        return sbEpisode.toString();
    }
	
}
