package org.ambientdynamix.contextplugins.lastfm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.contextplugins.context.action.data.IScrobleContextAction;

import android.os.Parcel;
import android.os.Parcelable;

public class ScrobbleContextAction  implements IContextInfo, IScrobleContextAction
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

	@Override
	public void scrobble(String trackName, String ArtistName, String username, String psw) 
	{
		// TODO implement scrobbling.
		
	}

}
