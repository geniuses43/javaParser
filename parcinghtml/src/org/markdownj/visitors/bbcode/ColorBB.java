package org.markdownj.visitors.bbcode;

import org.jsoup.nodes.Node;
import org.markdownj.visitors.Visitor;

public class ColorBB implements Visitor {
    @Override
    public String head(Node node, int i) {
        return "[color=" + node.attr("color") + "]";
    }

    @Override
    public String tail(Node node, int i) {
        return "[/color]";
    }

    @Override
    public String getTag() {
        return "color";
    }
}
