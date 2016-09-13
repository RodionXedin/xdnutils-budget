//package com.rodionxedin.util.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by rodion.shkrobot on 8/29/2016.
// */
//public class TreeNode {
//    private String data;
//    private TreeNode parent;
//    private List<TreeNode> children;
//
//    public TreeNode(String data, TreeNode parent) {
//        this.data = data;
//        this.parent = parent;
//        this.children = new ArrayList<>();
//    }
//
//    public TreeNode(String data) {
//        this.data = data;
//    }
//
//    public void add(TreeNode tTreeNode) {
//        tTreeNode.setParent(this);
//        children.add(tTreeNode);
//    }
//
//    public String lookup(String lookupString) {
//        String[] lookupItems = lookupString.split(" ");
//        return lookup(lookupItems);
//    }
//
//    private String lookup(String[] lookupArray) {
//        TreeNode child = getChild(lookupArray[0]);
//
//        return null;
////        return child.lookup(System.arraycopy(lookupArray, 1 , 0, lookupArray.length - 1));
//    }
//
//    public TreeNode getChild(String key) {
//        for (TreeNode child : children) {
//            if (child.data.equalsIgnoreCase(key)) {
//                return child;
//            }
//        }
//        return null;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public TreeNode getParent() {
//        return parent;
//    }
//
//    public void setParent(TreeNode parent) {
//        this.parent = parent;
//    }
//
//    public List<TreeNode> getChild() {
//        return children;
//    }
//
//    public void setChildren(List<TreeNode> children) {
//        this.children = children;
//    }
//
//}
