package kg.geektech.taskapp.ui.onBoard;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import org.jetbrains.annotations.NotNull;
import kg.geektech.taskapp.R;

public class BoardFragment extends Fragment implements BoardAdapter.FinishBoard {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);
        DotsIndicator dotsIndicator = view.findViewById(R.id.dotsIndicator);
        BoardAdapter adapter = new BoardAdapter();
        viewPager2.setAdapter(adapter);
        dotsIndicator.setViewPager2(viewPager2);
        adapter.setFinishBoard(this);

        skipBtnLogic(view);

        requireActivity().getOnBackPressedDispatcher().
                addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    private void skipBtnLogic(View view) {
        Button buttonSkip = view.findViewById(R.id.skipTv);
        buttonSkip.setOnClickListener(v -> navigate());
    }

    private void navigate() {
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.navigation_home);
    }

    @Override
    public void btnClickFinishBoard() {
        navigate();
    }
}