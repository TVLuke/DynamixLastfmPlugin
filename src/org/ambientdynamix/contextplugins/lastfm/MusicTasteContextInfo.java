package org.ambientdynamix.contextplugins.lastfm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.contextplugins.context.info.taste.IMusicTasteContextInfo;
import org.ambientdynamix.contextplugins.info.meta.IArtist;
import org.ambientdynamix.contextplugins.info.meta.ISong;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class MusicTasteContextInfo implements IContextInfo
{

	private final String TAG = Constants.TAG;
	private List<Song> top100tracks= new ArrayList<Song>();
	
	public static Parcelable.Creator<MusicTasteContextInfo> CREATOR = new Parcelable.Creator<MusicTasteContextInfo>() 
	{
			public MusicTasteContextInfo createFromParcel(Parcel in) 
			{
				return new MusicTasteContextInfo(in);
			}

			public MusicTasteContextInfo[] newArray(int size) 
			{
				return new MusicTasteContextInfo[size];
			}
	};
		
	public MusicTasteContextInfo(String uid)
	{
		 top100tracks = LastFMPluginRuntime.getTop100Tracks(uid);
		 for(int i=0; i<top100tracks.size(); i++)
		 {
			 Log.d(TAG, top100tracks.get(i).getArtistName()+"-"+top100tracks.get(i).getTitle());
		 }
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int arg1) 
	{
		out.writeList(top100tracks);
	}

	public MusicTasteContextInfo(Parcel in) 
	{
		in.readList(top100tracks, getClass().getClassLoader());
	}
	
	@Override
	public String getContextType() 
	{
		return "org.ambientdynamix.contextplugins.context.info.taste.musictaste";
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
			for(int i=0; i<top100tracks.size(); i++)
			{
				Song song = top100tracks.get(i);
				return song.getArtistName()+" - "+song.getTitle();				
			}
		}
		else if (format.equalsIgnoreCase("XML"))
		{
			result ="<musictaste>";
			result =result+"  <top100tracks>\n";
			for(int i=0; i<top100tracks.size(); i++)
			{
				Song song = top100tracks.get(i);
				result = result+"   <track>\n"+
						"     <place>"+(i+1)+"</place>\n"+
						"	  <name>"+song.getTitle()+"</name>\n"+
						"	  <artist>"+song.getArtistName()+"</artist>\n"+
						"     <duration>"+song.getLength()+"</duration>\n"+
						"  	  <playcount>"+song.getPlaycount()+"</playcount>\n"+
						"	  <album>"+song.getAlbumName()+"</album>\n";
				String[] tags = song.getTags();
				result=result+"	  <toptags>\n";
				for(int j=0; j<tags.length; j++)
				{
					result=result+"      <tag>\n";
					result=result+"        <name>"+tags[j]+"</name>\n";
					result=result+"      </tag>\n";
				}
				result=result+"    </toptags>\n";
				result = result+"   </track>\n";			
			}
			result =result+"  </top100tracks>";
			for(int i=0; i<top100tracks.size(); i++)
			{
				
			}
			result =result+"</musictaste>";
		}
		else if(format.equalsIgnoreCase("RDF/XML"))
		{
			result="<rdf:RDF\n" +
					"xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
					"xmlns:z.0=\"http://dynamix.org/semmodel/org.ambientdynamix.contextplugins.context.info.environment.currentsong/0.1/\"\n" +
					"xmlns:z.2=\"http://dynamix.org/semmodel/org.ambientdynamix.contextplugins.context.info.taste.musictaste/0.1/\"\n" +
					"xmlns:z.3=\"http://dynamix.org/semmodel/0.1/\" > \n";
			result=result+" <rdf:Description rdf:about=\"http://www.lastfm.de/music/taste_"+LastFMPluginRuntime.lastfmaccount+"\">\n";
			result=result+" <rdf:type>http://purl.org/ontology/mo/track</rdf:type>\n";
			for(int i=0; i<top100tracks.size(); i++)
			{
				Song song = top100tracks.get(i);
				result=result+" <z.3:hasData rdf:resource=http://www.lastfm.de/music/"+song.getArtistName().replace(" ", "+")+"/_/"+song.getTitle().replace(" ", "+")+"\"/>\n";
			}
			result=result+"  </rdf:Description>\n ";
			for(int i=0; i<top100tracks.size(); i++)
			{
				Song song = top100tracks.get(i);
				result=result+" <rdf:Description rdf:about=\"http://www.lastfm.de/music/"+song.getArtistName().replace(" ", "+")+"/_/"+song.getTitle().replace(" ", "+")+"\">\n";
				result=result+" <rdf:type>http://purl.org/ontology/mo/track</rdf:type>\n";
				result=result+"<z.0:hasArtist>"+song.getArtistName()+"</z.0:hasArtist>\n" +
						" <z.0:hasTitle>"+song.getTitle()+"</z.0:hasTitle>\n" +
						" <z.0:hasDuration>"+song.getLength()+"</z.0:hasDuration>" +
						" <z.0:hasPalycount>"+song.getPlaycount()+"</z.0:hasPalycount>" +
						" <z.0:hasAlbum>"+song.getAlbumName()+"</z.0:hasAlbum>";
						String[] tags = song.getTags();
						for(int j=0; j<tags.length; j++)
						{
							result=result+" <z.0:hasTag>"+tags[j]+"</z0:hasTag>\n";
						}
				result=result+"  </rdf:Description>\n";
			}
			result=result+"</rdf:RDF>";
			return result;
		}
		return result;
	}

	@Override
	public Set<String> getStringRepresentationFormats() 
	{
		Set<String> formats = new HashSet<String>();
		formats.add("text/plain");
		formats.add("XML");
		formats.add("RDF/XML");
		return formats;
	}

	
	public ArrayList<ISong> getFavoritSongs() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public HashMap<String, Integer> top100Albums() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public HashMap<IArtist, Integer> top100Artists() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	public HashMap<ISong, Integer> top100tracks() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
