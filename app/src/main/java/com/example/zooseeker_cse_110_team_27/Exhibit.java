package com.example.zooseeker_cse_110_team_27;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.zooseeker_cse_110_team_27.location.Coord;
import com.example.zooseeker_cse_110_team_27.utils.LatLngs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "search_list_items")
public class Exhibit {

    @NonNull
    public String id;
    public String group_id;
    public String kind;
    public String name;
    public List<String> tags;
    public double lat;
    public double lng;

    public Exhibit(@NonNull String id, String group_id, String kind, String name, List<String> tags, double lat, double lng) {
        this.id = id;
        this.group_id = group_id;
        this.kind = kind;
        this.name = name;
        this.tags = tags;
        this.lat = lat;
        this.lng = lng;
    }

    @NonNull
    @Override
    public String toString() {
        return "Exhibit{" +
                "id='" + id + '\'' +
                "group_id="+group_id + '\'' +
                ", kind='" + kind + '\'' +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public static List<Exhibit> loadJSONForSearching(Context context, String path){
        Log.d("exhibit", "loadJSON");
        try {
            InputStream inputStream = context.getAssets().open(path);
            Reader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Exhibit>>(){}.getType();
            return gson.fromJson(reader, type);
        }
        catch (IOException e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static Map<String, String> getIdMap(List<Exhibit> exhibits) {
        Log.d("exhibit", "getID");
        Map<String, String> idMap = new HashMap<>();
        for (Exhibit exhibit : exhibits) {
            idMap.put(exhibit.name, exhibit.id);
        }

        return idMap;
    }

    public static Map<String, String> getSearchMap(List<Exhibit> exhibits){
        Log.d("exhibit", "getSearch");
        Map<String, String> exhibitTagMap = new HashMap<>();
        for (Exhibit exhibit: exhibits) {
            for (String tag: exhibit.tags ) {
                exhibitTagMap.put(tag, exhibit.name);
            }
        }
        return exhibitTagMap;
    }

    public static HashMap<String, Coord> getCoordMap(List<Exhibit> exhibits) {
        Log.d("exhibit", "getCoord");
        HashMap<String, Coord> m = new HashMap<>();
        for (Exhibit e : exhibits) {
            Coord coord = new Coord(e.lat, e.lng);
            m.put(e.id, coord);
        }
        return m;
    }

}