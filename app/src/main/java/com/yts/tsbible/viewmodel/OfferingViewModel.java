package com.yts.tsbible.viewmodel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.chip.ChipGroup;
import com.yts.tsbible.R;
import com.yts.tsbible.data.TSLiveData;
import com.yts.tsbible.data.model.Offering;
import com.yts.tsbible.data.realm.RealmService;
import com.yts.tsbible.ui.dialog.AlertDialogCreate;
import com.yts.tsbible.ui.dialog.DateSelectDialog;
import com.yts.tsbible.utills.SendBroadcast;
import com.yts.tsbible.utills.ToastMake;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import io.realm.Realm;

public class OfferingViewModel extends BaseViewModel {
    public TSLiveData<Offering> mOffering = new TSLiveData<>(new Offering(System.currentTimeMillis()));

    public TSLiveData<List<String>> mOfferingKindList = new TSLiveData<>();
    public TSLiveData<String> mOfferingKind = new TSLiveData<>();

    public TSLiveData<List<String>> mOfferingPriceTextList = new TSLiveData<>();


    public TSLiveData<String> mOfferingPrice = new TSLiveData<>();

    public void initOfferingKindList(Context context, Realm realm) {
        this.realm = realm;
        mOfferingPriceTextList.setValue(Arrays.asList(context.getResources().getStringArray(R.array.prices_text)));
        mOfferingKindList.setValue(Arrays.asList(context.getResources().getStringArray(R.array.offering_kind)));
        mOfferingKind.setValue(mOfferingKindList.getValue().get(0));
    }

    public void setOffering(Offering offering) {
        this.realm = Realm.getDefaultInstance();
        mOffering.setValue(offering);
    }

    public ChipGroup.OnCheckedChangeListener mOfferingKindCheckedChangeListener = new ChipGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(ChipGroup chipGroup, int i) {
            if (chipGroup.getCheckedChipId() != -1) {
                mOfferingKind.setValue(mOfferingKindList.getValue().get(chipGroup.getCheckedChipId()));
            } else {
                mOfferingKind.setValue(null);
            }

        }
    };
    public View.OnClickListener mAddOfferingPrice = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                int viewId = view.getId();
                int price = view.getContext().getResources().getIntArray(R.array.prices)[viewId];


                if (mOfferingPrice.getValue() != null && mOfferingPrice.getValue().length() > 0) {
                    price = Integer.parseInt(mOfferingPrice.getValue()) + price;
                }

                price(String.valueOf(price));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public void price(CharSequence charSequence) {
        mOfferingPrice.setValue(charSequence.toString());
    }

    public void dateChange(View view) {
        Context context = view.getContext();
        if (mOffering.getValue() != null) {
            DateSelectDialog.start(context, mOffering.getValue().getDate(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    Offering offering = mOffering.getValue();

                    GregorianCalendar newCalendar = new GregorianCalendar();
                    newCalendar.setTimeInMillis(offering.getDate());
                    newCalendar.set(year, monthOfYear, dayOfMonth);

                    offering.setDate(newCalendar.getTimeInMillis());
                    mOffering.setValue(offering);
                }
            });
        }
    }

    public void deletePrice() {
        mOfferingPrice.setValue("");
    }

    public void saveOffering(View view) {
        if (mOfferingKind.getValue() == null || mOfferingKind.getValue().length() == 0) {
            ToastMake.make(view.getContext(), R.string.error_offering_kind);
            return;
        }
        Offering offering = mOffering.getValue();
        if (mOfferingPrice.getValue() != null && mOfferingPrice.getValue().length() > 0) {
            offering.setMoney(Long.parseLong(mOfferingPrice.getValue()));
        }
        offering.setName(mOfferingKind.getValue());

        RealmService.saveOffering(realm, offering);

        SendBroadcast.saveOffering(view.getContext());
    }

    public boolean deleteOffering(View view) {
        try {
            final Context context = view.getContext();
            AlertDialogCreate alertDialogCreate = new AlertDialogCreate(context);
            alertDialogCreate.deleteOffering(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (mOffering.getValue() != null) {
                        RealmService.deleteOffering(realm, mOffering.getValue());
                        SendBroadcast.deleteOffering(context);
                    }
                    dialogInterface.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
