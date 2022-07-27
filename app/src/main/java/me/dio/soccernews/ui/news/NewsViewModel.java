package me.dio.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import me.dio.soccernews.domain.News;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();

        // TODO: REMOVER MOCK DE NOTÍCIAS
        List<News> news = new ArrayList<>();
        news.add(new News("Ferroviária Tem...","Brasileirao"));
        news.add(new News("Man City joga...","Premier League"));
        news.add(new News("Copa do Mundo 2022..","World Cup"));

        this.news.setValue(news);
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}