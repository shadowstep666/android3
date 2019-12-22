package com.example.comicreader.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.comicreader.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

//khai báo img_adapter
public class img_adapter extends BaseAdapter {
    ArrayList<String> img_manga;
    Context context;

    public img_adapter(ArrayList<String> img_manga, Context context){
        this.img_manga = img_manga;
        this.context = context;
    }

    @Override
    public int getCount() {
        return img_manga.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    //tạo và  gắn ảnh vào trước khi add hoặc listview
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.fragment_isi_list_chapter,null,true);

        ImageView gambar = view.findViewById(R.id.iv_gambar);

        String eachGambar = this.img_manga.get(position);
        if(!eachGambar.equals("null")){
            Glide.with(context).load("https://cdn.mangaeden.com/mangasimg/200x/"+eachGambar).into(gambar);
        }
        else{
            Glide.with(context).load(R.drawable.noimage).into(gambar);
        }
        return view;
    }
}
