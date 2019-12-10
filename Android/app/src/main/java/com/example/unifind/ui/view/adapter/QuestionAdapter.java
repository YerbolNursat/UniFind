package com.example.unifind.ui.view.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unifind.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<String> questions;
    OnEditTextChanged onEditTextChanged;
    String[] editdata ;
    public QuestionAdapter(List<String> questions,OnEditTextChanged onEditTextChanged) {
        this.questions = questions;
        this.onEditTextChanged=onEditTextChanged;
        editdata= new String[41];
    }
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);

        return new QuestionViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder holder, final int position) {
        String question=questions.get(position);
        holder.question.setText(question);
        if(editdata[position]!=null) {
            holder.editText.setText(editdata[position]);
        }
        else {
            Log.d(TAG, String.valueOf(editdata[position]));
            holder.editText.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        EditText editText;
        public QuestionViewHolder(@NonNull final View itemView) {
            super(itemView);
            question =itemView.findViewById(R.id.question);
            editText=itemView.findViewById(R.id.edit_text);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onEditTextChanged.onTextChanged(getAdapterPosition(),s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    editdata[getAdapterPosition()]=s.toString();

                }
            });

        }
    }
}
