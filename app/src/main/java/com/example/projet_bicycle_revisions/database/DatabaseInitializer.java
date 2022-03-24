package com.example.projet_bicycle_revisions.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;

public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }
    private static void addMechanic(final AppDatabase db, final String email, final String password,
                                    final String name, final String surname,final String telephone,
                                    final String address){
        MechanicEntity mechanic = new MechanicEntity(email,password,name,surname,telephone,address);
        db.mechanicDao().insert(mechanic);
    }

    private static void addBike(final AppDatabase db,final String mechanic, final String firstNameBike, final String lastNameBike, final String emailBike, final String telephoneBike, final String addressBike, final String descriptionBike){
        BikesEntity bike = new BikesEntity(mechanic, firstNameBike,lastNameBike,emailBike, telephoneBike,addressBike,descriptionBike);
        db.bikeDao().insert(bike);
    }

    private static void populateWithTestData(AppDatabase db) {
        addMechanic(db,
                "jc@mail.com","12345678","Jean","Claude","+1",
                "Rue 1");
        addMechanic(db,
                "jp@mail.com","12345678","Jean","Pierre","+2",
                "Rue 2");
        addMechanic(db,
                "jm@mail.com","12345678","Jean","Michel","+3",
                "Rue 3");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addBike(db,"jc@mail.com","Paul","Dubuis","pb@mail.com","0785878787","rue du lac 1","Problème à la roue avant");
        addBike(db,"jm@mail.com","Martin","Dubuis","pb@mail.com","0785878787","rue du lac 1","Problème à la roue avant");
        addBike(db,"jp@mail.com","Jean","Dubuis","pb@mail.com","0785878787","rue du lac 1","Problème à la roue avant");

    }



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
           populateWithTestData(database);
            return null;
        }

    }
}
