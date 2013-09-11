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

public class MusicTasteContextInfo implements IContextInfo, IMusicTasteContextInfo
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
		return result;
	}

	@Override
	public Set<String> getStringRepresentationFormats() 
	{
		Set<String> formats = new HashSet<String>();
		formats.add("text/plain");
		formats.add("XML");
		return formats;
	}

	@Override
	public ArrayList<ISong> getFavoritSongs() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Integer> top100Albums() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<IArtist, Integer> top100Artists() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<ISong, Integer> top100tracks() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
