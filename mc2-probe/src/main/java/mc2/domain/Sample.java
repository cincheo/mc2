package mc2.domain;

import org.cincheo.dlite.Entity;

import java.time.Instant;

@Entity
public class Sample {

    private Instant instant = Instant.now();
    private Float cpuUser;
    private Float cpuSystem;
    private Float cpuIdle;
    private Long availableMemory;
    private Long diskRead;
    private Long diskWritten;
    private Long networkIn;
    private Long networkOut;

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public Float getCpuUser() {
        return cpuUser;
    }

    public void setCpuUser(Float cpuUser) {
        this.cpuUser = cpuUser;
    }

    public Float getCpuSystem() {
        return cpuSystem;
    }

    public void setCpuSystem(Float cpuSystem) {
        this.cpuSystem = cpuSystem;
    }

    public Float getCpuIdle() {
        return cpuIdle;
    }

    public void setCpuIdle(Float cpuIdle) {
        this.cpuIdle = cpuIdle;
    }

    public Long getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(Long availableMemory) {
        this.availableMemory = availableMemory;
    }

    public Long getDiskRead() {
        return diskRead;
    }

    public void setDiskRead(Long diskRead) {
        this.diskRead = diskRead;
    }

    public Long getDiskWritten() {
        return diskWritten;
    }

    public void setDiskWritten(Long diskWritten) {
        this.diskWritten = diskWritten;
    }

    public Long getNetworkIn() {
        return networkIn;
    }

    public void setNetworkIn(Long networkIn) {
        this.networkIn = networkIn;
    }

    public Long getNetworkOut() {
        return networkOut;
    }

    public void setNetworkOut(Long networkOut) {
        this.networkOut = networkOut;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "instant=" + instant +
                ", cpuUser=" + cpuUser +
                ", cpuSystem=" + cpuSystem +
                ", cpuIdle=" + cpuIdle +
                ", availableMemory=" + availableMemory +
                ", diskRead=" + diskRead +
                ", diskWritten=" + diskWritten +
                ", networkIn=" + networkIn +
                ", networkOut=" + networkOut +
                '}';
    }
}
