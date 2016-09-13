//package com.rodionxedin.util;
//
//import com.rodionxedin.util.model.TreeNode;
//import sun.reflect.generics.tree.Tree;
//
//import java.util.List;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
///**
// * Created by rodion.shkrobot on 8/29/2016.
// */
//public class PredictionsUtils {
//
//    private static TreeNode phrases;
//
//    static {
//        phrases = new TreeNode("");
//        TreeNode every = new TreeNode("Every");
//        phrases.add(every);
//        every.add(new TreeNode("Monday"));
//        every.add(new TreeNode("Tuesday"));
//        every.add(new TreeNode("Wednesday"));
//        every.add(new TreeNode("Thursday"));
//        every.add(new TreeNode("Friday"));
//        every.add(new TreeNode("Saturday"));
//        every.add(new TreeNode("Sunday"));
//        every.add(new TreeNode("day"));
//        every.add(new TreeNode("Month"));
//        every.add(new TreeNode("Year"));
//    }
//
//
//    public static List<String> getPredictions(String currentString) {
//        String currentWord = currentString.toLowerCase().split(" ")[currentString.split(" ").length - 1];
//        return null;
//    }
//
//}
