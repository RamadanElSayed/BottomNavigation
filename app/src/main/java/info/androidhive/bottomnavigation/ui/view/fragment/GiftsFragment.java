package info.androidhive.bottomnavigation.ui.view.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.bottomnavigation.R;


public class GiftsFragment extends Fragment {

    public GiftsFragment() {
        // Required empty public constructor
    }

    public static GiftsFragment newInstance(String param1, String param2) {
        GiftsFragment fragment = new GiftsFragment();
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
        return inflater.inflate(R.layout.fragment_gifts, container, false);
    }

}
