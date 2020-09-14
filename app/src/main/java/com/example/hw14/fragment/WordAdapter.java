package com.example.hw14.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw14.R;
import com.example.hw14.model.Word;


import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHoldeler> implements Filterable {

    private List<Word> mWords;
    private Context mContext;
    private Callbacks mCallbacks;

    public static WordAdapter newInstance(Context context, List wordList, Callbacks callbacks) {
        WordAdapter adapter = new WordAdapter();
        adapter.setWords(wordList);
        adapter.setContext(context);
        adapter.setCallbacks(callbacks);
        return adapter;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setWords(List<Word> words) {
        mWords = words;
    }

    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    private WordAdapter() {
    }


    @NonNull
    @Override
    public WordHoldeler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.word_element, parent, false);
        return new WordHoldeler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHoldeler holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return mWords.size();
    }

    public class WordHoldeler extends RecyclerView.ViewHolder {
        private TextView mTextViewleft;
        private TextView mTextViewRight;
        private View itemView;

        public WordHoldeler(@NonNull View itemView) {
            super(itemView);
            findView(itemView);
            this.itemView = itemView;
        }


        private void findView(View view) {
            mTextViewleft = view.findViewById(R.id.textView_left_word);
            mTextViewRight = view.findViewById(R.id.textView_right_word);
        }

        public void bind(final int position) {
            mTextViewRight.setText(mWords.get(position).getStringRight());
            mTextViewleft.setText(mWords.get(position).getStringLeft());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbacks.onCklickItemCalled(mWords.get(position));
                }
            });
        }
    }

    public interface Callbacks {
        void onCklickItemCalled(Word word);

        void getUpdateList(String search);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                if (constraint == null || constraint.length() == 0) {
                    mCallbacks.getUpdateList("");

                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    mCallbacks.getUpdateList(filterPattern);

                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }


}
