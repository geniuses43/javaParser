package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class OlBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[ol]\n";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/ol]";
    }

    @Override
    public String getTag() {
        return "ol";
    }
}
