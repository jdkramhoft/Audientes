package a3.audientes.models;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager {
    // static variable single_instance of type Singleton
    private static ProgramManager single_instance = null;

    private List<Program> programList = new ArrayList<>();

    // private constructor restricted to this class itself
    private ProgramManager() { }

    // static method to create instance of Singleton class
    public static ProgramManager getInstance() {
        if (single_instance == null)
            single_instance = new ProgramManager();

        return single_instance;
    }

    public void addProgram(Program program){
        programList.add(program);
    }

    public void deleteProgram(Program program){
        for(int i = 0; i < programList.size(); i++){
            if(program.getId() == programList.get(i).getId()){
                programList.remove(i);
            }
        }
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }

    public int getNextId(){
        return programList.get(programList.size()-1).getId()+ 1;
    }
}
