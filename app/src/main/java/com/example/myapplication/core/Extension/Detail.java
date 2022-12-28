package com.example.myapplication.core.Extension;

import android.util.JsonReader;

import androidx.annotation.NonNull;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList name = document.getElementsByTagName("name");
            this.name = name.item(0).getFirstChild().getNodeValue();
            NodeList pattern = document.getElementsByTagName("pattern");
            readPattern(pattern);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            //此时大多数情况下为空
            e.printStackTrace();
        }


    }

    private void readPattern(@NonNull NodeList detail) {
        for (int i = 0; i < detail.getLength(); i++) {
            Node item = detail.item(i).getFirstChild();
            this.pattern.add(item.getNodeValue());

        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPattern() {
        return pattern;
    }

}
