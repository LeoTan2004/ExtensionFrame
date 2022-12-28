package com.example.myapplication.core.Extension;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 加载扩展前boot会根据扩展的check结果来决定是否加载扩展
 * 这里的决定权在boot，为了让boot拥有最高权限，
 * 加载函数就是startup
 * 但是这个函数只负责加载，不负责运行，具体运行可以函数库之中定义，可是由于先后顺序有一定差别
 * startup函数会将文件名为“main.js”的文件最后加载，这也就规范了main函数作为其实文件，
 * 当然也可以不作为其实文件，比如你只是单独写一个供其他人调用的工具js，那么他是不需要运行的，
 * 而是等待其他人调用，就如同库函数一样。
 */


/**
 * JsExtension的基本目录结构
 * name-|_________readme.xml
 *      |
 *      |_________js-|_________main.js
 *                   |
 *                   |_________xxx.js
 *                   |
 *                   |_________xxx.js
 */
public class JsExtension implements Extension{
    private String id;

    public String getId() {
        return id;
    }

    static HashMap<String,String> s = new HashMap<>(){{
        put("js","js");
        put("descript","readme.xml");
    }};
    private final static HashMap<String ,File> directory = new HashMap<>(){{
        for (String s1 : s.keySet()) {
            put(s1,null);
        }
    }};
    private String path;
    private HashMap<String,Javascript> javascriptHashMap = new HashMap<>();
    private String name;
    private Detail detail;

    private JsExtension(String path, String name) {
        this.path = path;
        this.name = name;
    }

    @Nullable
    public static JsExtension getInstance(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        if (!isJsExtension(path)) return null;
        //检测已经通过，可以开始创建对象
        //Js part1
        JsExtension jsExtension = new JsExtension(path,file.getName());
        File []JsDir = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().equals(s.get("js"));
            }
        });
        jsExtension.directory.replace("js",JsDir[0]);
        jsExtension.setJavascripts();
        //descript part2
        File []descript = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().equals(s.get("descript"));
            }
        });
        jsExtension.directory.replace("descript",descript[0]);
        //相关的信息建立
        jsExtension.detail = new Detail(jsExtension.directory.get("descript"));
        jsExtension.id = String.valueOf(jsExtension.hashCode());
        return jsExtension;
    }

    public int setJavascripts() throws Exception {
        int counter = 0;
        File[] js = this.directory.get("js").listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".js");
            }
        });
        for (File j : js) {
            counter++;
            this.addJs(new Javascript(j));
        }
        return counter;
    }

    private void addJs(Javascript javascript){
        if (!this.javascriptHashMap.containsValue(javascript)){
            this.javascriptHashMap.put(javascript.getId(),javascript);
        }
    }

    private static boolean isJsExtension(String path) {
        File file = new File(path);
        //检测存在与否以及是否是一个目录
        if (!file.exists() || !file.isDirectory()){
            return false;
        }
        //获取相应的目录
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return JsExtension.s.containsValue(file.getName());
            }

        });
        //todo 检测有问题，获取到的文件数量3，也就是里面的筛选器煤气作用\


        //检测基本的目录结构是否完整
        if (files.length<s.size()){
            return false;
        }
        return true;
    }

    /**
     * @brief 对于整个扩展js文件的注入，包含多个文件
     * @param webView
     */
    public void startup(@NonNull WebView webView) {
        //可以根据需要在这里选着是否插入js文件，也可以完成一些系统初始化的判断
        //注入js,要初始化注射器，注射器会在WebView中生成一个叫__inject的函数，传入src，自动生成
        Javascript mainJs = null;
        initInjector(webView);
        for (Javascript javascript : this.javascriptHashMap.values()) {
            if (javascript.getFile().getName().equals("main.js")){
                mainJs = javascript;
                continue;
            }
            injector(webView,javascript);
        }
        if (mainJs != null) {
            injector(webView,mainJs);
        }
    }

    /**
     * @bridf 注射器，对于单个js文件的注入
     * @param webView
     * @param js js对象
     */
    public void injector(@NonNull WebView webView, @NonNull Javascript js){
        String src = "JAVASCRIPT:"+this.id+js.getId();
        String injection = "__inject(\""+src+"\");";
        webView.loadUrl("javascript: "+injection);
    }

    private static void initInjector(@NonNull WebView webView){

        String injection = "function __inject(string src){"+
                "\tvar header = document.getElementsByTagName(\"body\")[0];\n" +
                "\tvar injection = document.createElement(\"script\");\n" +
                "\tinjection.src= src;\n" +
                "\tinjection.className= \"injectedJs\";\n" +
                "\theader.appendChild(injection);\n}";
        webView.loadUrl("javascript: " + injection);
    }

    /**
     * @param url 网页当前的url
     * @return 是否符合url
     */
    public boolean check(String url) {
        for (String s : this.detail.getPattern()) {
            if (Pattern.matches(s,url)){
                return true;
            }
        }
        return false;
    }

    public Object invoke(Object o) {
        //todo 做本地js的文件返回，如果没有就返回null
        //每一个extension一个Js列表，用hashmap方式查询返回
        //外部也采用hashmap查询，
        String url = (String) o ;
        Javascript js;
        if ((js = this.javascriptHashMap.get(url)) == null) {
            return null;
        }else{
            try {
                return new WebResourceResponse("application/javascript","UTF-8",new FileInputStream(js.getFile()));
            } catch (FileNotFoundException e) {
                return null;
            }
        }

    }
}



