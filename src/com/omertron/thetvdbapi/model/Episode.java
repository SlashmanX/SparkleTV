/*
 *      Copyright (c) 2004-2013 Matthew Altman & Stuart Boston
 *
 *      This file is part of TheTVDB API.
 *
 *      TheTVDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheTVDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheTVDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.thetvdbapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matthew.altman
 */
public class Episode implements Serializable {

    private static final long serialVersionUID = 1L; // Default serial UID
    private String id;
    private String combinedEpisodeNumber;
    private String combinedSeason;
    private String dvdChapter;
    private String dvdDiscId;
    private String dvdEpisodeNumber;
    private String dvdSeason;
    private List<String> directors = new ArrayList<String>();
    private String epImgFlag;
    private String episodeName;
    private int episodeNumber;
    private String firstAired;
    private List<String> guestStars = new ArrayList<String>();
    private String imdbId;
    private String language;
    private String overview;
    private String productionCode;
    private String rating;
    private int seasonNumber;
    private List<String> writers = new ArrayList<String>();
    private String absoluteNumber;
    private int airsAfterSeason;
    private int airsBeforeSeason;
    private int airsBeforeEpisode;
    private String filename;
    private String lastUpdated;
    private String seriesId;
    private String seasonId;
    private boolean downloaded;

    public String getId() {
        return id;
    }

    public String getAbsoluteNumber() {
        return absoluteNumber;
    }

    public int getAirsAfterSeason() {
        return airsAfterSeason;
    }

    public int getAirsBeforeEpisode() {
        return airsBeforeEpisode;
    }

    public int getAirsBeforeSeason() {
        return airsBeforeSeason;
    }

    public String getCombinedEpisodeNumber() {
        return combinedEpisodeNumber;
    }

    public String getCombinedSeason() {
        return combinedSeason;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public String getDvdChapter() {
        return dvdChapter;
    }

    public String getDvdDiscId() {
        return dvdDiscId;
    }

    public String getDvdEpisodeNumber() {
        return dvdEpisodeNumber;
    }

    public String getDvdSeason() {
        return dvdSeason;
    }

    public String getEpImgFlag() {
        return epImgFlag;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }
    
    public String getSxxEyy() {
    	return String.format("S%02dE%02d", getSeasonNumber(), getEpisodeNumber());
    }
    
    public String getSxE() {
    	return String.format("%02dx%02d", getSeasonNumber(), getEpisodeNumber());
    }

    public String getFilename() {
        return filename;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public List<String> getGuestStars() {
        return guestStars;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getLanguage() {
        return language;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getOverview() {
        return overview;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public String getRating() {
        return rating;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setAbsoluteNumber(String absoluteNumber) {
        this.absoluteNumber = absoluteNumber;
    }

    public void setAirsAfterSeason(int airsAfterSeason) {
        this.airsAfterSeason = airsAfterSeason;
    }

    public void setAirsBeforeEpisode(int airsBeforeEpisode) {
        this.airsBeforeEpisode = airsBeforeEpisode;
    }

    public void setAirsBeforeSeason(int airsBeforeSeason) {
        this.airsBeforeSeason = airsBeforeSeason;
    }

    public void setCombinedEpisodeNumber(String combinedEpisodeNumber) {
        this.combinedEpisodeNumber = combinedEpisodeNumber;
    }

    public void setCombinedSeason(String combinedSeason) {
        this.combinedSeason = combinedSeason;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public void setDvdChapter(String dvdChapter) {
        this.dvdChapter = dvdChapter;
    }

    public void setDvdDiscId(String dvdDiscId) {
        this.dvdDiscId = dvdDiscId;
    }

    public void setDvdEpisodeNumber(String dvdEpisodeNumber) {
        this.dvdEpisodeNumber = dvdEpisodeNumber;
    }

    public void setDvdSeason(String dvdSeason) {
        this.dvdSeason = dvdSeason;
    }

    public void setEpImgFlag(String epImgFlag) {
        this.epImgFlag = epImgFlag;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public void setGuestStars(List<String> guestStars) {
        this.guestStars = guestStars;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}

	@Override
    public String toString() {
        StringBuilder sbEpisode = new StringBuilder("[Episode ");

        sbEpisode.append("[id=").append(id).append("]");
        sbEpisode.append("[combinedEpisodeNumber=").append(combinedEpisodeNumber).append("]");
        sbEpisode.append("[combinedSeason=").append(combinedSeason).append("]");
        sbEpisode.append("[dvdChapter=").append(dvdChapter).append("]");
        sbEpisode.append("[dvdChapter=").append(dvdDiscId).append("]");
        sbEpisode.append("[dvdEpisodeNumber=").append(dvdEpisodeNumber).append("]");
        sbEpisode.append("[dvdSeason=").append(dvdSeason).append("]");
        sbEpisode.append("[directors=").append(directors.toString()).append("]");
        sbEpisode.append("[epImgFlag=").append(epImgFlag).append("]");
        sbEpisode.append("[episodeName=").append(episodeName).append("]");
        sbEpisode.append("[episodeNumber=").append(episodeNumber).append("]");
        sbEpisode.append("[firstAired=").append(firstAired).append("]");
        sbEpisode.append("[guestStars=").append(guestStars.toString()).append("]");
        sbEpisode.append("[imdbId=").append(imdbId).append("]");
        sbEpisode.append("[language=").append(language).append("]");
        sbEpisode.append("[overview=").append(overview).append("]");
        sbEpisode.append("[productionCode=").append(productionCode).append("]");
        sbEpisode.append("[rating=").append(rating).append("]");
        sbEpisode.append("[seasonNumber=").append(seasonNumber).append("]");
        sbEpisode.append("[writers=").append(writers.toString()).append("]");
        sbEpisode.append("[absoluteNumber=").append(absoluteNumber).append("]");
        sbEpisode.append("[airsAfterSeason=").append(airsAfterSeason).append("]");
        sbEpisode.append("[airsBeforeSeason=").append(airsBeforeSeason).append("]");
        sbEpisode.append("[airsBeforeEpisode=").append(airsBeforeEpisode).append("]");
        sbEpisode.append("[filename=").append(filename).append("]");
        sbEpisode.append("[lastUpdated=").append(lastUpdated).append("]");
        sbEpisode.append("[seriesId=").append(seriesId).append("]");
        sbEpisode.append("[seasonId=").append(seasonId).append("]");
        sbEpisode.append("]");

        return sbEpisode.toString();
    }
}
