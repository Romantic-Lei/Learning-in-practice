package com.romanticlei.huffmanCode;

import java.io.*;
import java.util.*;

public class HuffmanCode {

    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        // 将对应的字母转成 ASCII
        byte[] contentBytes = content.getBytes();
        System.out.println(content.length()); // 40

        List<Node> nodes = getNodes(contentBytes);
        System.out.println("nodes = " + nodes);

        Node root = createHuffmanTree(nodes);
        // 赫夫曼树的根节点是： Node{data=null, weight=40}
        System.out.println("赫夫曼树的根节点是： " + root);
        // preOrder(root);

        System.out.println("测试是否生成了对应的赫夫曼编码");
        getCodes(root, "", stringBuffer);
        // 生成的赫夫曼编码表 {32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
        System.out.println("生成的赫夫曼编码表(根节点到子节点的路径) " + huffmanCodes);

        byte[] zip = zip(contentBytes, huffmanCodes);
        System.out.println("压缩过后的赫夫曼表 " + Arrays.toString(zip));
        System.out.println("压缩过后的赫夫曼表长度为 " + zip.length);

        byte[] decode = decode(huffmanCodes, zip);

        System.out.println("解码赫夫曼编码结果为： " + new String(decode));

        System.out.println("------------------------文件压缩测试----------------------");
        String srcFile = "F:\\桌面\\timg16MFP0RV.jpg";
        String dstFile = "C:\\Users\\asus\\Desktop\\dst.zip";
        zipFile(srcFile, dstFile);
        System.out.println("压缩文件完成！");

        System.out.println("------------------------文件解压测试----------------------");
        String srcFile1 = "C:\\Users\\asus\\Desktop\\dst.zip";
        String dstFile1 = "C:\\Users\\asus\\Desktop\\dst11.jpg";
        unZipFile(srcFile1, dstFile1);
        System.out.println("解压文件完成！");
    }

    private static byte[] huffmanZip(byte[] b) {
        stringBuffer = new StringBuffer();
        huffmanCodes = new HashMap<>();

        List<Node> nodes = getNodes(b);
        // 根据 节点 nodes 创建赫夫曼树
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        // 根据赫夫曼树获取对应的赫夫曼编码
        getCodes(huffmanTreeRoot, "", stringBuffer);
        // 根据生成的赫夫曼编码，压缩得到压缩后的赫夫曼编码字节数组
        byte[] huffmanCodeBytes = zip(b, huffmanCodes);
        return huffmanCodeBytes;
    }

    // 前序遍历
    public static void preOrder(Node root) {
        if (root == null) {
            System.out.println("赫夫曼树为空！");
        } else {
            root.preOrder();
        }
    }

    // 将赫夫曼编码存放在 Map<Byte, String> 形式
    static Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();
    // 在生成赫夫曼编码表示，需要拼接路径，定义一个StringBuilder 存储某个叶子结点的路径
    static StringBuffer stringBuffer = new StringBuffer();

    /**
     * 功能：将传入的Node 节点的所有叶子结点的赫夫曼编码得到，并放入的到集合中
     *
     * @param node         传入节点
     * @param code         路径：左子节点是0， 右子节点是1
     * @param stringBuffer 用于拼接路径
     */
    public static void getCodes(Node node, String code, StringBuffer stringBuffer) {
        StringBuffer stringBuffer1 = new StringBuffer(stringBuffer);
        stringBuffer1.append(code);
        if (node != null) {
            // 如node 为空表示这是非叶子节点
            if (node.data == null) {
                // 递归处理
                // 向左递归
                getCodes(node.left, "0", stringBuffer1);
                // 向右递归
                getCodes(node.right, "1", stringBuffer1);
            } else {
                // 说明是叶子结点
                huffmanCodes.put(node.data, stringBuffer1.toString());
            }
        }
    }

    // 统计字符出现次数
    private static List<Node> getNodes(byte[] bytes) {
        List<Node> nodes = new ArrayList<>();

        // 遍历 byte， 统计每一个 byte出现的次数 -> map[key, value]
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        // 把每个键值对转成一个 Node 对象，并加入到nodes 集合
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        return nodes;
    }

    // 可以通过 List 创建对应的 赫夫曼树
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            // 取出第一颗最小的二叉树节点
            Node leftNode = nodes.get(0);
            // 取出第二颗最小的二叉树节点
            Node rightNode = nodes.get(1);
            // 父节点的数据域都是 null
            Node parent = new Node(null, leftNode.weight + rightNode.weight);

            parent.left = leftNode;
            parent.right = rightNode;

            // 将已处理的两颗二叉树从 nodes 删除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            // 将新的节点放入到集合中
            nodes.add(parent);
        }

        // 返回最后一个结点，这个结点就是赫夫曼树的根节点
        return nodes.get(0);
    }

    /**
     * 编写一个方法，将字符串对应的 byte[] 数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码压缩后的 byte[]
     *
     * @param bytes        原始的字符串对应的 byte[]
     * @param huffmanCodes 生成的赫夫曼编码map
     * @return 返回赫夫曼编码处理后的 byte[]
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        // 利用 huffmanCodes 将 bytes 转成 赫夫曼编码对应的字符串

        for (byte b : bytes) {
            stringBuffer.append(huffmanCodes.get(b));
        }

        System.out.println("赫夫曼编码对应的字符串：" + stringBuffer.toString());

        // 将拼接的赫夫曼转成 byte[]
        // 下面的长度计算方式可以转成一句话 int len = (stringBuffer.length() + 7) / 8
        int len;
        if (stringBuffer.length() % 8 == 0) {
            len = stringBuffer.length() / 8;
        } else {
            len = stringBuffer.length() / 8 + 1;
        }

        int index = 0;
        // 创建压缩存储后的 byte 数组
        byte[] huffmanCodeByte = new byte[len];
        for (int i = 0; i < stringBuffer.length(); i += 8) {
            if (stringBuffer.length() < i + 8) {
                // 最后一个字节可能不是 8个字节，将2进制转成一个int形
                huffmanCodeByte[index++] = (byte) Integer.parseInt(stringBuffer.substring(i), 2);
            } else {
                huffmanCodeByte[index++] = (byte) Integer.parseInt(stringBuffer.substring(i, i + 8), 2);
            }
        }
        return huffmanCodeByte;
    }

    /**
     * 将一个 byte 转成一个二进制的字符串
     *
     * @param flag 表示是否需要补高位，true表示需要，false表示不需要
     * @param b    传入的byte
     * @return
     */
    public static String byteToBitString(boolean flag, byte b) {
        // 使用临时变量
        int temp = b; // 将 b 转成int
        // 如果是正数，我们还存在补高位
        if (flag) {
            temp |= 256; // 按位或 256
        }

        String str = Integer.toBinaryString(temp);

        if (flag) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }

    /**
     * 编写一个方法，完成对压缩数据的解码
     *
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return
     */
    public static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        // 1. 先得到 huffmanBytes 对应的二进制的字符串，形式1010001...
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("最后一位长度：" + huffmanBytes[huffmanBytes.length - 1]);
        System.out.println("倒数第二位长度：" + huffmanBytes[huffmanBytes.length - 2]);
        System.out.println("倒数第二位长度：" + huffmanBytes[huffmanBytes.length - 3]);
        // 将 byte 数组转成二进制的字符串，最后一位单独处理
        for (int i = 0; i < huffmanBytes.length - 1; i++) {
            // 判断是不是最后一个字节,最后一个字符可能存在补高位的情况
            // boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(true, huffmanBytes[i]));
        }

        String lastByteStr = byteToBitString(false, huffmanBytes[huffmanBytes.length - 1]);
        // 判断解压后的赫夫曼编码长度和原来的是否长度相同
        if (stringBuilder.length() + lastByteStr.length() == stringBuffer.length()) {
            stringBuilder.append(lastByteStr);
        } else {
            // 如果长度不一致就直接补0，补到相同为止
            while (stringBuilder.length() + lastByteStr.length() < stringBuffer.length()) {
                stringBuilder.append(0);
            }
            stringBuilder.append(lastByteStr);
        }

        // System.out.println("赫夫曼数组对应的二进制字符串" + stringBuilder.toString());

        HashMap<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        // 创建要给集合，存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1; // 小的计数器
            boolean flag = true;
            Byte b = null;

            while (flag) {
                // 递增截取出 key
                String key = stringBuilder.substring(i, i + count);
                // 对应的key 不存在，重新向后截取
                b = map.get(key);
                if (b == null) {
                    // 继续向后截取
                    count++;
                } else {
                    flag = false;
                }
            }

            list.add(b);
            i += count;

        }

        byte[] bytes = new byte[list.size()];
        for (int j = 0; j < list.size(); j++) {
            bytes[j] = list.get(j);
        }
        return bytes;
    }

    /**
     * 编写一个方法，将文件进行压缩
     *
     * @param srcFile 传入文件的全路径
     * @param dstFile 压缩后文件输出路径
     */
    public static void zipFile(String srcFile, String dstFile) {
        // 创建文件的输入类
        FileInputStream is = null;
        // 创建文件的输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            is = new FileInputStream(srcFile);
            // 创建一个和源文件大小一样的 byte[]
            byte[] b = new byte[is.available()];
            // 读取文件
            is.read(b);
            // 直接对源文件进行压缩
            byte[] huffmanBytes = huffmanZip(b);
            // 创建文件的输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            // 创建一个和文件输出流相关联的 ObjectOutputStream
            oos = new ObjectOutputStream(os);
            // 把赫夫曼编码后的 字节数组 写入到压缩文件
            oos.writeObject(huffmanBytes);

            // 这里我们以对象流的方式写入 赫夫曼编码，是为了以后我们恢复原文件时使用
            oos.writeObject(huffmanCodes);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    os.close();
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 编写一个方法，将文件进行解压
     *
     * @param zipFile 传入压缩文件的全路径
     * @param dstFile 解压后文件输出路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        // 定义文件输入流
        InputStream is = null;
        // 定义一个对象输入流
        ObjectInputStream ois = null;
        // 定义一个文件的输出流
        OutputStream os = null;

        try {
            // 创建文件输入流
            is = new FileInputStream(zipFile);
            // 创建一个和 is 关联的对象输入流
            ois = new ObjectInputStream(is);
            // 读取 byte数组
            byte[] huffmanBytes = (byte[]) ois.readObject();
            // 读取赫夫曼编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            // 解码
            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            // 将byte 数组写入到目标文件
            os = new FileOutputStream(dstFile);
            // 写数据到 dstFile
            os.write(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();

                }

                if (ois != null) {
                    ois.close();
                }

                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

// 创建一个Node，带数据和权值
class Node implements Comparable<Node> {

    Byte data; // 存放数据（字符）本身，比如 'a' = 97  ' ' = 32
    int weight;// 权重
    Node left; // 左子树
    Node right;// 右子树

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    // 前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}
