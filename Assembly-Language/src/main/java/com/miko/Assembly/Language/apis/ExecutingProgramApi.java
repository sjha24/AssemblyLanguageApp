package com.miko.Assembly.Language.apis;
import com.miko.Assembly.Language.entites.AssemblyProgram;
import com.miko.Assembly.Language.service.AssemblyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ExecutingProgramApi {
    private final AssemblyService assemblyService;

    public AssemblyProgram execute(String programText) {
        try {
            return assemblyService.execute(programText);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute program: " + e.getMessage(), e);
        }
    }
}
