package net.internetworkconsulting.template;

public class CSyntax implements SyntaxInterface {
    public String getVariable() { return "__NAME__"; }
    public String[] getVariableReplace() { return new String[] { "__" }; }
    public String getOpenBlock() { return "\\/\\/\\s*BEGIN\\s*NAME"; }
    public String[] getOpenBlockReplace() { return new String[] { "//", "BEGIN" }; }
    public String getCloseBlock() { return "\\/\\/\\s*END\\s*NAME"; }    
}
