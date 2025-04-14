package dml.majiang.specialrules.guipai.entity;

import dml.majiang.core.entity.MajiangPai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuipaiActPaiTypeCombinationCaculator {
    private static Map<String, List<MajiangPai[]>> cache = new ConcurrentHashMap<>();

    public static List<MajiangPai[]> get(int guipaiCount, List<MajiangPai> guipaiActPaiTypeList) {
        String key = generateKey(guipaiCount, guipaiActPaiTypeList);
        List<MajiangPai[]> guipaiActPaiTypeCombinationList = cache.get(key);
        if (guipaiActPaiTypeCombinationList == null) {
            guipaiActPaiTypeCombinationList = new ArrayList<>();
            caculateGuipaiActPaiTypeCombination(guipaiActPaiTypeList, 0,
                    new MajiangPai[guipaiCount], 0,
                    guipaiActPaiTypeCombinationList);
            cache.putIfAbsent(key, guipaiActPaiTypeCombinationList);
        }
        return guipaiActPaiTypeCombinationList;
    }

    private static String generateKey(int guipaiCount, List<MajiangPai> guipaiActPaiTypeList) {
        return guipaiCount + "@" + Arrays.toString(guipaiActPaiTypeList.toArray());
    }

    private static void caculateGuipaiActPaiTypeCombination(List<MajiangPai> guipaiActPaiTypeList, int guipaiActPaiTypeListIndex,
                                                            MajiangPai[] combination, int guipaiIndex,
                                                            List<MajiangPai[]> combinationStor) {
        combination[guipaiIndex] = guipaiActPaiTypeList.get(guipaiActPaiTypeListIndex);
        if (guipaiIndex == combination.length - 1) {
            //保存组合
            combinationStor.add(combination);
        } else {//combination鬼牌还没取满
            //递推给combination下一个格位取鬼牌，从同一个guipaiActPaiTypeListIndex开始取起
            MajiangPai[] newCombination = Arrays.copyOf(combination, combination.length);
            int newGuipaiIndex = guipaiIndex + 1;
            caculateGuipaiActPaiTypeCombination(guipaiActPaiTypeList, guipaiActPaiTypeListIndex,
                    newCombination, newGuipaiIndex, combinationStor);
        }
        //guipaiActPaiTypeCombinationList没有尝试完就递推
        if (guipaiActPaiTypeListIndex < guipaiActPaiTypeList.size() - 1) {
            int newGuipaiActPaiTypeListIndex = guipaiActPaiTypeListIndex + 1;
            MajiangPai[] newCombination = Arrays.copyOf(combination, combination.length);
            caculateGuipaiActPaiTypeCombination(guipaiActPaiTypeList, newGuipaiActPaiTypeListIndex,
                    newCombination, guipaiIndex, combinationStor);
        }
    }


}
