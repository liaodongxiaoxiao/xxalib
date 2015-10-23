package com.ldxx.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by LDXX on 2015/8/24.
 * company Ltd
 * liaodongxiaoxiao@gmail.com
 */
public class Test {
    public static void main(String[] args){
        List<String> list= new ArrayList<>();
        list.add("02");
        list.add("45");
        list.add("a");
        list.add("34");
        System.out.println("------------old------------");
        for (String str:list){
            System.out.println(str);
        }
        System.out.println("------------asc------------");
        Collections.sort(list,new ListStringComparator());
        for (String str:list){
            System.out.println(str);
        }
        System.out.println("------------desc------------");
        Collections.sort(list, new ListStringComparator(true));
        for (String str:list){
            System.out.println(str);
        }
    }
}
