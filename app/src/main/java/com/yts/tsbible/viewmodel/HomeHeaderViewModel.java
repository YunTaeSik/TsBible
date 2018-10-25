package com.yts.tsbible.viewmodel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.data.model.User;
import com.yts.tsbible.ui.activity.OfferingListActivity;
import com.yts.tsbible.ui.dialog.AlertDialogCreate;
import com.yts.tsbible.ui.dialog.GoalEditDialog;

public class HomeHeaderViewModel extends BaseViewModel {
    public TSLiveData<User> mUser = new TSLiveData<>();

    public void setUser(User user) {
        mUser.setValue(user);
    }

    public void startImageMake(final View view) {
        if (mUser != null && mUser.getValue() != null && mUser.getValue().getTodayBible() != null) {
            final Context context = view.getContext();
            AlertDialogCreate alertDialogCreate = new AlertDialogCreate(context);
            alertDialogCreate.moveImageMake(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bible bible = mUser.getValue().getTodayBible();
                    startImageMake(context, bible);
                }
            });

        }
    }

    public void startOffering(View view) {
        Context context = view.getContext();
        Intent offering = new Intent(context, OfferingListActivity.class);
        context.startActivity(offering);
    }

    public void startEditGoal(View view) {
        GoalEditDialog goalEditDialog = new GoalEditDialog(view.getContext(), mUser.getValue().getGoal());
        goalEditDialog.show();
    }
}
