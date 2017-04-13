package net.internetworkconsulting.template;

public class XmlSyntax implements SyntaxInterface {
    private String sPrefix;
    public XmlSyntax(String prefix) { sPrefix = prefix; }
    public String getVariable() { return "<\\s*" + sPrefix + "\\s*:\\s*NAME\\s*/\\s*>"; }
    public String[] getVariableReplace() { return new String[] { "<", sPrefix, ":", "/", ">" }; }
    public String getOpenBlock() { return "\\<\\s*" + sPrefix + "\\s*\\:\\s*NAME\\s*\\>"; }
    public String[] getOpenBlockReplace() { return new String[] { "<", sPrefix, ":", ">" }; }
    public String getCloseBlock() { return "\\<\\s*/\\s*" + sPrefix + "\\s*\\:\\s*NAME\\s*\\>"; }
}
