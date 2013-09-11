/*
 * Copyright (C) Institute of Telematics, Lukas Ruge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ambientdynamix.contextplugins.lastfm;

import java.util.StringTokenizer;

import org.ambientdynamix.contextplugins.info.meta.IArtist;
import org.ambientdynamix.contextplugins.info.meta.ISong;

import android.os.Parcel;
import android.os.Parcelable;

public class Song  implements Parcelable
{

	private final String TAG = Constants.TAG;
	
	String title;
	String artist;
	int length;
	String album;
	String tags;
	int playcount=-1;
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
    
    Song(String title, String artist, int length, String album, String tags, int playcount)
    {
    	this.title=title;
    	this.artist=artist;
    	this.length=length;
    	this.album=album;
    	this.tags=tags;
    	this.playcount=playcount;
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
		playcount = in.readInt();
    }
    
	@Override
	public void writeToParcel(Parcel out, int flags) 
	{
		out.writeString(title);
		out.writeString(artist);
		out.writeInt(length);
		out.writeString(album);
		out.writeString(tags);
		out.writeInt(playcount);
	}

	public String getTitle() 
	{
		return title;
	}

	public String getArtistName() 
	{
		return artist;
	}

	public int getLength() 
	{
		return length;
	}
	
	public String getAlbumName()
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

	
	public int getPlaycount() 
	{
		return playcount;
	}
}
