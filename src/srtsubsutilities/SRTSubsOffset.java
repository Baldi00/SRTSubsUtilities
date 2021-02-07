/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srtsubsutilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author Andrea
 */
public class SRTSubsOffset {
    
    private static int HOUR_OFFSET = 0;
    private static int MIN_OFFSET = 0;
    private static int SEC_OFFSET = -18;
    private static int MILSEC_OFFSET = 0;
    
    private static int hourInt, minInt, secInt, millisecInt;
    private static String millisecString, secString, minString, hourString;
    
    public static void main(String[] args) throws Exception{
        BufferedReader source  = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\original.srt"),"ISO-8859-1"));
        BufferedWriter dest = new BufferedWriter(new FileWriter("C:\\offset.srt"));
        
        String line = source.readLine();
        while(line!=null){
            if(line.contains("-->")){
                String start = line.substring(0,12);
                String end = line.substring(17,29);
                
                splitter(start);
                calculateOffset();
                if(hourInt < 0){        //In case negative offset is less than 0
                    dest.write(line);
                    dest.newLine();
                    line = source.readLine();
                    continue;
                }
                normalizeStringValues();
                String startOffset = hourString+":"+minString+":"+secString+","+millisecString;
                
                splitter(end);
                calculateOffset();
                normalizeStringValues();
                String endOffset = hourString+":"+minString+":"+secString+","+millisecString;

                dest.write(startOffset + " --> " + endOffset);
                dest.newLine();
            }else{
                dest.write(line);
                dest.newLine();
            }
            line = source.readLine();
        }
        
        source.close();
        dest.close();
    }
    
    public static void splitter(String toSplit){
        String [] split1 = toSplit.split(",");
        String [] split2 = split1[0].split(":");

        hourInt = Integer.valueOf(split2[0]);
        minInt = Integer.valueOf(split2[1]);
        secInt = Integer.valueOf(split2[2]);
        millisecInt = Integer.valueOf(split1[1]);
    }
    
    public static void calculateOffset() {  
        millisecInt += MILSEC_OFFSET;
        if(millisecInt < 0) {
            millisecInt += 1000;
            secInt--;
        } else if (millisecInt >= 1000) {
            millisecInt -= 1000;
            secInt++;
        }

        secInt += SEC_OFFSET;
        if(secInt < 0) {
            secInt += 60;
            minInt--;
        } else if (secInt >= 60) {
            secInt -= 60;
            minInt++;
        }

        minInt += MIN_OFFSET;
        if(minInt < 0) {
            minInt += 60;
            hourInt--;
        } else if (minInt >= 60) {
            minInt -= 60;
            hourInt++;
        }

        hourInt += HOUR_OFFSET;
    }
    
    public static void normalizeStringValues(){
        if(millisecInt<10) millisecString = "00"+millisecInt; else if (millisecInt<100) millisecString = "0"+millisecInt; else millisecString = String.valueOf(millisecInt);
        if(secInt<10) secString = "0"+secInt; else secString = String.valueOf(secInt);
        if(minInt<10) minString = "0"+minInt; else minString = String.valueOf(minInt);
        if(hourInt<10) hourString = "0"+hourInt; else hourString = String.valueOf(hourInt);
    }
    
}
