package fr.charlito33.fadeengine.sdk.fsl;

import fr.charlito33.fadeengine.sdk.fsl.sdk.FSLVar;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.LinkedList;
import java.util.List;

public class FSLCompiler {
    private String script;

    private List<String> lines;
    private List<String> variables;

    private List<String> loadInstructions;
    private List<String> updateInstructions;
    private List<String> drawInstructions;

    private String vPackage;
    private String vClass;

    private String java;

    public void compile(FSLScript script) {
        lines = new LinkedList<>();
        variables = new LinkedList<>();

        loadInstructions = new LinkedList<>();
        updateInstructions = new LinkedList<>();
        drawInstructions = new LinkedList<>();

        this.script = script.getScript();

        splitLines();
        createHeaders();
        createVariables();

        createLoad();

        toJava();

        System.out.println(java);
    }

    public void splitLines() {
        for (String line : script.split("\n")) {
            lines.add(line.trim());
        }
    }

    public void createHeaders() {
        for (String line : lines) {
            if (line.startsWith("package")) {
                vPackage = line.split(" ")[1].trim();
                if (vPackage.equals("null")) {
                    vPackage = null;
                }
            }
            if (line.startsWith("class")) {
                vClass = line.split(" ")[1].trim();
            }
        }
    }

    public void createVariables() {
        for (String line : lines) {
            if (line.startsWith("var")) {
                variables.add(line.split(" ")[1].trim());
            }
        }
    }

    public void createLoad() {
        boolean in = false;

        for (String line : lines) {
            if (line.startsWith("load:")) {
                in = true;
            }

            if (in) {
                check(line, loadInstructions);
            }

            if (in && line.startsWith("end")) {
                in = false;
            }
        }
    }

    public void check(String line, List<String> instructions) {
        if (line.startsWith("set")) {
            StringBuilder instruction = new StringBuilder();

            instruction.append(line.split(" ")[1].trim());

            String operation = line.split("=")[1].trim();
            String operator;

            if (operation.split(" ").length < 3) {
                operator = "null";
            } else {
                operator = operation.split(" ")[1].trim();
            }

            boolean doDefault = false;

            switch(operator) {
                case "+":
                    if (variables.contains(operation.split(" ")[0].trim()) && operation.split(" ")[0].trim().equals(line.split(" ")[1].trim())) {
                        instruction.append(".add(");
                        instruction.append(operation.split(" ")[2].trim());
                        break;
                    } else if (variables.contains(operation.split(" ")[0].trim())) {
                        instruction.append(".add(");
                        instruction.append(operation.split(" ")[0].trim()).append(".add(");
                        instruction.append(operation.split(" ")[2].trim());
                        instruction.append(")");
                        break;
                    }
                    doDefault = true;
                    break;
                case "-":
                    if (variables.contains(operation.split(" ")[0].trim()) && operation.split(" ")[0].trim().equals(line.split(" ")[1].trim())) {
                        instruction.append(".sub(");
                        instruction.append(operation.split(" ")[2].trim());
                        break;
                    } else if (variables.contains(operation.split(" ")[0].trim())) {
                        instruction.append(".sub(");
                        instruction.append(operation.split(" ")[0].trim()).append(".add(");
                        instruction.append(operation.split(" ")[2].trim());
                        instruction.append(")");
                        break;
                    }
                    doDefault = true;
                    break;
                case "*":
                    if (variables.contains(operation.split(" ")[0].trim()) && operation.split(" ")[0].trim().equals(line.split(" ")[1].trim())) {
                        instruction.append(".mul(");
                        instruction.append(operation.split(" ")[2].trim());
                        break;
                    } else if (variables.contains(operation.split(" ")[0].trim())) {
                        instruction.append(".mul(");
                        instruction.append(operation.split(" ")[0].trim()).append(".add(");
                        instruction.append(operation.split(" ")[2].trim());
                        instruction.append(")");
                        break;
                    }
                    doDefault = true;
                    break;
                case "/":
                    if (variables.contains(operation.split(" ")[0].trim()) && operation.split(" ")[0].trim().equals(line.split(" ")[1].trim())) {
                        instruction.append(".div(");
                        instruction.append(operation.split(" ")[2].trim());
                        break;
                    } else if (variables.contains(operation.split(" ")[0].trim())) {
                        instruction.append(".div(");
                        instruction.append(operation.split(" ")[0].trim()).append(".add(");
                        instruction.append(operation.split(" ")[2].trim());
                        instruction.append(")");
                        break;
                    }
                    doDefault = true;
                    break;
                default:
                    doDefault = true;
            }

            if (doDefault) {
                instruction.append(".set(");
                instruction.append(operation.trim());
            }

            instruction.append(");");

            instructions.add(instruction.toString());
        }
    }

    public void toJava() {
        StringBuilder result = new StringBuilder();

        if (vPackage != null) {
            result.append("package ").append(vPackage).append("\n\n");
        }
        result.append("class ").append(vClass).append(" extends FSLClass {").append("\n");

        for (String variable : variables) {
            result.append("    ").append("FSLVar ").append(variable).append(";").append("\n");
        }

        result.append("\n");

        result.append("    public ").append(vClass).append("() {").append("\n");

        for (String variable : variables) {
            result.append("    ").append("    ").append(variable).append(" = new FSLVar();").append("\n");
        }

        result.append("    }");
        result.append("\n\n");

        result.append("    ").append("@Override").append("\n");
        result.append("    ").append("public void load() {").append("\n");

        for (String instruction : loadInstructions) {
            result.append("    ").append("    ").append(instruction).append("\n");
        }

        result.append("    ").append("}").append("\n\n");


        result.append("}");

        java = result.toString();
    }
}
