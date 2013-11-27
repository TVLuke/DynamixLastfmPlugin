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

import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


public class LastFMPluginRuntime extends AutoReactiveContextPluginRuntime
{
	private final static String TAG = Constants.TAG;
	private static LastFMPluginRuntime context;
	public static String lastfmaccount="";
	public static ContextPluginSettings settings;

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
		Log.d(TAG, "context request 1");
		if(contextInfoType.equals("org.ambientdynamix.contextplugins.context.info.environment.currentsong"))
		{
			if(settings!=null)
			{
				Log.d(TAG, "prefs are not null x");
				String username = settings.get(Constants.USERNAME);
				Log.d(TAG, "username="+username);
				SecuredContextInfo aci= new SecuredContextInfo(new CurrentSongContextInfo(username), PrivacyRiskLevel.MEDIUM);
				Log.d(TAG, "---");
				sendContextEvent(requestId, aci, 180000);
				Log.d(TAG, "done with sending context");
			}
			else
			{
				Log.d(TAG, "prefs are null, this is not working...");
			}
		}
		if(contextInfoType.equals("org.ambientdynamix.contextplugins.context.info.taste.musictaste"))
		{
			if(settings!=null)
			{
				Log.d(TAG, "prefs are not null y");
				String username = settings.get(Constants.USERNAME);
				SecuredContextInfo aci= new SecuredContextInfo(new MusicTasteContextInfo(username), PrivacyRiskLevel.MEDIUM);
				sendContextEvent(requestId, aci, 1200000);
			}
			else
			{
				Log.d(TAG, "prefs are null, this is not working...");
			}
		}
		if(contextInfoType.equals("org.ambientdynamix.contextplugins.context.action.data.scrobblesong"))
		{

		}
		context=this;
	}

	@Override
	public void handleConfiguredContextRequest(UUID requestId, String contextInfoType, Bundle scanConfig) 
	{
		Log.d(TAG, "x");
		if(contextInfoType.equals("org.ambientdynamix.contextplugins.context.info.environment.currentsong"))
		{
			if(scanConfig.containsKey("action_type"))
			{
				String actiontype = scanConfig.getString("action_type");
				if(actiontype.equals("current_song"))
				{
					String username =Constants.USERNAME;
					if(scanConfig.containsKey("user_name"))
					{
						username = scanConfig.getString("user_name");
					}
					SecuredContextInfo aci= new SecuredContextInfo(new CurrentSongContextInfo(username), PrivacyRiskLevel.LOW);
					sendContextEvent(requestId, aci, 180000);
					context=this;
				}
			}
		}
		if(contextInfoType.equals("org.ambientdynamix.contextplugins.context.info.environment.musictaste"))
		{
			//TODO
		}
		if(contextInfoType.equals("org.ambientdynamix.contextplugins.context.action.data.scrobblesong"))
		{
			if(scanConfig.containsKey("action_type"))
			{
				String actiontype = scanConfig.getString("action_type");
				if(actiontype.equals("scrobble_song"))
				{
					if(settings!=null)
					{
						Log.d(TAG, "prefs are not null");
						String username = settings.get(Constants.USERNAME);
						String psw = settings.get(Constants.PSW);
						if(!psw.equals(""))
						{
							if(scanConfig.containsKey("song_name") && scanConfig.containsKey("artist_name"))
							{
								Log.d(TAG, "-->");
								String trackName =scanConfig.getString("song_name");
								String artistName =scanConfig.getString("artist_name");
								ScrobbleContextAction sca = new ScrobbleContextAction();
								sca.scrobble(trackName, artistName, username, psw);
							}
						}
					}
				}
			}
		
		}
		context=this;
	}

	@Override
	public void init(PowerScheme arg0, ContextPluginSettings arg1) throws Exception 
	{
		Log.d(TAG, "init 11");
		if(arg1!=null)
		{
			Log.d(TAG, "settings are not null and now get stored as a static variable");
			settings=  arg1;
			Log.d(TAG, "they are also stored via dynamix");
			getPluginFacade().storeContextPluginSettings(getSessionId(), settings);
		}
		else
		{
			Log.d(TAG, "settings given to this method are null");
			settings =  getPluginFacade().getContextPluginSettings(getSessionId());
			if(settings!=null)
			{
				Log.d(TAG, "ok that worked");
			}
			else
			{
				ContextPluginSettings s = new ContextPluginSettings();
				getPluginFacade().storeContextPluginSettings(getSessionId(), s);
				settings = getPluginFacade().getContextPluginSettings(getSessionId());
				if(settings!=null)
				{
					Log.d(TAG, "ok, third one is the charm I guess...");
				}
				else
				{
					Log.d(TAG, "the settings are still null");
				}
			}
		}
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
		Log.d(TAG, "checkforCurrentSong");
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
                //Log.d(TAG, ""+child.getName());
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
                					x = new Song(name, artist, -999, ggc.getText(), "", "");
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
		String id="";
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
                //Log.d(TAG, ""+child.getName());
                List<Element> grandchildren = child.getChildren();
                Iterator<Element> grandchildrenIterator = grandchildren.iterator();
                while(grandchildrenIterator.hasNext())
                {
                	Element grandchild = grandchildrenIterator.next();
                	//Log.d(TAG, ""+grandchild.getName());
                	if(grandchild.getName().equals("id"))
                	{
                		Log.d(TAG, grandchild.getText());
                		id=grandchild.getText();
                	}
                	if(grandchild.getName().equals("name"))
                	{
                		Log.d(TAG, grandchild.getText());
                		title=grandchild.getText();
                	}
                	if(grandchild.getName().equals("duration"))
                	{
                		if(!grandchild.getText().equals(""))
                		{
                			Log.d(TAG, grandchild.getText());
                			Log.d(TAG, "trytoparse");
                			duration = Integer.parseInt(grandchild.getText());
                			Log.d(TAG, "duration="+duration); 
                		}
                	}
                	if(grandchild.getName().equals("artist"))
                	{
                		Log.d(TAG, grandchild.getText());
                		artist = grandchild.getChild("name").getText();
                	}
                	if(grandchild.getName().equals("album"))
                	{
                		Log.d(TAG, grandchild.getText());
                		album = grandchild.getChild("title").getText();
                	}
                	if(grandchild.getName().equals("toptags"))
                	{
                		//Log.d(TAG, grandchild.getText());
                		Log.d(TAG, "toptags...");
                		List<Element> ggclist = grandchild.getChildren();
                		Iterator<Element> ggcit = ggclist.iterator();
                		while(ggcit.hasNext())
                		{
                			Element ggg = ggcit.next();
                			tags=tags+" "+ggg.getChild("name").getText();
                		}
                	}
                }
            }
			x=new Song(title, artist, duration, album, tags, id);
		}
		catch (Exception e)
		{
			Log.d(TAG, "-->");
			Log.e(TAG, "exception: "+e.getMessage());
		}
		return x;
	}
	
	public static ArrayList<Song> getTop100Tracks(String uid)
	{
		ArrayList<Song> top100tracks = new ArrayList<Song>();
		String url = "http://ws.audioscrobbler.com/2.0/?method=user.gettoptracks&user="+uid+"&api_key="+Constants.API_KEY+"&limit=100";
		final SAXBuilder builder = new SAXBuilder();
		try 
		{
			Log.d(TAG, "Try");
			Document doc = builder.build(url);
			Log.d(TAG, "2");
			Element root = doc.getRootElement();
			Log.d(TAG, "3");
			List<Element> children = root.getChildren();
			Log.d(TAG, "4");
			Iterator<Element> childrenIterator = children.iterator();
			Log.d(TAG, "5");
			while(childrenIterator.hasNext())
            {
				Element child = childrenIterator.next(); 
                Log.d(TAG, ""+child.getName());
                List<Element> grandchildren = child.getChildren();
    			Log.d(TAG, "6");
                Iterator<Element> grandchildrenIterator = grandchildren.iterator();
                while(grandchildrenIterator.hasNext())
                {
        			Log.d(TAG, "7");
                	Element grandchild = grandchildrenIterator.next();
                	if(grandchild.getName().equals("track") && grandchild.hasAttributes())
                	{
                		List<Element> ggclist = grandchild.getChildren();
            			Log.d(TAG, "8");
                		Iterator<Element> ggcit = ggclist.iterator();
            			Log.d(TAG, "9");
                		String artist="";
                		String name="";
                		int playcount=0;
            			Log.d(TAG, "10");
                		while(ggcit.hasNext())
                		{
                			Element ggc = ggcit.next();
                			if(ggc.getName().equals("artist"))
                			{
                				Log.d(TAG, "11");
                				artist=ggc.getChild("name").getText();
                				Log.d(TAG, "Artist="+artist);
                				top100tracks.add(new Song(name, artist, -999, ggc.getText(), "", playcount, ""));
                			}
                			if(ggc.getName().equals("name"))
                			{
                				Log.d(TAG, "12");
                				name=ggc.getText();
                			}
                			if(ggc.getName().equals("playcount"))
                			{
                				Log.d(TAG, "14 "+ggc.getText());
                				playcount=Integer.parseInt(ggc.getText());
                			}
                		}
                	}
                }
            }
		}
		catch (Exception e)
		{
			Log.d(TAG, "-->");
			Log.e(TAG, "exception: "+e.getMessage());
		}
		return top100tracks;
	}

}