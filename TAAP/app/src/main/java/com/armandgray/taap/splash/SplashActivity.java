package com.armandgray.taap.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.armandgray.taap.main.MainActivity;
import com.armandgray.taap.models.Drill;
import com.armandgray.taap.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.armandgray.taap.db.DatabaseContentProvider.insertDrillToDatabase;
import static com.armandgray.taap.db.CursorDataHelper.getDrillsListFromDatabase;
import static com.armandgray.taap.db.DrillsDataHelper.getDrillsList;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        insertAllDrillsToDatabase();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://armandgray.com/seeme/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        GitHubService service = retrofit.create(GitHubService.class);
//        Call<List<User>> repos = service.listUsers("armandgray@gmail.com", "Cienna13");
//        Response<List<User>> execute = null;
//        try {
//            execute = repos.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (execute != null && execute.body() != null && execute.body().size() > 0) {
//            for (User u : execute.body()) {
//                System.out.println(u.getFirstName() + u.getLastName());
//            }
//        }
    }

    @VisibleForTesting
    void insertAllDrillsToDatabase() {
        List<Drill> drills = getDrillsListFromDatabase(this);
        if (drills != null && drills.size() > 0) { return; }
        for (Drill drill : getDrillsList()) { insertDrillToDatabase(drill, this); }
    }

    public interface GitHubService {
        @GET("login/user")
        Call<List<User>> listUsers(@Query("username") String username, @Query("password") String password);
    }
}
