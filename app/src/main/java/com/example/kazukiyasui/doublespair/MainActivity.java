package com.example.kazukiyasui.doublespair;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //定数宣言
    private static final int COURT_MAX_NUM = 3;
    private static final int COURT_IN_PLAYER = 4;

    //変数宣言
    private AlertDialog alertDialog;
    //protected ArrayAdapter<String> adapter;
    private Button spbt_court;
    private Button spbt_player;
    protected String[] court_ary;
    protected String[] player_ary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice);

        Resources res = getResources();
        court_ary = res.getStringArray(R.array.txt_court_arr);
        player_ary = res.getStringArray(R.array.txt_player_arr);

        //スピナーボタンの設定
        spbt_court = findViewById(R.id.spbtn_court);
        spbt_player = findViewById(R.id.spbtn_player);
        spbt_court.setOnClickListener(this);
        spbt_player.setOnClickListener(this);


        ImageButton ibt_shuffle = findViewById(R.id.ibtn_shuffle);
        ibt_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(100);

                //プレーヤーのImageViewを初期化（空白）
                initImageView();

                int court_num = Integer.parseInt(((Button)findViewById(R.id.spbtn_court)).getText().toString());//Integer.parseInt((String)((Spinner)findViewById(R.id.spr_court)).getSelectedItem());
                int player_num = Integer.parseInt(((Button)findViewById(R.id.spbtn_player)).getText().toString());//Integer.parseInt((String)((Spinner)findViewById(R.id.spr_person)).getSelectedItem());

                //コート数チェック
                switch (court_num){
                    case 1:
                        break;
                    case 2:
                        if (player_num >= 4 && player_num <= 7){        //プレイヤー4～7名の場合
                            court_num = 1;  //コート1個に設定
                        }
                        break;
                    case 3:
                        if (player_num >= 4 && player_num <= 7){        //プレイヤー4～7名の場合
                            court_num = 1;  //コート1個に設定
                        }else if (player_num >= 8 && player_num <= 11){ //プレイヤー8～11名の場合
                            court_num = 2;  //コート2個に設定
                        }
                        break;
                }

                Shuffle shuffle = new Shuffle();
                shuffle.SetList(court_num * COURT_IN_PLAYER);

                for (int court = 1; court <= court_num; court++){
                    for (int person = 1; person <= COURT_IN_PLAYER; person++){
                        int answer = shuffle.GetShuffleList(player_num);
                        selectCourt(court, person, answer);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        switch(view.getId()){
            case R.id.spbtn_court:
                builder.setItems(R.array.txt_court_arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        spbt_court.setText(court_ary[i]);
                        alertDialog.dismiss();
                    }
                });
                break;
            case R.id.spbtn_player:
                builder.setItems(R.array.txt_player_arr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        spbt_player.setText(player_ary[i]);
                        alertDialog.dismiss();
                    }
                });
                break;
        }

        alertDialog = builder.create();
        alertDialog.show();
    }

    private void initImageView(){
        ImageView imgV;
        int ids[][] = new int[][]{
                {R.id.iv_court1_left1, R.id.iv_court1_left2, R.id.iv_court1_right1, R.id.iv_court1_right2},
                {R.id.iv_court2_left1, R.id.iv_court2_left2, R.id.iv_court2_right1, R.id.iv_court2_right2},
                {R.id.iv_court3_left1, R.id.iv_court3_left2, R.id.iv_court3_right1, R.id.iv_court3_right2}
        };

        for (int court = 0; court < COURT_MAX_NUM; court++){
            for (int player = 0; player < COURT_IN_PLAYER; player++){
                imgV = findViewById(ids[court][player]);
                imgV.setImageResource(R.drawable.icon_blank);
            }
        }
//        //1コート
//        imgV = findViewById(R.id.iv_court1_left1);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court1_left2);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court1_right1);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court1_right2);
//        imgV.setImageResource(R.drawable.icon_blank);
//        //2コート
//        imgV = findViewById(R.id.iv_court2_left1);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court2_left2);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court2_right1);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court2_right2);
//        imgV.setImageResource(R.drawable.icon_blank);
//        //3コート
//        imgV = findViewById(R.id.iv_court3_left1);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court3_left2);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court3_right1);
//        imgV.setImageResource(R.drawable.icon_blank);
//        imgV = findViewById(R.id.iv_court3_right2);
//        imgV.setImageResource(R.drawable.icon_blank);
    }

    private void selectCourt(int court_num, int player_num, int answer){

        ImageView imgV_court_in_person = null;

        //コート1-3まで
        switch (court_num){
            case 1:
                //1コート　1～35人まで
                switch (player_num){
                    case 1:
                        imgV_court_in_person = findViewById(R.id.iv_court1_left1);
                        break;
                    case 2:
                        imgV_court_in_person = findViewById(R.id.iv_court1_left2);
                        break;
                    case 3:
                        imgV_court_in_person = findViewById(R.id.iv_court1_right1);
                        break;
                    case 4:
                        imgV_court_in_person = findViewById(R.id.iv_court1_right2);
                        break;
                }
                break;
            case 2:
                //2コート　1～35人まで
                switch (player_num){
                    case 1:
                        imgV_court_in_person = findViewById(R.id.iv_court2_left1);
                        break;
                    case 2:
                        imgV_court_in_person = findViewById(R.id.iv_court2_left2);
                        break;
                    case 3:
                        imgV_court_in_person = findViewById(R.id.iv_court2_right1);
                        break;
                    case 4:
                        imgV_court_in_person = findViewById(R.id.iv_court2_right2);
                        break;
                }
                break;
            case 3:
                //3コート　1～35人まで
                switch (player_num){
                    case 1:
                        imgV_court_in_person = findViewById(R.id.iv_court3_left1);
                        break;
                    case 2:
                        imgV_court_in_person = findViewById(R.id.iv_court3_left2);
                        break;
                    case 3:
                        imgV_court_in_person = findViewById(R.id.iv_court3_right1);
                        break;
                    case 4:
                        imgV_court_in_person = findViewById(R.id.iv_court3_right2);
                        break;
                }
                break;
        }
        //アイコンを設定する
        setIcon(imgV_court_in_person, answer);
    }


    private void setIcon(ImageView imgV, int answer){

        if (imgV == null) return;

        switch (answer){
            case 1:
                imgV.setImageResource(R.drawable.icon_1);
                break;
            case 2:
                imgV.setImageResource(R.drawable.icon_2);
                break;
            case 3:
                imgV.setImageResource(R.drawable.icon_3);
                break;
            case 4:
                imgV.setImageResource(R.drawable.icon_4);
                break;
            case 5:
                imgV.setImageResource(R.drawable.icon_5);
                break;
            case 6:
                imgV.setImageResource(R.drawable.icon_6);
                break;
            case 7:
                imgV.setImageResource(R.drawable.icon_7);
                break;
            case 8:
                imgV.setImageResource(R.drawable.icon_8);
                break;
            case 9:
                imgV.setImageResource(R.drawable.icon_9);
                break;
            case 10:
                imgV.setImageResource(R.drawable.icon_10);
                break;
            case 11:
                imgV.setImageResource(R.drawable.icon_11);
                break;
            case 12:
                imgV.setImageResource(R.drawable.icon_12);
                break;
            case 13:
                imgV.setImageResource(R.drawable.icon_13);
                break;
            case 14:
                imgV.setImageResource(R.drawable.icon_14);
                break;
            case 15:
                imgV.setImageResource(R.drawable.icon_15);
                break;
            case 16:
                imgV.setImageResource(R.drawable.icon_16);
                break;
            case 17:
                imgV.setImageResource(R.drawable.icon_17);
                break;
            case 18:
                imgV.setImageResource(R.drawable.icon_18);
                break;
            case 19:
                imgV.setImageResource(R.drawable.icon_19);
                break;
            case 20:
                imgV.setImageResource(R.drawable.icon_20);
                break;
            case 21:
                imgV.setImageResource(R.drawable.icon_21);
                break;
            case 22:
                imgV.setImageResource(R.drawable.icon_22);
                break;
            case 23:
                imgV.setImageResource(R.drawable.icon_23);
                break;
            case 24:
                imgV.setImageResource(R.drawable.icon_24);
                break;
            case 25:
                imgV.setImageResource(R.drawable.icon_25);
                break;
            case 26:
                imgV.setImageResource(R.drawable.icon_26);
                break;
            case 27:
                imgV.setImageResource(R.drawable.icon_27);
                break;
            case 28:
                imgV.setImageResource(R.drawable.icon_28);
                break;
            case 29:
                imgV.setImageResource(R.drawable.icon_29);
                break;
            case 30:
                imgV.setImageResource(R.drawable.icon_30);
                break;
            case 31:
                imgV.setImageResource(R.drawable.icon_31);
                break;
            case 32:
                imgV.setImageResource(R.drawable.icon_32);
                break;
            case 33:
                imgV.setImageResource(R.drawable.icon_33);
                break;
            case 34:
                imgV.setImageResource(R.drawable.icon_34);
                break;
            case 35:
                imgV.setImageResource(R.drawable.icon_35);
                break;
        }
    }


}
