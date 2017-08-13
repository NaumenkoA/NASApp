package com.example.alex.nasapp.model.asteroid.deserializer;

import com.example.alex.nasapp.model.asteroid.Asteroid;
import com.example.alex.nasapp.model.asteroid.AsteroidList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// deserializer for dynamic JSON response
public class AsteroidDeserializer implements JsonDeserializer<AsteroidList> {
    @Override
    public AsteroidList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        List <Asteroid> asteroids = new ArrayList<>();
        Gson gson = new Gson();

        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonObject nearEarthObjects = jsonObject.getAsJsonObject("near_earth_objects");

        Set<Map.Entry<String, JsonElement>> entrySet = nearEarthObjects.entrySet();

        for(Map.Entry<String, JsonElement> entry : entrySet) {
            JsonArray jsonArray = nearEarthObjects.getAsJsonArray(entry.getKey());
            for (JsonElement element : jsonArray) {
                Asteroid asteroid = gson.fromJson(element, Asteroid.class);
                asteroids.add(asteroid);
            }
        }

        return new AsteroidList(asteroids);
    }
}
