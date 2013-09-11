# Ambient Dynamix Last.fm Plug-in
This plug-in can be used to extract current and historical music-listening habits of a user. It uses the last.fm API and only works if the user has configured the plug-in 
with his or her user name and if he or she has a public last.fm profile.

The last.fm Plugin provides action as well as information data types. It can provide information on the currently playing song as well as on the music taste of a user. It is 
able to scrobble data (write data into the last.fm database of the user) which can be understood as an action in virtual space.

Plug-in ID: org.ambientdynamix.contextplugins.lastfm  

### Supported Context Type
While the data is somewhat private/personal the Risk level is classified as medium since all data is already publicly available and is only retrieved from the 
website after the user has configured his or her own user name for this purpose.

<table>
    <tr>
        <td>Context Types</td><td>Privacy Risk Level</td><td>Data Types</td><td>Description</td>
    </tr>
    <tr>
        <td>org.ambientdynamix.contextplugins.context.info.environment.currentsong</td><td>MEDIUM</td><td>CurrentSongContextInfo</td><td>Information about the currently playing song, artist, name and album.</td>
    </tr>
    <tr>
        <td>org.ambientdynamix.contextplugins.context.info.environment.musictaste</td><td>MEDIUM</td><td>MusicTasteContextInfo</td><td>Information about most listened to track, artists and favorite songs.</td>
    </tr>
    <tr>
        <td>org.ambientdynamix.contextplugins.context.action.data.scrobblesong</td><td>MEDIUM</td><td>ScrobbleContectAction</td><td>Allows to Scrobble Songs to last.fm</td>
    </tr>
</table>

###Use
The context can be requested and accesses the last.fm API to retrieve information.

###Native App Usage
Add context support as follows:

```Java
dynamix.addContextSupport(dynamixCallback, "org.ambientdynamix.contextplugins.context.info.environment.currentsong");
```

#### CurrentSong

the "current song"-context can be requested either with a configured or with an unconfigured request. If the request is configured a last.fm-user name can be send in a bundle to get the current song playing from that account. With an unconfigured request it requests the current song of the user him/her-self.

```Java
Bundle scanConfig = new Bundle();
scanconfig.putString("action_type", "current_song");
scanconfig.putString("user_name", "LastFmUserName");
dynamix.configuredContextRequest(dynamixCallback, "org.ambientdynamix.contextplugins.lastfm", "org.ambientdynamix.contextplugins.context.info.environment.currentsong", scanConfig);
```    

an unconfigured requests can be made with:

```Java
dynamix.contextRequest(dynamixCallback, "org.ambientdynamix.contextplugins.lastfm", "org.ambientdynamix.contextplugins.context.info.environment.currentsong");
```   

The data returned can be used as a POJO or returned in XML where it adheres to the provided XSD-File:
```XML
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
    targetNamespace="http://www.currentsong.environment.info.context.contextplugins.ambientdynamix.org"
    xmlns="http://www.http://www.currentsong.environment.info.context.contextplugins.ambientdynamix.org">
    
    <xs:element name="contextEvent">
		<xs:complexType>
            <xs:sequence>
            	<xs:element name="contextType" maxOccurs="unbounded" minOccurs="1">
            		<xs:complexType>
           				<xs:sequence>
           				    <xs:element name="id" type="xs:string" maxOccurs="1" minOccurs="1"/>
           				    <xs:element name="createdAt" type="xs:string" maxOccurs="1" minOccurs="1"/>
           				    <xs:element name="expires" type="xs:string" maxOccurs="1" minOccurs="1"/>
           				    <xs:element name="expiresAt" type="xs:string" maxOccurs="1" minOccurs="1"/>
           				    <xs:element name="source">
           				    	<xs:complexType>
            						<xs:sequence>
            							<xs:element name="plugin">
			           				    	<xs:complexType>
			            						<xs:sequence>
			            							   <xs:element name="pluginId" type="xs:string" maxOccurs="1" minOccurs="1"/>
			            							   <xs:element name="pluginName" type="xs:string" maxOccurs="1" minOccurs="1"/>
			            						</xs:sequence>
			           						</xs:complexType>
			           					</xs:element>
            						</xs:sequence>
           						</xs:complexType>
           					</xs:element>
           				</xs:sequence>
           			</xs:complexType>
            	</xs:element>
            	<xs:element name="contextData" maxOccurs="unbounded" minOccurs="1">
            		<xs:complexType>
           				<xs:sequence>
           				    <xs:element name="track">
			            		<xs:complexType>
			           				<xs:sequence>
			           				    <xs:element name="name" type="xs:string" maxOccurs="1" minOccurs="1"/>
			           				    <xs:element name="artist" type="xs:string" maxOccurs="1" minOccurs="1"/>
			           				    <xs:element name="duration" type="xs:string" maxOccurs="1" minOccurs="1"/>
			           				    <xs:element name="album" type="xs:string" maxOccurs="1" minOccurs="1"/>
			           				    <xs:element name="toptags">
						            		<xs:complexType>
						           				<xs:sequence>
						           				    <xs:element name="tag">
									            		<xs:complexType>
									           				<xs:sequence>
									           				    <xs:element name="name" type="xs:string" maxOccurs="1" minOccurs="1"/>
									           				</xs:sequence>
									           			</xs:complexType>
									            	</xs:element>
						           				</xs:sequence>
						           			</xs:complexType>
						            	</xs:element>
			           				</xs:sequence>
			           			</xs:complexType>
			            	</xs:element>
           				</xs:sequence>
           			</xs:complexType>
            	</xs:element>
			</xs:sequence>
		</xs:complexType>
	</xs:element>
</xs:schema>
```  
#### Music Taste

#### Scrobbeling


## Copyright
### Kudos
Plug-in for the Ambient Dynamix Framework. You can find Ambient Dynamix at http://ambientdynamix.org

Using the Dynamix Plugin Builder: https://bitbucket.org/ambientlabs/dynamix-plug-in-builder

Using the Dynamix Context Library: https://github.com/TVLuke/DynamixContextInterfaceLibrary
### Copyright Notice
Copyright (C) Institute of Telematics, Lukas Ruge

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
