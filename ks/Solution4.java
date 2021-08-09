/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 花费时间:30min
 *
 * @author 17863137210
 * @since 2021-08-09
 *
 */
public class Solution4 {
    private static List<Pair<Integer, Integer>> res = new ArrayList<>();

    public static String Dominoes(String input) {

        String[] split = input.replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "").split(",");
        List<Integer> collect = Arrays.stream(split).map(Integer::valueOf).collect(Collectors.toList());
        int index = 0;
        Map<Integer, Integer> idMap = new HashMap<>();
        Map<Integer, Integer> idMap1 = new HashMap<>();
        List<Set<Integer>> path = new ArrayList<>();
        for (int i = 0; i < collect.size(); i++) {
            if (!idMap.containsKey(collect.get(i))) {
                idMap.put(collect.get(i), index);
                idMap1.put(index, collect.get(i));
                index += 1;
                path.add(new HashSet<>());
            }
            if (i % 2 == 1) {
                path.get(idMap.get(collect.get(i - 1))).add(idMap.get(collect.get(i)));
            }
        }
        //
        int[] flag = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            find(i, flag, path);
        }
        StringBuilder result = new StringBuilder();
        if (res.size() > 1) {
            Integer end = res.get(0).getValue();
            for (Pair<Integer, Integer> item : res) {
                result.insert(0, String.format("(%d,%d),", idMap1.get(item.getKey()), idMap1.get(item.getValue())));
                if (item.getKey().equals(end)) {
                    break;
                }
            }
            result.delete(result.length() - 1, result.length());
        }
        return result.toString();
    }

    private static boolean find(int x, int[] flag, List<Set<Integer>> path) {
        if (flag[x] != 0) {
            return flag[x] == 2;
        }
        flag[x] = 1;
        for (int i : path.get(x)) {
            if (!find(i, flag, path)) {
                res.add(new Pair<>(x, i));
                return false;
            }
        }
        flag[x] = 2;
        return true;
    }

    public static void main(String[] args) {
        String input = " (1, 2), (5, 3), (3, 1), (1, 2), (2, 4), (1, 6), (2, 3), (3, 4), (5, 6)";
        Dominoes(input);
    }
}
