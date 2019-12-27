package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class UlBB  implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[ul]\n";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/ul]";
    }

    @Override
    public String getTag() {
        return "ul";
    }
}
