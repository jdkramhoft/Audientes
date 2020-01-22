package a3.audientes.dao;

import java.util.ArrayList;
import java.util.List;
import a3.audientes.dto.Program;
import a3.audientes.view.adapter.ProgramAdapter;

public class ProgramDAO {
    private static ProgramDAO single_instance = null;
    private List<Program> programList = new ArrayList<>();
    public ProgramAdapter programadapter;

    private ProgramDAO() { }

    public static ProgramDAO getInstance() {
        if (single_instance == null)
            single_instance = new ProgramDAO();
        return single_instance;
    }

    public void addProgram(Program program){
        programList.add(programList.size(), program);
        programadapter.notifyItemInserted(programList.size()-1);
    }

    public void deleteProgram(Program program){
        for(int i = 0; i < programList.size(); i++){
            if(program.getId() == programList.get(i).getId()){
                programList.remove(i);
                programadapter.notifyItemRemoved(i);
                return;
            }
        }
    }

    public void update(Program program){
        System.out.println(program.getName());
        System.out.println(program.getId());
        for(int i = 0; i < programList.size(); i++){
            if(program.getId() == programList.get(i).getId()){
                programList.get(i).setName(program.getName());
                programList.get(i).setLow(program.getLow());
                programList.get(i).setLow_plus(program.getLow_plus());
                programList.get(i).setMiddle(program.getMiddle());
                programList.get(i).setHigh(program.getHigh());
                programList.get(i).setHigh_plus(program.getHigh_plus());
                if(programadapter != null){
                    programadapter.notifyItemChanged(i);
                }
                break;
            }
        }
    }

    public int defaultLevel(int level, int program) {
        int max = 3000;
        int diffents = (level-5)*100;
        switch (program) {
            case 1:
                return max/3+diffents;
            case 2:
                return max/2+diffents;
            case 3:
                return max-(max/3)+diffents;
            case 4:
                return max-(max/6)+diffents;
            default:
                return max/2;
        }
    }

    public Program getProgram(int id){
        Program temp = null;
        for(int i = 0; i < programList.size(); i++){
            if(id == programList.get(i).getId()){
                temp = programList.get(i);
            }
        }
        return temp;
    }

    public int getNextId(){
        if(programList.size()==0){
            return 0;
        }
        return programList.get(programList.size()-1).getId()+ 1;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }

    public void setAdapter(ProgramAdapter programadapter){
        this.programadapter = programadapter;
    }



}
