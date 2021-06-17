package com.app.vector.vectores.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.vector.vectores.R;

public class HelpFragment extends Fragment {
    private static final String ARG_PARAM1 = "text";
    private static final String ARG_PARAM2 = "imageResource";

    private String mText;
    private int mImageResource;

    public HelpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param text Parameter 1.
     * @param imageResource Parameter 2.
     * @return A new instance of fragment HelpFragment.
     */
    public static HelpFragment newInstance(String text, int imageResource) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, text);
        args.putInt(ARG_PARAM2, imageResource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString(ARG_PARAM1);
            mImageResource = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
        TextView tvText = (TextView) view.findViewById(R.id.tvText);
        tvText.setText(mText);
        ivImage.setImageResource(mImageResource);
        return view;
    }
}
