package com.teamsparkle.sparkletv;

import java.io.File;
import java.util.List;

import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;

import com.team.sparkle.sparkletv.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrefsFragment extends PreferenceFragment {
	
	SharedPreferences sharedPrefs;
	SharedPreferences.Editor editor;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.xml.preferences);
        
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(PrefsFragment.this.getActivity());
        editor = sharedPrefs.edit();
        
        Preference torrentLoc = findPreference("downloadlocation_preference");
        Preference watcherLoc = findPreference("folderwatcher_preference");
        Preference moveLoc = findPreference("filemover_preference");
        
        torrentLoc.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference p) {
				Intent fileChooserIntent = new Intent(PrefsFragment.this.getActivity(), FileChooserActivity.class);
				fileChooserIntent.putExtra(FileChooserActivity._Rootpath, sharedPrefs.getString("downloadlocation_preference", "/"));
				fileChooserIntent.putExtra(FileChooserActivity._FilterMode, IFileProvider.FilterMode.DirectoriesOnly);
				startActivityForResult(fileChooserIntent, 0);
				return false;
			}
		});
        
		watcherLoc.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
					
					@Override
					public boolean onPreferenceClick(Preference p) {
						Intent fileChooserIntent = new Intent(PrefsFragment.this.getActivity(), FileChooserActivity.class);
						fileChooserIntent.putExtra(FileChooserActivity._Rootpath, sharedPrefs.getString("folderwatcher_preference", "/"));
						fileChooserIntent.putExtra(FileChooserActivity._FilterMode, IFileProvider.FilterMode.DirectoriesOnly);
						startActivityForResult(fileChooserIntent, 1);
						return false;
					}
				});

		moveLoc.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference p) {
				Intent fileChooserIntent = new Intent(PrefsFragment.this.getActivity(), FileChooserActivity.class);
				fileChooserIntent.putExtra(FileChooserActivity._Rootpath, sharedPrefs.getString("filemover_preference", "/"));
				fileChooserIntent.putExtra(FileChooserActivity._FilterMode, IFileProvider.FilterMode.DirectoriesOnly);
				startActivityForResult(fileChooserIntent, 2);
				return false;
			}
		});
		
		
 
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(data != null){
	        List<LocalFile> files = (List<LocalFile>) data.getSerializableExtra(FileChooserActivity._Results);
	        String newPref = "";
	        for (File f : files)
	        {
	        	newPref = f.getAbsolutePath();
	        }
	        switch (requestCode) {
	        case 0:
	            editor.putString("downloadlocation_preference", newPref);   
	            break;
	        case 1:
	            editor.putString("folderwatcher_preference", newPref);   
	            break;
	        case 2:
	            editor.putString("filemover_preference", newPref);   
	            break;
	        }
	        
	        editor.commit();
    	}
    }

}
