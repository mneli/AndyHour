package be.mneli.rushhour.model.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.mneli.rushhour.model.Car;
import be.mneli.rushhour.model.Orientation;
import be.mneli.rushhour.model.Position;
import be.mneli.rushhour.model.RushHourException;
import be.mneli.rushhour.model.RushHourGame;

/**
 * Created by mneli on 05/07/17.
 */

public class ParseJson {

    public HashMap<String, List<RushHourGame>> parseLevels(InputStream inputStream) {

        HashMap<String, List<RushHourGame>> gameHashMap = new HashMap<>();

        String jsonString = loadJSONFromAsset(inputStream);

        if (jsonString != null) {
            try {

                JSONObject jsonObj = new JSONObject(jsonString);
                JSONArray gameDifficultyArray = jsonObj.getJSONArray("difficulty");

                for (int difficultyIndex = 0; difficultyIndex < gameDifficultyArray.length(); difficultyIndex++) {

                    JSONObject gameDifficulty = gameDifficultyArray.getJSONObject(difficultyIndex);
                    String gameDifficultyName = gameDifficulty.getString("name");

                    JSONArray gameLevelsArray = gameDifficulty.getJSONArray("levels");
                    List<RushHourGame> levelsList = getGameLevelsList(gameLevelsArray);

                    gameHashMap.put(gameDifficultyName, levelsList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (RushHourException e) {
                e.printStackTrace();
            }
        }
        return gameHashMap;
    }

    private List<RushHourGame> getGameLevelsList(JSONArray jsonArray) throws JSONException, RushHourException {
        List<RushHourGame> levelsList = new ArrayList<>();

        for (int levelIndex = 0; levelIndex < jsonArray.length(); levelIndex++) {
            JSONObject gameLevel = jsonArray.getJSONObject(levelIndex);
            RushHourGame level = getLevelFromJSONObject(gameLevel);
            levelsList.add(level);
        }
        return levelsList;
    }

    private String loadJSONFromAsset(InputStream inputStream) {
        String json;
        try {
            InputStream is = inputStream;
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

    private Car getCarFromJSONObject(JSONObject jsonObject) throws JSONException {
        char id = jsonObject.getString("id").charAt(0);
        int size = jsonObject.getInt("size");
        Orientation orientation = Orientation.valueOf(jsonObject.getString("orientation"));
        int row = jsonObject.getInt("row");
        int col = jsonObject.getInt("col");
        return new Car(id, size, orientation, new Position(row, col));
    }

    private RushHourGame getLevelFromJSONObject(JSONObject jsonObject) throws JSONException, RushHourException {

        int height = jsonObject.getInt("height");
        int width = jsonObject.getInt("width");
        Position exitPosition = new Position(jsonObject.getInt("exitRow"), jsonObject.getInt("exitCol"));
        List<Car> cars = getCarList(jsonObject.getJSONArray("cars"));
        Car redCar = getCarFromJSONObject(jsonObject.getJSONObject("redCar"));

        return new RushHourGame(height, width, exitPosition, cars, redCar);
    }

    private List<Car> getCarList(JSONArray carsArray) throws JSONException {
        List<Car> list = new ArrayList<>();
        for (int carIndex = 0; carIndex < carsArray.length(); carIndex++) {
            Car car = getCarFromJSONObject(carsArray.getJSONObject(carIndex));
            list.add(car);
        }
        return list;
    }
}
