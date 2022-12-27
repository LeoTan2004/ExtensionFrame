package com.example.myapplication.core.Extension;

import android.util.JsonReader;
import android.util.JsonToken;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 描述性对象，描述扩展的作用域等
 *
 */
/*
实例json格式
[
    {
        "name": "hello",
        "allowance": ["nihao","adfad"]
    }
]
可以有多个对象，但是名字只包含第一个对象的
 */
public class Detail {
    private String name;
    private ArrayList<String> pattern = new ArrayList<>();//匹配时使用正则表达式

    public Detail(@NonNull File file) throws IOException {

        JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        init(jsonReader);


    }

    private int init(JsonReader jsonReader) throws IOException {

        jsonReader.beginArray();
        this.pattern.clear();
        while (jsonReader.hasNext()){
            jsonReader.beginObject();//初始化
            while (jsonReader.hasNext()){
                if (jsonReader.nextName().equals("name")){
                    this.name = jsonReader.nextString();
                }
                if (jsonReader.nextName().equals("allowance")){
                    if (jsonReader.peek()!=JsonToken.NULL){
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()){
                            String s = jsonReader.nextString();
                            if (this.pattern.contains(s))
                                this.pattern.add(s);
                        }
                        jsonReader.endArray();
                    }
                }
            }
            jsonReader.endObject();
        }

        jsonReader.endArray();

        return pattern.size();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPattern() {
        return pattern;
    }

}
