package org.ambientdynamix.contextplugins.currentsong;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.ambientdynamix.api.application.IContextInfo;
import org.ambientdynamix.contextplugins.context.info.environment.ICurrentTimeContextInfo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class CurrentSongContextInfo implements IContextInfo, ICurrentTimeContextInfo
{

	private final String TAG = "TIMEPLUGIN";
	
	int year;
	int month;
	int dayofthemonth;
	int houroftheday;
	int minuteofthehour;
	int secondoftheminute;
	int dayoftheweek;
	
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
		Log.d(TAG, "create Current Time");
		Date d = new Date();
		year=d.getYear()+1900;
		month=d.getMonth();
		dayofthemonth=d.getDate();
		houroftheday=d.getHours();
		minuteofthehour=d.getMinutes();
		secondoftheminute=d.getSeconds();
		dayoftheweek=d.getDay();
	}
	
	public CurrentSongContextInfo(Parcel in) 
	{
		year=in.readInt();
		month=in.readInt();
		dayofthemonth=in.readInt();
		houroftheday=in.readInt();
		minuteofthehour=in.readInt();
		secondoftheminute=in.readInt();
		dayoftheweek=in.readInt();
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
		out.writeInt(year);
		out.writeInt(month);
		out.writeInt(dayofthemonth);
		out.writeInt(houroftheday);
		out.writeInt(minuteofthehour);
		out.writeInt(secondoftheminute);
		out.writeInt(dayoftheweek);
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
			String smonth=""+month;
			String sday=""+dayofthemonth;
			String shour=""+houroftheday;
			String sminute=""+minuteofthehour;
			String ssecond=""+secondoftheminute;
			if(month<10)
			{
				smonth="0"+smonth;
			}
			if(dayofthemonth<10)
			{
				sday="0"+sday;
			}
			if(houroftheday<10)
			{
				shour="0"+shour;
			}
			if(minuteofthehour<10)
			{
				sminute="0"+sminute;
			}
			if(secondoftheminute<10)
			{
				ssecond="0"+ssecond;
			}
			return ""+year+"-"+smonth+"-"+sday+" "+shour+":"+sminute+":"+ssecond;
		}
		else if (format.equalsIgnoreCase("XML"))
		{
			return "<time><year>"+year+"</year></time>";
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
	public int dayOfMonth() 
	{
		return month;
	}

	@Override
	public int dayOfWeek() 
	{
		return dayoftheweek;
	}

	@Override
	public int dayOfYear() 
	{
		return 0;
	}

	@Override
	public int era() 
	{
		return 0;
	}

	@Override
	public int hourOfDay() 
	{
		return houroftheday;
	}

	@Override
	public int millisOfDay() 
	{
		return 0;
	}

	@Override
	public int millisOfSecond() 
	{
		return 0;
	}

	@Override
	public int minuteOfDay() 
	{
		if(houroftheday>0)
		{
			return minuteofthehour+(60*(houroftheday-1));
		}
		else
		{
			return minuteofthehour;
		}
	}

	@Override
	public int minuteOfHour() 
	{
		return minuteofthehour;
	}

	@Override
	public int monthOfYear() 
	{
		return month;
	}

	@Override
	public int secondOfDay() 
	{
		return 0;
	}

	@Override
	public int secondOfMinute() 
	{
		return secondoftheminute;
	}

	@Override
	public int weekOfWeekyear() 
	{
		return 0;
	}

	@Override
	public int year() 
	{
		return year;
	}

	@Override
	public int yearOfCentury() 
	{
		return year%100;
	}

	@Override
	public int yearOfEra() 
	{
		return 0;
	}

}