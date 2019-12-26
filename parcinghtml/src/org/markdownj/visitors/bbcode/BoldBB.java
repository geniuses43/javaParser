package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class BoldBB implements Visitor {
    @Override
    public String head(Node var1, int var2) {
        return "[b]";
    }

    @Override
    public String tail(Node var1, int var2) {
        return "[/b]";
    }

    @Override
    public String getTag() {
        return "b";
    }
}
