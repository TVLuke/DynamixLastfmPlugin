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

import java.util.HashSet;
import java.util.Set;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.contextplugins.context.info.environment.ICurrentSongContextInfo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CurrentSongContextInfo implements IContextInfo
{

	private final String TAG = Constants.TAG;
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
		
	CurrentSongContextInfo(String username)
	{
		Log.d(TAG, "create CurrentSongContextInfo Object for "+username);
		song = LastFMPluginRuntime.checkForCurrentSong(username);
		Log.d(TAG, "create CurrentSongContextInfo Object2");
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
			return song.getArtistName()+" - "+song.getTitle();
		}
		else if (format.equalsIgnoreCase("XML"))
		{
			result = "<track>\n"+
					"	<name>"+song.getTitle()+"</name>\n"+
					"	<artist>"+song.getArtistName()+"</artist>\n"+
					"	<duration>"+song.getLength()+"</duration>\n"+
					"	<album>"+song.getAlbumName()+"</album>\n";
			String[] tags = song.getTags();
			result=result+"	<toptags>\n";
			for(int i=0; i<tags.length; i++)
			{
				result=result+"    <tag>\n";
				result=result+"      <name>"+tags[i]+"</name>\n";
				result=result+"    </tag>\n";
			}
			result=result+"  </toptags>\n";
			result = result+"</track>\n";
			return result;
		}
		else if (format.equalsIgnoreCase("JSON"))
		{
			return song.getTitle();
		}
		else if(format.equalsIgnoreCase("RDF/XML"))
		{
			result="<rdf:RDF\n" +
					"xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
					"xmlns:z.0=\"http://dynamix.org/semmodel/org.ambientdynamix.contextplugins.context.info.environment.currentsong/0.1/\"\n" +
					"xmlns:z.1=\"http://dynamix.org/semmodel/0.1/\" > \n";
			result=result+" <rdf:Description rdf:about=\"http://www.lastfm.de/music/"+song.getArtistName().replace(" ", "+")+"/_/"+song.getTitle().replace(" ", "+")+"\">\n";
			result=result+" <rdf:type>http://purl.org/ontology/mo/track</rdf:type>\n";
			result=result+"<z.0:hasArtist>"+song.getArtistName()+"</z.0:hasArtist>\n" +
					" <z.0:hasTitle>"+song.getTitle()+"</z.0:hasTitle>\n" +
					" <z.0:hasDuration>"+song.getLength()+"</z.0:hasDuration>" +
							"  </rdf:Description>\n </rdf:RDF>";
			return result;
		}
		else
			return result;
	}

	@Override
	public Set<String> getStringRepresentationFormats() 
	{
		Set<String> formats = new HashSet<String>();
		formats.add("text/plain");
		formats.add("XML");
		formats.add("JSON");
		formats.add("RDF/XML");
		return formats;
	}


	public String getName() 
	{
		return song.getTitle();
	}


	public String getArtist() 
	{
		return song.getArtistName();
	}


	public int getLength() 
	{
		return song.getLength();
	}
}