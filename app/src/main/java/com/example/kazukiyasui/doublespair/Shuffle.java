package com.example.kazukiyasui.doublespair;

import java.util.Random;

public class Shuffle {

    private int[] list;     //出力済みの数値リスト
    private int   count;

    public Shuffle(){
        //初期化
        count = 0;
    }

    public void SetList(int num)
    {
        //数字を配置
        list = new int[num];
    }

    public int GetShuffleList(int player_num)
    {
        int answer = 0;

        //自分より前の要素にかぶるやつがないか確かめる。
        //あったらもう1回random
        boolean flag = false;
        answer = (int) (Math.random() * player_num + 1);
        do {
            flag = false;
            for (int i = 0; i < list.length; i++) {
                if (answer == list[i]) {
                    flag = true;
                    answer = (int) (Math.random() * player_num + 1);
                }
            }

        } while (flag == true);
        list[count] = answer;
        count++;
        return answer;
    }
}
