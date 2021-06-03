package com.examples.restaurantmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SearchActivity extends Activity {

    SuggestionSearch suggestionSearch = SuggestionSearch.newInstance();

    private final List<SuggestionResult.SuggestionInfo> list = new ArrayList<>();
    SearchAdapter adapter = new SearchAdapter(list);
    RecyclerView recycler;
    AppCompatEditText search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recycler = findViewById(R.id.recycler);
        search = findViewById(R.id.search);


        initView();
        listener();
    }


    public void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        search.setFocusable(true);
        search.setFocusableInTouchMode(true);
        search.requestFocus();

        adapter.setListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void listener(SuggestionResult.SuggestionInfo info) {

                Intent intent = new Intent();
                intent.putExtra("key", info);
                setResult(1, intent);
                finish();
            }
        });
    }

    public void listener() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                suggestionSearch.requestSuggestion(new SuggestionSearchOption().city(s.toString()).keyword(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        OnGetSuggestionResultListener listener = suggestionResult -> {
            List<SuggestionResult.SuggestionInfo> allSuggestions = suggestionResult.getAllSuggestions();
            if (allSuggestions == null || allSuggestions.size() <= 0) {
                return;
            }
            Iterator<SuggestionResult.SuggestionInfo> iterator = allSuggestions.iterator();
            while (iterator.hasNext()) {
                SuggestionResult.SuggestionInfo next = iterator.next();
                if (next.address == null || next.address.isEmpty()) {
                    iterator.remove();
                }
            }
            list.clear();
            list.addAll(allSuggestions);
            adapter.notifyDataSetChanged();
        };

        suggestionSearch.setOnGetSuggestionResultListener(listener);
    }

    public void back(View view) {
        Intent intent = new Intent();
//        intent.putExtra("key", info);
        setResult(1, intent);
        finish();
    }
}
