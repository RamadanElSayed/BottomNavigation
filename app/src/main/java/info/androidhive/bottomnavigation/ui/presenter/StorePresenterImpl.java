package info.androidhive.bottomnavigation.ui.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import info.androidhive.bottomnavigation.app.MyApplication;
import info.androidhive.bottomnavigation.networkmodules.moviemodels.Movie;
import info.androidhive.bottomnavigation.ui.view.interfaces.StoreView;

public class StorePresenterImpl implements StorePresenter {

    private StoreView view;
    private Context mContext;

    public StorePresenterImpl(Context context, StoreView storeView) {
        mContext = context;
        view = storeView;

    }

    @Override
    public void getMovieList(String baseURL) {

        JsonArrayRequest request = new JsonArrayRequest(baseURL,
                response -> {
                    if (response == null) {
                        Toast.makeText(mContext, "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    List<Movie> items = new Gson().fromJson(response.toString(), new TypeToken<List<Movie>>() {
                    }.getType());
                    view.setMovieList(items);
                }, error -> {
            // error in getting json

            Toast.makeText(mContext, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }
}
