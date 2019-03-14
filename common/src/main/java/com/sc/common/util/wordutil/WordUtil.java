package com.sc.common.util.wordutil;

/**
 * 相关的jar包
 * lucene-core-3.6.2.jar,lucene-memory-3.6.2.jar,
 * lucene-highlighter-3.6.2.jar,lucene-analyzers-3.6.2.jar
 * IKAnalyzer2012.jar
 * <p>
 * 截取一片文章中频繁出现的关键字，并给予分组排序（倒叙），以数组格式返回n个关键字
 * <p>
 * 并该类内部含有一个List2Map方法，可将重复<String>集合转换为Map<String, Integer>格式
 * 并算出该<String>重复次数，放入相应的value中
 */

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.Map.Entry;

/**
 * 获取文章关键字
 *
 * @author anwj
 */
public class WordUtil {
    /**
     * 测试文章
     */
    static String keyWord = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，" +
            "但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
    /**
     * 获取关键字个数
     */
    private final static Integer NUM = 5;
    /**
     * 截取关键字在几个单词以上的数量
     */
    private final static Integer QUANTITY = 1;

    /**
     * 传入String类型的文章，智能提取单词放入list中
     *
     * @param article
     * @param a
     * @return
     * @throws IOException
     */
    private static List<String> extract(String article, Integer a) throws IOException {
        List<String> list = new ArrayList<String>(); //定义一个list来接收将要截取出来单词
        IKAnalyzer analyzer = new IKAnalyzer(); //初始化IKAnalyzer
        analyzer.setUseSmart(true); //将IKAnalyzer设置成智能截取
        TokenStream tokenStream = //调用tokenStream方法(读取文章的字符流)
                analyzer.tokenStream("", new StringReader(article));
        while (tokenStream.incrementToken()) { //循环获得截取出来的单词
            CharTermAttribute charTermAttribute = //转换为char类型
                    tokenStream.getAttribute(CharTermAttribute.class);
            String keWord = charTermAttribute.toString(); //转换为String类型
            if (keWord.length() > a) { //判断截取关键字在几个单词以上的数量(默认为2个单词以上)
                list.add(keWord); //将最终获得的单词放入list集合中
            }
        }
        return list;
    }

    /**
     * 将list中的集合转换成Map中的key，value为数量默认为1
     *
     * @param list
     * @return
     */
    private static Map<String, Integer> list2Map(List<String> list) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String key : list) { //循环获得的List集合
            if (list.contains(key)) { //判断这个集合中是否存在该字符串
                map.put(key, map.get(key) == null ? 1 : map.get(key) + 1);
            } //将集中获得的字符串放在map的key键上
        } //并计算其value是否有值，如有则+1操作
        return map;
    }

    /**
     * 提取关键字方法
     *
     * @param article
     * @param a
     * @param n
     * @return
     * @throws IOException
     */
    public static String[] getKeyWords(String article, Integer a, Integer n) throws IOException {
        List<String> keyWordsList = extract(article, a); //调用提取单词方法
        Map<String, Integer> map = list2Map(keyWordsList); //list转map并计次数


        //使用Collections的比较方法进行对map中value的排序
        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        Map<String, Integer> resultMap = sortMapByValue(map); //按Value进行排序
        System.out.println("******************* map start **********************");
        for (Map.Entry<String, Integer> entry : resultMap.entrySet()) {
            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            //entry.getKey() ;entry.getValue(); entry.setValue();
            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            System.out.println("key= " + entry.getKey() + " and value= "
                    + entry.getValue());
        }
        System.out.println("******************* map end **********************");
        if (list.size() < n) {
            n = list.size(); //排序后的长度，以免获得到null的字符
        }
        String[] keyWords = new String[n]; //设置将要输出的关键字数组空间
        for (int i = 0; i < list.size(); i++) { //循环排序后的数组
            if (i < n) { //判断个数
                keyWords[i] = list.get(i).getKey(); //设置关键字进入数组
            }
        }
        return keyWords;
    }


    /**
     * 使用 Map按value进行排序
     * @param
     * @return
     */
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
        Map.Entry<String, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    static class MapValueComparator implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Entry<String, Integer> me1, Entry<String, Integer> me2) {

            return me2.getValue().compareTo(me1.getValue());
        }
    }

    /**
     * @param article
     * @return
     * @throws IOException
     */
    public static String[] getKeyWords(String article) throws IOException {
        return getKeyWords(article, QUANTITY, NUM);
    }

    public static void main(String[] args) {
        try {
            String[] keywords = getKeyWords(keyWord);
            for (int i = 0; i < keywords.length; i++) {
                System.out.println(keywords[i]);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
