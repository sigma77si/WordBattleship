package com.sigma77dev.wordbattleship;

import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

public class MyApplication1 extends Application{
	 private Locale locale = null;
	    
	    public void setLanguage(String language){
	    	locale = new Locale(language);
	    }
	  
	    @Override
	    public void onConfigurationChanged(Configuration newConfig)
	    {
	        super.onConfigurationChanged(newConfig);
	        if (locale != null)
	        {
	            newConfig.locale = locale;
	            Locale.setDefault(locale);
	            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
	        }
	    }

	    @Override
	    public void onCreate()
	    {
	        super.onCreate();

	        SharedPreferences settings = getSharedPreferences("BattleshipSettings",
					Context.MODE_PRIVATE);

	        Configuration config = getBaseContext().getResources().getConfiguration();

	        String lang = settings.getString("language", "");
	        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang))
	        {
	            locale = new Locale(lang);
	            Locale.setDefault(locale);
	            config.locale = locale;
	            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
	        }
	    }

}
