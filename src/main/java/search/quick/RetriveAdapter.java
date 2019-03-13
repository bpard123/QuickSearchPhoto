package search.quick;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 13-03-2019.
 */

public class RetriveAdapter extends RecyclerView.Adapter<RetriveAdapter.MyViewholder> {

    List<RetrieveImageModel> list;
    Context context;
    int pos;
    int h;

    public RetriveAdapter(List<RetrieveImageModel> list, Context context,int pos) {
        this.list = list;
        this.context = context;
        this.pos=pos;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        MyViewholder viewHolder = new MyViewholder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {

        RetrieveImageModel listItem=list.get(position);

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
        holder.imageView.setImageBitmap(Bitmap.createScaledBitmap(listItem.getBitmap(), h,
                h, false));



    }

    @Override
    public int getItemCount() {
        return list.size();
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
}
