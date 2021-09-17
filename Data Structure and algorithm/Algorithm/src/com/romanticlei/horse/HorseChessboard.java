package com.romanticlei.horse;

import java.awt.*;
import java.util.ArrayList;

public class HorseChessboard {

    private static int X;   // 棋盘的列数(从 0 开始)
    private static int Y;   // 棋盘的行数(从 0 开始)
    // 创建一个数组，标记棋盘的各个位置是否被访问量过
    private static boolean visited[];
    // 标记是否棋盘的所有位置都被访问，如果为 true，表示成功
    private static boolean finished;

    public static void main(String[] args) {

    }

    /**
     * 完成骑士周游列国的算法
     * @param chessboard    棋盘
     * @param row           马儿当前位置的行 从0开始
     * @param column         马儿当前位置的列 从0开始
     * @param step          是第几步，初始位置就是第1步
     */
    public static void traversalChessboard(int[][] chessboard, int row, int column, int step) {
        // 标记当前位置是第几步
        chessboard[row][column] = step;
        // 标记该点已访问
        visited[row * X + column] = true;
        ArrayList<Point> ps = next(new Point(column, row));
        
    }

    /**
     * 根据当前位置（Point对象），计算马儿还能走那些位置（Point），
     * 并放入到一个集合中（ArrayList），最多能有8个位置能走
     * @param curPoint
     * @return
     */
    public static ArrayList<Point> next(Point curPoint) {
        // 创建一个 ArrayList
        ArrayList<Point> ps = new ArrayList<Point>();
        // 创建一个 Point
        Point p1 = new Point();

        // 表示当前结点 左左上这个位置
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }
        // 表示当前结点 左上上这个位置
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        // 表示当前结点 右上上这个位置
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p1));
        }
        // 表示当前结点 右右上这个位置
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p1));
        }

        // 表示当前结点 右右下这个位置
        if ((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }
        // 表示当前结点 右下下这个位置
        if ((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        // 表示当前结点 左下下这个位置
        if ((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p1));
        }
        // 表示当前结点 左左下这个位置
        if ((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p1));
        }

        return ps;
    }
}

