/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckOrder;

import java.util.ArrayList;
import java.util.List;

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
                        for (int q = k + 1; q < data.size(); q++) {
                            int[] d3 = data.get(q);
                            for (int d_3 : d3) {
                                for (int w = q + 1; w < data.size(); w++) {
                                    int[] d4 = data.get(w);
                                    for (int d_4 : d4) {
                                        in_data[0] = d1[j];
                                        in_data[1] = d;
                                        in_data[3] = d_4;
                                        in_data[2] = d_3;
                                        result.add(in_data);
                                        in_data = new int[4];
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
                        for (int q = k + 1; q < data.size(); q++) {
                            int[] d3 = data.get(q);
                            for (int d_3 : d3) {
                                in_data[0] = d1[j];
                                in_data[1] = d;
                                in_data[2] = d_3;
                                result.add(in_data);
                                in_data = new int[3];
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
                        in_data = new int[2];
                    }
                    
                }
            }
        }
        System.out.println("count:" + result.size());
        return result;
    }

    //連碰拆解
    public ArrayList<int[]> getAllSet_1(ArrayList<Integer> data_1, int star) {
        int size = data_1.size();
        int total = 0;
        data = new ArrayList();
        switch (star) {
            case 2:
                total = size * (size - 1) / 2;
                break;
            case 3:
                total = size * (size - 1) * (size - 2) / 6;
                break;
            case 4:
                total = size * (size - 1) * (size - 2) * (size - 3) / 24;
                break;
        }
        //2星        
        getAllSet(data_1, star, new ArrayList());
        while (true) {
            if (data.size() == total) {
                break;
            }
        }
        return data;
    }

    //連碰
    private ArrayList<int[]> data;

    //連碰拆解
    @SuppressWarnings("unchecked")
    private void getAllSet(List values, int count, ArrayList result) {
        assert (values != null && result != null);
        if (count <= 0) {
            data.add(list2array(result));
        } else if (!values.isEmpty()) {
            Object obj = values.remove(0);
            result.add(obj);
            getAllSet(values, count - 1, result);
            result.remove(result.size() - 1);
            getAllSet(values, count, result);
            values.add(0, obj);
        }
    }
    
    private int[] list2array(ArrayList data) {
        int[] result = new int[data.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (int) data.get(i);
        }
        return result;
    }
}
