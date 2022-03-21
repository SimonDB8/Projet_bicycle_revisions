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

    private static void addBike(final AppDatabase db, final String bikeModel, final String mechanic){
        BikesEntity bike = new BikesEntity(bikeModel,mechanic);
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
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addBike(db,"VTT 1","jc@mail.com");
        addBike(db,"VTT 2","jc@mail.com");
        addBike(db,"Velo1","jc@mail.com");
        addBike(db,"Velo2","jc@mail.com");
        addBike(db,"BMX 1","jc@mail.com");
        addBike(db,"BMX 2","jc@mail.com");
        addBike(db,"VTT 1","jp@mail.com");
        addBike(db,"VTT 2","jp@mail.com");
        addBike(db,"Velo1","jp@mail.com");
        addBike(db,"Velo2","jp@mail.com");
        addBike(db,"BMX 1","jp@mail.com");
        addBike(db,"BMX 2","jp@mail.com");
        addBike(db,"VTT 1","jm@mail.com");
        addBike(db,"VTT 2","jm@mail.com");
        addBike(db,"Velo1","jm@mail.com");
        addBike(db,"Velo2","jm@mail.com");
        addBike(db,"BMX 1","jm@mail.com");
        addBike(db,"BMX 2","jm@mail.com");


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
