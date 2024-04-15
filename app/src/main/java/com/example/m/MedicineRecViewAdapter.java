package com.example.m;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicineRecViewAdapter extends RecyclerView.Adapter<MedicineRecViewAdapter.ViewHolder>{
    private ArrayList<Medicine> medicines = new ArrayList<>();
    public MedicineRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_row_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.medicineName.setText(medicines.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView medicineName;
        private ImageView medicineImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicineName);
            medicineImage = itemView.findViewById(R.id.medicineImageV);
        }
    }
}
