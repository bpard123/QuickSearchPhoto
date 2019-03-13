package search.quick;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Asus on 10-03-2019.
 */

public class MySingleTonRequestQueue {
    //Variable Declarations

    private static MySingleTonRequestQueue mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    //Private Constructor
    private MySingleTonRequestQueue(Context context)
    {
        mCtx=context;
        requestQueue=getRequestQueue();
    }

    //Public Method Calling Private Constructor
    public static synchronized MySingleTonRequestQueue getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new MySingleTonRequestQueue(context);
        }
        return mInstance;
    }

    //Getting the Request Queue
    public RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
        {
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }
    //Adding to the Request Queue
    public<T> void addToRequestQueue(Request<T> request)
    {
        requestQueue.add(request);
    }
}
