package kg.geektech.taskapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import kg.geektech.taskapp.R;
import kg.geektech.taskapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.navigation_notifications ||
                    destination.getId() == R.id.navigation_dashboard ||
                    destination.getId() == R.id.navigation_home || destination.getId() == R.id.fouthFragment) {
                binding.navView.setVisibility(View.VISIBLE);
            } else {
                binding.navView.setVisibility(View.GONE);
            }
            if (destination.getId() == R.id.boardFragment) {
                Objects.requireNonNull(getSupportActionBar()).hide();
            } else {
                Objects.requireNonNull(getSupportActionBar()).show();
            }
        });
        if(true) {
            navController.navigate(R.id.boardFragment);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}