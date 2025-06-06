package de.athalion.game.twodgame.save;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import de.athalion.game.twodgame.logs.Logger;

import java.io.*;
import java.util.HashMap;

public class Progress {

    HashMap<String, Value> progress;

    public Progress(HashMap<String, Value> progress) {
        this.progress = progress;
    }

    public Progress() {
        progress = new HashMap<>();
    }

    public void setValue(String key, Value value) {
        progress.put(key, value);
    }

    public void setValue(String key, String value) {
        progress.put(key, new Value(value));
    }

    public void setValue(String key, int value) {
        progress.put(key, new Value(value));
    }

    public void setValue(String key, double value) {
        progress.put(key, new Value(value));
    }

    public void setValue(String key, boolean value) {
        progress.put(key, new Value(value));
    }

    public HashMap<String, Value> getProgress() {
        return progress;
    }

    public static Progress loadProgress(String name) {
        File file = new File(System.getProperty("user.dir") + "/saves/" + name + "/progress.json");
        if (file.exists()) {
            try {
                JsonObject jsonObject = JsonParser.parseReader(new BufferedReader(new FileReader(file))).getAsJsonObject();
                HashMap<String, Value> hashMap = new HashMap<>();
                jsonObject.asMap().forEach((string, jsonElement) -> hashMap.put(string, Value.parseJson(jsonElement.getAsJsonObject())));
                return new Progress(hashMap);
            } catch (FileNotFoundException e) {
                Logger.error("Could not load progress file for " + name + "!");
                Logger.stackTrace(e.getStackTrace());
            }
        } else {
            Logger.warn("Could not load progress file for " + name + "!");
        }
        return new Progress();
    }

    public static void saveProgress(String name, Progress progress) {
        File file = new File(System.getProperty("user.dir") + "/saves/" + name + "/progress.json");
        try (FileWriter writer = new FileWriter(file)) {
            JsonObject jsonObject = new JsonObject();
            progress.getProgress().forEach((string, value) -> jsonObject.add(string, value.toJsonPrimitive()));
            writer.write(jsonObject.toString());
        } catch (IOException e) {
            Logger.error("Could not save progress file for " + name + "!");
            Logger.stackTrace(e.getStackTrace());
        }
    }

    public static class Value {
        Object object;

        public Value(Object object) {
            this.object = object;
        }

        public String getAsString() {
            return (String) object;
        }

        public int getAsInt() {
            return (int) object;
        }

        public double getAsDouble() {
            return (double) object;
        }

        public boolean getAsBoolean() {
            return (boolean) object;
        }

        public JsonPrimitive toJsonPrimitive() {
            JsonPrimitive jsonPrimitive;
            switch (object) {
                case String s -> jsonPrimitive = new JsonPrimitive(s);
                case Integer i -> jsonPrimitive = new JsonPrimitive(i);
                case Double v -> jsonPrimitive = new JsonPrimitive(v);
                case Boolean b -> jsonPrimitive = new JsonPrimitive(b);
                default -> {
                    jsonPrimitive = null;
                    Logger.warn("Tried to convert progress value of unknown type: " + object + "!");
                }
            }
            return jsonPrimitive;
        }

        public static Value parseJson(JsonObject jsonObject) {
            Value value;
            if (jsonObject.isJsonPrimitive()) {
                if (jsonObject.getAsJsonPrimitive().isString()) {
                    value = new Value(jsonObject.getAsString());
                } else if (jsonObject.getAsJsonPrimitive().isNumber()) {
                    Number number = jsonObject.getAsNumber();
                    if (number instanceof Integer) {
                        value = new Value(jsonObject.getAsInt());
                    } else {
                        value = new Value(jsonObject.getAsDouble());
                    }
                } else if (jsonObject.getAsJsonPrimitive().isBoolean()) {
                    value = new Value(jsonObject.getAsBoolean());
                } else {
                    Logger.warn("Unknown type of progress value!");
                    value = new Value(null);
                }
            } else {
                Logger.warn("Unknown type of progress value!");
                value = new Value(null);
            }
            return value;
        }
    }

}
