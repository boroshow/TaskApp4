package kg.geektech.taskapp35.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.taskapp.R;
import kg.geektech.taskapp.databinding.FragmentHomeBinding;
import kg.geektech.taskapp35.FragmentOther;
import kg.geektech.taskapp35.ui.OnItemClick;
import kg.geektech.taskapp35.ui.newsFrag.News;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HomeAdapter();
        adapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onItemClick(int pos) {
                News news = adapter.getItem(pos);
                if (news.getEmail() != null && news.getEmail() == FirebaseAuth.getInstance().getCurrentUser().getEmail()) {
                    openFragment(news);
                } else {
                    FirebaseFirestore.getInstance()
                            .collection("news")
                            .document(news.getId())
                            .update("view_count", FieldValue.increment(1));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("text_key", "adapter.string");

                    Log.e("`TAG", "«: " + bundle);

//                    NavController navController = new NavController(getContext());
//                    navController.navigate(R.id.fragmentOther, bundle);

                    Navigation.findNavController(getView()).navigate(R.id.fragmentOther, bundle);
                }

            }
        });
        readDataLive();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(v -> {
            openFragment(null);
        });
  /*      getParentFragmentManager().setFragmentResultListener("rk_news",
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    News news = (News) result.getSerializable("news");
                    Log.e("Home", "text = " + news.getTitle());
                    adapter.addItem(news);
                });*/
        initRecycler();
    }

    private void initRecycler() {
        binding.recyclerHome.setAdapter(adapter);
    }

    private void openFragment(News news) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.taskFragment, bundle);
    }

    private void readData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {


                        List<News> list = snapshots.toObjects(News.class);
                        adapter.addItems(list);
                    }
                });
    }


    private void readDataLive() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("news")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(20)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                        List<News> list = new ArrayList<>();
                        for (DocumentSnapshot snapshot : snapshots) {
                            News news = snapshot.toObject(News.class);
                            news.setId(snapshot.getId());
                            list.add(news);
                        }
                        adapter.addItems(list);
                    }
                });


    }
}