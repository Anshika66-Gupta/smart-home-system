//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//package com.example.smarthome;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Singleton Pattern: SmartHomeHub
class SmartHomeHub {
    private static SmartHomeHub instance;
    private final List<String> devices; // Made final for immutability

    private SmartHomeHub() {
        devices = new ArrayList<>();
    }

    public static SmartHomeHub getInstance() {
        if (instance == null) {
            instance = new SmartHomeHub();
        }
        return instance;
    }

    public void addDevice(String device) {
        devices.add(device);
        System.out.println(device + " added to the Smart Home Hub.");
    }
}

// Command Pattern: Device Commands
interface Command {
    void execute();
}

class Device {
    private final String name; // Made final for immutability

    public Device(String name) {
        this.name = name;
    }

    public void on() {
        System.out.println(name + " is ON");
    }

    public void off() {
        System.out.println(name + " is OFF");
    }
}

class TurnOnCommand implements Command {
    private final Device device; // Made final for immutability

    public TurnOnCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.on();
    }
}

class TurnOffCommand implements Command {
    private final Device device; // Made final for immutability

    public TurnOffCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.off();
    }
}

// Proxy Pattern: Access Control for Devices
interface DeviceInterface {
    void request(String userRole);
}

class RealDevice implements DeviceInterface {
    @Override
    public void request(String userRole) {
        System.out.println("Device: Executing operation");
    }
}

class DeviceProxy implements DeviceInterface {
    private final RealDevice realDevice; // Made final for immutability

    public DeviceProxy() {
        this.realDevice = new RealDevice();
    }

    @Override
    public void request(String userRole) {
        if ("Admin".equals(userRole)) {
            System.out.println("DeviceProxy: Access granted");
            realDevice.request(userRole);
        } else {
            System.out.println("DeviceProxy: Access denied");
        }
    }
}

// Bridge Pattern: Device Controls
interface Control {
    void operate();
}

class RemoteControl implements Control {
    @Override
    public void operate() {
        System.out.println("Remote Control: Operating device");
    }
}

class VoiceControl implements Control {
    @Override
    public void operate() {
        System.out.println("Voice Control: Operating device");
    }
}

class DeviceControl {
    private final Control control; // Made final for immutability

    public DeviceControl(Control control) {
        this.control = control;
    }

    public void operate() {
        control.operate();
    }
}

// Template Method Pattern: Morning Routine
abstract class Routine {
    public void execute() {
        wakeUp();
        turnOnLights();
        startCoffeeMachine();
    }

    protected void wakeUp() {
        System.out.println("Wake up");
    }

    protected abstract void turnOnLights();

    protected void startCoffeeMachine() {
        System.out.println("Start coffee machine");
    }
}

class MorningRoutine extends Routine {
    @Override
    protected void turnOnLights() {
        System.out.println("Turn on bedroom lights");
    }
}

// Composite Pattern: Device Groups
interface Component {
    void operation();
}

class DeviceLeaf implements Component {
    private final String name; // Made final for immutability

    public DeviceLeaf(String name) {
        this.name = name;
    }

    @Override
    public void operation() {
        System.out.println(name + " operation");
    }
}

class DeviceGroup implements Component {
    private final List<Component> children = new ArrayList<>(); // Made final for immutability

    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void operation() {
        System.out.println("Device Group operation");
        for (Component child : children) {
            child.operation();
        }
    }
}

// Iterator Pattern: Device Iterator
class DeviceIterator {
    private final List<String> devices; // Made final for immutability
    private int index;

    public DeviceIterator(List<String> devices) {
        this.devices = devices;
        this.index = 0;
    }

    public boolean hasNext() {
        return index < devices.size();
    }

    public String next() {
        return hasNext() ? devices.get(index++) : null;
    }
}

// Main Class
public class SmartHomeSystem {
    public static void main(String[] args) {
        // Singleton Pattern
        SmartHomeHub hub = SmartHomeHub.getInstance();
        hub.addDevice("Smart Light");
        SmartHomeHub anotherHub = SmartHomeHub.getInstance();
        System.out.println(hub == anotherHub); // true

        // Command Pattern
        Device smartLight = new Device("Smart Light");
        Command turnOnLight = new TurnOnCommand(smartLight);
        Command turnOffLight = new TurnOffCommand(smartLight);
        turnOnLight.execute();
        turnOffLight.execute();

        // Proxy Pattern
        DeviceInterface proxyDevice = new DeviceProxy();
        proxyDevice.request("Admin"); // Access granted
        proxyDevice.request("Guest"); // Access denied

        // Bridge Pattern
        DeviceControl remoteControl = new DeviceControl(new RemoteControl());
        DeviceControl voiceControl = new DeviceControl(new VoiceControl());
        remoteControl.operate();
        voiceControl.operate();

        // Template Method Pattern
        Routine routine = new MorningRoutine();
        routine.execute();

        // Composite Pattern
        DeviceLeaf light1 = new DeviceLeaf("Light 1");
        DeviceLeaf light2 = new DeviceLeaf("Light 2");
        DeviceGroup group = new DeviceGroup();
        group.add(light1);
        group.add(light2);
        group.operation();

        // Iterator Pattern
        List<String> deviceList = Arrays.asList("Smart Light", "Smart Thermostat", "Smart Door Lock");
        DeviceIterator iterator = new DeviceIterator(deviceList);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
