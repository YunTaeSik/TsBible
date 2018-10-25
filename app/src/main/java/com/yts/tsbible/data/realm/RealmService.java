package com.yts.tsbible.data.realm;

import com.yts.tsbible.data.model.Bible;
import com.yts.tsbible.data.model.Goal;
import com.yts.tsbible.data.model.History;
import com.yts.tsbible.data.model.Offering;
import com.yts.tsbible.data.model.User;
import com.yts.tsbible.utills.DateFormat;
import com.yts.tsbible.utills.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.Sort;

public class RealmService {

    public static List<User> getUserList(Realm realm) {
        List<User> userList = realm.copyFromRealm(realm.where(User.class).findAll());

        User user;
        if (userList.size() <= 0) {
            user = new User();
            user.setId(Keys.USER_ID);
            user.setRefreshTime(System.currentTimeMillis());
            user.setTodayBible(getTodayBible(realm));
            saveUser(realm, user);

            userList.add(user);
        } else {
            user = userList.get(0);
            if (DateFormat.isTodayVerseRefresh(user.getRefreshTime())) {
                user.setRefreshTime(System.currentTimeMillis());
                user.setTodayBible(getTodayBible(realm));

                Goal goal = user.getGoal() != null ? user.getGoal() : new Goal();
                goal.setTodaySuccess(false);
                goal.setStartReadTime("0");
                goal.setEndReadTime("0");
                goal.setReadTime("0");
                user.setGoal(goal);

                saveUser(realm, user);
            }
            userList.set(0, user);
        }
        return userList;
    }

    public static void saveBibleList(Realm realm, ArrayList<Bible> bibles) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bibles);
        realm.commitTransaction();
    }

    public static List<Bible> getBibleList(Realm realm) {
        return realm.copyFromRealm(realm.where(Bible.class).findAll());
    }


    public static Observable<List<Bible>> getBibleList(Realm realm, String label, String chapter) {
        return Observable.just(realm.copyFromRealm(realm.where(Bible.class).equalTo("label", label).equalTo("chapter", chapter).findAll()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Bible getTodayBible(Realm realm) {
        List<Bible> bibles = realm.copyFromRealm(realm.where(Bible.class).findAll());
        Random random = new Random();
        int randomInt = random.nextInt(bibles.size());
        return bibles.get(randomInt);
    }

    public static void saveBible(Realm realm, Bible bible) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(bible);
        realm.commitTransaction();
    }

    public static void saveHistory(Realm realm, History history) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(history);
        realm.commitTransaction();
    }

    public static void saveUser(Realm realm, User user) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    public static void saveGoal(Realm realm, Goal goal) {
        List<User> userList = getUserList(realm);
        User user = userList.get(0);
        user.setGoal(goal);
        saveUser(realm, user);
    }

    public static Observable<List<Object>> getHomeList(final Realm realm) {
        return Observable.create(new ObservableOnSubscribe<List<Object>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Object>> emitter) throws Exception {
                List<Object> homeList = new ArrayList<>();

                List<User> userList = getUserList(realm);
                List<History> historyList = realm.copyFromRealm(realm.where(History.class).findAll().sort("date", Sort.DESCENDING));


                homeList.addAll(userList);
                homeList.addAll(historyList);

                emitter.onNext(homeList);
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<List<Object>> getOfferingList(final Realm realm) {
        return Observable.create(new ObservableOnSubscribe<List<Object>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Object>> emitter) throws Exception {
                List<Object> offeringList = new ArrayList<>();

                List<User> userList = getUserList(realm);
                List<Offering> historyList = realm.copyFromRealm(realm.where(Offering.class).findAll().sort("date", Sort.DESCENDING));


                offeringList.addAll(userList);
                offeringList.addAll(historyList);

                emitter.onNext(offeringList);
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void saveOffering(Realm realm, Offering offering) {

        List<User> userList = getUserList(realm);
        User user = userList.get(0);
        long amount = user.getOfferingAmount();


        long money = offering.getMoney();
        offering.setAmount(amount + money);
        user.setOfferingAmount(offering.getAmount());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.copyToRealmOrUpdate(offering);
        realm.commitTransaction();
    }

    public static void deleteOffering(Realm realm, Offering offering) {
        List<User> userList = getUserList(realm);
        User user = userList.get(0);
        long amount = user.getOfferingAmount();

        long money = offering.getMoney();
        user.setOfferingAmount(amount - money);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.where(Offering.class).equalTo("date", offering.getDate()).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void startReadBible(Realm realm) {
        List<User> userList = getUserList(realm);
        User user = userList.get(0);
        Goal goal = user.getGoal() != null ? user.getGoal() : new Goal();
        goal.setStartReadTime(String.valueOf(System.currentTimeMillis()));

        user.setGoal(goal);
        saveUser(realm, user);
    }

    public static void stopReadBible(Realm realm) {
        List<User> userList = getUserList(realm);
        User user = userList.get(0);
        Goal goal = user.getGoal() != null ? user.getGoal() : new Goal();
        goal.setEndReadTime(String.valueOf(System.currentTimeMillis()));

        double startReadTime = goal.getStartReadTime() != null ? Double.parseDouble(goal.getStartReadTime()) : 0;
        double endReadTime = goal.getEndReadTime() != null ? Double.parseDouble(goal.getEndReadTime()) : 0;
        double readTime = goal.getReadTime() != null ? Double.parseDouble(goal.getReadTime()) : 0;

        readTime = readTime + (endReadTime - startReadTime);
        goal.setReadTime(String.valueOf(readTime));


        double goalTime = (goal != null ? goal.getGoal() != null ? Double.parseDouble(goal.getGoal()) : 0 : 0) * 60 * 1000;
        if (goalTime > 0 && !goal.isTodaySuccess()) {
            double progress = (readTime / goalTime) * (100.0);
            if (progress >= 100) {
                goal.setTodaySuccess(true);
                goal.setTotalSuccessesNumber(goal.getTotalSuccessesNumber() + 1);
            }
        }
        user.setGoal(goal);
        saveUser(realm, user);
    }

}
