package br.com.generic.logformatter.message;

import java.lang.management.ManagementFactory;

public class ApplicationInfo {

    private String threadId;
    private String threadName;
    private String hostName;
    private String machineIp;
    private String stacktrace;

    public String getThreadId() {return this.threadId;}
    private void setThreadId(){
        try{
            String[] thread = ManagementFactory.getRuntimeMXBean().getName().split("@");
            this.threadId = thread[0];
        }catch (Exception e){
            this.threadId = null;
        }
    }

    public String getThreadName() {return  threadName;}
    public void setThreadName(){
        this.threadName = Thread.currentThread().getName();
        setThreadId();
    }

    public String getHostName() {return hostName;}
    public void setHostName(String hostName){
        this.hostName = hostName;
    }
    public String getMachineIp() {return machineIp;}
    public void setMachineIp(String machineIp){
        this.machineIp = machineIp;
    }
    public String getStacktrace() {return stacktrace;}
    public void setStacktrace(String stacktrace){
        this.stacktrace = stacktrace;
    }
}
