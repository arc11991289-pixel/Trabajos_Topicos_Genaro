package genaromendez.com.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gmendez on 31/05/17.
 */

public class Fragment_1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_1,container);


        return view;
    }
}
