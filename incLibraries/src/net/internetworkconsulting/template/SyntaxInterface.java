package net.internetworkconsulting.template;

public interface SyntaxInterface {
    String getVariable();
    String[] getVariableReplace();
    String getOpenBlock();
    String[] getOpenBlockReplace();
    String getCloseBlock();
}
