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
