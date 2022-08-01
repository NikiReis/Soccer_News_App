package me.dio.soccernews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.dio.soccernews.R;
import me.dio.soccernews.databinding.NewsItemBinding;
import me.dio.soccernews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private final List<News> news;
    private FavoriteListener favoriteListener;

    public NewsAdapter(List<News> news, FavoriteListener favoriteListener){
        this.news = news;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvText.setText(news.description);
        Picasso.get().load(news.image).fit().into(holder.binding.imageView);

        // implementação da funcionalidade de abrir link
        holder.binding.btOpenLink.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.link));
            context.startActivity(i);
        });
        // implementação da funcionalidade de compartilhar
        holder.binding.imageView5.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, news.link);
            context.startActivity(Intent.createChooser(i,"Compartilhar via"));
        });

        // implementação da funcionalidade de favoritar
        holder.binding.imageView4.setOnClickListener(view -> {
            news.favorite = !news.favorite;
            this.favoriteListener.onFavorite(news);
            notifyItemChanged(position);
        });
        if(news.favorite){
            holder.binding.imageView4.setColorFilter(context.getResources().getColor(R.color.apply_fav_default));
        }else{
            holder.binding.imageView4.setColorFilter(context.getResources().getColor(R.color.fav_default_base));
        }
    }


    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

            public final NewsItemBinding binding;

            public ViewHolder(NewsItemBinding binding){
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    public interface FavoriteListener {
        void onFavorite(News news);
    }

}

