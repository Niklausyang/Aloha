/*展示文件： 这个是我今天在刷题的时候自己分析所做的Max point的解法，因为时间很赶也没多想就把这道题作为一个展示案例，我把我自己在IDE上的记录放了上来
虽然这只是一道leetcode的题目，我先表达的是我自己的逻辑性思维和critical thinking的能力， 我喜欢将自己对问题的思考过程记录下来，并且在原有的基础上继续
探究其他的可能性，我认为这个方面我可以在工作之中为team为同事节省更多的code review的时间，也同时是一种良好的coding 习惯。


/*Max Points on a Line

描述

Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.

Violence enumeration. Two points to determine a straight line, n points two pairs of combinations, you can get \ dfrac {1} {2} n (n + 1)
2
In the case of
1
N (n + 1) straight lines, for each line, to determine whether n points in the line, which can get the number of points on this line, select the largest straight line to return. Complexity O (n ^ 3).
The above violent enumeration method takes the "edge" as the center, looks at another violent enumeration law, takes each "point" as the center, and then traverses the remaining points, finds all the slopes, and if the slope is the same, For each point,
With a hash table, key for the slope, value for the number of points on the line, calculate the hash table, take the maximum, and update the global maximum, the last is the result. Time complexity O (n ^ 2), spatial complexity O (n).
*/

// Max Points on a Line
// 暴力枚举法，以边为中心，时间复杂度O(n^3)，空间复杂度O(1)
public class Solution {
    public int maxPoints(Point[] points) {
        if (points.length < 3) return points.length;
        int result = 0;

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int sign = 0;
                int a = 0, b = 0, c = 0;
                if (points[i].x == points[j].x) sign = 1;
                else {
                    a = points[j].x - points[i].x;
                    b = points[j].y - points[i].y;
                    c = a * points[i].y - b * points[i].x;
                }
                int count = 0;
                for (int k = 0; k < points.length; k++) {
                    if ((0 == sign && a * points[k].y == c +  b * points[k].x) ||
                            (1 == sign&&points[k].x == points[j].x))
                        count++;
                }
                if (count > result) result = count;
            }
        }
        return result;
    }
}

// Max Points on a Line
// 暴力枚举，以点为中心，时间复杂度O(n^2)，空间复杂度O(n^2)
public class SolutionII {
    public int maxPoints(Point[] points) {
        if (points.length < 3) return points.length;
        int result = 0;

        HashMap<Double, Integer> slope_count = new HashMap<>();
        for (int i = 0; i < points.length-1; i++) {
            slope_count.clear();
            int samePointNum = 0; // 与i重合的点
            int point_max = 1;    // 和i共线的最大点数

            for (int j = i + 1; j < points.length; j++) {
                final double slope; // 斜率
                if (points[i].x == points[j].x) {
                    slope = Double.POSITIVE_INFINITY;
                    if (points[i].y == points[j].y) {
                        ++ samePointNum;
                        continue;
                    }
                } else {
                    if (points[i].y == points[j].y) {
                        // 0.0 and -0.0 is the same
                        slope = 0.0;
                    } else {
                        slope = 1.0 * (points[i].y - points[j].y) /
                                (points[i].x - points[j].x);
                    }
                }

                int count = 0;
                if (slope_count.containsKey(slope)) {
                    final int tmp = slope_count.get(slope);
                    slope_count.put(slope, tmp + 1);
                    count = tmp + 1;
                } else {
                    count = 2;
                    slope_count.put(slope, 2);
                }

                if (point_max < count) point_max = count;
            }
            result = Math.max(result, point_max + samePointNum);
        }
        return result;
    }
}
