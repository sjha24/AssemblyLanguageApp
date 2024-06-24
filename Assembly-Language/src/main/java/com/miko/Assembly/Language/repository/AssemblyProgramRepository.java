package com.miko.Assembly.Language.repository;
import com.miko.Assembly.Language.entites.AssemblyProgram;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssemblyProgramRepository extends JpaRepository<AssemblyProgram,String> {

}
