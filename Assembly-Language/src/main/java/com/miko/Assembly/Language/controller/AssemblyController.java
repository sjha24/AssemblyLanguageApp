package com.miko.Assembly.Language.controller;
import com.miko.Assembly.Language.apis.ExecutingProgramApi;
import com.miko.Assembly.Language.entites.AssemblyProgram;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AssemblyController {

    private final ExecutingProgramApi executingProgramApi;

    @PostMapping("/execute")
    public AssemblyProgram executeProgram(@RequestBody String programText){
        return executingProgramApi.execute(programText);
    }
}