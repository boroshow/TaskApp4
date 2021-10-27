package kg.geektech.taskapp35.ui.fourthFrag;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import kg.geektech.taskapp.R;
import kg.geektech.taskapp.databinding.FragmentFouthBinding;
import kg.geektech.taskapp35.ui.Prefs;
import kg.geektech.taskapp35.ui.auth.LoginFragment;

public class FouthFragment extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    private FragmentFouthBinding binding;
    private ActivityResultLauncher<String> activityResultLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFouthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void signOut() {
        if (user != null){
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireActivity());
            dialog.setTitle("DO YOU WANT TO SIGN OUT ?")
                    .setMessage("Sign out? ")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth.getInstance().signOut();
                            Toast.makeText(requireActivity(), "SignOut: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            signOutUser();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show() ;
        }
    }

    private void signOutUser() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.loginFragment);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSignOut.setOnClickListener(v -> signOut());
        changeBtn();
        pickImage();
        editSave();
        setLocalContent();
    }
    private void editSave() {
        binding.nikNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Prefs prefs = new Prefs(requireContext());
                prefs.saveEditText(editable.toString());
            }
        });
    }


    private void pickImage() {
        binding.avatarIv.setOnClickListener(view -> {
            activityResultLauncher.launch("image/*");
        });
    }

    private void setLocalContent() {
        Prefs prefs = new Prefs(requireContext());
        String url = prefs.getAvatar();
        String name = prefs.getTextEdit();
        Glide.with(requireActivity()).load(url).circleCrop().into(binding.avatarIv);
        binding.nikNameEdit.setText(name);
    }

    private void changeBtn() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                result -> {
            Glide.with(requireActivity()).load(result).circleCrop().into(binding.avatarIv);
            Prefs prefs = new Prefs(requireContext());
            prefs.saveAvatar(result);
        });

    }
}