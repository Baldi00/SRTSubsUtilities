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
public class SRTSubsExtractor {
    public static void main(String[] args) throws Exception{
        BufferedReader source  = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\original.srt"),"ISO-8859-1"));
        BufferedWriter dest = new BufferedWriter(new FileWriter("C:\\extracted.txt"));
        
        boolean write = false;
        
        String line = source.readLine();
        while(line!=null){
            if(line.contains("-->")) {
                write = true;
                line = source.readLine();
                continue;
            } else if(line.equals("")) {
                dest.write("###!!!!###");
                dest.newLine();
                write = false;
            }
            
            if(write) {
                dest.write(line);
                dest.newLine();
            }
            
            line = source.readLine();
        }
        
        source.close();
        dest.close();
    }
}
