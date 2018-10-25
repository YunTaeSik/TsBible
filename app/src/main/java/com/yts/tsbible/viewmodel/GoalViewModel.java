package com.yts.tsbible.viewmodel;

import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Goal;
import com.yts.tsbible.data.realm.RealmService;
import com.yts.tsbible.interactor.GoalEditCallback;
import com.yts.tsbible.utills.SendBroadcast;

import io.realm.Realm;

public class GoalViewModel extends BaseViewModel {
    public TSLiveData<Goal> mGoal = new TSLiveData<>(new Goal());

    private GoalEditCallback goalEditCallback;

    public void setGoal(Goal goal) {
        if (goal != null) {
            this.mGoal.setValue(goal);
        }
        this.realm = Realm.getDefaultInstance();
    }

    public void setGoalEditCallback(GoalEditCallback goalEditCallback) {
        this.goalEditCallback = goalEditCallback;
    }

    public void setGoal(CharSequence charSequence) {
        if (mGoal.getValue() != null) {
            Goal goal = mGoal.getValue();
            goal.setGoal(charSequence.toString());
            mGoal.setValue(goal);
        }
    }

    public void close() {
        if (goalEditCallback != null) {
            goalEditCallback.close();
        }
    }

    public void editGoal(View view) {
        if (mGoal != null && mGoal.getValue() != null) {
            RealmService.saveGoal(realm, mGoal.getValue());
            SendBroadcast.saveGoal(view.getContext());
            close();
        }
    }

}
