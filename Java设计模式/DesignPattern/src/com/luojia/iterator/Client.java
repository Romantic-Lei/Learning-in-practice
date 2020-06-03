package com.luojia.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/3
 * @description
 */
public class Client {

    public static void main(String[] args) {
        List<College> collegeList = new ArrayList<>();

        ComputerCollege computerCollege = new ComputerCollege();
        InfoCollege infoCollege = new InfoCollege();

        collegeList.add(computerCollege);
        collegeList.add(infoCollege);

        OutPutImpl outPutImpl = new OutPutImpl(collegeList);
        outPutImpl.printCollege();

    }

}
