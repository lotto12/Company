/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import java.util.ArrayList;

/**
 *
 * @author Jimmy 拆解組數
 */
public class GetAllSet {

    //1.放入立柱資料 2.星數 ----->回傳所有組合
    public ArrayList<int[]> getAllSet(ArrayList<int[]> data, int star) {
        switch (star) {
            case 2:
                return getAllSet_2(data);
            case 3:
                return getAllSet_3(data);
            case 4:
                return getAllSet_4(data);
            default:
                return null;
        }
    }

    //四星拆解
    private ArrayList<int[]> getAllSet_4(ArrayList<int[]> data) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int[] d1 = data.get(i);
            for (int j = 0; j < d1.length; j++) {
                int[] in_data = new int[4];
                for (int k = i + 1; k < data.size(); k++) {
                    int[] d2 = data.get(k);
                    for (int d : d2) {
                        in_data[0] = d1[j];
                        in_data[1] = d;
                        for (int q = k + 1; q < data.size(); q++) {
                            int[] d3 = data.get(q);
                            for (int d_3 : d3) {
                                in_data[2] = d_3;
                                for (int w = q + 1; w < data.size(); w++) {
                                    int[] d4 = data.get(w);
                                    for (int d_4 : d4) {
                                        in_data[3] = d_4;
                                        result.add(in_data);
                                        //System.out.println(in_data[0] + "," + in_data[1] + "," + in_data[2] + "," + in_data[3]);
                                        //System.out.println("");
                                    }
                                }
                            }
                        }

                    }

                }
            }
        }
        System.out.println("count:" + result.size());
        return result;
    }

    //三星拆解
    private ArrayList<int[]> getAllSet_3(ArrayList<int[]> data) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int[] d1 = data.get(i);
            for (int j = 0; j < d1.length; j++) {
                int[] in_data = new int[3];
                for (int k = i + 1; k < data.size(); k++) {
                    int[] d2 = data.get(k);
                    for (int d : d2) {
                        in_data[0] = d1[j];
                        in_data[1] = d;
                        for (int q = k + 1; q < data.size(); q++) {
                            int[] d3 = data.get(q);
                            for (int d_3 : d3) {
                                in_data[2] = d_3;
                                //System.out.println(in_data[0]+","+in_data[1]+","+in_data[2]);
                                result.add(in_data);
                                //System.out.println("");
                            }
                        }

                    }

                }
            }
        }
        System.out.println("count:" + result.size());
        return result;
    }

    //二星拆解
    private ArrayList<int[]> getAllSet_2(ArrayList<int[]> data) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int[] d1 = data.get(i);
            for (int j = 0; j < d1.length; j++) {
                int[] in_data = new int[2];
                for (int k = i + 1; k < data.size(); k++) {
                    int[] d2 = data.get(k);
                    for (int d : d2) {
                        in_data[0] = d1[j];
                        in_data[1] = d;
                        result.add(in_data);
                    }

                }
            }
        }
        System.out.println("count:" + result.size());
        return result;
    }
}
