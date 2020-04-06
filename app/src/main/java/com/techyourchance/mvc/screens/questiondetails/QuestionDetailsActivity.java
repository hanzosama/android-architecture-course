package com.techyourchance.mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.techyourchance.mvc.R;
import com.techyourchance.mvc.networking.QuestionDetailsResponseSchema;
import com.techyourchance.mvc.networking.QuestionSchema;
import com.techyourchance.mvc.networking.StackoverflowApi;
import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionDetailsActivity extends BaseActivity implements QuestionDetailsViewMVC.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";
    private static QuestionDetailsViewMVC mViewMvc;
    private StackoverflowApi mStackoverflowApi;

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchDetailQuestions();
    }

    private void fetchDetailQuestions() {
        Bundle extras = getIntent().getExtras();
        String questionId = null;
        onQuestionFetch();
        if (extras != null) {
            questionId = (String) extras.get(EXTRA_QUESTION_ID);
            mStackoverflowApi.fetchQuestionDetails(questionId).enqueue(new Callback<QuestionDetailsResponseSchema>() {
                @Override
                public void onResponse(Call<QuestionDetailsResponseSchema> call, Response<QuestionDetailsResponseSchema> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            bindQuestionsDetails(response.body().getQuestion());
                            onQuestionLoad();
                        }
                    } else {
                        networkCallFailed();
                    }
                }

                @Override
                public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable t) {
                    networkCallFailed();
                }
            });
        }
    }

    private void bindQuestionsDetails(QuestionSchema question) {
        QuestionDetails questionDetails = new QuestionDetails(question.getId(), question.getTitle(), question.getBody());
        mViewMvc.bindQuestion(questionDetails);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMVC(null);
        mViewMvc.registerListener(this);

        mStackoverflowApi = getCompositionRoot().getStackoverflowApi();
        setContentView(mViewMvc.getRootView());
    }

    @Override
    public void onQuestionFetch() {
        mViewMvc.progressActive(true);
    }

    @Override
    public void onQuestionLoad() {
        mViewMvc.progressActive(false);
    }


    private void networkCallFailed() {
        mViewMvc.progressActive(false);
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }

}
