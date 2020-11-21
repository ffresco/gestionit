package com.gestionit.base;

import java.io.IOException;
import java.util.Scanner;

public class KeyGenerator {

	public static void main(String[] args) {


	


		           // wmic command for diskdrive id: wmic DISKDRIVE GET SerialNumber
		           // wmic command for cpu id : wmic cpu get ProcessorId
		           Process process = null;
				try {
					process = Runtime.getRuntime().exec(new String[] { "wmic", "bios", "get", "serialnumber" });
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		           try {
					process.getOutputStream().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		           Scanner sc = new Scanner(process.getInputStream());
		           String property = sc.next();
		           String serial = sc.next();
		           System.out.println(property + ": " + serial);
		       
		}

	}


