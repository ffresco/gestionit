package com.gestionit.base;



import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.Constants;

public class KeyGenerator2 {

	public static String getComputerIdentifier() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();

        String vendor = operatingSystem.getManufacturer();
        String processorSerialNumber = computerSystem.getSerialNumber();
        String processorIdentifier = centralProcessor.getProcessorIdentifier().getIdentifier();
        int processors = centralProcessor.getLogicalProcessorCount();

        String delimiter = "-";

        return String.format("%08x", vendor.hashCode()) + delimiter
                + String.format("%08x", processorSerialNumber.hashCode()) + delimiter
                + String.format("%08x", processorIdentifier.hashCode()) + delimiter + processors;
    }

    public static void main(String[] arguments)
    {
    	String unknownHash = String.format("%08x", Constants.UNKNOWN.hashCode());

        System.out.println("Here's a unique (?) id for your computer.");
        System.out.println(getComputerIdentifier());
        System.out.println("If any field is " + unknownHash
                + " then I couldn't find a serial number or uuid, and running as sudo might change this.");
    }
	}




