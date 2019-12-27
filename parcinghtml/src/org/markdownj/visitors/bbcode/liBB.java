package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class liBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "\t[li]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/li]\n";
    }

    @Override
    public String getTag() {
        return "li";
    }
}
