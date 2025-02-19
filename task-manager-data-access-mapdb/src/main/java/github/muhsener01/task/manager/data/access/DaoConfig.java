package github.muhsener01.task.manager.data.access;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan
public class DaoConfig {

    @Value("${mapdb.file}")
    private String dbUrl;

    @Bean
    public DB db(){
        return DBMaker.fileDB(dbUrl)
                .fileMmapEnable()
                .closeOnJvmShutdown()
                .transactionEnable()
                .make();
    }

}
