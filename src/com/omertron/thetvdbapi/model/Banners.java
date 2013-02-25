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
public class Banners implements Serializable {

    private static final long serialVersionUID = 1L; // Default serial UID
    private int seriesId = 0;
    private List<Banner> seriesList = new ArrayList<Banner>();
    private List<Banner> seasonList = new ArrayList<Banner>();
    private List<Banner> posterList = new ArrayList<Banner>();
    private List<Banner> fanartList = new ArrayList<Banner>();

    public int getSeriesId() {
        return seriesId;
    }

    public List<Banner> getSeriesList() {
        return seriesList;
    }

    public List<Banner> getSeasonList() {
        return seasonList;
    }

    public List<Banner> getPosterList() {
        return posterList;
    }

    public List<Banner> getFanartList() {
        return fanartList;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public void setSeriesList(List<Banner> seriesList) {
        this.seriesList = seriesList;
    }

    public void addSeriesBanner(Banner banner) {
        this.seriesList.add(banner);
    }

    public void setSeasonList(List<Banner> seasonList) {
        this.seasonList = seasonList;
    }

    public void addSeasonBanner(Banner banner) {
        this.seasonList.add(banner);
    }

    public void setPosterList(List<Banner> posterList) {
        this.posterList = posterList;
    }

    public void addPosterBanner(Banner banner) {
        this.posterList.add(banner);
    }

    public void setFanartList(List<Banner> fanartList) {
        this.fanartList = fanartList;
    }

    public void addFanartBanner(Banner banner) {
        this.fanartList.add(banner);
    }

    public void addBanner(Banner banner) {
        if (banner != null) {
            if (seriesId == 0) {
                seriesId = banner.getId();
            }

            if (banner.getBannerType() == BannerListType.series) {
                addSeriesBanner(banner);
            } else if (banner.getBannerType() == BannerListType.season) {
                addSeasonBanner(banner);
            } else if (banner.getBannerType() == BannerListType.poster) {
                addPosterBanner(banner);
            } else if (banner.getBannerType() == BannerListType.fanart) {
                addFanartBanner(banner);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder banners = new StringBuilder("[Banners ");

        banners.append("[seriesId=").append(seriesId).append("]");

        banners.append("[seriesList=");
        for (Banner banner : seriesList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("[seasonList=");
        for (Banner banner : seasonList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("[posterList=");
        for (Banner banner : posterList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("[fanartList=");
        for (Banner banner : fanartList) {
            banners.append(banner.toString());
        }
        banners.append("]");

        banners.append("]");
        return banners.toString();
    }
}
