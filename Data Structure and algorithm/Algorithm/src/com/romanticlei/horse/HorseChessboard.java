package com.romanticlei.horse;

import java.awt.*;
import java.util.ArrayList;

public class HorseChessboard {

    private static int X;   // 棋盘的列数(从 0 开始)
    private static int Y;   // 棋盘的行数(从 0 开始)

    public static void main(String[] args) {

    }

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

