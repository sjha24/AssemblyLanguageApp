package com.miko.Assembly.Language.service;

import com.miko.Assembly.Language.entites.AssemblyProgram;
import com.miko.Assembly.Language.repository.AssemblyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;



@Service
public class AssemblyService {

    private static final Logger logger = Logger.getLogger(AssemblyService.class.getName());

    @Autowired
    private AssemblyProgramRepository repository;

    public AssemblyProgram execute(String programText) {
        String[] lines = programText.split("\\r?\\n");
        Map<String, Integer> registers = new LinkedHashMap<>(); // Use LinkedHashMap to maintain insertion order

        try {
            for (String line : lines) {
                line = line.trim();
                logger.info("Executing line: " + line);
                if (line.startsWith("MV")) {
                    String[] parts = line.split("[ ,]+");
                    if (parts.length < 3) {
                        throw new IllegalArgumentException("Invalid MV instruction: " + line);
                    }
                    String reg = parts[1];
                    int value = Integer.parseInt(parts[2].replace("#", ""));
                    registers.put(reg, value);
                    logger.info("Set " + reg + " to " + value);
                } else if (line.startsWith("ADD")) {
                    String[] parts = line.split("[ ,]+");
                    if (parts.length < 3) {
                        throw new IllegalArgumentException("Invalid ADD instruction: " + line);
                    }
                    String reg1 = parts[1];
                    if (parts[2].startsWith("#")) {
                        int value = Integer.parseInt(parts[2].replace("#", ""));
                        registers.put(reg1, registers.getOrDefault(reg1, 0) + value);
                        logger.info("Added " + value + " to " + reg1 + ", new value: " + registers.get(reg1));
                    } else {
                        String reg2 = parts[2];
                        if (registers.containsKey(reg2)) {
                            registers.put(reg1, registers.getOrDefault(reg1, 0) + registers.get(reg2));
                            logger.info("Added " + reg2 + " to " + reg1 + ", new value: " + registers.get(reg1));
                        } else {
                            throw new IllegalArgumentException("Register " + reg2 + " not initialized.");
                        }
                    }
                } else if (line.startsWith("SHOW REG")) {
                    logger.info("Showing register values");
                } else {
                    throw new IllegalArgumentException("Invalid instruction: " + line);
                }
            }
        } catch (Exception e) {
            logger.severe("Error executing program: " + e.getMessage());
            throw e;
        }

        StringBuilder result = new StringBuilder("SHOW REG\n");
        for (Map.Entry<String, Integer> entry : registers.entrySet()) {
            result.append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
        }

        AssemblyProgram program = new AssemblyProgram();
        program.setProgramText(programText);
        program.setResult(result.toString().trim());

        return repository.save(program);
    }
}