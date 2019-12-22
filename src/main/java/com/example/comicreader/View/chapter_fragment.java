package com.example.comicreader.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.comicreader.Model.Manga;
import com.example.comicreader.Presenter.Presenter;
import com.example.comicreader.R;

import java.util.ArrayList;
public class chapter_fragment extends Fragment implements View.OnClickListener{
    private static chapter_fragment chapter_fragment;
    private Presenter presenter;
    private Context context;
    private img_adapter adapter;
    private ArrayList<Manga> mangalist;
    private ArrayList<String> img_manga;
    private TextView tv_chapter,tv_chapterText;
    private ListView lv_list_chapter;
    private Button back;
    private String chapter_num;

    //khởi tạo nội dung trang tạo chapter
    public static chapter_fragment createChapterScreen(Context context,ArrayList<Manga> mangalist, Presenter presenter){
        if(chapter_fragment == null){
            chapter_fragment = new chapter_fragment();
            chapter_fragment.context = context;
            chapter_fragment.mangalist = mangalist;
            chapter_fragment.presenter = presenter;
        }
        Animatoo.animateSpin(chapter_fragment.context);
        return chapter_fragment;
    }

    public chapter_fragment() {
        // Required empty public constructor
    }

    @Override
    //khởi tạo create view
    //LayoutInflater inflater dùng thay đổi chế độ view với bất kỳ fragment
    //ViewGroup container chế độ xem
    //Bundle savedInstanceState xd trạng thái đã luu trước
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_list_chapter, container, false);

        this.img_manga = presenter.getImg_manga();
        this.chapter_num = presenter.getChapter_numb();

        this.tv_chapter = view.findViewById(R.id.tv_chapter);
        this.tv_chapterText = view.findViewById(R.id.tv_chapterText);
        this.lv_list_chapter = view.findViewById(R.id.lv_list_chapter);
        this.back = view.findViewById(R.id.b_back_dariChapter);
        this.back.setOnClickListener(this);

        this.tv_chapterText.setText(this.chapter_num);
        this.adapter = new img_adapter(this.img_manga,context);
        this.lv_list_chapter.setAdapter(this.adapter);

        return view;
    }
    //quay về trang thông tin
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.b_back_dariChapter:
                presenter.changePage(2);
                break;
        }
    }
}
