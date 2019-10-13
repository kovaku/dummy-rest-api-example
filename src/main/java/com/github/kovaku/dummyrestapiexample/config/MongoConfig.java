package org.github.kovaku.dummyrestapiexample.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.cdi.Eager;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
@EnableMongoRepositories(basePackages = "org.github.kovaku")
public class MongoConfig extends AbstractMongoConfiguration {

    private static final String LOCALHOST = "localhost";
    private static final int MONGO_PORT = 27017;

    @Autowired
    private MongodExecutable embeddedMongo;

    @Override
    protected String getDatabaseName() {
        return "employee";
    }

    @Override
    public MongoClient mongoClient() {
        try {
            embeddedMongo.start();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return new MongoClient(LOCALHOST, MONGO_PORT);
    }

    @Override
    protected String getMappingBasePackage() {
        return "org.github.kovaku";
    }

    @Bean(destroyMethod = "stop")
    public MongodExecutable embeddedMongo() throws IOException {
        IMongodConfig mongodbConfig = new MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(new Net(LOCALHOST, MONGO_PORT, Network.localhostIsIPv6()))
            .build();
        MongodStarter starter = MongodStarter.getDefaultInstance();
        return starter.prepare(mongodbConfig);
    }
}
