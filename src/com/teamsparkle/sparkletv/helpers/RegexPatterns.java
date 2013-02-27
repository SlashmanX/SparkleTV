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
		// foo.1x23-24*
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?(?<seasonnumber>[0-9]+)[xX](?<episodenumberstart>[0-9]+)([\\-+][0-9]+)*[\\-+](?<episodenumberend>[0-9]+)([\\.\\-+ ].*|$)");
		// foo.s0101, foo.0201
		patterns.add("^(?<seriesname>.+?)[ \\._\\-][Ss](?<seasonnumber>[0-9]{2})[\\.\\- ]?(?<episodenumber>[0-9]{2})[^0-9]");
		// foo.1x09*
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?\\[?(?<seasonnumber>[0-9]+)[xX](?<episodenumber>[0-9]+)\\]?[^\\\\/]"); 
		// foo.s01.e01, foo.s01_e01, "foo.s01 - e01"
		patterns.add("((?<seriesname>.+?)[ \\._\\-])?\\[?[Ss](?<seasonnumber>[0-9]+)[ ]?[\\._\\- ]?[ ]?[Ee]?(?<episodenumber>[0-9]+)\\]?[^\\\\/]");
		// foo.103*
		patterns.add("(?<seriesname>.+)[ \\._\\-](?<seasonnumber>[0-9]{1})(?<episodenumber>[0-9]{2})[\\._ -][^\\\\/]");
		// foo.0103*
		patterns.add("(?<seriesname>.+)[ \\._\\-](?<seasonnumber>[0-9]{2})(?<episodenumber>[0-9]{2,3})[\\._ -][^\\\\/]");
		// show name Season 01 Episode 20
		patterns.add("(?<seriesname>.+?)[ ]?[Ss]eason[ ]?(?<seasonnumber>[0-9]+)[ ]?[Ee]pisode[ ]?(?<episodenumber>[0-9]+)[^\\\\/]");
	}
	public ArrayList<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(ArrayList<String> patterns) {
		this.patterns = patterns;
	}
	
	public ParsedEpisode parseEpisode(String file) {
		Pattern p;
        Matcher m;
        ParsedEpisode pe = new ParsedEpisode();
        Map<String, String> ng = new HashMap<String, String>();
        for(String s : patterns)
        {
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
