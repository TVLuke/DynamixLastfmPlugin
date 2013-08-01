package org.ambientdynamix.contextplugins.lastfm;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.ambientdynamix.api.contextplugin.*;
import org.ambientdynamix.api.contextplugin.security.PrivacyRiskLevel;
import org.ambientdynamix.api.contextplugin.security.SecuredContextInfo;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class CurrentSongPluginRuntime extends AutoReactiveContextPluginRuntime
{
	private final static String TAG = "LSTFM PLUGIN";
	private static CurrentSongPluginRuntime context;

	@Override
	public void start() 
	{
		/*
		 * Nothing to do, since this is a pull plug-in... we're now waiting for context scan requests.
		 */
		context=this;
		Log.i(TAG, "Started!");
	}

	@Override
	public void stop() 
	{
		/*
		 * At this point, the plug-in should cancel any ongoing context scans, if there are any.
		 */
		Log.i(TAG, "Stopped!");
	}

	@Override
	public void destroy() 
	{
		/*
		 * At this point, the plug-in should release any resources.
		 */
		stop();
		Log.i(TAG, "Destroyed!");
	}

	@Override
	public void updateSettings(ContextPluginSettings settings) 
	{
		// Not supported
	}

	@Override
	public void handleContextRequest(UUID requestId, String contextInfoType) 
	{
		checkForCurrentSong("tvluke");
		SecuredContextInfo aci= new SecuredContextInfo(new CurrentSongContextInfo(), PrivacyRiskLevel.LOW);
		sendContextEvent(requestId, aci, 1000);
		context=this;
	}

	@Override
	public void handleConfiguredContextRequest(UUID requestId, String contextInfoType, Bundle scanConfig) 
	{
		handleContextRequest(requestId, contextInfoType);
		context=this;
	}

	@Override
	public void init(PowerScheme arg0, ContextPluginSettings arg1) throws Exception 
	{
		Log.d(TAG, "init");
		context=this;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPowerScheme(PowerScheme arg0) throws Exception 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doManualContextScan() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public static Song checkForCurrentSong(String uid)
	{
		Song x = null;
		
		String url="http://ws.audioscrobbler.com/2.0/?method=user.getrecenttracks&user="+uid+"&api_key="+Constants.API_KEY;
		final SAXBuilder builder = new SAXBuilder();
		try 
		{
			Document doc = builder.build(url);
			Element root = doc.getRootElement();
			List<Element> children = root.getChildren();
			Iterator<Element> childrenIterator = children.iterator();
			while(childrenIterator.hasNext())
            {
				Element child = childrenIterator.next(); 
                Log.d(TAG, ""+child.getName());
                List<Element> grandchildren = child.getChildren();
                Iterator<Element> grandchildrenIterator = grandchildren.iterator();
                boolean gotit=false;
                while(grandchildrenIterator.hasNext())
                {
                	Element grandchild = grandchildrenIterator.next();
                	if(grandchild.getName().equals("track") && grandchild.hasAttributes())
                	{
                		List<Element> ggclist = grandchild.getChildren();
                		Iterator<Element> ggcit = ggclist.iterator();
                		String artist="";
                		String name="";
                		while(ggcit.hasNext())
                		{
                			Element ggc = ggcit.next();
                			if(ggc.getName().equals("artist"))
                			{
                				artist=ggc.getText();
                			}
                			if(ggc.getName().equals("name"))
                			{
                				name=ggc.getText();
                			}
                			if(ggc.getName().equals("mbid"))
                			{
                				x = songinfo(ggc.getText());
                			}
                			if(ggc.getName().equals("album"))
                			{
                				if(x!=null)
                				{
                					
                				}
                				else
                				{
                					x = new Song(artist, name, 0, ggc.getText(), "");
                				}
                			}
                		}
                	}
                }
            }
		}
		catch (Exception e)
		{
			Log.e(TAG, "exception: "+e.getMessage());
		}
		return x;
	}

	private static Song songinfo(String mbid) 
	{
		Song x=null;
		String url = "http://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key="+Constants.API_KEY+"+&mbid="+mbid;
		String title="";
		String artist="";
		String album="";
		int duration=0;
		String tags="";
		final SAXBuilder builder = new SAXBuilder();
		try 
		{
			Document doc = builder.build(url);
			Element root = doc.getRootElement();
			List<Element> children = root.getChildren();
			Iterator<Element> childrenIterator = children.iterator();
			while(childrenIterator.hasNext())
            {
				Element child = childrenIterator.next(); 
                Log.d(TAG, ""+child.getName());
                List<Element> grandchildren = child.getChildren();
                Iterator<Element> grandchildrenIterator = grandchildren.iterator();
                while(grandchildrenIterator.hasNext())
                {
                	Element grandchild = grandchildrenIterator.next();
                	Log.d(TAG, ""+grandchild.getName());
                	if(grandchild.getName().equals("name"))
                	{
                		Log.d(TAG, grandchild.getText());
                		title=grandchild.getText();
                	}
                	if(grandchild.getName().equals("durartion"))
                	{
                		if(!grandchild.getText().equals(""))
                		{
                			duration = Integer.parseInt(grandchild.getText());
                		}
                	}
                	if(grandchild.getName().equals("artist"))
                	{
                		artist = grandchild.getChild("artist").getText();
                	}
                	if(grandchild.getName().equals("album"))
                	{
                		album = grandchild.getChild("title").getText();
                	}
                	if(grandchild.getName().equals("toptags"))
                	{
                		Log.d(TAG, "toptags...");
                	}
                }
            }
			x=new Song(title, artist, duration, album, tags);
		}
		catch (Exception e)
		{
			Log.e(TAG, "exception: "+e.getMessage());
		}
		return x;
	}

}