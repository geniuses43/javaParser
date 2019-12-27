package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class LineBreakBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "\n";
    }

    @Override
    public String tail(Node node, int i) {
        return "";
    }

    @Override
    public String getTag() {
        return "br";
    }
}
