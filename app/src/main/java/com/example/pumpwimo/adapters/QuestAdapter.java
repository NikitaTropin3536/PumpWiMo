//package com.example.pumpwimo.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.pumpwimo.R;
//import com.example.pumpwimo.models.Quest;
//
//import java.util.ArrayList;
//
//public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.Viewholder> {
//
//    private Context context;
//    private ArrayList<Quest> quest;
//
//    public QuestAdapter(Context context, ArrayList<Quest> quest) {
//        this.context = context;
//        this.quest = quest;
//    }
//
//    @NonNull
//    @Override
//    public QuestAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quest_card, parent, false);
//        return new Viewholder(view);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull QuestAdapter.Viewholder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return quest.size();
//    }
//
//    public class Viewholder {
//        //
//    }
//}
