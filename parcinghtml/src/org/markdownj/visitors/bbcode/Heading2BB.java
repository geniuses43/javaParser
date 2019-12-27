package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class Heading2BB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[size=25]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/size]";
    }

    @Override
    public String getTag() {
        return "h2";
    }
}
