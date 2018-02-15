package com.shhridoy.worldcup2018russia.myRecyclerViewData;

import com.shhridoy.worldcup2018russia.R;

/**
 * Created by Dream Land on 2/15/2018.
 */

public class Flags {

    public static int getFlag(String country) {
        if (country.contains("Russia")) {
            return R.drawable.ru;
        } else if (country.contains("Saudi Arabia")) {
            return R.drawable.sa;
        } else if (country.contains("Egypt")) {
            return R.drawable.eg;
        } else if (country.contains("Uruguay")) {
            return R.drawable.uy;
        } else if (country.contains("Portugal")) {
            return R.drawable.pt;
        } else if (country.contains("Spain")) {
            return R.drawable.es;
        } else if (country.contains("Morocco")) {
            return R.drawable.ma;
        } else if (country.contains("Iran")) {
            return R.drawable.ir;
        } else if (country.contains("France")) {
            return R.drawable.fr;
        } else if (country.contains("Australia")){
            return R.drawable.au;
        } else if (country.contains("Peru")) {
            return R.drawable.pe;
        } else if (country.contains("Denmark")) {
            return R.drawable.dk;
        } else if (country.contains("Argentina")) {
            return R.drawable.ar;
        } else if (country.contains("Iceland")) {
            return R.drawable.is;
        } else if (country.contains("Nigeria")) {
            return R.drawable.ng;
        } else if (country.contains("Croatia")) {
            return R.drawable.hr;
        } else if (country.contains("Brazil")) {
            return R.drawable.br;
        } else if (country.contains("Switzerland")) {
            return R.drawable.ch;
        } else if (country.contains("Costa Rica")) {
            return R.drawable.cr;
        } else if (country.contains("Serbia")) {
            return R.drawable.rs;
        } else if (country.contains("Germany")) {
            return R.drawable.de;
        } else if (country.contains("Mexico")) {
            return R.drawable.mx;
        } else if (country.contains("Sweden")) {
            return R.drawable.se;
        } else if (country.contains("South Korea")) {
            return R.drawable.kr;
        } else if (country.contains("Belgium")) {
            return R.drawable.be;
        } else if (country.contains("Panama")) {
            return R.drawable.pa;
        } else if (country.contains("Tunisia")) {
            return R.drawable.tn;
        } else if (country.contains("England")) {
            return R.drawable.en;
        } else if (country.contains("Poland")) {
            return R.drawable.pl;
        } else if (country.contains("Senegal")) {
            return R.drawable.sn;
        } else if (country.contains("Colombia")) {
            return R.drawable.co;
        } else if (country.contains("Japan")) {
            return R.drawable.jp;
        } else {
            return R.drawable.ic_action_circle;
        }
    }

}
