package com.example.comicreader.View;


import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.comicreader.Model.Chapter;
import com.example.comicreader.Model.Manga;
import com.example.comicreader.Presenter.Presenter;
import com.example.comicreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class info_fragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private static info_fragment info_fragment;
    private static final String BASE_URL = "https://www.mangaeden.com/api/manga/";
    private Presenter presenter;
    private Context context;
    private ArrayList<Manga> mangaList;
    private ArrayList<Chapter> chapters;
    private Manga mangaClick;
    private int position;
    ImageView poster;
    TextView title;
    TextView description;
    TextView chapter_length;
    ListView list_chapter;
    Button back;

    public info_fragment() {
        // Required empty public constructor
    }

    public static info_fragment createInfoScreen(Context context, ArrayList<Manga> mangaList, Presenter presenter){
        if(info_fragment==null){
            info_fragment = new info_fragment();
            info_fragment.context = context;
            info_fragment.mangaList = (ArrayList<Manga>)mangaList;
            info_fragment.presenter = presenter;
        }
        Animatoo.animateZoom(info_fragment.context);
        return info_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info_fragment, container, false);

        this.mangaClick = presenter.getManga();
        this.position = presenter.getPosition();
        this.chapters = presenter.getChap();

        this.poster = view.findViewById(R.id.iv_posterInfo);
        this.title = view.findViewById(R.id.tv_infotitletext);
        this.description = view.findViewById(R.id.tv_descinfotext);
        this.description.setMovementMethod(new ScrollingMovementMethod());
        this.chapter_length = view.findViewById(R.id.tv_chapterinfotext);
        this.list_chapter = view.findViewById(R.id.list_chapter);
        this.back = view.findViewById(R.id.b_back_dariInfo);
        this.back.setOnClickListener(this);

        chapter_adapter adapter = new chapter_adapter(this.chapters,context);

        this.cretePage(mangaClick,position);
        this.list_chapter.setAdapter(adapter);

        this.list_chapter.setOnItemClickListener(this);


        return view;
    }
    //khỏi tạo trang
    public void cretePage(Manga manga,int position){
        mangaClick = manga;
        if(!manga.getImage().equals("null")){
            //lấy hình ảnh về từ trên API
            Glide.with(this).load("https://cdn.mangaeden.com/mangasimg/200x/" + manga.getImage()).into(poster);
        }
        else{
            //không có ảnh  trên API
            poster.setImageResource(R.drawable.noimage);
        }

        //têm manga , tóm tắt nội dung , số chapter
        title.setText(manga.getTitle());
        description.setText(manga.getSummary());
        chapter_length.setText(manga.getChapter_length());
    }

    @Override
    //bắt sự kiện chọn vào chapter
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Chapter nowChap = chapters.get(position);
        //địa chỉ API cần trả về theo hàng đã ấn (id)
        String url = "https://www.mangaeden.com/api/chapter/"+ nowChap.getId()+"/";
        //phân tích chuỗi JSon trả về
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray imgURL = obj.getJSONArray("images");

                    ArrayList<String> img_manga = new ArrayList<>();
                    for (int i = imgURL.length() - 1; i >= 0; i--) {
                        JSONArray t = imgURL.getJSONArray(i);
                        String temp = t.getString(1);
                        img_manga.add(temp);
                    }
                    presenter.setChapter_numb(nowChap.getNumber()+"");
                    presenter.setImg_manga(img_manga);
                    presenter.changePage(3);
                } catch (Exception e) {
                    //chuẩn đoán ngoại lệ cho biết những gì trong đoạn mã đã xảy  ra
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            //phân biệt các lỗi phản hồi mạng
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.add(stringRequest);
    }

    //trở về trang đầu nếu ấn back
    public void onClick(View view) {
        if(view.getId() == this.back.getId()){
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            presenter.changePage(1);
        }
    }

}
