package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class BlockquoteBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[quote]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/quote]";
    }

    @Override
    public String getTag() {
        return "blockquote";
    }
}
