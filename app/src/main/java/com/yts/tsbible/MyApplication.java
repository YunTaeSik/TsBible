package com.yts.tsbible;

import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.yts.tsbible.data.Migration;
import com.yts.tsbible.data.realm.RealmService;
import com.yts.tsbible.data.sqlite.SqlitHelper;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends MultiDexApplication {
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.ad_app_id));
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("TsBible.realm")
                .schemaVersion(0)
                .migration(new Migration())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        if (RealmService.getBibleList(realm).size() < 31138) {
            SqlitHelper sqlitHelper = new SqlitHelper(this);
            RealmService.saveBibleList(realm, sqlitHelper.getBibleList());
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
