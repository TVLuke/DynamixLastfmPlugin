package org.ambientdynamix.contextplugins.lastfm;

import java.util.StringTokenizer;

import android.os.Parcel;
import android.os.Parcelable;

public class Song  implements Parcelable 
{

	String title;
	String artist;
	int length;
	String album;
	String tags;
	/**
     * Static Creator factory for Parcelable.
     */
    public static Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() 
    {
		public Song createFromParcel(Parcel in) 
		{
		    return new Song(in);
		}
	
		public Song[] newArray(int size) 
		{
		    return new Song[size];
		}
    };
    
    Song(String title, String artist, int length, String album, String tags)
    {
    	this.title=title;
    	this.artist=artist;
    	this.length=length;
    	this.album=album;
    	this.tags=tags;
    }
    
	@Override
	public int describeContents() 
	{
		return 0;
	}
	
    Song(Parcel in)
    {
		title = in.readString();
		artist = in.readString();
		length = in.readInt();
		album = in.readString();
		tags = in.readString();
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

	public String getTitle() 
	{
		return title;
	}

	public String getArtist() 
	{
		return artist;
	}

	public int getLength() 
	{
		return length;
	}
	
	public String getAlbum()
	{
		return album;
	}

	public String[] getTags() 
	{
		StringTokenizer tk = new StringTokenizer(tags, " ");
		String[] result= new String[0];
		if(tk.countTokens()>0)
		{
			result=new String[tk.countTokens()];
		}
		int count=0;
		while(tk.hasMoreElements())
		{
			String cc = tk.nextToken();
			result[count]=cc;
			count++;
		}
		return result;
	}
}
