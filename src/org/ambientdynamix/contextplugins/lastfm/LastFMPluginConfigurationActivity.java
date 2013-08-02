package org.ambientdynamix.contextplugins.lastfm;

import org.ambientdynamix.api.contextplugin.ContextPluginRuntime;
import org.ambientdynamix.api.contextplugin.IContextPluginConfigurationViewFactory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LastFMPluginConfigurationActivity extends Activity implements IContextPluginConfigurationViewFactory
{

	LinearLayout rootLayout;
	private final static String TAG = "LSTFM PLUGIN";
	private Context ctx;
	Activity activity;
	SharedPreferences prefs =null;
	
	@Override
	public void destroyView() throws Exception 
	{
		
		
	}

	@Override
	public View initializeView(Context context, final ContextPluginRuntime arg1, int arg2) throws Exception 
	{

		Log.i(TAG, "version 5.0");
		ctx=context;
		activity=this;
		// Discover our screen size for proper formatting 
		DisplayMetrics met = context.getResources().getDisplayMetrics();
		
		// Access our Locale via the incoming context's resource configuration to determine language
		String language = context.getResources().getConfiguration().locale.getDisplayLanguage();
		
        TextView text = new TextView(ctx);
        text.setText("Username");
        
        final EditText username = new EditText(ctx);
        prefs = context.getSharedPreferences(Constants.PREFS, 0);
        if(prefs!=null)
        {
        	String u = prefs.getString(Constants.USERNAME, "");
        	username.setText(u);
        }
        else
        {
        	Log.d(TAG, "prefs are null. second try:");
        	prefs = arg1.getSecuredContext().getSharedPreferences(Constants.PREFS, 0);
            if(prefs!=null)
            {
            	String u = prefs.getString(Constants.USERNAME, "");
            	username.setText(u);
            }
            else
            {
            	Log.d(TAG, "prefs are still null, third try:");
            	try
            	{
            		prefs= this.getSharedPreferences(Constants.PREFS, 0);
            	}
            	catch(Exception e)
            	{
            		Log.e(TAG, "error "+e.getMessage());
            	}
            	if(prefs!=null)
                {
                	String u = prefs.getString(Constants.USERNAME, "");
                	username.setText(u);
                }
                else
                {
                	prefs = LastFMPluginRuntime.prefs;
                	if(prefs!=null)
                    {
                    	String u = prefs.getString(Constants.USERNAME, "");
                    	username.setText(u);
                    }
                    else
                    {
                    	Log.d(TAG, "I am out of options here...");
                    }
                }
            }
        	
        }
        
        Button b2 = new Button(context);
       	b2.setText("Save");
        b2.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v)
            {
            	String x = username.getEditableText().toString();
            	if(prefs!=null)
            	{
            		Editor edit = prefs.edit();
            		edit.putString(Constants.USERNAME, x);
            		edit.commit();
            	}
            	//TODO: finish()
            	arg1.getPluginFacade().setPluginConfiguredStatus(arg1.getSessionId(), true);
            	activity.finish();
            }
        });
        
		// Main layout. 
		rootLayout = new LinearLayout(context);
		rootLayout.setOrientation(LinearLayout.VERTICAL);
		
	       rootLayout.addView(text,  new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
	        		FrameLayout.LayoutParams.WRAP_CONTENT));
	 
	       rootLayout.addView(username,  new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
	        		FrameLayout.LayoutParams.WRAP_CONTENT));
	 
	       rootLayout.addView(b2,  new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
	        		FrameLayout.LayoutParams.WRAP_CONTENT));
	 
		return rootLayout;
  
	}

}
