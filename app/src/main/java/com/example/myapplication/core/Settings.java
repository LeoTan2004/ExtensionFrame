package com.example.myapplication.core;

import com.example.myapplication.core.DataStore.DataStore;

import java.util.HashMap;

public class Settings {
    /**
     * 使用HashMap来存储数据，如果绑定了dataStore，就是用dataStore来存。
     * 如果中途更改绑定，不会对两个dataStore造成任何影响。
     */

    private HashMap<String,Object> settings = new HashMap<>();

    private DataStore dataStore = null;

    /**
     * 推荐使用，这样可以保存设置，当然，如果是临时的（比如无痕模式），就可以使用这样的方式。
     * @param dataStore
     */
    public void bindDataStore(DataStore dataStore){
        this.dataStore = dataStore;
    }


    public Object getSettings(String key) {
        if (dataStore != null){
            return dataStore.getSharedPreferences().getString(key,null);
        }else{
            return settings.get(key);
        }
    }

    public Object setSettings(String key,Object obj){
        if (dataStore != null) {
            return dataStore.getEditor().putString(key,obj.toString());
        }else{
            return settings.put(key, obj);
        }
    }
}
