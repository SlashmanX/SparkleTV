package com.teamsparkle.sparkletv.helpers;

import com.google.gson.annotations.SerializedName;

public class TorrentSearchResult {
    @SerializedName("name")
    public String name;
    
    @SerializedName("size")
    public long size;
    
    @SerializedName("page")
    public String page;
    
    @SerializedName("torrent")
    public String torrent;
    
    @SerializedName("magnet")
    public String magnet;
    
    @SerializedName("hash")
    public String hash;
    
    @SerializedName("category")
    public String category;
    
    @SerializedName("seeder")
    public int seeders;
    
    @SerializedName("verified")
    public int verified;
    
    public String query;
}
