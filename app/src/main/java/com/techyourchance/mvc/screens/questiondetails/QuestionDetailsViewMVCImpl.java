package com.techyourchance.mvc.screens.questiondetails;

import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.BaseObservableViewMvc;

public class QuestionDetailsViewMVCImpl extends BaseObservableViewMvc<QuestionDetailsViewMVC.Listener> implements QuestionDetailsViewMVC {

    private TextView questionTitle;
    private TextView questionDetail;
    private ProgressBar progressBar;

    public QuestionDetailsViewMVCImpl(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layaout_question_details, parent));

        questionTitle = findViewById(R.id.question_title);
        questionDetail = findViewById(R.id.question_detail);
        progressBar = findViewById(R.id.indeterminate_progress);

    }

    @Override
    public void bindQuestion(QuestionDetails questionDetails) {
        questionTitle.setText(questionDetails.getTitle());
        questionDetail.setText(Html.fromHtml(questionDetails.getBody()));
    }

    @Override
    public void progressActive(boolean active) {
        progressBar.setVisibility(active? View.VISIBLE: View.GONE);
    }

}
