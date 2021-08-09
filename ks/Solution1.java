/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021-2021. All rights reserved.
 */

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 花费时间:40min
 *
 * @author 17863137210
 * @since 2021-08-09
 *
 */
public class Solution1 {

    public static String Tournament(String segment) {
        String[] lines = segment.split("\n");
        TreeMap<String, int[]> map = new TreeMap<>();
        for (String line : lines) {
            String[] cons = line.split(";");
            if (cons.length != 3) {
                continue;//需要打印日志
            }
            int[] first = map.getOrDefault(cons[0], new int[3]);
            int[] second = map.getOrDefault(cons[1], new int[3]);
            switch (cons[2].toLowerCase(Locale.ROOT)) {
                case "win":
                    first[0] = first[0] + 1;
                    second[2] = second[2] + 1;
                    break;
                case "loss":
                    first[2] = first[2] + 1;
                    second[0] = second[0] + 1;
                    break;
                case "draw":
                    first[1] = first[1] + 1;
                    second[1] = second[1] + 1;
                    break;
                default:
                    break;//需要打印日志
            }
            map.put(cons[0], first);
            map.put(cons[1], second);
        }

        Comparator<Map.Entry<String, int[]>> comparator = new Comparator<Map.Entry<String, int[]>>() {
            @Override
            public int compare(Map.Entry<String, int[]> o1, Map.Entry<String, int[]> o2) {
                int[] value1 = o1.getValue();
                int[] value2 = o2.getValue();
                if (value1[0] == value2[0]) {
                    if (value1[1] == value2[1]) {
                        if (value1[2] == value2[2]) {
                            return o1.getKey().compareTo(o2.getKey());
                        } else {
                            return Integer.compare(value2[2], value1[2]);
                        }
                    } else {
                        return Integer.compare(value2[1], value1[1]);
                    }
                } else {
                    return Integer.compare(value2[0], value1[0]);
                }
            }
        };
        List<Map.Entry<String, int[]>> list = map.entrySet().stream().collect(Collectors.toList());
        list.sort(comparator);
        StringBuilder builder = new StringBuilder();
        builder.append("Team                            | MP |  W |  D |  L |  P\n");
        for (Map.Entry<String, int[]> item : list) {
            int[] value = item.getValue();
            builder.append(String.format("%-32s|%3d |%3d |%3d |%3d |%3d\n", item.getKey(),
                value[0] + value[1] + value[2], value[0], value[1], value[2], 3 * value[0] + value[1]));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        String s = "Allegoric Alaskans;Blithering Badgers;win\n" + "Devastating Donkeys;Courageous Californians;draw\n"
            + "Devastating Donkeys;Allegoric Alaskans;win\n" + "Courageous Californians;Blithering Badgers;loss\n"
            + "Blithering Badgers;Devastating Donkeys;loss\n" + "Allegoric Alaskans;Courageous Californians;win";
        Tournament(s);
    }
}
