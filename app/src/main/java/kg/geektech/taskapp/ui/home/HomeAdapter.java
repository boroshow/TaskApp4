package kg.geektech.taskapp.ui.home;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import kg.geektech.taskapp.R;
import kg.geektech.taskapp.databinding.ItemListBinding;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final ArrayList<News> list = new ArrayList<>();
    private ItemListBinding binding;

    public HomeAdapter() { }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        if (position %2 == 0){
            binding.textItemTv.setBackgroundResource(R.color.ser);
        }else{
            binding.textItemTv.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(news);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemListBinding binding;

        public ViewHolder(@NonNull @NotNull ItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void onBind(News news) {

            binding.textItemTv.setText(news.getTitle());
            binding.getRoot().setOnLongClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Attention !!").setMessage("delete item ?").setPositiveButton("yes", (dialog, which) -> {
                           list.remove(getAdapterPosition());
                           notifyItemRemoved(getAdapterPosition());
                        }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();
                return true;
            });

        }
    }
}
