package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class LinkBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[URL=]" +
                node.attr("href") +
                "]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/URL]";
    }

    @Override
    public String getTag() {
        return "a";
    }
}
