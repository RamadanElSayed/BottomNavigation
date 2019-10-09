package info.androidhive.bottomnavigation.ui.view.fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.helper.GridSpacingItemDecoration;
import info.androidhive.bottomnavigation.networkmodules.MovieService;
import info.androidhive.bottomnavigation.networkmodules.NetworkModule;
import info.androidhive.bottomnavigation.networkmodules.moviemodels.Movie;
import info.androidhive.bottomnavigation.ui.presenter.StorePresenterImpl;
import info.androidhive.bottomnavigation.ui.view.adapters.StoreAdapter;
import info.androidhive.bottomnavigation.ui.view.interfaces.StoreView;

public class StoreFragment extends Fragment implements StoreView {

    private static final String TAG = StoreFragment.class.getSimpleName();

    private MovieService movieService;
    // url to fetch shopping items
    private static final String URL = "https://api.androidhive.info/json/movies_2017.json";

    private RecyclerView recyclerView;
    private List<Movie> itemsList;
    private StoreAdapter mAdapter;
    private StorePresenterImpl storePresenter;

    public StoreFragment() {
        // Required empty public constructor
    }

    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        itemsList = new ArrayList<>();
        movieService = NetworkModule.provideRetrofit().create(MovieService.class);
        storePresenter = new StorePresenterImpl(getContext(), this);
        mAdapter = new StoreAdapter(getActivity(), itemsList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storePresenter.getMovieList(URL);
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void setMovieList(List<Movie> items) {
        itemsList.clear();
        itemsList.addAll(items);

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
    }

}
