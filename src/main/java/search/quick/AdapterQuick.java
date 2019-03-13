package search.quick;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Asus on 10-03-2019.
 */

public class AdapterQuick extends RecyclerView.Adapter<AdapterQuick.MyViewholder> {

    public  static List<Model> list;
    Context context;
    int pos;
    public static Boolean getClicked=false;
    ViewGroup.LayoutParams layoutParams;
    DatabaseHelper dbHandler;

    int gvWidth;
    int h;
    String tag;



    public AdapterQuick(List<Model> list,Context context,int pos,String tag) {
        this.list=list;
        this.context = context;
        this.pos=pos;
        this.tag=tag;
        dbHandler=new DatabaseHelper(this.context);

    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        MyViewholder viewHolder = new MyViewholder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {

        final Model listItem=list.get(position);
        String imageURL="https://farm" +listItem.getFarm_id()+
                ".staticflickr.com/" +listItem.getServ_id()+
                "/" +
                listItem.getId()+
                "_" +
                listItem.getSecret()+
                ".jpg";

        ImageUrlInfo.imageUrls.add(imageURL);
        DownloadThread downloadThread=new DownloadThread(){
            @Override
            protected void onPostExecute(byte[] bytes) {
                //Toast.makeText(context,"Downloaded",Toast.LENGTH_LONG).show();
                dbHandler.insertPhoto(bytes,tag);
            }
        };
        downloadThread.execute(imageURL);

        RelativeLayout relativeLayout=holder.getRl();

        if(pos==2)
        {
            h=300;
        }
        if(pos==3)
        {
            h=200;

        }
        if(pos==4)
        {
            h=200;

        }
        relativeLayout.getLayoutParams().height=h;
        Picasso.get().load(imageURL).resize(h,h).into(holder.imageView);

    }
    public class MyViewholder extends RecyclerView.ViewHolder
    {
        private ImageView imageView;
        private RelativeLayout rl;
        private View v;

        public MyViewholder(View itemView) {
            super(itemView);
            this.v=itemView;
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            rl=itemView.findViewById(R.id.rl);

        }

        public RelativeLayout getRl() {
            return rl;
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setGvWidth(int w){
        this.gvWidth=w;

    }
}
