/*
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
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
