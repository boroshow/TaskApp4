package kg.geektech.taskapp35;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kg.geektech.taskapp.R;

public class FragmentOther extends Fragment {

    private TextView textView1, textView2;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView1 = view.findViewById(R.id.textOther);
        textView2 = view.findViewById(R.id.textWatche);

        Bundle bundle = new Bundle();
//        bundle.getString("text_key");
//        Toast.makeText(requireContext(), getArguments().getString("text_key"), Toast.LENGTH_SHORT).show();
        textView1.setText(bundle.getString("text_key"));
    }
}