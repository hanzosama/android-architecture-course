package com.techyourchance.mvc.screens.questiondetails;

import com.techyourchance.mvc.questions.QuestionDetails;
import com.techyourchance.mvc.screens.common.ObservableViewMvc;

public interface QuestionDetailsViewMVC extends ObservableViewMvc<QuestionDetailsViewMVC.Listener> {

    public interface Listener {
       void onQuestionFetch();
       void onQuestionLoad();
    }

    void bindQuestion(QuestionDetails questionDetails);

    void progressActive(boolean active);

}
