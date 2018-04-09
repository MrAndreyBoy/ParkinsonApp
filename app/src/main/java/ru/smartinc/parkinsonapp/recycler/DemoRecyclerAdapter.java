package ru.smartinc.parkinsonapp.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ru.smartinc.parkinsonapp.R;
import ru.smartinc.parkinsonapp.data.Exercise;

/**
 * Created by AndreyBoy on 05.04.2018.
 */

public class DemoRecyclerAdapter extends RecyclerView.Adapter<DemoRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Exercise> exerList;
    private View.OnClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvName;
        private final TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView)itemView.findViewById(R.id.ivIcon);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvTime = (TextView)itemView.findViewById(R.id.tvTime);
        }
    }

    public DemoRecyclerAdapter(Context context, List<Exercise> exerList){
        this.context = context;
        this.exerList = exerList;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Exercise exercise = exerList.get(position);
        holder.tvName.setText(exercise.getName());
        holder.tvTime.setText(
                String.format(Locale.getDefault(),"%1$dm%2$02ds", exercise.getTime()/60, exercise.getTime()%60));
        holder.ivIcon.setImageResource(context.getResources()
                .getIdentifier(exercise.getIcon(), "drawable", "ru.smartinc.parkinsonapp"));
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return exerList.size();
    }

}
