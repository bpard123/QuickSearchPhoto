package search.quick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuickActivity extends AppCompatActivity {
    public static Spinner spinner;
    EditText search_item;
    Button search_btn;
    String search="";
    String farm_id="";
    String servId="";
    String id="";
    String secret="";
    ImageView imageView;
    ArrayList<Model> image_info=new ArrayList<>();
    RecyclerView recyclerView;
    AdapterQuick adapter;
    DatabaseHelper dbHandler;


    String url="https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=34e79c04fbaa1477bb1cbbc1e9fd285d" +
            "&format=json&nojsoncallback=1&text=" +search+
            "&page=6";
    String imageURL="https://farm" +farm_id+
            ".staticflickr.com/" +servId+
            "/" +
            id+
            "_" +
            secret+
            ".jpg";
    ArrayList<String> items;
    ArrayList<RetrieveImageModel> retrieveImageModels;
    RetriveAdapter retriveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        search_btn=(Button)findViewById(R.id.button);
        search_item=(EditText)findViewById(R.id.search_item);

        spinner=(Spinner)findViewById(R.id.options_spinner);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        retrieveImageModels=new ArrayList<>();
        //Toast.makeText(getApplicationContext(),""+recyclerView.getLayoutParams().width,Toast.LENGTH_LONG).show();

        items=new ArrayList<>();
        dbHandler=new DatabaseHelper(this);



        items.add(0,"2");
        items.add(1,"3");
        items.add(2,"4");
        final ArrayAdapter<String> spinner_item=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spinner.setAdapter(spinner_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner.getSelectedItem().toString().matches("2"))
                {
                    RecyclerView.LayoutManager gr=new GridLayoutManager(QuickActivity.this,2);


                    recyclerView.setLayoutManager(gr);
                    if(!isNetworkAvailable())
                    {
                        retriveAdapter=new RetriveAdapter(retrieveImageModels,QuickActivity.this,2);
                        recyclerView.setAdapter(retriveAdapter);
                    }
                    else
                    {
                        adapter=new AdapterQuick(image_info,QuickActivity.this,2,search_item.getText().toString());

                        recyclerView.setAdapter(adapter);
                    }





                }
                if(spinner.getSelectedItem().toString().matches("3"))
                {

                    RecyclerView.LayoutManager gr=new GridLayoutManager(QuickActivity.this,3);

                    recyclerView.setLayoutManager(gr);

                    if(!isNetworkAvailable())
                    {
                        retriveAdapter=new RetriveAdapter(retrieveImageModels,QuickActivity.this,3);
                        recyclerView.setAdapter(retriveAdapter);
                    }
                    else
                    {
                        adapter=new AdapterQuick(image_info,QuickActivity.this,3,search_item.getText().toString());
                        recyclerView.setAdapter(adapter);
                    }




                }
                if(spinner.getSelectedItem().toString().matches("4"))
                {
                    RecyclerView.LayoutManager gr=new GridLayoutManager(QuickActivity.this,4);
                    //RecyclerView.LayoutManager gr = new LinearLayoutManager(QuickActivity.this);
                    recyclerView.setLayoutManager(gr);
                    if(!isNetworkAvailable())
                    {
                        retriveAdapter=new RetriveAdapter(retrieveImageModels,QuickActivity.this,4);
                        recyclerView.setAdapter(retriveAdapter);
                    }
                    else
                    {
                        adapter=new AdapterQuick(image_info,QuickActivity.this,4,search_item.getText().toString());
                        recyclerView.setAdapter(adapter);
                    }






                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_item.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Empty Field",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (!isNetworkAvailable()) {
                        retrieveFromDB();
                    } else {
                        search = search_item.getText().toString();
                        image_info.clear();
                        RecyclerView.LayoutManager gr = new GridLayoutManager(QuickActivity.this, Integer.parseInt(spinner.getSelectedItem().toString()));
                        //RecyclerView.LayoutManager gr = new LinearLayoutManager(QuickActivity.this);
                        recyclerView.setLayoutManager(gr);
                        url = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=ad8c44dd52beec896d0a7140c7b72527&format=json&nojsoncallback=1&text=" + search +
                                "&page=1";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //Toast.makeText(getApplicationContext(),"HII "+response,Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("photos");
                                    JSONArray jsonArray = jsonObject1.getJSONArray("photo");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                                        farm_id = jsonObject2.getString("farm");
                                        servId = jsonObject2.getString("server");
                                        secret = jsonObject2.getString("secret");
                                        id = jsonObject2.getString("id");
                                        image_info.add(i, new Model(farm_id, id, secret, servId));
                                        // Log.d("IMAge URL ", imageURL);


                                    }
                                    adapter = new AdapterQuick(image_info, QuickActivity.this, Integer.parseInt(spinner.getSelectedItem().toString()), search);
                                    recyclerView.setAdapter(adapter);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });

                        MySingleTonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    }
                }

            }
        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void retrieveFromDB(){
        ArrayList<byte[]> listUrlBytes=new ArrayList<>();


        listUrlBytes=dbHandler.retrieveImage(search_item.getText().toString());
        Toast.makeText(getApplicationContext(),"No Network",Toast.LENGTH_SHORT).show();

        if(listUrlBytes.size()==0)
        {
            Toast.makeText(getApplicationContext(),"No Search Performed Yet",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int j=0;
            for (int i=0;i<listUrlBytes.size();i++)
            {
                byte[] file= listUrlBytes.get(i);
                if(!(file==null))
                {
                    Bitmap bmp = BitmapFactory.decodeByteArray(file, 0, file.length);
                    Log.d("tag",bmp.toString());

                    retrieveImageModels.add(j,new RetrieveImageModel(bmp));
                    j++;
                }


            }
            retriveAdapter=new RetriveAdapter(retrieveImageModels,getApplicationContext(),Integer.parseInt(spinner.getSelectedItem().toString()));
            recyclerView.setAdapter(retriveAdapter);
        }



    }
}
