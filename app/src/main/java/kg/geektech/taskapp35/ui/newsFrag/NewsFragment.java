package kg.geektech.taskapp35.ui.newsFrag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import kg.geektech.taskapp.R;
import kg.geektech.taskapp.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private News news;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSave.setOnClickListener(v -> saveData());
        news = (News) requireArguments().getSerializable("news");
        if (news != null) {
            binding.editText.setText(news.getTitle());
        }
    }
    private void saveData() {
        String text = Objects.requireNonNull(binding.editText.getText()).toString();
        if (news == null) {
            news = new News(text);
            news.setCreatedAt(System.currentTimeMillis());
            news.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        } else{
            news.setTitle(text);
            update(news);
        }

        News news = new News(text);
        addToFireStore(news);
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        getParentFragmentManager()
                .setFragmentResult("rk_news", bundle);
        Toast.makeText(requireActivity(), "Your data is saved", Toast.LENGTH_SHORT).show();
    }

    private void update(News news) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news")
        .document(news.getId())
        .update("title",news.getTitle())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        close();
                    }
                });
    }

    private void addToFireStore(News news) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news")
                .add(news)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                close();

            }
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}