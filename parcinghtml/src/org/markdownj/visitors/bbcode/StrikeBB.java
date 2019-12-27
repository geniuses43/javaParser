package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class StrikeBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[s]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/s]";
    }

    @Override
    public String getTag() {
        return "s";
    }
}
