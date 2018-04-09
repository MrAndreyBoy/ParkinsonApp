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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Exercise> exerList;
    private ItemTouchHelper itemTouchHelper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvName;
        private final TextView tvTime;
        LinearLayout llFront, llBack;
        View ivReorder;

        ViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView)itemView.findViewById(R.id.ivIcon);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvTime = (TextView)itemView.findViewById(R.id.tvTime);
            llFront = itemView.findViewById(R.id.llFront);
            llBack = itemView.findViewById(R.id.llBack);
            ivReorder = itemView.findViewById(R.id.ivReorder);
        }

        public TextView getTvName() {
            return tvName;
        }
    }

    public RecyclerAdapter(Context context, List<Exercise> exerList){
        this.context = context;
        this.exerList = exerList;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reorder, parent, false);
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
        holder.ivReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if(itemTouchHelper != null) {
                        itemTouchHelper.startDrag(holder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerList.size();
    }

    public Exercise removeItem(int position) {
        Exercise exercise = exerList.get(position);
        exerList.remove(position);
        notifyItemRemoved(position);
        return exercise;
    }

    public void restoreItem(int position, Exercise exercise) {
        exerList.add(position, exercise);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPos, int toPos) {
        //TODO maybe an error on 1
        Exercise exercise = exerList.get(fromPos);
        exerList.remove(fromPos);
        exerList.add(toPos, exercise);
        notifyItemMoved(fromPos, toPos);
    }

}
