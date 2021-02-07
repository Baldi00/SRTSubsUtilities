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
import java.util.ArrayList;

/**
 *
 * @author Andrea
 */
public class SRTSubsReplacer {
    public static void main(String[] args) throws Exception{
        BufferedReader sourceSrt  = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\original.srt"),"ISO-8859-1"));
        BufferedReader sourceTxt  = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\translation.txt"),"ISO-8859-1"));
        BufferedWriter dest = new BufferedWriter(new FileWriter("C:\\replaced.srt"));
        
        ArrayList<String> replacers = new ArrayList();
        
        String line = sourceTxt.readLine();
        while(line!=null){
            if(!line.equals("###!!!!###"))
                replacers.add(line);
            line = sourceTxt.readLine();
        }
        
        boolean write = false;
        int i = 0;
        
        line = sourceSrt.readLine();
        while(line!=null){
            if(line.contains("-->")) {
                write = true;
                dest.write(line);
                dest.newLine();
                line = sourceSrt.readLine();
                continue;
            } else if(line.equals("")) {
                dest.write("");
                dest.newLine();
                line = sourceSrt.readLine();
                if(line!=null){
                    dest.write(line);
                    dest.newLine();
                }
                write = false;
            }
            
            if(write) {
                dest.write(replacers.get(i++));
                dest.newLine();
            }
            
            line = sourceSrt.readLine();
        }
        
        sourceSrt.close();
        sourceTxt.close();
        dest.close();
    }
}
