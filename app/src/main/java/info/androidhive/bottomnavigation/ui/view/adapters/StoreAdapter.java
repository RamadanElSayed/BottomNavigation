package info.androidhive.bottomnavigation.ui.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.networkmodules.moviemodels.Movie;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
    private Context context;
    private List<Movie> movieList;

    class MyViewHolder extends RecyclerView.ViewHolder {
       private TextView name, price;
        private ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.title);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }


    public StoreAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie movie = movieList.get(position);
        holder.name.setText(movie.getTitle());
        holder.price.setText(movie.getPrice());

        Glide.with(context)
                .load(movie.getImage())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}

