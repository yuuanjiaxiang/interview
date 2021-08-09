/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 花费时间:20min
 *
 * @author 17863137210
 * @since 2021-08-09
 *
 */
public class Solution3 {
    public static class TreeNode {
        private String name;
        private int id;
        private List<TreeNode> sons = new ArrayList<>();

        private int parent = -1;

        TreeNode(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static TreeNode CreateTree(String input) {
        String[] lines = input.split("\n");
        Map<Integer, TreeNode> id2node = new HashMap<>();
        for (String line : lines) {
            int nodeId = -1;
            String nodeName = "";
            String id = "id:(\\d+)";
            Pattern r = Pattern.compile(id);
            Matcher m = r.matcher(line);
            if (m.find()) {
                nodeId = Integer.parseInt(m.group(1));
                if (id2node.containsKey(nodeId)) {
                    continue;
                }
            }

            String name = "name:\\s*'(\\S+)'";
            r = Pattern.compile(name);
            m = r.matcher(line);
            if (m.find()) {
                nodeName = m.group(1);
            }
            if (nodeId != -1 && !nodeName.equals("")) {
                TreeNode node = new TreeNode(nodeId, nodeName);
                id2node.put(nodeId, node);
                String parentId = "parentId\\s*:\\s+(\\d+)";
                r = Pattern.compile(parentId);
                m = r.matcher(line);
                if (m.find()) {
                    node.parent = Integer.parseInt(m.group(1));
                }
            }

        }
        TreeNode root = null;
        for (TreeNode node : id2node.values()) {
            if (node.parent == -1) {
                root = node;
            } else if (id2node.get(node.parent) != null) {
                id2node.get(node.parent).sons.add(node);
            } else {
                return null;
            }
        }
        // ListNode
        return root;
    }

    public static void main(String[] args) {
        String input1 = "[\n" + "    {id:1, name: 'i1'},\n" + "    {id:2, name:'i2', parentId: 1},\n"
            + "    {id:4, name:'i4', parentId: 3},\n" + "    {id:3, name:'i3', parentId: 2},\n"
            + "    {id:8, name:'i8', parentId: 7}\n" + "]";
        CreateTree(input1);
    }
}
