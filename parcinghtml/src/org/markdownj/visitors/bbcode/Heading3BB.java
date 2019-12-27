package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class Heading3BB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[size=23]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/size]";
    }

    @Override
    public String getTag() {
        return "h3";
    }
}
