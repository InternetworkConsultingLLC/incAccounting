package net.internetworkconsulting.template;

public class HtmlSyntax implements SyntaxInterface {
    public String getVariable() { return "\\{\\s*NAME\\s*\\}"; }
    public String[] getVariableReplace() { return new String[] { "{", "}" }; }
    public String getOpenBlock() { return "\\<\\!\\-\\-\\s*BEGIN\\s*NAME\\s*\\-\\-\\>"; }
    public String[] getOpenBlockReplace() { return new String[] { "<!--", "BEGIN", "-->" }; }
    public String getCloseBlock() { return "\\<\\!\\-\\-\\s*END\\s*NAME\\s*\\-\\-\\>"; }
}
