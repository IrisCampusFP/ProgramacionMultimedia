package com.example.calculadora_irisperez;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageFragment extends Fragment {

    private ImageView imageView;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_image, container, false);

        imageView = v.findViewById(R.id.imageView);

        return v;
    }

    public void ChangeImageAlpha(int alpha) {
        // seekbar progress va desde 0 hasta 100 mientras que image alpha va de 0 a 255
        int valor = alpha * 255 / 100;
        imageView.setImageAlpha(valor);
    }
}