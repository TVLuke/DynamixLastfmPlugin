package org.ambientdynamix.contextplugins.lastfm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.contextplugins.context.info.environment.ICurrentSongContextInfo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CurrentSongContextInfo implements IContextInfo, ICurrentSongContextInfo
{

	private final String TAG = "TIMEPLUGIN";
	Song song;
	
	public static Parcelable.Creator<CurrentSongContextInfo> CREATOR = new Parcelable.Creator<CurrentSongContextInfo>() 
			{
			public CurrentSongContextInfo createFromParcel(Parcel in) 
			{
				return new CurrentSongContextInfo(in);
			}

			public CurrentSongContextInfo[] newArray(int size) 
			{
				return new CurrentSongContextInfo[size];
			}
		};
		
	CurrentSongContextInfo()
	{
		song = CurrentSongPluginRuntime.checkForCurrentSong("tvluke");
	}
	
	public CurrentSongContextInfo(Parcel in) 
	{
		song=in.readParcelable(getClass().getClassLoader());
	}

	@Override
	public String toString() 
	{
		return this.getClass().getSimpleName();
	}
	
	@Override
	public int describeContents() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		out.writeParcelable(this.song, flags);
	}

	@Override
	public String getContextType() 
	{
		return "org.ambientdynamix.contextplugins.context.info.environment.currentsong";
	}

	@Override
	public String getImplementingClassname() 
	{
		return this.getClass().getName();
	}

	@Override
	public String getStringRepresentation(String format) 
	{
		String result="";
		if (format.equalsIgnoreCase("text/plain"))
		{
			return song.getTitle();
		}
		else if (format.equalsIgnoreCase("XML"))
		{
			return song.getTitle();
		}
		else if (format.equalsIgnoreCase("JSON"))
		{
			return song.getTitle();
		}
		else
			return null;
	}

	@Override
	public Set<String> getStringRepresentationFormats() 
	{
		Set<String> formats = new HashSet<String>();
		formats.add("text/plain");
		formats.add("XML");
		formats.add("JSON");
		return formats;
	}

	@Override
	public String getName() 
	{
		return song.getTitle();
	}

	@Override
	public String getArtist() 
	{
		return song.getArtist();
	}

	@Override
	public int getLength() 
	{
		return song.getLength();
	}
}