package io.mhmtonrn.spc.service;

import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpringCliService {

    public String createApp(CreateAppDTO createAppDTO) throws IOException, InterruptedException {
        UUID uuid = UUID.randomUUID();
        StringBuilder cmd = new StringBuilder();
        cmd.append("cd temp ");
        cmd.append(" && mkdir ").append(uuid);
        cmd.append(" && cd ").append(uuid);
        cmd.append(" && java -jar /home/idea/projects/spc/doc/spring-cli-0.9.0.jar boot new ")
                .append(createAppDTO.getAppName());

        String[] cmdBash = {"/bin/bash", "-c", cmd.toString()};
        Runtime.getRuntime().exec(cmdBash).waitFor();

        return "";
    }
}
