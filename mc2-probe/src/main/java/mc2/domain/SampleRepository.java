package mc2.domain;

import org.cincheo.dlite.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SampleRepository {

    private static SampleRepository instance;

    public static synchronized SampleRepository getInstance() {
        if (instance == null) {
            instance = new SampleRepository();
        }
        return instance;
    }

    List<Sample> samples = new ArrayList<>();

    public List<Sample> getSamples() {
        samples.sort((s1, s2) -> (int)s1.getInstant().toEpochMilli() - (int)s2.getInstant().toEpochMilli());
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public void addSample(Sample sample) {
        this.samples.add(sample);
    }

    public List<Sample> selectSamples(Instant from, Instant to) {
        return samples.stream().filter(sample ->
                sample.getInstant().toEpochMilli() >= from.toEpochMilli()
                        && sample.getInstant().toEpochMilli() <= to.toEpochMilli())
                .collect(Collectors.toList());
    }

    public void clearSamples() {
        this.samples.clear();
    }

}
