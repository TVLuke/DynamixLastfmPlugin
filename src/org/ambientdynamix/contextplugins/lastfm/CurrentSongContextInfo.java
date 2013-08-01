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
	
	String title;
	String artist;
	int length;
	String album;
	String tags;
	
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

	}
	
	public CurrentSongContextInfo(Parcel in) 
	{
		String title = in.readString();
		String artist = in.readString();
		int length = in.readInt();
		String album = in.readString();
		String tags = in.readString();
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
		out.writeString(title);
		out.writeString(artist);
		out.writeInt(length);
		out.writeString(album);
		out.writeString(tags);
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
			return "";
		}
		else if (format.equalsIgnoreCase("XML"))
		{
			return "";
		}
		else if (format.equalsIgnoreCase("JSON"))
		{
			return "";
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
		return title;
	}

	@Override
	public String getArtist() 
	{
		return artist;
	}

	@Override
	public int getLength() 
	{
		return length;
	}
}