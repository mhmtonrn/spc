package io.mhmtonrn.spc.service.impl;

import io.mhmtonrn.spc.model.springcli.CreateAppDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectZipService {

    public String createZip(CreateAppDTO createAppDTO, String projectPath) throws IOException, InterruptedException {
        StringBuilder cmdZip = new StringBuilder();
        cmdZip.append(" cd ").append(projectPath);
        cmdZip.append(" && zip -r ").append(createAppDTO.getAppName()).append(" ").append(createAppDTO.getAppName());
        Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmdZip.toString()}).waitFor();
        return projectPath + "/" + createAppDTO.getAppName() + ".zip";
    }

}
