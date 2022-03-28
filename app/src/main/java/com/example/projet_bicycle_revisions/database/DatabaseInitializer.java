package com.example.projet_bicycle_revisions.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;

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

    private static void addBike(final AppDatabase db,final String mechanic,final String typeBike, final String firstNameBike, final String lastNameBike, final String emailBike, final String telephoneBike, final String addressBike, final String descriptionBike){
        BikesEntity bike = new BikesEntity(mechanic, typeBike,firstNameBike,lastNameBike,emailBike, telephoneBike,addressBike,descriptionBike,false);
        db.bikeDao().insert(bike);
    }

    private static void populateWithTestData(AppDatabase db) {
        addMechanic(db,
                "jc@mail.com","12345678","Jean","Claude","+0711111111",
                "Rue de la paix 50");
        addMechanic(db,
                "jp@mail.com","12345678","Jean","Pierre","+0722222222",
                "Avenue de la guerre 14");
        addMechanic(db,
                "jm@mail.com","12345678","Jean","Michel","+0733333333",
                "Chemin de la neutralité 1");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addBike(db,"jc@mail.com","Yellow BMX","Paul","Dubuis","pb@mail.com","0785878787","rue du lac 1","Problem with front wheel");
        addBike(db,"jc@mail.com","Red Peugeot Bicycle","Martin","Dubuis","pb@mail.com","0785878787","rue du lac 1","New tires");
        addBike(db,"jc@mail.com","Blue MountainBike","Jean","Dubuis","pb@mail.com","0785878787","rue du lac 1","Annual Revision");
        addBike(db,"jp@mail.com","Yellow BMX","Paul","Dubuis","pb@mail.com","0785878787","rue du lac 1","Problem with front wheel");
        addBike(db,"jp@mail.com","Red Peugeot Bicycle","Martin","Dubuis","pb@mail.com","0785878787","rue du lac 1","New tires");
        addBike(db,"jp@mail.com","Blue MountainBike","Jean","Dubuis","pb@mail.com","0785878787","rue du lac 1","Annual Revision");
        addBike(db,"jm@mail.com","Yellow BMX","Paul","Dubuis","pb@mail.com","0785878787","rue du lac 1","Problem with front wheel");
        addBike(db,"jm@mail.com","Red Peugeot Bicycle","Martin","Dubuis","pb@mail.com","0785878787","rue du lac 1","New tires");
        addBike(db,"jm@mail.com","Blue MountainBike","Jean","Dubuis","pb@mail.com","0785878787","rue du lac 1","Annual Revision");
        addBike(db,"jm@mail.com","Green Bicycle","John","Deere","jd@mail.com","0785878787","rue du lac 1","New paint - Red");
        addBike(db,"jm@mail.com","Pink Race Bicycle","Maria","Stoffer","ms@mail.com","0785878787","rue du lac 1","New brakes");
        addBike(db,"jm@mail.com","Black Moutainbike with blue lights","Marc","Dupont","md@mail.com","0785878787","rue du lac 1","Problem with chain");
        addBike(db,"jm@mail.com","Little Tricycle","Johnny","Little","jl@mail.com","0785878787","rue du lac 1","Front wheel broken");
        addBike(db,"jm@mail.com","White BMX","Barack","Obama","bo@mail.com","0785878787","rue du lac 1","Oil change");

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
