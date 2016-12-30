package iloveyouboss.domain;


import java.time.Instant;

public interface Persistable {
    void setCreateTimeStamp(Instant instant);

    int getId();
}
