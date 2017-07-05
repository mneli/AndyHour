package be.mneli.rushhour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class PlayActivity extends AppCompatActivity {
    String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
    }

    private void initView() {

        parseLevels();
    }

    private void parseLevels() {
        jsonString = loadJSONFromAsset();
        if (jsonString != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                JSONArray gameDifficultyArray = jsonObj.getJSONArray("difficulty");
                for (int difficultyIndex = 0; difficultyIndex < gameDifficultyArray.length(); difficultyIndex++) {
                    JSONObject gameDifficulty = gameDifficultyArray.getJSONObject(difficultyIndex);
                    //Log.e("DIFFICULTY", String.valueOf(gameDifficulty));
                    String gameDifficultyName = gameDifficulty.getString("name");
                    JSONArray gameLevelsArray = gameDifficulty.getJSONArray("levels");
                    for (int levelIndex = 0; levelIndex < gameLevelsArray.length(); levelIndex++) {
                        JSONObject gameLevel = gameLevelsArray.getJSONObject(levelIndex);
                        Log.e("LEVEL", String.valueOf(gameLevel));
                        int height = gameLevel.getInt("height");
                        int width = gameLevel.getInt("width");
                        int exitRow = gameLevel.getInt("exitRow");
                        int exitCol = gameLevel.getInt("exitCol");
                        JSONArray carsArray = gameLevel.getJSONArray("cars");
                        for (int carIndex = 0; carIndex < carsArray.length(); carIndex++) {
                            JSONObject car = carsArray.getJSONObject(carIndex);
                            char id = car.getString("id").charAt(0);
                            int size = car.getInt("size");
                            String orientation = car.getString("orientation");
                            int row = car.getInt("row");
                            int col = car.getInt("col");
                            Log.e("CAR", "" + id + " " + size + " " + orientation + " " + row + " " + col);
                        }
                        JSONObject redCar = gameLevel.getJSONObject("redCar");
                        char id = redCar.getString("id").charAt(0);
                        int size = redCar.getInt("size");
                        String orientation = redCar.getString("orientation");
                        int row = redCar.getInt("row");
                        int col = redCar.getInt("col");
                        Log.e("REDCAR", "" + id + " " + size + " " + orientation + " " + row + " " + col);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getResources().openRawResource(R.raw.levels);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
