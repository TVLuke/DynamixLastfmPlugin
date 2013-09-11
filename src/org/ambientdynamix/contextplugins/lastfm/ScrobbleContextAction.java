package org.ambientdynamix.contextplugins.lastfm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.contextplugins.context.action.data.IScrobleContextAction;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleResult;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ScrobbleContextAction  implements IContextInfo
{

	private final String TAG = Constants.TAG;
	
	public static Parcelable.Creator<ScrobbleContextAction> CREATOR = new Parcelable.Creator<ScrobbleContextAction>() 
	{
			public ScrobbleContextAction createFromParcel(Parcel in) 
			{
				return new ScrobbleContextAction(in);
			}

			public ScrobbleContextAction[] newArray(int size) 
			{
				return new ScrobbleContextAction[size];
			}
	};
	
	public ScrobbleContextAction()
	{
		
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		
	}

	public ScrobbleContextAction(Parcel in) 
	{
	}

	@Override
	public String getContextType() 
	{
		return "org.ambientdynamix.contextplugins.context.action.data.scrobblesong";
	}

	@Override
	public String getImplementingClassname() 
	{
		return this.getClass().getName();
	}

	@Override
	public String getStringRepresentation(String arg0) 
	{
		return "";
	}

	@Override
	public Set<String> getStringRepresentationFormats() 
	{
		return null;
	}

	
	public void scrobble(String trackName, String artistName, String username, String psw) 
	{
		Session session = null;
		Log.d(TAG, "><");
		//Caller.getInstance().setCache(null);
		try
		{
			Log.d(TAG, ">x<");
			//Caller.getInstance().setCache(null);
			Log.d(TAG, ">xx<");
			session = Authenticator.getMobileSession(username, psw, Constants.API_KEY, Constants.API_SECRET);
			Log.d(TAG, ">xxx<");
			int now = (int) (System.currentTimeMillis()/1000);
			Log.d(TAG, ">xxxx<");
			ScrobbleResult result = Track.updateNowPlaying(artistName, trackName, session);
			Log.d(TAG, ">xxxxx<");
			result = Track.scrobble(artistName, trackName, now, session);
			Log.d(TAG, ">xxxxxx<");

		}
		catch(Exception e)
		{
			
		}
		
	}

}
