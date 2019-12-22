package com.example.comicreader.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.comicreader.Model.Chapter;
import com.example.comicreader.R;

import java.util.ArrayList;

//tạo class cho chapter_adapter
//baseAdapter giúp nối giữa giao diện người dùng với dữ liệu
public class chapter_adapter extends BaseAdapter {
    ArrayList<Chapter> chapter = new ArrayList<>();
    Context context;

    public chapter_adapter(ArrayList<Chapter> chapter, Context context){
        this.chapter = chapter;
        this.context = context;
    }


    @Override
    public int getCount() {
        return chapter.size();
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

    // tạo và gắn data vào view trước khi add và listview(thuận lợi cho viêc scrollview
    //position : vị trí của item trong listview
    //view : là đối tượng cache view
    //ViewGroup đối tượng listview
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.chapter_row,null,true);

        TextView number_chapter = view.findViewById(R.id.tv_chapternumber);

        Chapter eachChap = this.chapter.get(position);

        number_chapter.setText(eachChap.getNumber()+"");
        return view;
    }
}
