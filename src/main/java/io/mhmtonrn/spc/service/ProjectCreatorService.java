package io.mhmtonrn.spc.service;

import io.mhmtonrn.spc.model.springcli.CreateAppDTO;

import java.io.IOException;
import java.util.UUID;


public interface ProjectCreatorService {


    String createProjectFromBasicTemplate(CreateAppDTO createAppDTO) throws IOException, InterruptedException;

    String getInnerPath(CreateAppDTO createAppDTO, String projectPath);

    String[] springCliCreateCommand(CreateAppDTO createAppDTO, UUID uuid);
}
