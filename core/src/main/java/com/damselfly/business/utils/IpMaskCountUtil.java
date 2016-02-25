package com.damselfly.business.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: maoni
 * Date: 14-8-7
 * Time: 下午11:48
 * To change this template use File | Settings | File Templates.
 */
public class IpMaskCountUtil {
    /*public static void main(String[] args) {
        String ip = "192.168.101.100";
        String mask = "255.255.255.0";
        int ipNum=1;
        int maskNum=0;
        ip=converToBinary(ip);
        System.out.println(ip);
        mask = converToBinary(mask);
        System.out.println(mask);
        Map<String, Object> map = getNetworkAddressAndBrodcast(ip,mask);
        for(Map.Entry<String, Object> key :map.entrySet()){
            System.out.println(key.getKey()+"    "+key.getValue());
        }
        mask=mask.replace(".","");
        System.out.println(mask);
        for(int i=0;i<=mask.length();i++){
            if(mask.charAt(i)!='1'){
                maskNum=i;
                break;
            }
        }
        System.out.println("子网掩码位数："+maskNum);
        for(int i=1 ;i<=32-maskNum;i++){
            if(i==1)
                ipNum=2;
            else
                ipNum=ipNum*2;
        }
        System.out.println("ip总数为："+ipNum);

        countIpList(map.get("NETWORK").toString(),map.get("BROADCAST").toString(),ipNum,maskNum);

    }*/
    //传入IP或子网掩码返回二进制
    public  String converToBinary(String str){
        String[] strs = str.split("\\.");
        String tempStr = "";
        for (int i = 0; i < strs.length; i++) {
            if (i != 0) {
                tempStr += "." + convertBinary(Integer.parseInt(strs[i]));
            } else {
                tempStr += convertBinary(Integer.parseInt(strs[i]));
            }
        }
        return tempStr;
    }
    // 二进制转十进制
    private  int convertAlgorism(char[] cars) {
        int result = 0;
        int num = 0;
        for (int i = cars.length - 1; 0 <= i; i--) {
            int temp = 2;
            if (num == 0) {
                temp = 1;
            } else if (num == 1) {
                temp = 2;
            } else {
                for (int j = 1; j < num; j++) {
                    temp = temp * 2;
                }
            }
            int sum = Integer.parseInt(String.valueOf(cars[i]));
            result = result + (sum * temp);
            num++;
        }
        return result;
    }

    // 十进制转二进制
    private  String convertBinary(int sum) {
        StringBuffer binary = new StringBuffer();
        if (sum != 0 && sum != 1) {
            while (sum != 0 && sum != 1) {
                binary.insert(0, sum % 2);
                // System.out.println("sum=" + sum + "余数=" + (sum % 2) + "除数="
                // + sum / 2);
                sum = sum / 2;
                if (sum == 0 || sum == 1) {
                    binary.insert(0, sum % 2);
                }
            }
            if (binary.length() < 8) {    //不足8位补0
                int count = 8 - binary.length();
                for (int i = 0; i < count; i++) {
                    binary.insert(0, "0");
                }
            }
        } else {
            if (sum == 0) {
                binary.insert(0, "0000000" + sum);
            } else {
                binary.insert(0, "0000000" + sum);
            }

        }
        return binary.toString();
    }

    // 与运算operation
    public  String operation(String ip, String work) {
        StringBuffer buf = new StringBuffer();
        char[] ips = ip.toCharArray();
        char[] works = work.toCharArray();
        for (int i = 0; i < ips.length; i++) {
            if (ips[i] == works[i]) {
                if (ips[i] == '0' && works[i] == '0') {
                    buf.append("0");
                } else {
                    buf.append("1");
                }
            } else {
                buf.append("0");
            }
        }

        return buf.toString();
    }

    // 或运算operationOr
    public  String operationOr(String ip, String work) {
        StringBuffer buf = new StringBuffer();
        char[] ips = ip.toCharArray();
        char[] works = work.toCharArray();
        for (int i = 0; i < ips.length; i++) {
            if (ips[i] == works[i]) {
                if (ips[i] == '1' && works[i] == '1') {
                    buf.append("1");
                } else {
                    buf.append("0");
                }
            } else {
                buf.append("1");
            }
        }

        return buf.toString();
    }

    // 子网掩码主机按位取反
    public  String operationReverse(String mask) {
        StringBuffer buf = new StringBuffer();
        int maskPreNum = getMaskPreNum(mask);//获取子网掩码位数，小数点也包含进去
        char[] ips = mask.toCharArray();
        for (int i = 0; i < ips.length; i++) {
            if(i<maskPreNum) {//子网掩码位数前面的全变0
                if(ips[i] == '.')
                    buf.append('.');
                else
                    buf.append('0');
            } else {//子网掩码位数后面的全变1
                if(ips[i] == '.')
                    buf.append('.');
                else
                    buf.append('1');
            }
/*            if (ips[i] == '0') {
                    buf.append('1');
                } else if (ips[i] == '1'){
                    buf.append('0');
                } else {
                buf.append('.');
            }*/
        }
        return buf.toString();
    }

    //根据ip、子网掩码的二进制技术网路地址和广播地址
    public  Map<String, Object> getNetworkAddressAndBrodcast(String ip,String mask) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] ips = ip.split("\\.");
        String[] masks = mask.split("\\.");
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < ips.length; i++) {
            if(i<ips.length-1){
                buf.append(operation(ips[i],masks[i])+".");
            }else{
                buf.append(operation(ips[i],masks[i]));
            }
        }
        String [] result = buf.toString().split("\\.");
        StringBuffer networkAddres  = new StringBuffer();
        for(int i=0;i<result.length;i++){
            if(i<ips.length-1){
                networkAddres.append(convertAlgorism(result[i].toCharArray())+".");
            }else{
                networkAddres.append(convertAlgorism(result[i].toCharArray()));
            }
        }
        /**
         * 计算子网掩码位数
         * 子网掩码取反，再和网络地址 相或
         */
        String maskReverse = operationReverse(mask);
        String [] maskReverseS  = maskReverse.split("\\.");
        StringBuffer brder = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            if(i<result.length-1){
                brder.append(operationOr(result[i],maskReverseS[i])+".");
            }else{
                brder.append(operationOr(result[i],maskReverseS[i]));
            }
        }
        String bStr [] = brder.toString().split("\\.");
        StringBuffer borderAddres = new StringBuffer();
        for(int i=0;i<bStr.length;i++){
            if(i<ips.length-1){
                borderAddres.append(convertAlgorism(bStr[i].toCharArray())+".");
            }else{
                borderAddres.append(convertAlgorism(bStr[i].toCharArray()));
            }
        }
        map.put("NETWORK", networkAddres.toString());
        map.put("BROADCAST", borderAddres.toString());
        return map;

    }

    public  List<String> countIpList(String network,String broadcast,int ipNum,int maskNum){
        String[] networkArray = network.split("\\.");
        String[] broadcastArray = broadcast.split("\\.");
        int network_0 = Integer.valueOf(networkArray[0]);
        int network_1 = Integer.valueOf(networkArray[1]);
        int network_2 = Integer.valueOf(networkArray[2]);
        int network_3 = Integer.valueOf(networkArray[3]);
        int broadcast_0 = Integer.valueOf(broadcastArray[0]);
        int broadcast_1 = Integer.valueOf(broadcastArray[1]);
        int broadcast_2 = Integer.valueOf(broadcastArray[2]);
        int broadcast_3 = Integer.valueOf(broadcastArray[3]);
        List<String> ipList = new ArrayList();

        if (maskNum > 24 & maskNum < 32) {//C类地址
            for (int i = 0; i < ipNum; i++) {
                if ((i + network_3) > ipNum)
                    break;
                ipList.add(network_0 + "." + network_1 + "." + network_2 + "." + (network_3 + i));
            }
        } else if (maskNum > 16 & maskNum <= 24) {//B类地址
            for (int j = 0; j <= broadcast_2 - network_2; j++) {
                if (j == 0) {
                    for (int i = network_3; i < 256; i++) {
                        ipList.add(network_0 + "." + network_1 + "." + network_2 + "." + i);
                    }
                } else if (j == broadcast_2 - network_2) {
                    for (int i = 0; i <= broadcast_3; i++) {
                        ipList.add(network_0 + "." + network_1 + "." + (network_2 + j) + "." + i);
                    }
                } else {
                    for (int i = 0; i < 256; i++) {
                        ipList.add(network_0 + "." + network_1 + "." + (network_2 + j) + "." + i);
                    }
                }
            }
        } else if (maskNum > 8 & maskNum <= 16) {//A类地址
            for (int k = 0; k <= broadcast_1 - network_1; k++) {
                if (k == 0) {
                    for (int j = network_2; j < 256; j++) {
                        if (j == network_2) {
                            for (int i = network_3; i < 256; i++) {
                                ipList.add(network_0 + "." + network_1 + "." + j + "." + i);
                            }
                        } else {
                            for (int i = 0; i < 256; i++) {
                                ipList.add(network_0 + "." + network_1 + "." + j + "." + i);
                            }
                        }
                    }
                } else if (k == broadcast_1 - network_1) {
                    for (int j = 0; j <= broadcast_2; j++) {
                        if (j == broadcast_2) {
                            for (int i = 0; i <= broadcast_3; i++) {
                                ipList.add(network_0 + "." + (network_1 + k) + "." + j + "." + i);
                            }
                        } else {
                            for (int i = 0; i < 256; i++) {
                                ipList.add(network_0 + "." + (network_1 + k) + "." + j + "." + i);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 256; j++) {
                        for (int i = 0; i < 256; i++) {
                            ipList.add(network_0 + "." + (network_1 + k) + "." + j + "." + i);
                        }
                    }
                }
            }
        }
        /*System.out.println("<<以下是ip列表>>");
        System.out.println("---------------");

        for (String ipstr : ipList) {
            System.out.println(ipstr);
        }
        System.out.println("---------------");
        System.out.println("<<....完毕....>>");
        System.out.println("<<共输出" + ipList.size() + "行>>");*/
        return ipList;
    }

    /**
     * 计算子网掩码位数(包含小数点)
     * @maoni 2014.10.24
     * @param mask
     * @return
     */
    public int getMaskPreNum(String mask) {
        int returnNum = 0;
        if(mask != null && !"".equals(mask)) {
            for (int i = 0; i<mask.length();i++) {
                if (mask.charAt(i) == '1' || mask.charAt(i) == '.'){
                    if (returnNum == 0){
                        returnNum = 1;
                    }else {
                        returnNum = returnNum+1;
                    }
                }else {
                    break;
                }
            }
        }
        return returnNum;
    }

}

