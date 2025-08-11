package lk.sliitcu.quizizz.adapters;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lk.sliitcu.quizizz.R;
import lk.sliitcu.quizizz.models.ScoreEntry;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private final List<ScoreEntry> scoreList;

    public ScoreAdapter(List<ScoreEntry> scoreList) {
        this.scoreList = scoreList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreEntry scoreEntry = scoreList.get(position);
        holder.bind(scoreEntry);

        if (position == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFDE7"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAttempt, textViewScore, textViewDate;

        ScoreViewHolder(View itemView) {
            super(itemView);
            textViewAttempt = itemView.findViewById(R.id.textViewAttempt);
            textViewScore = itemView.findViewById(R.id.textViewScore);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }

        void bind(ScoreEntry entry) {
            textViewAttempt.setText(String.format(Locale.getDefault(), "Attempt %d", entry.getAttempt()));
            textViewScore.setText(String.format(Locale.getDefault(), "Score: %d", entry.getScore()));
            textViewDate.setText(formatTimestamp(entry.getTimestamp()));
        }


        private String formatTimestamp(long timestamp) {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTimeInMillis(timestamp);
            return DateFormat.format("dd MMM yyyy, hh:mm a", cal).toString();
        }
    }
}