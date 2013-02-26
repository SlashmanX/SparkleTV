package com.teamsparkle.sparkletv.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.google.code.regexp.Pattern;
import com.google.code.regexp.Matcher;

public class RegexPatterns {

	private ArrayList<String> patterns = new ArrayList<String>();
	
	public RegexPatterns() {
		// foo s01e23 s01e24 s01e25 *
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?[Ss](?<seasonnumber>[0-9]+)[\\.\\- ]?[Ee](?<episodenumberstart>[0-9]+)([\\.\\- ]+[Ss]\\k<seasonnumber>[\\.\\- ]?[Ee][0-9]+)*([\\.\\- ]+[Ss]\\k<seasonnumber>[\\.\\- ]?[Ee](?<episodenumberend>[0-9]+))[^\\/]");
		// foo.s01e23e24*
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?[Ss](?<seasonnumber>[0-9]+)[\\.\\- ]?[Ee](?<episodenumberstart>[0-9]+)([\\.\\- ]?[Ee][0-9]+)*[\\.\\- ]?[Ee](?<episodenumberend>[0-9]+)[^\\/]");
		// foo.1x23 1x24 1x25
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?(?<seasonnumber>[0-9]+)[xX](?<episodenumberstart>[0-9]+)([ \\._\\-]+\\k<seasonnumber>[xX][0-9]+)*([ \\._\\-]+\\k<seasonnumber>[xX](?<episodenumberend>[0-9]+))[^\\/]");
		// foo.1x23x24*
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?(?<seasonnumber>[0-9]+)[xX](?<episodenumberstart>[0-9]+)([xX][0-9]+)*[xX](?<episodenumberend>[0-9]+)[^\\/]");
		// foo.s01e23-24*
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?[Ss](?<seasonnumber>[0-9]+)[\\.\\- ]?[Ee](?<episodenumberstart>[0-9]+)([\\-][Ee]?[0-9]+)*[\\-][Ee]?(?<episodenumberend>[0-9]+)[\\.\\- ][^\\/]");
		// foo.s0101, foo.0201
		patterns.add("^(?<seriesname>.+?)[ \\._\\-][Ss](?<seasonnumber>[0-9]{2})[\\.\\- ]?(?<episodenumber>[0-9]{2})[^0-9]");
		// foo.1x09*
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?\\[?(?<seasonnumber>[0-9]+)[xX](?<episodenumber>[0-9]+)\\]?[^\\\\/]"); 
		// foo.s01.e01, foo.s01_e01, "foo.s01 - e01"
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?\\[?[Ss](?<seasonnumber>[0-9]+)[ ]?[\\._\\- ]?[ ]?[Ee]?(?<episodenumber>[0-9]+)\\]?[^\\\\/]");

	}
	
	/*
 		Pattern p = Pattern.compile("((?<seriesname>.+?)[ \\._\\-])?\\[?(?<seasonnumber>[0-9]+)[xX](?<episodenumber>[0-9]+)\\]?[^\\\\/]");
		Matcher m = p.matcher("Archer 2009 4x06 HDTV x264-2HD");
		System.out.println(m.find());       // true
		System.out.println(m.group("seriesname"));
		System.out.println(m.group("seasonnumber"));
		System.out.println(m.group("episodenumber"));
	 */
	public ArrayList<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(ArrayList<String> patterns) {
		this.patterns = patterns;
	}
	
	public ParsedEpisode parseEpisode(String file) {
		Pattern p;
        Matcher m;
        String TAG = "RegexParser";
        ParsedEpisode pe = new ParsedEpisode();
        Map<String, String> ng = new HashMap<String, String>();
        for(String s : patterns)
        {
        	Log.d(TAG, s);
        	p = Pattern.compile(s);
    		m = p.matcher(file);
    		if(m.find())
    		{
    			ng = m.namedGroups();
    			if(ng.containsKey("seriesname"))
    			{
    				pe.setShowName(ng.get("seriesname"));
    			}
    			if(ng.containsKey("seasonnumber"))
    			{
    				pe.setSeasonNumber(ng.get("seasonnumber"));
    			}
    			if(ng.containsKey("episodenumber"))
    			{
    				pe.setEpisodeNumber(ng.get("episodenumber"));
    			}
    			if(ng.containsKey("episodenumberstart"))
    			{
    				pe.setEpisodeStart(ng.get("episodenumberstart"));
    			}
    			if(ng.containsKey("episodenumberend"))
    			{
    				pe.setEpisodeEnd(ng.get("episodenumberend"));
    			}

        		break;
    		}
        }
        return pe;
	}
	
	
}
