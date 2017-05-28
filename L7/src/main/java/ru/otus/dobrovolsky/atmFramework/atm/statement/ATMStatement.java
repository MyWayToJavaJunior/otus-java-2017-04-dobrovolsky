package ru.otus.dobrovolsky.atmFramework.atm.statement;

import ru.otus.dobrovolsky.atmFramework.atm.ATM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMStatement {
    private static ATMStatement atmStatement = new ATMStatement();
    private Map<ATM, List<String>> statementMap = new HashMap<>();

    private ATMStatement() {
    }

    public static ATMStatement getAtmStatement() {
        return atmStatement;
    }

    /**
     * C - credit
     * D - debit
     */
    public void addStatement(ATM atm, String atmStatement) {
        List<String> statements = new ArrayList<>();
        if (statementMap.containsKey(atm)) {
            statements = statementMap.get(atm);
            statements.add(atmStatement);
            statementMap.put(atm, statements);
        } else {
            statements.add(atmStatement);
            statementMap.put(atm, statements);
        }
    }

    public String getATMMiniStatement(ATM atm) {
        List<String> statements;
        StringBuilder retStr = new StringBuilder("==========================================================\n"
                + "Mini statement for:\n" + atm.toString() + "\n");
        int n = 10;
        if (statementMap.containsKey(atm)) {
            statements = statementMap.get(atm);

            if (statements.size() < n) {
                n = statements.size();
            }

            for (int i = 1; i <= n; i++) {
                retStr.append(statements.get(statements.size() - i)).append("\n");
            }
            return retStr.append("==========================================================").toString();
        }

        return null;
    }
}
