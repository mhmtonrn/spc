package io.mhmtonrn.spc.service.impl;

import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import io.mhmtonrn.spc.service.ProjectCreatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasicProjectCreatorService implements ProjectCreatorService {
    @Value("${spc.app.basic}")
    private String basicApp;


    @Override
    public String createProjectFromBasicTemplate(CreateAppDTO createAppDTO) throws IOException, InterruptedException {
        UUID uuid = UUID.randomUUID();
        String[] cmdBash = springCliCreateCommand(createAppDTO, uuid);
        Runtime.getRuntime().exec(cmdBash).waitFor();
        return "temp/" + uuid;
    }

    @Override
    public String getInnerPath(CreateAppDTO createAppDTO, String projectPath) {
        return projectPath + "/" + createAppDTO.getAppName() +
                "/src/main/java/" + createAppDTO.getProjectPackageName().replace(".", "//") + "/"
                + createAppDTO.getAppName();
    }

    @Override
    public String[] springCliCreateCommand(CreateAppDTO createAppDTO, UUID uuid) {
        String cmd = "cd temp " +
                " && mkdir " + uuid +
                " && cd " + uuid +
                " && java -jar ../../doc/spring-cli-0.9.0.jar boot new " +
                " --from " + basicApp +
                " --group-id " + createAppDTO.getProjectGroupId() +
                " --artifact-id " + createAppDTO.getProjectArtifactId() +
                " --version " + createAppDTO.getProjectVersion() +
                " --description " + createAppDTO.getProjectDescription() +
                " --package-name " + createAppDTO.getProjectPackageName() +
                " --package-name " + createAppDTO.getProjectPackageName() +
                " --name " + createAppDTO.getAppName();

        return new String[]{"/bin/bash", "-c", cmd};
    }

}
