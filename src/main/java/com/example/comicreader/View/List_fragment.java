package com.example.comicreader.View;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.comicreader.Model.Chapter;
import com.example.comicreader.Model.Manga;
import com.example.comicreader.Presenter.Presenter;
import com.example.comicreader.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class List_fragment extends Fragment implements AdapterView.OnItemClickListener {
    FragmentManager manager;
    FragmentTransaction ft;
    public Presenter presenter;
    private static List_fragment listFragment;
    private Context context;
    private ListView lvManga;
    private static final String JSON_URL = "https://www.mangaeden.com/api/list/0/";
    public ArrayList<Manga> mangaList;
    private EditText et_search;
    public list_adapter adapter;
    public View view;

    public boolean closeKeyboard = false;

    public List_fragment() {
        // Required empty public constructor
    }

    public static List_fragment createHomeScreen(Context context, ArrayList<Manga> mangaList,Presenter presenter){
        if(listFragment==null){
            listFragment = new List_fragment();
            listFragment.context = context;
            listFragment.mangaList = (ArrayList<Manga>) mangaList.clone();
            listFragment.presenter = presenter;
        }
        Animatoo.animateSplit(listFragment.context);
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.et_search = view.findViewById(R.id.et_search);
        this.lvManga = view.findViewById(R.id.my_list);
        this.getMangaList();
        this.lvManga.setOnItemClickListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = et_search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.searchMangabyTitle(input);
            }
        });

        return view;
    }

    public void getMangaList() {
        //lấy dữ liệu get request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            //lấy dữ liệu phản hồi
                            JSONObject obj = new JSONObject(response);
                            JSONArray mangalist = obj.getJSONArray("manga");
                            //lấy các liên kết
                            for (int i = 0; i < mangalist.length(); i++) {

                                JSONObject mangaReader = mangalist.getJSONObject(i);


                                Manga manga = new Manga(mangaReader.getString("i"),
                                        mangaReader.getString("t"),
                                        mangaReader.getString("a"),
                                        mangaReader.getString("c"),
                                        mangaReader.getString("s"),
                                        mangaReader.getString("h"),
                                        mangaReader.getString("im"));
                                //thêm các liên kết vào mângList
                                mangaList.add(manga);
                            }
                            adapter = new list_adapter(mangaList,context);
                            lvManga.setAdapter(adapter);
//                            sendList(mangaList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
        final int pos = position;
        final Manga mangaClick = mangaList.get(position);
        String idManga = mangaClick.getId();
        String url = "https://www.mangaeden.com/api/manga/"+idManga+"/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String deskripsi = obj.getString("description");
                    String author = obj.getString("author");
                    String chapter_length = obj.getString("chapters_len");
                    JSONArray arrayChapter = obj.getJSONArray("chapters");
                    ArrayList<Chapter> arr = new ArrayList<>();
                    String id = "";
                    int num = 0;
                    for (int i = 0; i < arrayChapter.length(); i++) {
                        JSONArray eachChap = arrayChapter.getJSONArray(i);
                        id = eachChap.getString(3);
                        num = eachChap.getInt(0);
                        Chapter newChap = new Chapter(id,num);
                        arr.add(newChap);
                    }
                    mangaClick.setAuthor(author);
                    mangaClick.setSummary(deskripsi);
                    mangaClick.setChapter_length(chapter_length);
                    mangaClick.setChapter(arr);
                    presenter.setManga(mangaClick);
                    presenter.setPosition(position);
                    presenter.setChap(arr);
                    sendPage(2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.add(stringRequest);
    }

    public void sendPage(int id){
        this.presenter.changePage(id);
    }
}
