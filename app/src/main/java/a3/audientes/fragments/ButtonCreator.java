package a3.audientes.fragments;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import a3.audientes.R;

public class ButtonCreator {

    private final View root;
    private final View.OnClickListener listener;
    private final Map<Button, Program> btnProgramMap;

    public ButtonCreator(View root, Map<Button, Program> btnProgramMap, View.OnClickListener listener) {
        this.root = root;
        this.listener = listener;
        this.btnProgramMap = btnProgramMap;
    }


    public void createButtonsFromPrograms(List<Program> allPrograms) {

        List<Button> programButtons = new ArrayList<>();
        for(Program program : allPrograms){
            programButtons.add(createButton(program));
        }

        TableLayout table = root.findViewById(R.id.table);
        List<TableRow> rows = new ArrayList<>();

        TableRow row = new TableRow(root.getContext());
        for (int i = 0; i < programButtons.size(); i++) {
            boolean newRow = i % 2 == 0;
            if(newRow){
                row = new TableRow(root.getContext());
                rows.add(row);
            }
            row.addView(programButtons.get(i));
            setButtonLayout(programButtons.get(i));
        }

        int lastRowButtonCount = row.getChildCount();

        if(lastRowButtonCount % 2 == 0){
            TableRow newRow = new TableRow(root.getContext());
            Button addProgramButton = createButton(null);
            newRow.addView(addProgramButton);
            setButtonLayout(addProgramButton);
            rows.add(newRow);
        } else {
            row.addView(createButton(null));
        }

        for(TableRow r : rows){
            table.addView(r);
            setRowLayout(r);
        }
    }

    private void setButtonLayout(Button b) {
        TableRow.LayoutParams layoutParams = (TableRow.LayoutParams) b.getLayoutParams();
        layoutParams.leftMargin = (int) root.getContext().getResources().getDimension(R.dimen.buttonLeftMargin);
        layoutParams.rightMargin = (int) root.getContext().getResources().getDimension(R.dimen.buttonRightMargin);
        layoutParams.topMargin = (int) root.getContext().getResources().getDimension(R.dimen.buttonTopMargin);
        layoutParams.bottomMargin = (int) root.getContext().getResources().getDimension(R.dimen.buttonBottomMargin);
        b.setLayoutParams(layoutParams);
    }

    private void setRowLayout(TableRow row) {
        TableLayout.LayoutParams layoutParams = (TableLayout.LayoutParams) row.getLayoutParams();
        layoutParams.leftMargin = (int) root.getContext().getResources().getDimension(R.dimen.rowLeftMargin);
        row.setLayoutParams(layoutParams);
    }

    private Button createButton(Program p) {
        Button b = new Button(root.getContext());
        b.setOnClickListener(listener);
        if(p != null){
            btnProgramMap.put(b, p);
        }
        int height = (int) root.getContext().getResources().getDimension(R.dimen.programHeight);
        int width = (int) root.getContext().getResources().getDimension(R.dimen.programWidth);
        b.setHeight(height);
        b.setWidth(width);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //TODO: Get rid of if, and simply update min API
            if(p == null){
                b.setBackground(root.getContext().getResources().getDrawable(R.drawable.xml_button_monka_fucking_s));
            } else {
                b.setBackground(root.getContext().getResources().getDrawable(R.drawable.two_round_two_sharp));
            }
        }
        if(p != null){
            b.setText(String.valueOf(p.getVolume()));
        } else {
            b.setText("+");
            b.setTextSize(root.getContext().getResources().getDimension(R.dimen.plusSize));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                b.setTextColor(root.getContext().getColor(R.color.white));
            }
        }
        return b;
    }


}
